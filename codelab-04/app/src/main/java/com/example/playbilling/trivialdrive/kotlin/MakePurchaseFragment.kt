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


import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.playbilling.trivialdrive.kotlin.adapters.InventoryAdapter
import com.example.playbilling.trivialdrive.kotlin.billingrepo.localdb.AugmentedSkuDetails
import com.example.playbilling.trivialdrive.kotlin.viewmodels.BillingViewModel
import kotlinx.android.synthetic.main.fragment_make_purchase.view.*

/**
 * This Fragment is simply a wrapper for the inventory (i.e. items for sale). It contains two
 * [lists][RecyclerView], one for subscriptions and one for in-app products. Here again there is
 * no complicated billing logic. All the billing logic reside inside the [BillingRepository].
 * The [BillingRepository] provides a [AugmentedSkuDetails] object that shows what
 * is for sale and whether the user is allowed to buy the item at this moment. E.g. if the user
 * already has a full tank of gas, then they cannot buy gas at this moment.
 */
class MakePurchaseFragment : Fragment() {

    val LOG_TAG = "MakePurchaseFragment"
    private lateinit var billingViewModel: BillingViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_make_purchase, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(LOG_TAG, "onViewCreated")

        val inappAdapter = object : InventoryAdapter() {
            override fun onSkuDetailsClicked(item: AugmentedSkuDetails) {
                onPurchase(view, item)
            }
        }

        val subsAdapter = object : InventoryAdapter() {
            override fun onSkuDetailsClicked(item: AugmentedSkuDetails) {
                onPurchase(view, item)
            }
        }
        attachAdapterToRecyclerView(view.inapp_inventory, inappAdapter)
        attachAdapterToRecyclerView(view.subs_inventory, subsAdapter)

        billingViewModel = ViewModelProviders.of(this).get(BillingViewModel::class.java)
        billingViewModel.inappSkuDetailsListLiveData.observe(this, Observer {
            it?.let { inappAdapter.setSkuDetailsList(it) }
        })
        billingViewModel.subsSkuDetailsListLiveData.observe(this, Observer {
            it?.let { subsAdapter.setSkuDetailsList(it) }
        })

    }

    private fun attachAdapterToRecyclerView(recyclerView: RecyclerView, skuAdapter: InventoryAdapter) {
        with(recyclerView) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = skuAdapter
        }
    }

    private fun onPurchase(view: View, item: AugmentedSkuDetails) {
        billingViewModel.makePurchase(activity as Activity, item)
        view.findNavController().navigate(R.id.action_playGame)
        Log.d(LOG_TAG, "starting purchase flow for SkuDetail:\n ${item}")
    }
}
