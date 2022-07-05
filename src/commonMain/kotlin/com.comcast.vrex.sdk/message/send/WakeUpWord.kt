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

import kotlinx.serialization.Serializable

@Serializable
data class WakeUpWord(
    val sowuw: Int,
    val eowuw: Int,
    val unit: Unit?,
    val detector: Detector?
) {

    private fun getByMs(time: Int): Int {
        return if (null == unit || Unit.SAMPLE == unit) {
            getTimeByMs(time)
        } else time
    }

    fun getSowuwByMs(): Int {
        return getByMs(sowuw)
    }

    fun getEowuwByMs(): Int {
        return getByMs(eowuw)
    }

    private fun getTimeByMs(sampleTime: Int): Int {
        return sampleTime ushr 4 // Our audio rate is 16K.
    }
}