package api

import dto.AuthResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi {

    @FormUrlEncoded
    @POST("v2/auth/token/")
    suspend fun getAccessToken(
        @Field("grant_type") grantType: String = "client_credentials"
    ): Response<AuthResponse>
}