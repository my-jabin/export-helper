package com.jiujiu.helper.data.model

//@Entity(tableName = "orders",
//        foreignKeys = arrayOf(ForeignKey(
//        entity = Customer::class,
//        parentColumns = arrayOf("id"),
//        childColumns = arrayOf("customerId")
//)))
data class Order(val customerId: Long, val product: Product, var amount: Int = 1) {

//    var status: OrderStatus = OrderStatus.NOTPURCHASED
//
//    val orderDate: LocalDate? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//        LocalDate.now()
//    } else {
//        null
//    }
//
//    @PrimaryKey(autoGenerate = true)
//    var id: Long? = null
//
//    @ColumnInfo(name = "created_at")
//    var createdAt: Calendar? = Calendar.getInstance(Locale.getDefault())
//
//    @ColumnInfo(name = "updated_at")
//    var updateAt: Calendar? = Calendar.getInstance(Locale.getDefault())
//
//    enum class OrderStatus(val status: Int) {
//        PURCHASED(1),
//        NOTPURCHASED(2)
//    }
}

