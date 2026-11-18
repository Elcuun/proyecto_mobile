package duoc.cl.ui.teacher

data class LogbookFormState(
    val month: String = "",
    val title: String = "",
    val purpose: String = "",
    val instructions: String = "",
    val guidingQuestions: String = "",
    val monthError: String? = null,
    val titleError: String? = null,
    val purposeError: String? = null,
    val instructionsError: String? = null,
    val isFormValid: Boolean = false
)
