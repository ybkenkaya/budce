
package com.ybk.budce.data.repository

import com.ybk.budce.data.Transaction
import com.ybk.budce.data.dao.TransactionDao
import kotlinx.coroutines.flow.Flow

class AppRepository(private val transactionDao: TransactionDao) {

    // TransactionDao'daki fonksiyonu doğrudan çağırıyoruz.
    // İleride buraya başka veri kaynaklarından (örn: internetten) gelen veriler de eklenebilir.
    fun getAllTransactions(): Flow<List<Transaction>> {
        return transactionDao.getAllTransactions()
    }

    suspend fun insertTransaction(transaction: Transaction) {
        transactionDao.insertTransaction(transaction)
    }
}
