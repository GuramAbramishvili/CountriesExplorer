package ge.example.countriesexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import ge.example.countriesexplorer.navigation.AppNavigation
import ge.example.designsystem.CountriesExplorerTheme

/**
 * პროექტში დაშვებულია მხოლოდ ეს ერთი Activity. მისი ერთადერთი პასუხისმგებლობაა
 * თემის დაყენება და AppNavigation-ის გაშვება - ყველა შემდგომი ეკრანი
 * Navigation 3-ის საშუალებით იმართება ამავე Activity-ის შიგნით.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val repository = (application as CountriesApplication).appContainer.countryRepository

        setContent {
            CountriesExplorerTheme {
                AppNavigation(repository = repository)
            }
        }
    }
}
