package di

import com.example.bookcabin.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import local.TokenManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @Named("BasicOkhttp")
    fun provideBasicOkHttpClient(): OkHttpClient {
        val authInterceptor = Interceptor { chain ->
            val original: Request = chain.request()
            val request = original.newBuilder()
                .header("Authorization", BuildConfig.BASIC_TOKEN)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build()
            chain.proceed(request)
        }

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }


    @Provides
    @Singleton
    @Named("BasicRetrofit")
    fun provideBasicRetrofit(@Named("BasicOkhttp") client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @Named("BearerOkhttp")
    fun provideBearerOkHttpClient(tokenManager: TokenManager): OkHttpClient {
        val authInterceptor = Interceptor { chain ->
            val original: Request = chain.request()
            val request = original.newBuilder()
                .header("Authorization", "Bearer ${tokenManager.getAccessToken()}")
                .addHeader("Accept", "*/*")
                .addHeader("Content-Type", "application/json")
                .addHeader("session-id", tokenManager.getSessionId())
                .build()
            chain.proceed(request)
        }

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @Named("BearerRetrofit")
    fun provideBearerRetrofit(@Named("BearerOkhttp") client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}