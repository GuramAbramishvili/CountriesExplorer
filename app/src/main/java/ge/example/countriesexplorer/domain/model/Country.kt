package ge.example.countriesexplorer.domain.model

/**
 * დომეინის მოდელი, სრულიად დამოუკიდებელი API-ის ფორმატისგან.
 * Presentation ფენა მუშაობს მხოლოდ ამ კლასთან და არასდროს ხედავს DTO-ს.
 */
data class Country(
    val id: String,
    val commonName: String,
    val officialName: String,
    val capital: String?,
    val region: String,
    val subregion: String?,
    val population: Long,
    val flagImageUrl: String,
    val flagEmoji: String
)
