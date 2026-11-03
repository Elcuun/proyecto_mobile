package duoc.cl.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import duoc.cl.data.local.Role
import duoc.cl.data.local.User
import duoc.cl.data.repository.AppRepository
import duoc.cl.data.repository.UserRepository
import duoc.cl.validaciones.LoginValidations
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepository = UserRepository.getInstance(application)
    private val appRepository = AppRepository.getInstance(application)

    val currentUser: StateFlow<User?> = userRepository.currentUser
    val courses = appRepository.courses

    // Login Form State
    data class LoginFormState(
        val email: String = "",
        val password: String = "",
        val emailError: String? = null,
        val passwordError: String? = null,
        val generalError: String? = null,
        val isLoading: Boolean = false
    )

    private val _loginState = MutableStateFlow(LoginFormState())
    val loginState: StateFlow<LoginFormState> = _loginState.asStateFlow()

    // Registration Form State
    data class RegisterFormState(
        val firstName: String = "",
        val lastName: String = "",
        val email: String = "",
        val password: String = "",
        val confirmPassword: String = "",
        val selectedCourseId: Long? = null,
        val firstNameError: String? = null,
        val lastNameError: String? = null,
        val emailError: String? = null,
        val passwordError: String? = null,
        val confirmPasswordError: String? = null,
        val courseError: String? = null,
        val generalError: String? = null,
        val isLoading: Boolean = false
    )

    private val _registerState = MutableStateFlow(RegisterFormState())
    val registerState: StateFlow<RegisterFormState> = _registerState.asStateFlow()

    // --- Login Handlers ---

    fun onLoginEmailChanged(value: String) {
        _loginState.update {
            it.copy(
                email = value,
                emailError = LoginValidations.validateLoginEmail(value),
                generalError = null
            )
        }
    }

    fun onLoginPasswordChanged(value: String) {
        _loginState.update {
            it.copy(
                password = value,
                passwordError = LoginValidations.validateLoginPassword(value),
                generalError = null
            )
        }
    }

    fun login(role: Role, onSuccess: () -> Unit) {
        val state = _loginState.value
        val email = state.email.trim()
        val password = state.password.trim()

        val emailErr = LoginValidations.validateLoginEmail(email)
        val passErr = LoginValidations.validateLoginPassword(password)
        val isValid = emailErr == null && passErr == null

        if (!isValid) {
            _loginState.update { it.copy(emailError = emailErr, passwordError = passErr) }
            return
        }

        _loginState.update { it.copy(isLoading = true, generalError = null) }

        val result = userRepository.login(email, password, role)
        result.fold(
            onSuccess = {
                _loginState.update { LoginFormState() }
                onSuccess()
            },
            onFailure = { error ->
                _loginState.update {
                    it.copy(
                        isLoading = false,
                        generalError = error.message ?: "Error al iniciar sesión"
                    )
                }
            }
        )
    }

    // --- Register Handlers ---

    fun onRegisterFirstNameChanged(value: String) {
        _registerState.update {
            it.copy(
                firstName = value,
                firstNameError = LoginValidations.validateFirstName(value),
                generalError = null
            )
        }
    }

    fun onRegisterLastNameChanged(value: String) {
        _registerState.update {
            it.copy(
                lastName = value,
                lastNameError = LoginValidations.validateLastName(value),
                generalError = null
            )
        }
    }

    fun onRegisterEmailChanged(value: String) {
        _registerState.update {
            it.copy(
                email = value,
                emailError = LoginValidations.validateRegisterEmail(value),
                generalError = null
            )
        }
    }

    fun onRegisterPasswordChanged(value: String) {
        _registerState.update {
            it.copy(
                password = value,
                passwordError = LoginValidations.validateRegisterPassword(value),
                generalError = null
            )
        }
    }

    fun onRegisterConfirmPasswordChanged(value: String) {
        _registerState.update {
            it.copy(
                confirmPassword = value,
                confirmPasswordError = LoginValidations.validateConfirmPassword(_registerState.value.password, value),
                generalError = null
            )
        }
    }

    fun onRegisterCourseSelected(courseId: Long) {
        _registerState.update {
            it.copy(
                selectedCourseId = courseId,
                courseError = null,
                generalError = null
            )
        }
    }

    fun register(role: Role, onSuccess: () -> Unit) {
        val state = _registerState.value
        val firstName = state.firstName.trim()
        val lastName = state.lastName.trim()
        val email = state.email.trim()
        val password = state.password
        val confirmPassword = state.confirmPassword
        val courseId = state.selectedCourseId

        val fNameErr = LoginValidations.validateFirstName(firstName)
        val lNameErr = LoginValidations.validateLastName(lastName)
        val emailErr = LoginValidations.validateRegisterEmail(email)
        val passErr = LoginValidations.validateRegisterPassword(password)
        val confirmErr = LoginValidations.validateConfirmPassword(password, confirmPassword)
        val courseErr = LoginValidations.validateCourseSelection(role, courseId)

        val isValid = fNameErr == null && lNameErr == null && emailErr == null &&
                passErr == null && confirmErr == null && courseErr == null

        if (!isValid) {
            _registerState.update {
                it.copy(
                    firstNameError = fNameErr,
                    lastNameError = lNameErr,
                    emailError = emailErr,
                    passwordError = passErr,
                    confirmPasswordError = confirmErr,
                    courseError = courseErr
                )
            }
            return
        }

        _registerState.update { it.copy(isLoading = true, generalError = null) }

        val result = userRepository.register(
            firstName = firstName,
            lastName = lastName,
            email = email,
            password = password,
            role = role,
            courseId = if (role == Role.ALUMNO) courseId else null
        )

        result.fold(
            onSuccess = {
                _registerState.update { RegisterFormState() }
                onSuccess()
            },
            onFailure = { error ->
                _registerState.update {
                    it.copy(
                        isLoading = false,
                        generalError = error.message ?: "Error al registrar la cuenta"
                    )
                }
            }
        )
    }

    fun logout() {
        userRepository.logout()
        _loginState.value = LoginFormState()
        _registerState.value = RegisterFormState()
    }
}
