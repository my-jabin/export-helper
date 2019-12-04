package com.jiujiu.helper.data.model

import androidx.room.*
import java.text.SimpleDateFormat
import java.util.*

@Entity(foreignKeys = arrayOf(
        ForeignKey(entity = ProductType::class, parentColumns = arrayOf("id"), childColumns = arrayOf("type_id")
        )))
data class Product(

        var name: String? = null,

        var brand: String? = null,

        var brandZh: String? = null,

        var nameZh: String? = null,

        var model: String? = null,

        var modelZh: String? = null,

        @ColumnInfo(name = "type_id")
        var typeId: Int? = null,

        var description: String? = null,

        var netWeight: Double? = null,

        var salePrice: Double? = null,

        var saleCurrency: Currency? = Currency.getInstance("CNY"),

        var purchasePrice: Double? = null,

        var purchaseCurrency: Currency? = Currency.getInstance(Locale.getDefault()),

        var madeIn: String? = "Germany",

        var purchaseApproach: String? = null

) {
    @ColumnInfo(name = "created_at")
    var createdAt: Calendar? = Calendar.getInstance(Locale.getDefault())

    @ColumnInfo(name = "updated_at")
    var updateAt: Calendar? = Calendar.getInstance(Locale.getDefault())

    @PrimaryKey(autoGenerate = true)
    var id: Long? = null

    @Ignore
    var createdAtDataString: String = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(createdAt?.time)

    fun transform(locale: Locale): ProductVO {
        return ProductVO(this, locale)
    }

}


