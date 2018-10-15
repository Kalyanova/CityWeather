package by.paranoidandroid.cityweather.injection

import by.paranoidandroid.cityweather.network.Api
import by.paranoidandroid.cityweather.view.fragment.CityFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [Api::class,
    AppModule::class])
interface AppComponent {

    fun inject(cityFragment: CityFragment)
}