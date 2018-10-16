package by.paranoidandroid.cityweather.injection

import by.paranoidandroid.cityweather.db.room.repository.DBRepository
import by.paranoidandroid.cityweather.domain.repository.CityRepository
import by.paranoidandroid.cityweather.network.repository.WebRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideCityRepository(webService: WebRepository, dbRepository: DBRepository): CityRepository {
        return CityRepository(webService, dbRepository)
    }
}