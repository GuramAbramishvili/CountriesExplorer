package ge.example.countriesexplorer.presentation.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ge.example.countriesexplorer.domain.model.Country
import ge.example.designsystem.components.DsListItemCard
import ge.example.designsystem.components.DsSearchField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryListScreen(
    viewModel: CountryListViewModel,
    onCountryClick: (Country) -> Unit,
    onFilterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(title = { Text("ქვეყნების გზამკვლევი") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onFilterClick) {
                Icon(Icons.Default.FilterList, contentDescription = "ფილტრი")
            }
        }
    ) { padding ->
        when (val state = uiState) {
            is CountryListUiState.Loading -> LoadingContent(padding)
            is CountryListUiState.Error -> ErrorContent(padding, state.message)
            is CountryListUiState.Success -> SuccessContent(
                padding = padding,
                state = state,
                onSearchQueryChanged = viewModel::onSearchQueryChanged,
                onCountryClick = onCountryClick
            )
        }
    }
}

@Composable
private fun LoadingContent(padding: PaddingValues) {
    Box(
        modifier = Modifier.fillMaxSize().padding(padding),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorContent(padding: PaddingValues, message: String) {
    Box(
        modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "შეცდომა: $message",
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun SuccessContent(
    padding: PaddingValues,
    state: CountryListUiState.Success,
    onSearchQueryChanged: (String) -> Unit,
    onCountryClick: (Country) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(padding)) {
        DsSearchField(
            value = state.searchQuery,
            onValueChange = onSearchQueryChanged,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            placeholder = "მოძებნე ქვეყანა..."
        )

        if (state.selectedRegion != null) {
            Text(
                text = "რეგიონი: ${state.selectedRegion}",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )
        }

        if (state.visibleCountries.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("შედეგი ვერ მოიძებნა", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items = state.visibleCountries, key = { it.id }) { country ->
                    DsListItemCard(
                        title = country.commonName,
                        subtitle = country.capital ?: "დედაქალაქი უცნობია",
                        onClick = { onCountryClick(country) },
                        flagContent = {
                            AsyncImage(
                                model = country.flagImageUrl,
                                contentDescription = "დროშა: ${country.commonName}"
                            )
                        }
                    )
                }
            }
        }
    }
}
