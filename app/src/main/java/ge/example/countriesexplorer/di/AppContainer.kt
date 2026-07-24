package ge.example.countriesexplorer.di

import ge.example.countriesexplorer.data.remote.CountryApi
import ge.example.countriesexplorer.data.repository.CountryRepositoryImpl
import ge.example.countriesexplorer.domain.repository.CountryRepository
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType

/**
 * მარტივი, ხელით აწყობილი DI კონტეინერი. აწყობს ერთადერთ ინსტანციებს მთელი აპლიკაციისთვის
 * და გადმოსცემს მათ repository-ის სახით ViewModel-ებს.
 */
class AppContainer {

    private val json = Json { ignoreUnknownKeys = true }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://countries.dev/")
        .client(okHttpClient)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    private val countryApi: CountryApi = retrofit.create(CountryApi::class.java)

    val countryRepository: CountryRepository = CountryRepositoryImpl(countryApi)
}
