package ge.example.countriesexplorer.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import ge.example.countriesexplorer.domain.repository.CountryRepository
import ge.example.countriesexplorer.presentation.ViewModelFactory
import ge.example.countriesexplorer.presentation.detail.CountryDetailScreen
import ge.example.countriesexplorer.presentation.detail.CountryDetailViewModel
import ge.example.countriesexplorer.presentation.filter.FilterModalScreen
import ge.example.countriesexplorer.presentation.filter.FilterViewModel
import ge.example.countriesexplorer.presentation.list.CountryListScreen
import ge.example.countriesexplorer.presentation.list.CountryListUiState
import ge.example.countriesexplorer.presentation.list.CountryListViewModel

/**
 * აპლიკაციის ერთადერთი ნავიგაციური გრაფი, აწყობილი Navigation 3-ით
 * (androidx.navigation3). ეს ფუნქცია გამოიძახება მხოლოდ ერთხელ, ერთადერთი
 * Activity-დან, და მართავს გადასვლას სამ გვერდს შორის: სია, დეტალები, ფილტრი.
 */
@Composable
fun AppNavigation(repository: CountryRepository) {
    val backStack = rememberNavBackStack(AppNavKey.CountryList)

    // სიის ViewModel აქ იქმნება, რომ ფილტრის მოდალს შეეძლოს მასზე პირდაპირი
    // წვდომა და აირჩეული რეგიონი დაუყოვნებლივ გადასცეს სიას "ბრძანების" გარეშე.
    val countryListViewModel: CountryListViewModel = viewModel(
        factory = remember { ViewModelFactory { CountryListViewModel(repository) } }
    )

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        // ეს დეკორატორები აუცილებელია: მათ გარეშე ყველა ეკრანის ViewModel-ი
        // ერთსა და იმავე Activity-დონის საცავს იზიარებდა და ერთი და იმავე
        // key-ით ინახებოდა, რის გამოც პირველად ჩატვირთული ქვეყანა ყველგან
        // მეორდებოდა - ViewModel-ს ახალი factory აღარ ეშვებოდა.
        entryDecorators = listOf(
            rememberSavedStateNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<AppNavKey.CountryList> {
                CountryListScreen(
                    viewModel = countryListViewModel,
                    onCountryClick = { country -> backStack.add(AppNavKey.CountryDetail(country.id)) },
                    onFilterClick = { backStack.add(AppNavKey.FilterModal) }
                )
            }

            entry<AppNavKey.CountryDetail> { key ->
                val detailViewModel: CountryDetailViewModel = viewModel(
                    factory = remember { ViewModelFactory { CountryDetailViewModel(repository, key.countryId) } }
                )
                CountryDetailScreen(
                    viewModel = detailViewModel,
                    onBackClick = { backStack.removeLastOrNull() }
                )
            }

            entry<AppNavKey.FilterModal>(
                metadata = NavDisplay.transitionSpec {
                    slideInVertically(animationSpec = tween(300), initialOffsetY = { it }) togetherWith fadeOut(tween(150))
                } + NavDisplay.popTransitionSpec {
                    fadeIn(tween(150)) togetherWith slideOutVertically(animationSpec = tween(300), targetOffsetY = { it })
                }
            ) {
                val filterViewModel: FilterViewModel = viewModel(
                    factory = remember { ViewModelFactory { FilterViewModel(repository) } }
                )
                val currentRegion = (countryListViewModel.uiState.value as? CountryListUiState.Success)?.selectedRegion

                FilterModalScreen(
                    viewModel = filterViewModel,
                    currentRegion = currentRegion,
                    onApply = { region ->
                        countryListViewModel.onRegionSelected(region)
                        backStack.removeLastOrNull()
                    },
                    onDismiss = { backStack.removeLastOrNull() }
                )
            }
        }
    )
}
