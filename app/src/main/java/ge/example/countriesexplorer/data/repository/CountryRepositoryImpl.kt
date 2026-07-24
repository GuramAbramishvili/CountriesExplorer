package ge.example.countriesexplorer.data.repository

import ge.example.countriesexplorer.data.remote.CountryApi
import ge.example.countriesexplorer.data.remote.dto.CountryDto
import ge.example.countriesexplorer.domain.model.Country
import ge.example.countriesexplorer.domain.repository.CountryRepository
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * CountryRepository-ის რეალური იმპლემენტაცია. API call ხდება მხოლოდ აქ,
 * ViewModel-ებმა ამის შესახებ არაფერს არ იციან.
 *
 * მარტივი in-memory ქეში ინახავს ბოლო წარმატებულ პასუხს, რომ ერთი და იმავე
 * მონაცემებისთვის (სია -> დეტალი -> ფილტრი) ხელახლა ქსელში არ გავიდეთ.
 */
class CountryRepositoryImpl(
    private val api: CountryApi
) : CountryRepository {

    private val mutex = Mutex()
    private var cachedCountries: List<Country>? = null

    override suspend fun getCountries(region: String?): Result<List<Country>> {
        return try {
            val all = getOrFetchAll()
            val filtered = if (region.isNullOrBlank()) all else all.filter { it.region == region }
            Result.success(filtered)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCountryById(id: String): Result<Country> {
        return try {
            val all = getOrFetchAll()
            val found = all.firstOrNull { it.id == id }
            if (found != null) {
                Result.success(found)
            } else {
                Result.failure(NoSuchElementException("ქვეყანა id-ით '$id' ვერ მოიძებნა"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAvailableRegions(): Result<List<String>> {
        return try {
            val all = getOrFetchAll()
            Result.success(all.map { it.region }.distinct().sorted())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun getOrFetchAll(): List<Country> {
        cachedCountries?.let { return it }
        mutex.withLock {
            cachedCountries?.let { return it }
            val dtos = api.getAllCountries()
            val mapped = dtos.map { it.toDomain() }.sortedBy { it.commonName }
            cachedCountries = mapped
            return mapped
        }
    }

    private fun CountryDto.toDomain(): Country = Country(
        id = alpha3Code,
        commonName = name,
        // countries.dev-ის lean პასუხი ცალკე "official name" ველს არ აბრუნებს
        // (ეს არის მხოლოდ "full" რეჟიმში, translations-თან ერთად), ამიტომ
        // დეტალების ეკრანზე იგივე სახელს ვაჩვენებთ ორივე ველისთვის.
        officialName = name,
        capital = capital,
        region = region,
        subregion = subregion,
        population = population,
        flagImageUrl = flags.png,
        flagEmoji = flagEmoji ?: ""
    )
}
