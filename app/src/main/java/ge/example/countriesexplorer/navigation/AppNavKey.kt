package ge.example.countriesexplorer.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

/**
 * აპლიკაციის ყველა ეკრანის ნავიგაციური გასაღები (Navigation 3).
 * ყველა ქვეტიპი სავალდებულოდ არის @Serializable, რადგან rememberNavBackStack
 * ინახავს ამ სტეკს Bundle-ში configuration change-ისა და process death-ის დროსაც.
 */
@Serializable
sealed interface AppNavKey : NavKey {

    /** ქვეყნების სია - აპლიკაციის საწყისი ეკრანი. */
    @Serializable
    data object CountryList : AppNavKey

    /** კონკრეტული ქვეყნის დეტალური გვერდი, cca3 კოდით. */
    @Serializable
    data class CountryDetail(val countryId: String) : AppNavKey

    /** ფილტრის მოდალი (რეგიონის მიხედვით არჩევა) - მესამე გვერდი. */
    @Serializable
    data object FilterModal : AppNavKey
}
