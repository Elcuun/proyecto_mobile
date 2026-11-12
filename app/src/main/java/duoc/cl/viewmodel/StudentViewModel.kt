package duoc.cl.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import duoc.cl.data.local.LogbookEntryEntity
import duoc.cl.data.repository.AppRepository
import duoc.cl.ui.student.EntryFormState
import duoc.cl.validaciones.SubmitEntryValidations
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class StudentViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = AppRepository.getInstance(application)

    val courses = repository.courses
    val subjects = repository.subjects

    fun getLogbooks(courseId: Long, subjectId: Long) = repository.getLogbooks(courseId, subjectId)

    fun getEntriesForLogbook(logbookId: Long) = repository.getEntriesByLogbook(logbookId)

    private val _entryFormState = MutableStateFlow(EntryFormState())
    val entryFormState: StateFlow<EntryFormState> = _entryFormState.asStateFlow()

    fun onStudentNameChanged(value: String) {
        val error = SubmitEntryValidations.validateStudentName(value)
        _entryFormState.update { it.copy(studentName = value, studentNameError = error).validate() }
    }

    fun onContentsChanged(value: String) {
        val error = SubmitEntryValidations.validateContents(value)
        _entryFormState.update { it.copy(contents = value, contentsError = error).validate() }
    }

    fun onActivitiesChanged(value: String) {
        val error = SubmitEntryValidations.validateActivities(value)
        _entryFormState.update { it.copy(activities = value, activitiesError = error).validate() }
    }

    fun onLearningsChanged(value: String) {
        val error = SubmitEntryValidations.validateLearnings(value)
        _entryFormState.update { it.copy(learnings = value, learningsError = error).validate() }
    }

    fun onDoubtsChanged(value: String) {
        _entryFormState.update { it.copy(doubts = value) }
    }

    fun setPhotoUri(uri: String?) {
        _entryFormState.update { it.copy(photoUri = uri) }
    }

    fun setLocation(lat: Double?, lon: Double?) {
        _entryFormState.update { it.copy(latitude = lat, longitude = lon) }
    }

    private fun EntryFormState.validate(): EntryFormState {
        val valid = SubmitEntryValidations.isSubmitEntryFormValid(
            studentName = studentName,
            contents = contents,
            activities = activities,
            learnings = learnings,
            studentNameError = studentNameError,
            contentsError = contentsError,
            activitiesError = activitiesError,
            learningsError = learningsError
        )
        return copy(isFormValid = valid)
    }

    fun submitEntry(logbookId: Long, onResult: (Boolean) -> Unit) {
        val state = _entryFormState.value
        if (!state.isFormValid) {
            onResult(false)
            return
        }
        viewModelScope.launch {
            repository.insertEntry(
                LogbookEntryEntity(
                    logbookId = logbookId,
                    studentName = state.studentName,
                    contents = state.contents,
                    activities = state.activities,
                    learnings = state.learnings,
                    doubts = state.doubts,
                    photoUri = state.photoUri,
                    latitude = state.latitude,
                    longitude = state.longitude
                )
            )
            _entryFormState.value = EntryFormState()
            onResult(true)
        }
    }
}
