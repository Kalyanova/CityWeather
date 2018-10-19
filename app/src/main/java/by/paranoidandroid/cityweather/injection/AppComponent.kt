package by.paranoidandroid.cityweather.injection

import by.paranoidandroid.cityweather.AndroidApplication
import by.paranoidandroid.cityweather.view.fragment.CitiesFragment
import by.paranoidandroid.cityweather.view.fragment.CityFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RoomModule::class, RetrofitModule::class])
interface AppComponent {

    fun inject(application: AndroidApplication)

    fun inject(cityFragment: CityFragment)

    fun inject(citiesFragment: CitiesFragment)
}