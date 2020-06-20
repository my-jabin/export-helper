package com.jiujiu.helper.data.model

import com.google.firebase.auth.FirebaseAuth
data class Customer(

        var id: String? = null,
        var name: String? = null,
        var IDNumber: String? = null,
        var street: String? = null,
        var district: String? = null,
        var city: String? = null,
        var province: String? = null,
        var country: String? = "中国",
        var phoneNumber: String? = null,
        var email: String? = null,
        var userUID: String? = FirebaseAuth.getInstance().uid
) {
    val address: String
        get() = "$country$province$city$district$street"

    fun validAddress(): Boolean = province != null && city != null && district != null && street != null
}



