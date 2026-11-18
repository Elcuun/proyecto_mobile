package duoc.cl.api.model

import duoc.cl.data.local.Role

data class UserRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val role: Role,
    val courseId: Long? = null
)

data class LoginRequest(
    val email: String,
    val password: String,
    val role: Role
)

data class UserResponse(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val role: Role,
    val courseId: Long? = null
)

data class AuthResponse(
    val user: UserResponse,
    val token: String
)

data class NamedResponse(
    val id: Long,
    val name: String
)

data class LogbookRequest(
    val courseId: Long,
    val subjectId: Long,
    val month: String,
    val title: String,
    val purpose: String,
    val instructions: String,
    val guidingQuestions: String? = null
)

data class LogbookResponse(
    val id: Long,
    val courseId: Long,
    val subjectId: Long,
    val month: String,
    val title: String,
    val purpose: String,
    val instructions: String,
    val guidingQuestions: String? = null,
    val isClosed: Boolean = false,
    val createdAt: String? = null
)

data class EntryRequest(
    val studentName: String,
    val contents: String,
    val activities: String,
    val learnings: String,
    val doubts: String? = null,
    val photoUri: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null
)

data class ReviewRequest(
    val feedback: String?,
    val isReviewed: Boolean
)

data class EntryResponse(
    val id: Long,
    val logbookId: Long,
    val studentName: String,
    val contents: String,
    val activities: String,
    val learnings: String,
    val doubts: String? = null,
    val photoUri: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val feedback: String? = null,
    val isReviewed: Boolean = false,
    val createdAt: String? = null
)
