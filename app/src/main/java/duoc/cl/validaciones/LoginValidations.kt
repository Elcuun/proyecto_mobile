package duoc.cl.validaciones

import duoc.cl.data.local.Role

object LoginValidations {
    fun validateLoginEmail(email: String): String? {
        if (email.isBlank()) return "El correo es obligatorio"
        if (!email.contains("@")) return "Ingrese un correo válido"
        return null
    }

    fun validateLoginPassword(password: String): String? {
        if (password.isBlank()) return "La contraseña es obligatoria"
        return null
    }

    fun validateFirstName(name: String): String? {
        if (name.isBlank()) return "El nombre es obligatorio"
        return null
    }

    fun validateLastName(name: String): String? {
        if (name.isBlank()) return "El apellido es obligatorio"
        return null
    }

    fun validateRegisterEmail(email: String): String? {
        if (email.isBlank()) return "El correo es obligatorio"
        if (!email.contains("@")) return "Ingrese un correo válido"
        return null
    }

    fun validateRegisterPassword(password: String): String? {
        if (password.isBlank()) return "La contraseña es obligatoria"
        if (password.length < 6) return "La contraseña debe tener al menos 6 caracteres"
        return null
    }

    fun validateConfirmPassword(password: String, confirm: String): String? {
        if (confirm.isBlank()) return "Confirme su contraseña"
        if (password != confirm) return "Las contraseñas no coinciden"
        return null
    }

    fun validateCourseSelection(role: Role, courseId: Long?): String? {
        if (role == Role.ALUMNO && courseId == null) {
            return "Debe seleccionar un curso"
        }
        return null
    }
}
