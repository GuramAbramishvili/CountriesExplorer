package ge.example.countriesexplorer.domain.repository

import ge.example.countriesexplorer.domain.model.Country

/**
 * Repository-ის კონტრაქტი. ViewModel-ები დამოკიდებულნი არიან ამ ინტერფეისზე,
 * არა კონკრეტულ იმპლემენტაციაზე - ეს აადვილებს ტესტირებას და API-ის შეცვლას.
 */
interface CountryRepository {

    /** აბრუნებს ყველა ქვეყანას. თუ პარამეტრში მოცემულია region, აბრუნებს მხოლოდ იმ რეგიონის ქვეყნებს. */
    suspend fun getCountries(region: String? = null): Result<List<Country>>

    /** აბრუნებს ერთ კონკრეტულ ქვეყანას მისი id-ით (ISO კოდი) უკვე ჩამოტვირთული სიიდან ან ახალი მოთხოვნით. */
    suspend fun getCountryById(id: String): Result<Country>

    /** აბრუნებს ხელმისაწვდომი რეგიონების უნიკალურ სიას, ფილტრის მოდალისთვის. */
    suspend fun getAvailableRegions(): Result<List<String>>
}
