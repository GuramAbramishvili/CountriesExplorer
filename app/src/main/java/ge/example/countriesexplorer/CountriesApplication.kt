package ge.example.countriesexplorer

import android.app.Application
import ge.example.countriesexplorer.di.AppContainer

class CountriesApplication : Application() {

    lateinit var appContainer: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer()
    }
}
