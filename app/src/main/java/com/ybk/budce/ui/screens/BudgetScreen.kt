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
import com.ybk.budce.data.AppDatabase
import com.ybk.budce.data.Category

@Composable
fun BudgetScreen(padding: PaddingValues) {
    val context = LocalContext.current
    val categoryDao = remember { AppDatabase.getDatabase(context).categoryDao() }
    val categoriesFlow = remember { categoryDao.getAllCategories() }

    val categories by categoriesFlow.collectAsState(initial = emptyList())

    LazyColumn(modifier = Modifier.padding(padding)) {
        items(categories) { category ->
            CategoryRow(category)
        }
    }
}

@Composable
private fun CategoryRow(category: Category) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Text(
            text = category.name,
            modifier = Modifier.padding(16.dp),
            fontWeight = FontWeight.SemiBold
        )
        // Eğer “budgetAmount” gösterilsin isterseniz:
        // category.budgetAmount?.let {
        //  Text("Limit: ₺$it", modifier = Modifier.padding(start = 16.dp, bottom = 16.dp))
        // }
    }
}
