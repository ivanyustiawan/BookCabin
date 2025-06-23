package di

import api.PassengerApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import local.TokenManager
import repository.PassengerRepository
import repository.PassengerRepositoryImpl
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PassengerModule {

    @Provides
    @Singleton
    fun providePassengerApi(@Named("BearerRetrofit") retrofit: Retrofit): PassengerApi {
        return retrofit.create(PassengerApi::class.java)
    }

    @Provides
    @Singleton
    fun providePassengerRepository(
        passengerApi: PassengerApi,
        tokenManager: TokenManager,
    ): PassengerRepository {
        return PassengerRepositoryImpl(passengerApi, tokenManager)
    }
}