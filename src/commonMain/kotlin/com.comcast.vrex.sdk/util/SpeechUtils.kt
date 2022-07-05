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
package com.comcast.vrex.sdk.util

import com.benasher44.uuid.uuid4
import com.comcast.vrex.sdk.message.common.EventMessage
import com.comcast.vrex.sdk.message.common.MessageType
import com.comcast.vrex.sdk.message.send.Audio
import com.comcast.vrex.sdk.message.send.Codec
import com.comcast.vrex.sdk.message.send.TriggeredBy
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject

fun generateTrx(): String {
    return uuid4().toString()
}

fun createContextMessage(trx: String, jsonPayload: JsonObject?): EventMessage {
    return EventMessage(MessageType.CONTEXT.value, trx, jsonPayload)
}

object LogTemplates {
    const val LOG_TEMPLATE = "trx={} | msg={}"
    const val LOG_SESSION_TEMPLATE = "trx={} | msg={}"
    const val LOG_JSON_TEMPLATE = "trx={} | msg={} | json={}"
}

object SpeechConstants {
    const val THIS_WEB_SOCKET = "thisWebSocket"
    const val STANDARD_BUFFER_SIZE: Long = 2048
}

val jsonEncodeDefaults = Json {
    encodeDefaults = true
}

val jsonIgnoreUnknown = Json {
    ignoreUnknownKeys = true
}

fun audioFromDefaultPttConfig(): Audio {
    return Audio(
        audioProfile = "XR11",
        envoyCodec = Codec.PCM_16_16K,
        triggeredBy = TriggeredBy.PTT
    )
}