package duoc.cl.validaciones

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Pruebas unitarias para [SubmitEntryValidations] siguiendo el patrón Arrange-Act-Assert (AAA)
 * de acuerdo a las directrices de Guía Testing Android.
 */
class SubmitEntryValidationsTest {

    @Test
    fun validateStudentName_vacioOCorto_retornaError() {
        assertEquals("El nombre del estudiante es obligatorio", SubmitEntryValidations.validateStudentName(""))
        assertEquals("El nombre debe tener al menos 4 caracteres", SubmitEntryValidations.validateStudentName("Ana"))
        assertNull(SubmitEntryValidations.validateStudentName("Ignacia Soto"))
    }

    @Test
    fun validateContents_vacioOCorto_retornaError() {
        assertEquals("Los contenidos trabajados son obligatorios", SubmitEntryValidations.validateContents(""))
        assertEquals("Los contenidos deben tener al menos 4 caracteres", SubmitEntryValidations.validateContents("Mat"))
        assertNull(SubmitEntryValidations.validateContents("Fracciones y decimales"))
    }

    @Test
    fun validateActivities_vacioOCorto_retornaError() {
        assertEquals("Las actividades realizadas son obligatorias", SubmitEntryValidations.validateActivities(""))
        assertEquals("Las actividades deben tener al menos 4 caracteres", SubmitEntryValidations.validateActivities("Gui"))
        assertNull(SubmitEntryValidations.validateActivities("Resolución de guías de trabajo"))
    }

    @Test
    fun validateLearnings_vacioOCorto_retornaError() {
        assertEquals("Los aprendizajes obtenidos son obligatorios", SubmitEntryValidations.validateLearnings(""))
        assertEquals("Los aprendizajes deben tener al menos 4 caracteres", SubmitEntryValidations.validateLearnings("Mal"))
        assertNull(SubmitEntryValidations.validateLearnings("Aprendí a simplificar fracciones"))
    }

    @Test
    fun isSubmitEntryFormValid_formularioCompletoValido_retornaTrue() {
        val result = SubmitEntryValidations.isSubmitEntryFormValid(
            studentName = "Ignacia Soto",
            contents = "Fracciones",
            activities = "Ejercicios",
            learnings = "Aprendizajes",
            studentNameError = null,
            contentsError = null,
            activitiesError = null,
            learningsError = null
        )
        assertTrue(result)
    }

    @Test
    fun isSubmitEntryFormValid_campoCortoOConError_retornaFalse() {
        assertFalse(SubmitEntryValidations.isSubmitEntryFormValid("Ana", "Contenidos", "Actividades", "Aprendizajes", null, null, null, null))
        assertFalse(SubmitEntryValidations.isSubmitEntryFormValid("Nombre", "", "Actividades", "Aprendizajes", null, null, null, null))
    }
}
