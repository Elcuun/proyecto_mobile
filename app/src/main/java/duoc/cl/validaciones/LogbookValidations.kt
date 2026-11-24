package duoc.cl.validaciones

/**
 * Objeto de validación para la creación de Bitácoras Mensuales por parte del Docente.
 * 
 * Pertenece a la vista:
 * - CreateLogbookScreen (Pantalla de Creación de Bitácora del Profesor)
 *
 * Utilizado por: TeacherViewModel
 */
object LogbookValidations {

    /**
     * Valida la fecha de la bitácora en formato Día/Mes/Año (00/00/0000).
     * 
     * Reglas:
     * 1. Formato requerido: DD/MM/AAAA (ej. 15/03/2025).
     * 2. Día válido (entre 01 y 31).
     * 3. Mes válido (entre 01 y 12).
     * 4. Año no puede ser anterior a 2025 (año >= 2025).
     * 
     * @param month Texto ingresado para la fecha/periodo de la bitácora.
     * @return Mensaje de error detallado si es inválido, o null si es correcto.
     */
    fun validateMonth(month: String): String? {
        val trimmed = month.trim()
        if (trimmed.isBlank()) {
            return "La fecha es obligatoria"
        }

        // Formato obligatorio 00/00/0000 (DD/MM/AAAA)
        val regex = Regex("""^\d{2}/\d{2}/\d{4}$""")
        if (!regex.matches(trimmed)) {
            return "El formato debe ser DD/MM/AAAA (ej. 15/03/2025)"
        }

        val parts = trimmed.split("/")
        val day = parts[0].toIntOrNull() ?: return "Día inválido"
        val m = parts[1].toIntOrNull() ?: return "Mes inválido"
        val year = parts[2].toIntOrNull() ?: return "Año inválido"

        if (day !in 1..31) {
            return "El día debe estar entre 01 y 31"
        }

        if (m !in 1..12) {
            return "El mes debe estar entre 01 y 12"
        }

        if (year < 2025) {
            return "El año no puede ser anterior a 2025"
        }

        return null
    }

    /**
     * Valida el título de la bitácora mensual (mínimo 4 caracteres).
     * @param title Título ingresado.
     * @return Mensaje de error si está vacío o tiene menos de 4 caracteres, o null si es válido.
     */
    fun validateTitle(title: String): String? {
        val trimmed = title.trim()
        if (trimmed.isBlank()) return "El título es obligatorio"
        if (trimmed.length < 4) return "El título debe tener al menos 4 caracteres"
        return null
    }

    /**
     * Valida el propósito pedagógico de la bitácora (mínimo 4 caracteres).
     * @param purpose Propósito ingresado.
     * @return Mensaje de error si está vacío o tiene menos de 4 caracteres, o null si es válido.
     */
    fun validatePurpose(purpose: String): String? {
        val trimmed = purpose.trim()
        if (trimmed.isBlank()) return "El propósito es obligatorio"
        if (trimmed.length < 4) return "El propósito debe tener al menos 4 caracteres"
        return null
    }

    /**
     * Valida las instrucciones para el estudiante (mínimo 4 caracteres).
     * @param instructions Instrucciones ingresadas.
     * @return Mensaje de error si está vacío o tiene menos de 4 caracteres, o null si es válido.
     */
    fun validateInstructions(instructions: String): String? {
        val trimmed = instructions.trim()
        if (trimmed.isBlank()) return "Las instrucciones son obligatorias"
        if (trimmed.length < 4) return "Las instrucciones deben tener al menos 4 caracteres"
        return null
    }

    /**
     * Determina si el formulario completo de la bitácora es válido para habilitar su publicación.
     * @return true si todos los campos requeridos están completos, cumplen la longitud mínima y sin errores.
     */
    fun isLogbookFormValid(
        month: String,
        title: String,
        purpose: String,
        instructions: String,
        monthError: String? = null,
        titleError: String? = null,
        purposeError: String? = null,
        instructionsError: String? = null
    ): Boolean {
        return month.isNotBlank() &&
                title.trim().length >= 4 &&
                purpose.trim().length >= 4 &&
                instructions.trim().length >= 4 &&
                monthError == null &&
                titleError == null &&
                purposeError == null &&
                instructionsError == null
    }
}
