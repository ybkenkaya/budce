// ui/viewmodel/AccountsViewModel.kt
package com.ybk.budce.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ybk.budce.data.AppDatabase
import com.ybk.budce.data.Account
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class AccountsViewModel(app: Application) : AndroidViewModel(app) {
    private val dao = AppDatabase.getDatabase(app).accountDao()
    val accounts: StateFlow<List<Account>> = dao.getAllAccounts()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
}

