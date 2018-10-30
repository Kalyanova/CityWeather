package by.paranoidandroid.cityweather.injection

import by.paranoidandroid.cityweather.AndroidApplication
import by.paranoidandroid.cityweather.view.fragment.base.CitiesFragment
import by.paranoidandroid.cityweather.view.fragment.city.CityFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RoomModule::class, NetworkModule::class])
interface AppComponent {

    fun inject(application: AndroidApplication)

    fun inject(cityFragment: CityFragment)

    fun inject(citiesFragment: CitiesFragment)
}