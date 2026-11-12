package duoc.cl.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import duoc.cl.data.local.LogbookEntity
import duoc.cl.data.local.LogbookEntryEntity
import duoc.cl.data.repository.AppRepository
import duoc.cl.ui.apoderado.ApoderadoUiState
import duoc.cl.validaciones.ApoderadoValidations
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

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
            _uiState.update { it.copy(errorMessage = error) }
            return
        }

        _uiState.update { it.copy(errorMessage = null, isSearchSubmitted = true) }
    }

    fun resetSearch() {
        _uiState.update { it.copy(isSearchSubmitted = false) }
    }

    fun getStudentEntries(): Flow<List<Pair<LogbookEntity, LogbookEntryEntity>>> {
        val state = _uiState.value
        val nameQuery = state.studentName.trim()
        val emailQuery = state.studentEmail.trim()
        val query = if (nameQuery.isNotBlank()) nameQuery else emailQuery
        return repository.getEntriesByStudentQuery(query)
    }

    fun getCourseName(courseId: Long): String {
        return repository.getCourseById(courseId)?.name ?: "Curso #$courseId"
    }

    fun getSubjectName(subjectId: Long): String {
        return repository.getSubjectById(subjectId)?.name ?: "Asignatura #$subjectId"
    }
}
