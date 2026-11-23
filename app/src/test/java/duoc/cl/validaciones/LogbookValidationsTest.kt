package duoc.cl.validaciones

import org.junit.Assert.*
import org.junit.Test

/**
 * Clase de pruebas unitarias para validar la creación y edición de bitácoras por parte del docente
 * implementada en [LogbookValidations].
 */
class LogbookValidationsTest {

    /**
     * Prueba la validación de la fecha o periodo de la bitácora en formato DD/MM/AAAA.
     * Verifica que rechace campos vacíos, formatos incorrectos, días fuera de rango (1-31),
     * meses fuera de rango (1-12) y años anteriores a 2025, aceptando únicamente fechas válidas.
     */
    @Test
    fun testMonthDateValidation() {
        assertNotNull(LogbookValidations.validateMonth(""))
        assertNotNull(LogbookValidations.validateMonth("15-03-2025")) // Formato incorrecto (guiones en vez de barras)
        assertNotNull(LogbookValidations.validateMonth("32/03/2025")) // Día inválido (> 31)
        assertNotNull(LogbookValidations.validateMonth("15/13/2025")) // Mes inválido (> 12)
        assertNotNull(LogbookValidations.validateMonth("15/03/2024")) // Año anterior a 2025 (< 2025)
        assertNull(LogbookValidations.validateMonth("15/03/2025"))   // Fecha correcta
    }

    /**
     * Prueba la validación del título de la bitácora.
     * Verifica que rechace títulos vacíos y acepte textos válidos.
     */
    @Test
    fun testTitleValidation() {
        assertNotNull(LogbookValidations.validateTitle(""))
        assertNull(LogbookValidations.validateTitle("Bitácora de Matemáticas"))
    }

    /**
     * Prueba la validación del propósito pedagógico de la bitácora.
     * Verifica que rechace propósitos vacíos y acepte textos válidos.
     */
    @Test
    fun testPurposeValidation() {
        assertNotNull(LogbookValidations.validatePurpose(""))
        assertNull(LogbookValidations.validatePurpose("Propósito pedagógico"))
    }

    /**
     * Prueba la validación de las instrucciones para el estudiante.
     * Verifica que rechace instrucciones vacías y acepte textos válidos.
     */
    @Test
    fun testInstructionsValidation() {
        assertNotNull(LogbookValidations.validateInstructions(""))
        assertNull(LogbookValidations.validateInstructions("Instrucciones detalladas"))
    }

    /**
     * Prueba la validación global del formulario de creación de bitácoras.
     * Verifica que retorne true cuando todos los campos son válidos y sin errores,
     * y false si hay errores o campos obligatorios vacíos.
     */
    @Test
    fun testLogbookFormValidity() {
        val valid = LogbookValidations.isLogbookFormValid(
            month = "15/03/2025",
            title = "Título",
            purpose = "Propósito",
            instructions = "Instrucciones"
        )
        assertTrue(valid)

        val invalid = LogbookValidations.isLogbookFormValid(
            month = "",
            title = "Título",
            purpose = "Propósito",
            instructions = "Instrucciones",
            monthError = "Error"
        )
        assertFalse(invalid)
    }
}
