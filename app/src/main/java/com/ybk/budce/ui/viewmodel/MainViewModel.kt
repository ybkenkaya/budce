
package com.ybk.budce.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ybk.budce.data.Transaction
import com.ybk.budce.data.repository.AppRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(private val repository: AppRepository) : ViewModel() {

    // Repository'den gelen tüm işlemleri bir StateFlow olarak tutuyoruz.
    // stateIn, Flow'u bir StateFlow'a dönüştürür ve UI'ın bunu güvenle dinlemesini sağlar.
    val allTransactions: StateFlow<List<Transaction>> = repository.getAllTransactions()
        .stateIn(
            scope = viewModelScope, // Bu Flow, ViewModel yaşadığı sürece aktif olacak
            started = SharingStarted.WhileSubscribed(5000), // Ekran 5 saniye boyunca kapalı kalırsa flow durdurulur
            initialValue = emptyList() // Başlangıçta liste boş olacak
        )

    // Yeni işlem eklemek için kullanılacak fonksiyon
    fun insertTransaction(transaction: Transaction) {
        // viewModelScope, bu işlemin arka planda güvenli bir şekilde yapılmasını sağlar.
        viewModelScope.launch {
            repository.insertTransaction(transaction)
        }
    }
}
