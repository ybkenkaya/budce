// ui/screen/CategoriesScreen.kt  (Budget sekmesinin yerine geçecek basit liste)
package com.ybk.budce.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ybk.budce.data.Category
import com.ybk.budce.ui.viewmodel.CategoriesViewModel

@Composable
fun CategoriesScreen(
    vm: CategoriesViewModel = viewModel()
) {
    val categories by vm.categories.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { /* TODO – yeni kategori formu */ }) {
                Icon(Icons.Default.Category, null)
            }
        }
    ) { padding ->
        LazyColumn(Modifier.padding(padding)) {
            items(categories) { cat -> CategoryRow(cat) }
        }
    }
}

@Composable
private fun CategoryRow(cat: Category) {
    ListItem(headlineContent = { Text(cat.name) })
    HorizontalDivider()
}
