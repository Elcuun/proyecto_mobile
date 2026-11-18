package duoc.cl.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    /**
     * URL Base de tu backend Spring Boot.
     *
     * Nota para desarrollo local:
     * - Emulador Android -> "http://10.0.2.2:8080/"
     * - Dispositivo físico -> IP de tu PC en la red local (ej: "http://192.168.1.X:8080/")
     */
    private const val BASE_URL = "http://10.0.2.2:8080/"

    @Volatile
    var authToken: String? = null

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()

            val token = authToken
            if (!token.isNullOrBlank() && original.header("Authorization") == null) {
                val formattedToken = if (token.startsWith("Bearer ")) token else "Bearer $token"
                requestBuilder.header("Authorization", formattedToken)
            }

            chain.proceed(requestBuilder.build())
        }
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
