package ge.example.countriesexplorer.presentation.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ge.example.countriesexplorer.domain.repository.CountryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface FilterUiState {
    data object Loading : FilterUiState
    data class Success(val regions: List<String>) : FilterUiState
    data class Error(val message: String) : FilterUiState
}

class FilterViewModel(
    private val repository: CountryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<FilterUiState>(FilterUiState.Loading)
    val uiState: StateFlow<FilterUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAvailableRegions()
                .onSuccess { regions -> _uiState.value = FilterUiState.Success(regions) }
                .onFailure { error ->
                    _uiState.value = FilterUiState.Error(
                        error.message ?: "რეგიონების ჩატვირთვა ვერ მოხერხდა"
                    )
                }
        }
    }
}
