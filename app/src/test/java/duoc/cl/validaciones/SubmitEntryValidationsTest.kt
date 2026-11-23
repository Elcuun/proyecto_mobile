package duoc.cl.validaciones

import org.junit.Assert.*
import org.junit.Test

/**
 * Clase de pruebas unitarias para validar el formulario de envío de entregas de los estudiantes
 * implementado en [SubmitEntryValidations].
 */
class SubmitEntryValidationsTest {

    /**
     * Prueba la validación del nombre del estudiante.
     * Verifica que rechace nombres vacíos y acepte nombres válidos.
     */
    @Test
    fun testStudentNameValidation() {
        assertNotNull(SubmitEntryValidations.validateStudentName(""))
        assertNull(SubmitEntryValidations.validateStudentName("Ignacia Soto"))
    }

    /**
     * Prueba la validación de los contenidos trabajados.
     * Verifica que rechace campos vacíos y acepte contenidos válidos.
     */
    @Test
    fun testContentsValidation() {
        assertNotNull(SubmitEntryValidations.validateContents(""))
        assertNull(SubmitEntryValidations.validateContents("Fracciones"))
    }

    /**
     * Prueba la validación de las actividades realizadas.
     * Verifica que rechace campos vacíos y acepte actividades válidas.
     */
    @Test
    fun testActivitiesValidation() {
        assertNotNull(SubmitEntryValidations.validateActivities(""))
        assertNull(SubmitEntryValidations.validateActivities("Ejercicios en pizarra"))
    }

    /**
     * Prueba la validación de los aprendizajes obtenidos.
     * Verifica que rechace campos vacíos y acepte aprendizajes válidos.
     */
    @Test
    fun testLearningsValidation() {
        assertNotNull(SubmitEntryValidations.validateLearnings(""))
        assertNull(SubmitEntryValidations.validateLearnings("Comprendí las fracciones"))
    }

    /**
     * Prueba la validación global del formulario de entrega de bitácora.
     * Verifica que retorne true cuando todos los campos están completos y sin errores,
     * y false si hay errores o campos obligatorios vacíos.
     */
    @Test
    fun testFormValidity() {
        // Formulario completamente válido
        val valid = SubmitEntryValidations.isSubmitEntryFormValid(
            studentName = "Ignacia Soto",
            contents = "Fracciones",
            activities = "Ejercicios",
            learnings = "Aprendizajes",
            studentNameError = null,
            contentsError = null,
            activitiesError = null,
            learningsError = null
        )
        assertTrue(valid)

        // Formulario con error en el nombre del estudiante
        val invalid = SubmitEntryValidations.isSubmitEntryFormValid(
            studentName = "",
            contents = "Fracciones",
            activities = "Ejercicios",
            learnings = "Aprendizajes",
            studentNameError = "Error",
            contentsError = null,
            activitiesError = null,
            learningsError = null
        )
        assertFalse(invalid)
    }
}
