# CountriesExplorer

მარტივი Android აპლიკაცია, რომელიც აჩვენებს მსოფლიოს ქვეყნების სიას
[restcountries.com](https://restcountries.com) API-დან, დეტალურ ინფორმაციასთან
და რეგიონის მიხედვით ფილტრაციასთან ერთად.

## როგორ გავხსნათ

1. გახსენით პროექტის root ფოლდერი Android Studio-ში (Koala ან უფრო ახალი ვერსია).
2. დაელოდეთ Gradle sync-ს — ყველა დამოკიდებულება public Maven რეპოზიტორიებიდან იტვირთება.
3. გაუშვით `app` კონფიგურაცია ემულატორზე ან მოწყობილობაზე (`minSdk 26`).

## არქიტექტურა

პროექტი შედგება ორი მოდულისგან:

- **`design-system`** — ცალკე Android library მოდული, სადაც აღწერილია აპლიკაციის
  თემა (`CountriesExplorerTheme`), ფერები, ტიპოგრაფია და გამოსაყენებელი
  კომპონენტები (`DsPrimaryButton`, `DsSecondaryButton`, `DsListItemCard`,
  `DsSearchField`, `DsSectionTitle`).
- **`app`** — თავად აპლიკაცია, დაყოფილი სამ ფენად:
  - **data** — `CountryApi` (Retrofit + kotlinx.serialization), DTO კლასები და
    `CountryRepositoryImpl`, რომელიც აქცევს DTO-ებს domain მოდელებად და ინახავს
    მარტივ in-memory ქეშს.
  - **domain** — `Country` მოდელი და `CountryRepository` ინტერფეისი, რომელზეც
    დამოკიდებულია presentation ფენა (და არა კონკრეტულ იმპლემენტაციაზე).
  - **presentation** — MVVM: სამი ეკრანი (`list`, `detail`, `filter`), თითოეული
    საკუთარი `ViewModel`-ითა და `StateFlow`-ზე დაფუძნებული `UiState`-ით.

### ტექნიკური მოთხოვნების დაკმაყოფილება

| მოთხოვნა | სად არის განხორციელებული |
|---|---|
| მინიმუმ 3 გვერდი | ქვეყნების სია, ქვეყნის დეტალები, ფილტრის მოდალი |
| მონაცემები API-დან | `data/remote/CountryApi.kt` (restcountries.com) |
| MVVM | `presentation/list`, `presentation/detail`, `presentation/filter` |
| API call repository-ის გავლით | `CountryRepositoryImpl`, ViewModel-ები API-ს პირდაპირ არასდროს არ სვამენ |
| ერთი Activity | `MainActivity.kt` — ერთადერთი Activity პროექტში |
| Navigation 3 | `navigation/AppNavKey.kt`, `navigation/AppNavigation.kt` |
| დიზაინ სისტემის მოდული | `design-system` მოდული, თემა + კომპონენტები |

## შენიშვნა Navigation 3-ის ვერსიის შესახებ

Navigation 3 ჯერ კიდევ alpha ეტაპზეა და API სწრაფად იცვლება. თუ Gradle sync-ისას
დამოკიდებულების ვერსია ვერ მოიძებნა, გადაამოწმეთ უახლესი ვერსია
[developer.android.com/jetpack/androidx/releases/navigation3](https://developer.android.com/jetpack/androidx/releases/navigation3)
გვერდზე და განაახლეთ `app/build.gradle.kts`-ში.
