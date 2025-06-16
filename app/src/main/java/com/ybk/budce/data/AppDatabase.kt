
// =================================================================================
package com.ybk.budce.data

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.ybk.budce.data.dao.*

@Database(
    entities = [Transaction::class, Account::class, Category::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun accountDao(): AccountDao
    abstract fun categoryDao(): CategoryDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "budce_database"
                )
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // seed data with coroutine
                            val scope = CoroutineScope(Dispatchers.IO)
                            scope.launch {
                                getDatabase(context).apply {
                                    accountDao().insertAccount(Account(name = "Nakit", initialBalance = 0.0, type = AccountType.CASH))
                                    accountDao().insertAccount(Account(name = "Banka", initialBalance = 0.0, type = AccountType.BANK_ACCOUNT))
                                    categoryDao().insertCategory(Category(name = "Gıda"))
                                    categoryDao().insertCategory(Category(name = "Kira"))
                                }
                            }
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
