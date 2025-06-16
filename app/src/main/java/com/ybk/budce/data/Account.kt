package com.ybk.budce.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class Account(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val initialBalance: Double,
    val type: AccountType // HESAP TİPİ EKLENDİ
)