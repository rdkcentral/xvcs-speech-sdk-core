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
package com.comcast.vrex.sdk.message.common

enum class MessageType(val value: String) {
    //incoming
    TRANSCRIPTION("asr"),
    LISTENING("listening"),
    WUW_VERIFICATION("wuwVerification"),
    VREX_RESPONSE("vrexResponse"),
    CLOSE_CONNECTION("closeConnection"),

    //other
    INIT("init"),
    ARS("ars"),
    CONTEXT("context"),
    CONTEXT_READY("context_ready"),
    END_OF_STREAM("endOfStream");

    override fun toString(): String {
        return value
    }

    companion object {
        private val MSG_MAP: MutableMap<String, MessageType> = HashMap()

        fun reverseLookup(value: String): MessageType? {
            return MSG_MAP[value]
        }

        init {
            for (msg in MessageType.values()) {
                MSG_MAP[msg.value] = msg
            }
        }
    }
}