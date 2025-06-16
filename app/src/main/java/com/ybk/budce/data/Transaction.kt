package com.ybk.budce.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// Bu enum, işlem tiplerini sabit ve güvenli bir şekilde yönetmemizi sağlar.
enum class TransactionType {
    INCOME, // Gelir
    EXPENSE, // Gider
    TRANSFER // Transfer
}

@Entity(tableName = "transactions") // Veritabanı tablo adı
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String,
    val amount: Double,
    val date: Long, // Tarihi milisaniye olarak saklamak en verimli yoldur
    val type: TransactionType,

    val accountId: Int, // Bu işlem hangi hesaptan yapıldı?
    val categoryId: Int, // Bu işlem hangi kategoriye ait?

    // Sadece transfer işlemleri için kullanılacak. Gelir ve giderde null olacak.
    val destinationAccountId: Int? = null,

    val note: String? = null // Not eklemek opsiyonel
)
