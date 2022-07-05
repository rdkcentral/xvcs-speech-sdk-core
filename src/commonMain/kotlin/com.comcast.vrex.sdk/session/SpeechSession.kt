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

import com.comcast.vrex.sdk.config.SpeechConfig
import com.comcast.vrex.sdk.message.send.InitPayloadBuilder
import com.comcast.vrex.sdk.message.send.customizeExistingInitPayload
import com.comcast.vrex.sdk.message.send.Capability
import com.comcast.vrex.sdk.message.send.InitPayload

class SpeechSession(
    val trx: String,
    val speechConfig: SpeechConfig,
    var initPayload: InitPayload,
    val speechResultObserver: SpeechResultObserver? = null,
    val sendContext: Boolean = false,
    val textTranscription: String? = null,
) {
    private var initPayloadBuilder: InitPayloadBuilder = customizeExistingInitPayload(initPayload)

    init {
        if (sendContext) {
            initPayloadBuilder.withAddedCapability(Capability.CONTEXT)
        } else {
            initPayloadBuilder.withRemovedCapability(Capability.CONTEXT)
        }

        if (textTranscription != null) initPayloadBuilder.withText(textTranscription!!)
        initPayload = initPayloadBuilder.buildMessage()
        initPayload.id = speechConfig.id
    }
}