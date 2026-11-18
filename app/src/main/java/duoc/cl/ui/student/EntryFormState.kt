package duoc.cl.ui.student

data class EntryFormState(
    val studentName: String = "",
    val contents: String = "",
    val activities: String = "",
    val learnings: String = "",
    val doubts: String = "",
    val photoUri: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val studentNameError: String? = null,
    val contentsError: String? = null,
    val activitiesError: String? = null,
    val learningsError: String? = null,
    val isFormValid: Boolean = false
)
