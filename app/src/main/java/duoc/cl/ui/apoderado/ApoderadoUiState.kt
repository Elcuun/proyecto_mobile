package duoc.cl.ui.apoderado

import duoc.cl.data.local.LogbookEntity
import duoc.cl.data.local.LogbookEntryEntity

data class ApoderadoUiState(
    val studentName: String = "",
    val studentEmail: String = "",
    val isSearchSubmitted: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val entries: List<Pair<LogbookEntity, LogbookEntryEntity>> = emptyList()
)
