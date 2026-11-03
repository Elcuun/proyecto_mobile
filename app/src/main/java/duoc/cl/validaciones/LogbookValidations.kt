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
     * Valida el título de la bitácora mensual.
     * @param title Título ingresado.
     * @return Mensaje de error si está vacío, o null si es válido.
     */
    fun validateTitle(title: String): String? {
        return if (title.isBlank()) "El título es obligatorio" else null
    }

    /**
     * Valida el propósito pedagógico de la bitácora.
     * @param purpose Propósito ingresado.
     * @return Mensaje de error si está vacío, o null si es válido.
     */
    fun validatePurpose(purpose: String): String? {
        return if (purpose.isBlank()) "El propósito es obligatorio" else null
    }

    /**
     * Valida las instrucciones para el estudiante.
     * @param instructions Instrucciones ingresadas.
     * @return Mensaje de error si está vacío, o null si es válido.
     */
    fun validateInstructions(instructions: String): String? {
        return if (instructions.isBlank()) "Las instrucciones son obligatorias" else null
    }

    /**
     * Determina si el formulario completo de la bitácora es válido para habilitar su publicación.
     * @return true si todos los campos requeridos están completos y sin errores.
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
                title.isNotBlank() &&
                purpose.isNotBlank() &&
                instructions.isNotBlank() &&
                monthError == null &&
                titleError == null &&
                purposeError == null &&
                instructionsError == null
    }
}
