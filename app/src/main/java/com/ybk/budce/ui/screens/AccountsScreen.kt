package com.ybk.budce.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ybk.budce.data.Account
import com.ybk.budce.data.AppDatabase

@Composable
fun AccountsScreen(padding: PaddingValues) {
    val context = LocalContext.current
    // DAO ve Flow’u hatırlamak; Compose her recomposition’da yeniden yaratmasın
    val accountDao = remember { AppDatabase.getDatabase(context).accountDao() }
    val accountsFlow = remember { accountDao.getAllAccounts() }

    val accounts by accountsFlow.collectAsState(initial = emptyList())

    LazyColumn(modifier = Modifier.padding(padding)) {
        items(accounts) { account ->
            AccountRow(account)
        }
    }
}

@Composable
private fun AccountRow(account: Account) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Text(
            text = account.name,
            modifier = Modifier.padding(16.dp),
            fontWeight = FontWeight.SemiBold
        )
        // İsterseniz bakiyeyi de gösterin:
        Text("₺${account.initialBalance}", modifier = Modifier.padding(start = 16.dp, bottom = 16.dp))
    }
}