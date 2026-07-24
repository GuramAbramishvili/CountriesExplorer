package ge.example.countriesexplorer.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ge.example.countriesexplorer.domain.model.Country
import ge.example.countriesexplorer.domain.repository.CountryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/** ეკრანის მდგომარეობა - კლასიკური MVVM UiState. */
sealed interface CountryListUiState {
    data object Loading : CountryListUiState
    data class Success(
        val allCountries: List<Country>,
        val visibleCountries: List<Country>,
        val searchQuery: String,
        val selectedRegion: String?
    ) : CountryListUiState
    data class Error(val message: String) : CountryListUiState
}

class CountryListViewModel(
    private val repository: CountryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<CountryListUiState>(CountryListUiState.Loading)
    val uiState: StateFlow<CountryListUiState> = _uiState.asStateFlow()

    init {
        loadCountries()
    }

    fun loadCountries() {
        viewModelScope.launch {
            _uiState.value = CountryListUiState.Loading
            repository.getCountries()
                .onSuccess { countries ->
                    _uiState.value = CountryListUiState.Success(
                        allCountries = countries,
                        visibleCountries = countries,
                        searchQuery = "",
                        selectedRegion = null
                    )
                }
                .onFailure { error ->
                    _uiState.value = CountryListUiState.Error(
                        error.message ?: "უცნობი შეცდომა მოხდა მონაცემების ჩატვირთვისას"
                    )
                }
        }
    }

    fun onSearchQueryChanged(query: String) {
        val current = _uiState.value as? CountryListUiState.Success ?: return
        _uiState.value = current.copy(
            searchQuery = query,
            visibleCountries = applyFilters(current.allCountries, query, current.selectedRegion)
        )
    }

    fun onRegionSelected(region: String?) {
        val current = _uiState.value as? CountryListUiState.Success ?: return
        _uiState.value = current.copy(
            selectedRegion = region,
            visibleCountries = applyFilters(current.allCountries, current.searchQuery, region)
        )
    }

    private fun applyFilters(
        countries: List<Country>,
        query: String,
        region: String?
    ): List<Country> {
        return countries
            .filter { region == null || it.region == region }
            .filter { query.isBlank() || it.commonName.contains(query, ignoreCase = true) }
    }
}
