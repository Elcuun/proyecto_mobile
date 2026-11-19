package duoc.cl.data.repository

import android.content.Context
import android.content.SharedPreferences
import duoc.cl.api.RetrofitClient
import duoc.cl.api.model.LoginRequest
import duoc.cl.api.model.UserRequest
import duoc.cl.data.local.Role
import duoc.cl.data.local.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

class UserRepository private constructor(context: Context) {

    private val prefs: SharedPreferences = context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    init {
        initDefaultUsersIfNeeded()
        restoreSession()
    }

    private fun initDefaultUsersIfNeeded() {
        val usersJson = prefs.getString(KEY_USERS, null)
        if (usersJson.isNull_or_empty_json()) {
            val defaultUsers = listOf(
                User(
                    firstName = "María",
                    lastName = "González",
                    email = "docente@duoc.cl",
                    password = "123456",
                    role = Role.PROFESOR,
                    courseId = null
                ),
                User(
                    firstName = "Ignacia",
                    lastName = "Soto",
                    email = "estudiante@duoc.cl",
                    password = "123456",
                    role = Role.ALUMNO,
                    courseId = 1L
                )
            )
            saveUsers(defaultUsers)
        }
    }

    private fun String?.isNull_or_empty_json(): Boolean {
        return this.isNullOrBlank() || this == "[]"
    }

    private fun getUsers(): MutableList<User> {
        val usersJson = prefs.getString(KEY_USERS, "[]") ?: "[]"
        val users = mutableListOf<User>()
        try {
            val jsonArray = JSONArray(usersJson)
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                val courseIdVal = if (obj.has("courseId") && !obj.isNull("courseId")) obj.optLong("courseId") else null
                users.add(
                    User(
                        id = obj.optString("id"),
                        firstName = obj.optString("firstName"),
                        lastName = obj.optString("lastName"),
                        email = obj.optString("email"),
                        password = obj.optString("password"),
                        role = Role.fromKey(obj.optString("role")),
                        courseId = courseIdVal
                    )
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return users
    }

    private fun saveUsers(users: List<User>) {
        val jsonArray = JSONArray()
        for (user in users) {
            val obj = JSONObject().apply {
                put("id", user.id)
                put("firstName", user.firstName)
                put("lastName", user.lastName)
                put("email", user.email)
                put("password", user.password)
                put("role", user.role.name)
                put("courseId", user.courseId ?: JSONObject.NULL)
            }
            jsonArray.put(obj)
        }
        prefs.edit().putString(KEY_USERS, jsonArray.toString()).apply()
    }

    private fun restoreSession() {
        val savedToken = prefs.getString(KEY_LOGGED_IN_TOKEN, null)
        if (!savedToken.isNullOrBlank()) {
            RetrofitClient.authToken = savedToken
        }
        val activeEmail = prefs.getString(KEY_LOGGED_IN_EMAIL, null)
        if (!activeEmail.isNullOrBlank()) {
            val user = getUsers().firstOrNull { it.email.equals(activeEmail, ignoreCase = true) }
            _currentUser.value = user
        }
    }

    suspend fun register(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        role: Role,
        courseId: Long? = null
    ): Result<User> = withContext(Dispatchers.IO) {
        val cleanEmail = email.trim().lowercase()

        // 1. Intentar registrar en el Backend via Retrofit
        try {
            val response = RetrofitClient.apiService.register(
                UserRequest(
                    firstName = firstName.trim(),
                    lastName = lastName.trim(),
                    email = cleanEmail,
                    password = password,
                    role = role,
                    courseId = courseId
                )
            )

            if (response.isSuccessful && response.body() != null) {
                val authResp = response.body()!!
                val apiUser = authResp.user
                val newUser = User(
                    id = apiUser.id,
                    firstName = apiUser.firstName,
                    lastName = apiUser.lastName,
                    email = apiUser.email,
                    password = password,
                    role = apiUser.role,
                    courseId = apiUser.courseId
                )
                RetrofitClient.authToken = authResp.token
                saveUserLocallyAndSetSession(newUser, authResp.token)
                return@withContext Result.success(newUser)
            } else {
                val errorMsg = response.errorBody()?.string()
                if (!errorMsg.isNullOrBlank()) {
                    return@withContext Result.failure(Exception(errorMsg))
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            // Caída de red o backend no disponible -> continuar con fallback local
        }

        // 2. Fallback Local (si el backend no está disponible)
        val users = getUsers()

        if (users.any { it.email.lowercase() == cleanEmail }) {
            return@withContext Result.failure(Exception("El correo electrónico '$cleanEmail' ya está registrado."))
        }

        val newUser = User(
            firstName = firstName.trim(),
            lastName = lastName.trim(),
            email = cleanEmail,
            password = password,
            role = role,
            courseId = courseId
        )

        users.add(newUser)
        saveUsers(users)
        setSession(newUser)
        Result.success(newUser)
    }

    suspend fun login(
        email: String,
        password: String,
        role: Role
    ): Result<User> = withContext(Dispatchers.IO) {
        val cleanEmail = email.trim().lowercase()

        // 1. Intentar iniciar sesión en el Backend via Retrofit
        try {
            val response = RetrofitClient.apiService.login(
                LoginRequest(
                    email = cleanEmail,
                    password = password,
                    role = role
                )
            )

            if (response.isSuccessful && response.body() != null) {
                val authResp = response.body()!!
                val apiUser = authResp.user
                val loggedUser = User(
                    id = apiUser.id,
                    firstName = apiUser.firstName,
                    lastName = apiUser.lastName,
                    email = apiUser.email,
                    password = password,
                    role = apiUser.role,
                    courseId = apiUser.courseId
                )
                RetrofitClient.authToken = authResp.token
                saveUserLocallyAndSetSession(loggedUser, authResp.token)
                return@withContext Result.success(loggedUser)
            } else if (response.code() == 401 || response.code() == 400) {
                val errorMsg = response.errorBody()?.string()
                return@withContext Result.failure(
                    Exception(if (!errorMsg.isNullOrBlank()) errorMsg else "Correo o contraseña incorrectos.")
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            // Backend no disponible -> probar login local
        }

        // 2. Fallback Local (offline)
        val users = getUsers()
        val matchedUser = users.firstOrNull {
            it.email.lowercase() == cleanEmail && it.password == password
        }

        if (matchedUser == null) {
            return@withContext Result.failure(Exception("Correo electrónico o contraseña incorrectos."))
        }

        if (matchedUser.role != role) {
            val expectedRole = role.displayName
            return@withContext Result.failure(Exception("Esta cuenta no tiene perfil de $expectedRole."))
        }

        setSession(matchedUser)
        Result.success(matchedUser)
    }

    private fun saveUserLocallyAndSetSession(user: User, token: String?) {
        val users = getUsers()
        val index = users.indexOfFirst { it.email.equals(user.email, ignoreCase = true) }
        if (index != -1) {
            users[index] = user
        } else {
            users.add(user)
        }
        saveUsers(users)
        prefs.edit()
            .putString(KEY_LOGGED_IN_EMAIL, user.email)
            .putString(KEY_LOGGED_IN_TOKEN, token ?: "")
            .apply()
        _currentUser.value = user
    }

    private fun setSession(user: User) {
        prefs.edit().putString(KEY_LOGGED_IN_EMAIL, user.email).apply()
        _currentUser.value = user
    }

    fun logout() {
        RetrofitClient.authToken = null
        prefs.edit()
            .remove(KEY_LOGGED_IN_EMAIL)
            .remove(KEY_LOGGED_IN_TOKEN)
            .apply()
        _currentUser.value = null
    }

    companion object {
        private const val PREFS_NAME = "app_user_prefs_v2"
        private const val KEY_USERS = "registered_users_v2"
        private const val KEY_LOGGED_IN_EMAIL = "logged_in_user_email_v2"
        private const val KEY_LOGGED_IN_TOKEN = "logged_in_user_token_v2"

        @Volatile
        private var INSTANCE: UserRepository? = null

        fun getInstance(context: Context): UserRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = UserRepository(context)
                INSTANCE = instance
                instance
            }
        }
    }
}
