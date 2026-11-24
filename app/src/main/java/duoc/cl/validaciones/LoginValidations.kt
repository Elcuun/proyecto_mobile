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
        val trimmed = name.trim()
        if (trimmed.isBlank()) return "El nombre es obligatorio"
        if (trimmed.length < 4) return "El nombre debe tener al menos 4 caracteres"
        return null
    }

    fun validateLastName(name: String): String? {
        val trimmed = name.trim()
        if (trimmed.isBlank()) return "El apellido es obligatorio"
        if (trimmed.length < 4) return "El apellido debe tener al menos 4 caracteres"
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
        val letters = password.count { it.isLetter() }
        val digits = password.count { it.isDigit() }
        if (letters < 2 || digits < 2) {
            return "La contraseña debe tener al menos 2 letras y 2 números"
        }
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
