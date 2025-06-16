package com.ybk.budce

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ybk.budce.data.AppDatabase
import com.ybk.budce.data.Transaction
import com.ybk.budce.data.TransactionType
import com.ybk.budce.data.repository.AppRepository
import com.ybk.budce.ui.theme.BudceTheme
import com.ybk.budce.ui.viewmodel.MainViewModel
import com.ybk.budce.ui.viewmodel.MainViewModelFactory
import java.text.NumberFormat
import java.util.Locale

class MainActivity : ComponentActivity() {

    private val database by lazy { AppDatabase.getDatabase(this) }
    // Hatanın olduğu satır düzeltildi. DAO'nun doğru paketten import edilmesi sağlandı.
    private val repository by lazy { AppRepository(database.transactionDao()) }
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BudceTheme {
                val transactions by viewModel.allTransactions.collectAsState()

                MainScreen(
                    transactions = transactions,
                    onAddClick = {
                        val testTransaction = Transaction(
                            name = "Market Shopping", // Test verisi de İngilizce oldu
                            amount = (50..200).random().toDouble(),
                            date = System.currentTimeMillis(),
                            type = TransactionType.EXPENSE,
                            accountId = 1,
                            categoryId = 1
                        )
                        viewModel.insertTransaction(testTransaction)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    transactions: List<Transaction>,
    onAddClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                // Metin artık `strings.xml` dosyasından okunuyor.
                title = { Text(stringResource(id = R.string.transactions_title)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.add_new_transaction)
                )
            }
        }
    ) { innerPadding ->
        if (transactions.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(text = stringResource(id = R.string.no_transactions_yet))
            }
        } else {
            LazyColumn(modifier = Modifier.padding(innerPadding)) {
                items(transactions) { transaction ->
                    // Para formatını locali de dikkate alarak oluştur
                    val formattedAmount = NumberFormat.getCurrencyInstance(Locale("tr", "TR"))
                        .format(transaction.amount)

                    ListItem(
                        headlineContent = { Text(transaction.name) },
                        supportingContent = {
                            Text(
                                stringResource(
                                    id = R.string.amount_label,
                                    formattedAmount
                                )
                            )
                        }
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BudceTheme {
        MainScreen(transactions = emptyList(), onAddClick = {})
    }
}
