package duoc.cl.validaciones

object SubmitEntryValidations {
    fun validateStudentName(name: String): String? {
        if (name.isBlank()) return "El nombre del estudiante es obligatorio"
        return null
    }

    fun validateContents(contents: String): String? {
        if (contents.isBlank()) return "Los contenidos trabajados son obligatorios"
        return null
    }

    fun validateActivities(activities: String): String? {
        if (activities.isBlank()) return "Las actividades realizadas son obligatorias"
        return null
    }

    fun validateLearnings(learnings: String): String? {
        if (learnings.isBlank()) return "Los aprendizajes obtenidos son obligatorios"
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
        return studentName.isNotBlank() &&
                contents.isNotBlank() &&
                activities.isNotBlank() &&
                learnings.isNotBlank() &&
                studentNameError == null &&
                contentsError == null &&
                activitiesError == null &&
                learningsError == null
    }
}
