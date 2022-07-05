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
package com.comcast.vrex.sdk.config

import com.comcast.vrex.sdk.message.send.Id
import com.comcast.vrex.sdk.message.send.IdType
import com.comcast.vrex.sdk.message.send.Values
import kotlinx.serialization.Serializable

@Serializable
data class SpeechConfig(
    val speechUrl: String,
    val appId: String,
    val deviceId: String,
    val accountId: String,
    val customerId: String? = null,
) {
    val id: Id = Id()

    init {
        id.values.add(Values(IdType.DEVICE_ID, deviceId))
        id.values.add(Values(IdType.ACCOUNT_ID, accountId))
        if (customerId != null) id.values.add(Values(IdType.CUSTOMER_ID, customerId))
    }
}