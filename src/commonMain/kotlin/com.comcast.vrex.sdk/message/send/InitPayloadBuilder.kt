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

import com.comcast.vrex.sdk.message.common.LanguageType
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*

val json2 = Json {
    coerceInputValues = true

}

class InitPayloadBuilder {
    private var initPayload: InitPayload
    private val executeResponse: String = "executeResponse"

    constructor() {
        initPayload = InitPayload()
    }

    constructor(jsonElement: JsonElement) {
        initPayload = json2.decodeFromJsonElement(jsonElement)
    }

    constructor(initPayload: InitPayload, shouldCopy: Boolean) {
        if (shouldCopy) {
            this.initPayload = Json.decodeFromString(Json.encodeToString(initPayload))
        } else {
            this.initPayload = initPayload
        }
    }

    fun withRoles(vararg roles: Roles): InitPayloadBuilder {
        initPayload.roles = roles.toSet()
        return this
    }

    fun withAddedRole(roles: Roles): InitPayloadBuilder {
        initPayload.roles = initPayload.roles.plus(roles)
        return this
    }

    fun withCapabilities(vararg capabilities: Capability): InitPayloadBuilder {
        initPayload.capabilities = capabilities.toSet()
        return this
    }

    fun withAddedCapability(capability: Capability): InitPayloadBuilder {
        initPayload.capabilities = initPayload.capabilities.plus(capability)
        return this
    }

    fun withRemovedCapability(capability: Capability): InitPayloadBuilder {
        initPayload.capabilities = initPayload.capabilities.minus(capability)
        return this
    }

    fun withLanguage(language: LanguageType): InitPayloadBuilder {
        initPayload.language = language.languageCode
        return this
    }

    fun withAudio(audio: Audio): InitPayloadBuilder {
        initPayload.audio = audio
        return this
    }

    fun withText(text: String): InitPayloadBuilder {
        initPayload.text = text
        return this
    }

    fun withDictationModeEnabled(dictationModeEnabled: Boolean): InitPayloadBuilder {
        initPayload.dictationMode = dictationModeEnabled
        return this
    }

    fun withVrexModes(vararg vrexModes: VrexMode): InitPayloadBuilder {
        initPayload.vrexModes.addAll(vrexModes)
        return this
    }

    fun withExecuteResponse(): InitPayloadBuilder {
        initPayload.vrexFields.add(VrexField.EXECUTE_RESPONSE)
        return this
    }

    fun withTimeZone(timeZone: String): InitPayloadBuilder {
        initPayload.timeZone = timeZone
        return this
    }

    fun buildMessage(): InitPayload {
        if (initPayload.text != null) initPayload.audio = null
        return initPayload
    }
}

fun copyFromExistingInitPayload(initPayload: InitPayload): InitPayloadBuilder {
    return InitPayloadBuilder(initPayload, true)
}

fun fromEmptyInitPayload(): InitPayloadBuilder {
    return InitPayloadBuilder()
}

fun customizeExistingInitPayload(initPayload: InitPayload): InitPayloadBuilder {
    return InitPayloadBuilder(initPayload, false)
}

fun fromInitJson(jsonElement: JsonElement): InitPayloadBuilder {
    return InitPayloadBuilder(jsonElement)
}