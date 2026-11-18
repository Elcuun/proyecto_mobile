package duoc.cl.ui.apoderado

data class ApoderadoUiState(
    val studentName: String = "",
    val studentEmail: String = "",
    val isSearchSubmitted: Boolean = false,
    val errorMessage: String? = null
)
