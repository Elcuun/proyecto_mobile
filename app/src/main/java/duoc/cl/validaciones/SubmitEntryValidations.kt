package duoc.cl.validaciones

object SubmitEntryValidations {
    fun validateStudentName(name: String): String? {
        val trimmed = name.trim()
        if (trimmed.isBlank()) return "El nombre del estudiante es obligatorio"
        if (trimmed.length < 4) return "El nombre debe tener al menos 4 caracteres"
        return null
    }

    fun validateContents(contents: String): String? {
        val trimmed = contents.trim()
        if (trimmed.isBlank()) return "Los contenidos trabajados son obligatorios"
        if (trimmed.length < 4) return "Los contenidos deben tener al menos 4 caracteres"
        return null
    }

    fun validateActivities(activities: String): String? {
        val trimmed = activities.trim()
        if (trimmed.isBlank()) return "Las actividades realizadas son obligatorias"
        if (trimmed.length < 4) return "Las actividades deben tener al menos 4 caracteres"
        return null
    }

    fun validateLearnings(learnings: String): String? {
        val trimmed = learnings.trim()
        if (trimmed.isBlank()) return "Los aprendizajes obtenidos son obligatorios"
        if (trimmed.length < 4) return "Los aprendizajes deben tener al menos 4 caracteres"
        return null
    }

    fun isSubmitEntryFormValid(
        studentName: String,
        contents: String,
        activities: String,
        learnings: String,
        studentNameError: String?,
        contentsError: String?,
        activitiesError: String?,
        learningsError: String?
    ): Boolean {
        return studentName.trim().length >= 4 &&
                contents.trim().length >= 4 &&
                activities.trim().length >= 4 &&
                learnings.trim().length >= 4 &&
                studentNameError == null &&
                contentsError == null &&
                activitiesError == null &&
                learningsError == null
    }
}
