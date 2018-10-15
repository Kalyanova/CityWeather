package by.paranoidandroid.cityweather.injection

import by.paranoidandroid.cityweather.domain.repository.CityRepository
import by.paranoidandroid.cityweather.network.Api
import by.paranoidandroid.cityweather.network.repository.WebRepository
import by.paranoidandroid.cityweather.viewmodel.CityViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [Api::class, WebRepository::class, CityRepository::class])
interface AppComponent {

    fun inject(webRepository: WebRepository)

    fun inject(cityRepository: CityRepository)

    fun inject(cityViewModel: CityViewModel)

}