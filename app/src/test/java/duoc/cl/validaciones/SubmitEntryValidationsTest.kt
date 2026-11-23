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
    fun validateStudentName_vacioOEspacios_retornaError() {
        // ARRANGE & ACT & ASSERT
        assertEquals("El nombre del estudiante es obligatorio", SubmitEntryValidations.validateStudentName(""))
        assertEquals("El nombre del estudiante es obligatorio", SubmitEntryValidations.validateStudentName("   "))
        assertNull(SubmitEntryValidations.validateStudentName("Ignacia Soto"))
    }

    @Test
    fun validateContents_vacioOEspacios_retornaError() {
        // ARRANGE & ACT & ASSERT
        assertEquals("Los contenidos trabajados son obligatorios", SubmitEntryValidations.validateContents(""))
        assertEquals("Los contenidos trabajados son obligatorios", SubmitEntryValidations.validateContents("   "))
        assertNull(SubmitEntryValidations.validateContents("Fracciones y decimales"))
    }

    @Test
    fun validateActivities_vacioOEspacios_retornaError() {
        // ARRANGE & ACT & ASSERT
        assertEquals("Las actividades realizadas son obligatorias", SubmitEntryValidations.validateActivities(""))
        assertEquals("Las actividades realizadas son obligatorias", SubmitEntryValidations.validateActivities("   "))
        assertNull(SubmitEntryValidations.validateActivities("Resolución de guías de trabajo"))
    }

    @Test
    fun validateLearnings_vacioOEspacios_retornaError() {
        // ARRANGE & ACT & ASSERT
        assertEquals("Los aprendizajes obtenidos son obligatorios", SubmitEntryValidations.validateLearnings(""))
        assertEquals("Los aprendizajes obtenidos son obligatorios", SubmitEntryValidations.validateLearnings("   "))
        assertNull(SubmitEntryValidations.validateLearnings("Aprendí a simplificar fracciones"))
    }

    @Test
    fun isSubmitEntryFormValid_formularioCompletoValido_retornaTrue() {
        // ARRANGE
        val name = "Ignacia Soto"
        val contents = "Fracciones"
        val activities = "Ejercicios"
        val learnings = "Comprendí el tema"

        // ACT
        val result = SubmitEntryValidations.isSubmitEntryFormValid(
            studentName = name,
            contents = contents,
            activities = activities,
            learnings = learnings,
            studentNameError = null,
            contentsError = null,
            activitiesError = null,
            learningsError = null
        )

        // ASSERT
        assertTrue(result)
    }

    @Test
    fun isSubmitEntryFormValid_campoVacioOConError_retornaFalse() {
        // ARRANGE & ACT & ASSERT
        assertFalse(SubmitEntryValidations.isSubmitEntryFormValid("", "Contenidos", "Actividades", "Aprendizajes", null, null, null, null))
        assertFalse(SubmitEntryValidations.isSubmitEntryFormValid("Nombre", "", "Actividades", "Aprendizajes", null, null, null, null))
        assertFalse(SubmitEntryValidations.isSubmitEntryFormValid("Nombre", "Contenidos", "", "Aprendizajes", null, null, null, null))
        assertFalse(SubmitEntryValidations.isSubmitEntryFormValid("Nombre", "Contenidos", "Actividades", "", null, null, null, null))

        assertFalse(SubmitEntryValidations.isSubmitEntryFormValid("Nombre", "Contenidos", "Actividades", "Aprendizajes", "Error", null, null, null))
        assertFalse(SubmitEntryValidations.isSubmitEntryFormValid("Nombre", "Contenidos", "Actividades", "Aprendizajes", null, "Error", null, null))
        assertFalse(SubmitEntryValidations.isSubmitEntryFormValid("Nombre", "Contenidos", "Actividades", "Aprendizajes", null, null, "Error", null))
        assertFalse(SubmitEntryValidations.isSubmitEntryFormValid("Nombre", "Contenidos", "Actividades", "Aprendizajes", null, null, null, "Error"))
    }
}
