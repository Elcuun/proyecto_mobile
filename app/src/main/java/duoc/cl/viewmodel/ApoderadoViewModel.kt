package duoc.cl.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import duoc.cl.data.repository.AppRepository
import duoc.cl.ui.apoderado.ApoderadoUiState
import duoc.cl.validaciones.ApoderadoValidations
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ApoderadoViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = AppRepository.getInstance(application)

    private val _uiState = MutableStateFlow(ApoderadoUiState())
    val uiState: StateFlow<ApoderadoUiState> = _uiState.asStateFlow()

    fun onStudentNameChanged(value: String) {
        _uiState.update { it.copy(studentName = value, errorMessage = null) }
    }

    fun onStudentEmailChanged(value: String) {
        _uiState.update { it.copy(studentEmail = value, errorMessage = null) }
    }

    fun searchStudent() {
        val state = _uiState.value
        val name = state.studentName
        val email = state.studentEmail

        val error = ApoderadoValidations.validateStudentSearchQuery(name, email)
        if (error != null) {
            _uiState.update { it.copy(errorMessage = error, isSearchSubmitted = false) }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null, isSearchSubmitted = true, entries = emptyList()) }
            val result = repository.searchGuardianEntries(name, email)
            result.fold(
                onSuccess = { list ->
                    _uiState.update { it.copy(isLoading = false, entries = list, errorMessage = null) }
                },
                onFailure = { err ->
                    _uiState.update { it.copy(isLoading = false, entries = emptyList(), errorMessage = err.message ?: "Error al consultar entregas del estudiante") }
                }
            )
        }
    }

    fun resetSearch() {
        _uiState.update { it.copy(isSearchSubmitted = false, entries = emptyList(), errorMessage = null) }
    }

    fun getCourseName(courseId: Long): String {
        return repository.getCourseById(courseId)?.name ?: "Curso #$courseId"
    }

    fun getSubjectName(subjectId: Long): String {
        return repository.getSubjectById(subjectId)?.name ?: "Asignatura #$subjectId"
    }
}
