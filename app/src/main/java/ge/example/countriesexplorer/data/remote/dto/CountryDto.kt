package ge.example.countriesexplorer.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * https://countries.dev/countries პასუხის სტრუქტურის ზუსტი ასახვა.
 * ეს კლასი ეკუთვნის მხოლოდ data ფენას და არასდროს გაედინება domain ან
 * presentation ფენებში - იმისთვის, რომ API-ის ცვლილება არ დაარღვევდეს UI-ს.
 */
@Serializable
data class CountryDto(
    val name: String,
    @SerialName("alpha3Code") val alpha3Code: String,
    val capital: String? = null,
    val region: String,
    val subregion: String? = null,
    val population: Long,
    val flags: FlagsDto,
    @SerialName("flag") val flagEmoji: String? = null
)

@Serializable
data class FlagsDto(
    val png: String,
    val svg: String? = null
)

