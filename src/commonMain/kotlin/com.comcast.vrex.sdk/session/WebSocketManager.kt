/*
 * Copyright 2021 Comcast Cable Communications Management, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.comcast.vrex.sdk.session

import com.comcast.vrex.sdk.auth.SatAuthenticator
import com.comcast.vrex.sdk.message.common.EventMessage
import com.comcast.vrex.sdk.message.common.MessageType
import com.comcast.vrex.sdk.message.receive.CloseConnection
import com.comcast.vrex.sdk.message.receive.VrexResponse
import com.comcast.vrex.sdk.message.receive.WbwResponse
import com.comcast.vrex.sdk.message.receive.WuwVerificationMessage
import com.comcast.vrex.sdk.message.send.EndOfStream
import com.comcast.vrex.sdk.message.send.InitMessage

import com.comcast.vrex.sdk.util.jsonEncodeDefaults
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.encodeToJsonElement

abstract class WebSocketManager(
    protected val session: SpeechSession,
    protected val satAuthenticator: SatAuthenticator,
) {

    protected val wsUrlPath: String = "?trx=${session.trx}&id=${session.speechConfig.appId}"

    abstract fun connect()

    protected open fun handleIncomingMessage(newMsg: String) {
        val newEventMessage = Json.decodeFromString<EventMessage>(newMsg)
        when (MessageType.reverseLookup(newEventMessage.msgType)) {
            MessageType.LISTENING -> handleListening(newEventMessage)
            MessageType.TRANSCRIPTION -> handleTranscription(newEventMessage)
            MessageType.VREX_RESPONSE -> handleVrexResponse(newEventMessage)
            MessageType.WUW_VERIFICATION -> handleWuwVerification(newEventMessage)
            MessageType.CLOSE_CONNECTION -> handleCloseConnection(newEventMessage)
            else -> println("unhandled message $newEventMessage")
        }
    }

    protected open fun handleOnConnect() {
        session.speechResultObserver?.onConnect()
    }

    protected open fun handleListening(eventMessage: EventMessage) {
        session.speechResultObserver?.onListening()
    }

    protected open fun handleTranscription(eventMessage: EventMessage) {
        val messagePayload: JsonElement = eventMessage.msgPayload ?: return
        val response: WbwResponse = Json.decodeFromJsonElement(messagePayload)
        if (response.isFinal) {
            session.speechResultObserver?.onFinalTranscriptionReceived(response.text)
        } else {
            session.speechResultObserver?.onPartialTranscriptionReceived(response.text)
        }
    }

    protected open fun handleVrexResponse(eventMessage: EventMessage) {
        val messagePayload: JsonElement = eventMessage.msgPayload ?: return
        val response: VrexResponse = Json.decodeFromJsonElement(messagePayload)

        if (response.returnCode == 0) {
            session.speechResultObserver?.onFinalResponseSuccess(response)
        } else {
            session.speechResultObserver?.onFinalResponseFailure(response)
        }
    }

    protected open fun handleWuwVerification(eventMessage: EventMessage) {
        val messagePayload: JsonElement = eventMessage.msgPayload ?: return
        val response: WuwVerificationMessage = Json.decodeFromJsonElement(messagePayload)
        if (response.passed) {
            session.speechResultObserver?.onWakeUpWordVerificationSuccess(response.confidence)
        } else {
            session.speechResultObserver?.onWakeUpWordVerificationFailure(response.confidence)
        }
    }

    protected open fun handleCloseConnection(eventMessage: EventMessage) {
        val messagePayload: JsonElement = eventMessage.msgPayload ?: return
        val response: CloseConnection = Json.decodeFromJsonElement(messagePayload)
        session.speechResultObserver?.onCloseConnection(response)
    }

    protected fun getInitMessage(): String {
        val initMessage = InitMessage(listOf(session.initPayload))
        val eventMessage = EventMessage(MessageType.INIT.value, session.trx, null)
        eventMessage.msgPayload = jsonEncodeDefaults.encodeToJsonElement(initMessage)
        return Json.encodeToString(eventMessage)
    }

    protected fun getEndOfAudio(): String {
        val endOfStreamMessage: EventMessage = createEndOfStreamMessage(session.trx, 0)
        return Json.encodeToString(endOfStreamMessage)
    }

    protected fun createEndOfStreamMessage(trx: String, reasonCode: Int): EventMessage {
        val endOfStream = EndOfStream(reasonCode)
        val jsonPayload: JsonElement = Json.encodeToJsonElement(endOfStream)
        return EventMessage(MessageType.END_OF_STREAM.value, trx, jsonPayload)
    }
}