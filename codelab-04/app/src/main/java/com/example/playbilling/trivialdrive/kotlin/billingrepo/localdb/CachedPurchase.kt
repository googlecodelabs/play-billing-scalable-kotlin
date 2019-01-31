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

import androidx.room.*
import com.android.billingclient.api.Purchase

@Entity(tableName = "purchase_table")
@TypeConverters(PurchaseTypeConverter::class)
class CachedPurchase(val data: Purchase) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @Ignore
    val purchaseToken = data.purchaseToken
    @Ignore
    val sku = data.sku

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is CachedPurchase -> data.equals(other.data)
            is Purchase -> data.equals(other)
            else -> false
        }
    }

    override fun hashCode(): Int {
        return data.hashCode()
    }

}

class PurchaseTypeConverter {
    @TypeConverter
    fun toString(purchase: Purchase): String = purchase.originalJson + '|' + purchase.signature

    @TypeConverter
    fun toPurchase(data: String): Purchase = data.split('|').let {
        Purchase(it[0], it[1])
    }
}