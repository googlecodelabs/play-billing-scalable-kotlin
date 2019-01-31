/**
 * Copyright (C) 2018 Google Inc. All Rights Reserved.
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
package com.example.playbilling.trivialdrive.kotlin.billingrepo.localdb

import androidx.lifecycle.LiveData
import androidx.room.*
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.SkuDetails

@Dao
interface AugmentedSkuDetailsDao {

    @Query("SELECT * FROM AugmentedSkuDetails WHERE type = '${BillingClient.SkuType.SUBS}'")
    fun getSubscriptionSkuDetails(): LiveData<List<AugmentedSkuDetails>>

    @Query("SELECT * FROM AugmentedSkuDetails WHERE type = '${BillingClient.SkuType.INAPP}'")
    fun getInappSkuDetails(): LiveData<List<AugmentedSkuDetails>>

    @Transaction
    fun insertOrUpdate(skuDetails: SkuDetails) = skuDetails.apply {
        val result = getById(sku)
        val bool = if (result == null) true else result.canPurchase
        val originalJson = toString().substring("SkuDetails: ".length)
        val skuDetails = AugmentedSkuDetails(bool, sku, type, price, title, description, originalJson)
        insert(skuDetails)
    }

    @Transaction
    fun insertOrUpdate(sku: String, canPurchase: Boolean) {
        val result = getById(sku)
        if (result != null) {
            update(sku, canPurchase)
        } else {
            insert(AugmentedSkuDetails(canPurchase, sku, null, null, null, null, null))
        }
    }

    @Query("SELECT * FROM AugmentedSkuDetails WHERE sku = :sku")
    fun getById(sku: String): AugmentedSkuDetails

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(augmentedSkuDetails: AugmentedSkuDetails)

    @Query("UPDATE AugmentedSkuDetails SET canPurchase = :canPurchase WHERE sku = :sku")
    fun update(sku: String, canPurchase: Boolean)
}