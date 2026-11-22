package duoc.cl.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import duoc.cl.data.local.LogbookEntity
import duoc.cl.data.local.LogbookEntryEntity
import duoc.cl.data.repository.AppRepository
import duoc.cl.ui.teacher.LogbookFormState
import duoc.cl.validaciones.LogbookValidations
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TeacherViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = AppRepository.getInstance(application)

    val courses = repository.courses
    val subjects = repository.subjects

    private val _selectedCourseId = MutableStateFlow<Long?>(null)
    val selectedCourseId: StateFlow<Long?> = _selectedCourseId.asStateFlow()

    private val _selectedSubjectId = MutableStateFlow<Long?>(null)
    val selectedSubjectId: StateFlow<Long?> = _selectedSubjectId.asStateFlow()

    fun selectCourseAndSubject(courseId: Long, subjectId: Long) {
        _selectedCourseId.value = courseId
        _selectedSubjectId.value = subjectId
    }

    fun getLogbooks(courseId: Long, subjectId: Long): Flow<List<LogbookEntity>> {
        return repository.getLogbooks(courseId, subjectId)
    }

    fun getEntries(logbookId: Long): Flow<List<LogbookEntryEntity>> {
        return repository.getEntriesByLogbook(logbookId)
    }

    fun refreshEntries(logbookId: Long) {
        viewModelScope.launch {
            repository.fetchEntriesByLogbook(logbookId)
        }
    }

    private val _formState = MutableStateFlow(LogbookFormState())
    val formState: StateFlow<LogbookFormState> = _formState.asStateFlow()

    fun onMonthChanged(value: String) {
        val error = LogbookValidations.validateMonth(value)
        _formState.update { it.copy(month = value, monthError = error).validate() }
    }

    fun onTitleChanged(value: String) {
        val error = LogbookValidations.validateTitle(value)
        _formState.update { it.copy(title = value, titleError = error).validate() }
    }

    fun onPurposeChanged(value: String) {
        val error = LogbookValidations.validatePurpose(value)
        _formState.update { it.copy(purpose = value, purposeError = error).validate() }
    }

    fun onInstructionsChanged(value: String) {
        val error = LogbookValidations.validateInstructions(value)
        _formState.update { it.copy(instructions = value, instructionsError = error).validate() }
    }

    fun onGuidingQuestionsChanged(value: String) {
        _formState.update { it.copy(guidingQuestions = value) }
    }

    private fun LogbookFormState.validate(): LogbookFormState {
        val valid = LogbookValidations.isLogbookFormValid(
            month = month,
            title = title,
            purpose = purpose,
            instructions = instructions,
            monthError = monthError,
            titleError = titleError,
            purposeError = purposeError,
            instructionsError = instructionsError
        )
        return copy(isFormValid = valid)
    }

    fun createLogbook(courseId: Long, subjectId: Long, onResult: (Boolean) -> Unit) {
        val state = _formState.value
        if (!state.isFormValid) {
            onResult(false)
            return
        }
        viewModelScope.launch {
            repository.insertLogbook(
                LogbookEntity(
                    courseId = courseId,
                    subjectId = subjectId,
                    month = state.month,
                    title = state.title,
                    purpose = state.purpose,
                    instructions = state.instructions,
                    guidingQuestions = state.guidingQuestions
                )
            )
            _formState.value = LogbookFormState()
            onResult(true)
        }
    }

    fun deleteLogbook(logbookId: Long, onResult: () -> Unit = {}) {
        viewModelScope.launch {
            repository.deleteLogbook(logbookId)
            onResult()
        }
    }

    fun updateFeedback(entryId: Long, feedback: String, isReviewed: Boolean) {
        viewModelScope.launch {
            val entry = repository.getEntryById(entryId)
            entry?.let {
                repository.updateEntry(it.copy(feedback = feedback, isReviewed = isReviewed))
            }
        }
    }

    fun toggleCloseLogbook(logbook: LogbookEntity) {
        viewModelScope.launch {
            repository.updateLogbook(logbook.copy(isClosed = !logbook.isClosed))
        }
    }
}
