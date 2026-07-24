package ge.example.countriesexplorer.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ge.example.countriesexplorer.domain.model.Country
import ge.example.countriesexplorer.domain.repository.CountryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface CountryDetailUiState {
    data object Loading : CountryDetailUiState
    data class Success(val country: Country) : CountryDetailUiState
    data class Error(val message: String) : CountryDetailUiState
}

class CountryDetailViewModel(
    private val repository: CountryRepository,
    private val countryId: String
) : ViewModel() {

    private val _uiState = MutableStateFlow<CountryDetailUiState>(CountryDetailUiState.Loading)
    val uiState: StateFlow<CountryDetailUiState> = _uiState.asStateFlow()

    init {
        loadCountry()
    }

    fun loadCountry() {
        viewModelScope.launch {
            _uiState.value = CountryDetailUiState.Loading
            repository.getCountryById(countryId)
                .onSuccess { country ->
                    _uiState.value = CountryDetailUiState.Success(country)
                }
                .onFailure { error ->
                    _uiState.value = CountryDetailUiState.Error(
                        error.message ?: "ქვეყნის დეტალების ჩატვირთვა ვერ მოხერხდა"
                    )
                }
        }
    }
}
