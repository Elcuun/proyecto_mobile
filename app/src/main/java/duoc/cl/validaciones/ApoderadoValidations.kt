package duoc.cl.validaciones

object ApoderadoValidations {
    fun validateStudentSearchQuery(name: String, email: String): String? {
        val trimmedName = name.trim()
        val trimmedEmail = email.trim()
        if (trimmedName.isBlank() && trimmedEmail.isBlank()) {
            return "Debe ingresar al menos el nombre o el correo del estudiante."
        }
        return null
    }
}
