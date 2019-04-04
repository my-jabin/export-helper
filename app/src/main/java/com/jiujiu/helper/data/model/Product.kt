package com.jiujiu.helper.data.model

import androidx.room.*
import java.text.SimpleDateFormat
import java.util.*

@Entity(foreignKeys = arrayOf(
        ForeignKey(entity = ProductType::class, parentColumns = arrayOf("id"), childColumns = arrayOf("type_id")
)))
data class Product(

        var name: String,

        var brand: String

) {

    @PrimaryKey(autoGenerate = true)
    var id: Long? = null

    var nameZh: String? = null

    var model: String? = null

    var modelZh: String? = null

    @ColumnInfo(name = "type_id")
    var typeId: Int? = null

    var description: String? = null

    var netWeight: String? = null

    var salePrice: Double? = null

    var saleCurrency: Currency? = Currency.getInstance("CNY")

    var purchasePrice: Double? = null

    var purchaseCurrency: Currency? = Currency.getInstance(Locale.getDefault())

    var origin: String? = null

    var purchaseApproach: String? = null

    @ColumnInfo(name = "created_at")
    var createdAt: Calendar? = Calendar.getInstance(Locale.getDefault())

    @ColumnInfo(name = "updated_at")
    var updateAt: Calendar? = Calendar.getInstance(Locale.getDefault())

    @Ignore
    var createdAtDataString: String = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(createdAt?.time)
}


