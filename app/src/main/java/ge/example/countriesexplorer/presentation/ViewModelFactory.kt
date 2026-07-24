package ge.example.countriesexplorer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras

/**
 * მარტივი, ზოგადი ViewModelProvider.Factory.
 * ეს კლასი საშუალებას გვაძლევს ViewModel-ს ხელით გადავცეთ
 * repository-ის კონსტრუქტორის პარამეტრად, MVVM კონტრაქტის დაურღვევლად.
 */
class ViewModelFactory(
    private val creator: () -> ViewModel
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return creator() as T
    }
}
