
package com.ybk.budce.data.dao

import androidx.room.*
import com.ybk.budce.data.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: Transaction)

    @Query("SELECT * FROM transactions ORDER BY date DESC")
    fun getAllTransactions(): Flow<List<Transaction>> // Flow, veri değiştiğinde UI'ı otomatik günceller
}
