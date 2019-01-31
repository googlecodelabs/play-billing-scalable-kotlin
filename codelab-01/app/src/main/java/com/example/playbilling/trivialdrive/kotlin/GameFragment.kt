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
package com.example.playbilling.trivialdrive.kotlin


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_game.*

/**
 * This Fragment represents the game world. Hence it contains logic to display the items the user
 * owns -- gas, premium car, and gold status--,and logic for what it means to drive
 * the car; this is a driving game after all. When the user wants to buy, the app navigates to a
 * different Fragment.
 *
 * As you can see there is really nothing about Billing here. That's on purpose, all the billing
 * code reside in the [repo][BillingRepository] layer and below.
 */
class GameFragment : androidx.fragment.app.Fragment() {
    private val LOG_TAG = "GameFragment"

    override fun onCreateView(inflater: LayoutInflater, containter: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game, containter, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_drive.setOnClickListener { onDrive() }
        btn_purchase.setOnClickListener { onPurchase(it) }
    }

    private fun onDrive() {
        showGasLevel()
        Toast.makeText(context, getString(R.string.alert_no_gas), Toast.LENGTH_LONG).show()
    }

    private fun onPurchase(view: View) {
        view.findNavController().navigate(R.id.action_makePurchase)
    }

    private fun showGasLevel() {
        gas_gauge.setImageResource(R.drawable.gas_level_0)
    }

    private fun showPremiumCar(entitled: Boolean) {
        if (entitled) {
            free_or_premium_car.setImageResource(R.drawable.premium_car)
        } else {
            free_or_premium_car.setImageResource(R.drawable.free_car)
        }
    }

    private fun showGoldStatus(entitled: Boolean) {
        if (entitled) {
            gold_status.setBackgroundResource(R.drawable.gold_status)
        } else {
            gold_status.setBackgroundResource(0)
        }
    }

}
