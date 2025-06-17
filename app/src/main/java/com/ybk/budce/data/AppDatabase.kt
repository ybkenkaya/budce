// ============================================================================
// AppDatabase.kt  – v3
//  * New default Accounts: Cash Wallet, Bank 1/2, Credit Card 1/2
//  * New default Categories with explicit type (INCOME | EXPENSE)
//  * Bumped schema version -> 2 (destructive migration for now)
//  * Requires:
//      - enum class CategoryType { INCOME, EXPENSE }
//      - Category.kt to add `val type: CategoryType`
//      - Converters.kt add converters for CategoryType
// ============================================================================
package com.ybk.budce.data

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ybk.budce.data.dao.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [Transaction::class, Account::class, Category::class],
    version = 2,            // ↑ bump: new column `type` in Category
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun transactionDao(): TransactionDao
    abstract fun accountDao(): AccountDao
    abstract fun categoryDao(): CategoryDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        // --------------------------------------------------------------------
        //  Seed data – inserted **once** when DB is first created.
        // --------------------------------------------------------------------
        private val seedAccounts = listOf(
            Account(name = "Cash Wallet",      initialBalance = 0.0, type = AccountType.CASH),
            Account(name = "Bank Account 1",  initialBalance = 0.0, type = AccountType.BANK_ACCOUNT),
            Account(name = "Bank Account 2",  initialBalance = 0.0, type = AccountType.BANK_ACCOUNT),
            Account(name = "Credit Card 1",   initialBalance = 0.0, type = AccountType.CREDIT_CARD),
            Account(name = "Credit Card 2",   initialBalance = 0.0, type = AccountType.CREDIT_CARD)
        )

        private val seedCategories = listOf(
            // EXPENSE buckets
            Category(name = "Food",          type = CategoryType.EXPENSE),
            Category(name = "Rent",          type = CategoryType.EXPENSE),
            Category(name = "Transport",     type = CategoryType.EXPENSE),
            Category(name = "Shopping",      type = CategoryType.EXPENSE),
            Category(name = "Utilities",     type = CategoryType.EXPENSE),
            Category(name = "Health",        type = CategoryType.EXPENSE),
            Category(name = "Entertainment", type = CategoryType.EXPENSE),
            Category(name = "Other",         type = CategoryType.EXPENSE),
            // INCOME buckets
            Category(name = "Salary",        type = CategoryType.INCOME),
            Category(name = "Bonus",         type = CategoryType.INCOME),
            Category(name = "Investment",    type = CategoryType.INCOME)
        )

        fun getDatabase(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context): AppDatabase =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "budce_database"
            )
                // Simple destructive migration for now – upgrade path later
                .fallbackToDestructiveMigration()
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        CoroutineScope(Dispatchers.IO).launch {
                            val dao = getDatabase(context)
                            seedAccounts.forEach { dao.accountDao().insertAccount(it) }
                            seedCategories.forEach { dao.categoryDao().insertCategory(it) }
                        }
                    }
                })
                .build()
    }
}
