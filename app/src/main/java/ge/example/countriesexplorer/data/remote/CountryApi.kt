package ge.example.countriesexplorer.data.remote

import ge.example.countriesexplorer.data.remote.dto.CountryDto
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * countries.dev - თავისუფალი, გასაღების გარეშე მუშაობადი API, restcountries.com-ის
 * ალტერნატივა (v3.1 ვერსია აღარ არის მხარდაჭერილი და აბრუნებს მხოლოდ error-ს).
 * იხ. https://countries.dev/docs
 */
interface CountryApi {

    @GET("countries")
    suspend fun getAllCountries(
        @Query("fields") fields: String = "name,alpha3Code,capital,region,subregion,population,flags,flag"
    ): List<CountryDto>
}

