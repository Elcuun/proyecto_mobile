package duoc.cl.api

import duoc.cl.api.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // --- Autenticación (/api/auth) ---

    @POST("api/auth/register")
    suspend fun register(@Body request: UserRequest): Response<AuthResponse>

    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    // --- Catálogos (/api/courses, /api/subjects) ---

    @GET("api/courses")
    suspend fun getCourses(): Response<List<NamedResponse>>

    @GET("api/subjects")
    suspend fun getSubjects(): Response<List<NamedResponse>>

    // --- Bitácoras Mensuales (/api/logbooks) ---

    @GET("api/logbooks")
    suspend fun getLogbooks(
        @Query("courseId") courseId: Long? = null,
        @Query("subjectId") subjectId: Long? = null
    ): Response<List<LogbookResponse>>

    @GET("api/logbooks/{id}")
    suspend fun getLogbookById(@Path("id") id: Long): Response<LogbookResponse>

    @POST("api/logbooks")
    suspend fun createLogbook(
        @Header("Authorization") token: String? = null,
        @Body request: LogbookRequest
    ): Response<LogbookResponse>

    @PATCH("api/logbooks/{id}/status")
    suspend fun updateLogbookStatus(
        @Header("Authorization") token: String? = null,
        @Path("id") id: Long,
        @Query("closed") closed: Boolean
    ): Response<LogbookResponse>

    // --- Entregas / Entradas de Bitácora (/api/logbooks/{id}/entries, /api/entries, /api/guardian/entries) ---

    @GET("api/logbooks/{id}/entries")
    suspend fun getEntriesByLogbook(
        @Header("Authorization") token: String? = null,
        @Path("id") id: Long
    ): Response<List<EntryResponse>>

    @POST("api/logbooks/{id}/entries")
    suspend fun createEntry(
        @Header("Authorization") token: String? = null,
        @Path("id") id: Long,
        @Body request: EntryRequest
    ): Response<EntryResponse>

    @PATCH("api/entries/{id}/review")
    suspend fun reviewEntry(
        @Header("Authorization") token: String? = null,
        @Path("id") id: Long,
        @Body request: ReviewRequest
    ): Response<EntryResponse>

    @GET("api/guardian/entries")
    suspend fun searchGuardianEntries(
        @Header("Authorization") token: String? = null,
        @Query("name") name: String? = null,
        @Query("email") email: String? = null
    ): Response<List<EntryResponse>>
}
