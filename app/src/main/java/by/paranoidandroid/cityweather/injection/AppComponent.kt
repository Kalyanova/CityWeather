package by.paranoidandroid.cityweather.injection

import by.paranoidandroid.cityweather.network.Api
import by.paranoidandroid.cityweather.view.fragment.CitiesFragment
import by.paranoidandroid.cityweather.view.fragment.CityFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [Api::class, AppModule::class, RoomModule::class])
interface AppComponent {

    fun inject(cityFragment: CityFragment)

    fun inject(citiesFragment: CitiesFragment)
}