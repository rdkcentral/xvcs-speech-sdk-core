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
package com.comcast.vrex.sdk.message.send

import com.comcast.vrex.sdk.util.SpeechConstants.THIS_WEB_SOCKET
import kotlinx.serialization.Serializable

@Serializable
data class InitPayload(
    var aspectRatio: String? = null,
    var audio: Audio? = null,
    var bouquet: String? = null,
    var capabilities: Set<Capability> = emptySet<Capability>(),
    var clientProfile: String? = null,
    var deviceSwVersion: String? = null,
    var dictationMode: Boolean = false,
    var id: Id = Id(),
    var language: String? = null,
    var mac: String? = null,
    var name: String? = null,
    //TODO @NotEmpty
    var roles: Set<Roles> = setOf(),
    var stbStatus: String? = null,
    var stbSwVersion: String? = null,
    var subBouquet: String? = null,
    var text: String? = null,
    var suppressCallRecording: Boolean = false,
    var timeZone: String? = null,
    var tvStatus: String? = null,
    var vrexFields: HashSet<VrexField> = HashSet(),
    var vrexModes: HashSet<VrexMode> = HashSet(),
    val downstreamProtocol: String = THIS_WEB_SOCKET,
    val transmissionProtocol: String = THIS_WEB_SOCKET,
    val experience: String = "X1"
) {
}