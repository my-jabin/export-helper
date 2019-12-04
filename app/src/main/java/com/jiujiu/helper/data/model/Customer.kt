package com.jiujiu.helper.data.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Customer(

        var name: String? = null,

        @ColumnInfo(name = "id_number")
        var IDNumber: String? = null,

        @Embedded
        var address: Address? = null,

        @ColumnInfo(name = "phone_number")
        var phoneNumber: String? = null,

        var email: String? = null

) {
    @ColumnInfo(name = "created_at")
    var createdAt: Calendar? = Calendar.getInstance(Locale.getDefault())

    @ColumnInfo(name = "updated_at")
    var updateAt: Calendar? = Calendar.getInstance(Locale.getDefault())

    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
}


