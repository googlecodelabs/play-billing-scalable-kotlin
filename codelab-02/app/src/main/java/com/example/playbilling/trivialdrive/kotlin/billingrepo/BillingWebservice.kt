package com.example.playbilling.trivialdrive.kotlin.billingrepo

import com.android.billingclient.api.Purchase

/**
 * Copyright (C) 2019 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

class BillingWebservice {
    fun getPurchases(): Any {
        return Any()//TODO("not implemented")
    }
    fun updateServer(purchases: Set<Purchase>) {
        //TODO("not implemented")
    }
    fun onComsumeResponse(purchaseToken: String?, responseCode: Int) {
        //TODO("not implemented")
    }
    companion object {
        fun create(): BillingWebservice {
            //TODO("not implemented")
            return BillingWebservice()
        }
    }
}
