package duoc.cl.validaciones

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Pruebas unitarias para [LogbookValidations] siguiendo el patrón Arrange-Act-Assert (AAA)
 * de acuerdo a las directrices de Guía Testing Android.
 */
class LogbookValidationsTest {

    // --- validateMonth ---

    @Test
    fun validateMonth_fechaVaciaOSoloEspacios_retornaErrorObligatorio() {
        // ARRANGE
        val monthVacio = ""
        val monthEspacios = "   "

        // ACT
        val resVacio = LogbookValidations.validateMonth(monthVacio)
        val resEspacios = LogbookValidations.validateMonth(monthEspacios)

        // ASSERT
        assertEquals("La fecha es obligatoria", resVacio)
        assertEquals("La fecha es obligatoria", resEspacios)
    }

    @Test
    fun validateMonth_formatoIncorrecto_retornaErrorFormato() {
        // ARRANGE & ACT & ASSERT
        assertEquals("El formato debe ser DD/MM/AAAA (ej. 15/03/2025)", LogbookValidations.validateMonth("15-03-2025"))
        assertEquals("El formato debe ser DD/MM/AAAA (ej. 15/03/2025)", LogbookValidations.validateMonth("15/3/2025"))
        assertEquals("El formato debe ser DD/MM/AAAA (ej. 15/03/2025)", LogbookValidations.validateMonth("2025/03/15"))
        assertEquals("El formato debe ser DD/MM/AAAA (ej. 15/03/2025)", LogbookValidations.validateMonth("marzo 2025"))
    }

    @Test
    fun validateMonth_diaFueraDeRango_retornaErrorDia() {
        // ARRANGE & ACT & ASSERT
        assertEquals("El día debe estar entre 01 y 31", LogbookValidations.validateMonth("00/03/2025"))
        assertEquals("El día debe estar entre 01 y 31", LogbookValidations.validateMonth("32/03/2025"))
    }

    @Test
    fun validateMonth_mesFueraDeRango_retornaErrorMes() {
        // ARRANGE & ACT & ASSERT
        assertEquals("El mes debe estar entre 01 y 12", LogbookValidations.validateMonth("15/00/2025"))
        assertEquals("El mes debe estar entre 01 y 12", LogbookValidations.validateMonth("15/13/2025"))
    }

    @Test
    fun validateMonth_anioAnteriorA2025_retornaErrorAnio() {
        // ARRANGE & ACT & ASSERT
        assertEquals("El año no puede ser anterior a 2025", LogbookValidations.validateMonth("15/03/2024"))
        assertEquals("El año no puede ser anterior a 2025", LogbookValidations.validateMonth("31/12/2000"))
    }

    @Test
    fun validateMonth_fechaValida_retornaNull() {
        // ARRANGE & ACT & ASSERT
        assertNull(LogbookValidations.validateMonth("15/03/2025"))
        assertNull(LogbookValidations.validateMonth("01/01/2025"))
        assertNull(LogbookValidations.validateMonth("31/12/2030"))
    }

    // --- validateTitle ---

    @Test
    fun validateTitle_tituloVacio_retornaError() {
        // ARRANGE & ACT & ASSERT
        assertEquals("El título es obligatorio", LogbookValidations.validateTitle(""))
        assertEquals("El título es obligatorio", LogbookValidations.validateTitle("   "))
    }

    @Test
    fun validateTitle_tituloValido_retornaNull() {
        // ARRANGE & ACT & ASSERT
        assertNull(LogbookValidations.validateTitle("Bitácora de Ciencias"))
    }

    // --- validatePurpose ---

    @Test
    fun validatePurpose_propositoVacio_retornaError() {
        // ARRANGE & ACT & ASSERT
        assertEquals("El propósito es obligatorio", LogbookValidations.validatePurpose(""))
        assertEquals("El propósito es obligatorio", LogbookValidations.validatePurpose("   "))
    }

    @Test
    fun validatePurpose_propositoValido_retornaNull() {
        // ARRANGE & ACT & ASSERT
        assertNull(LogbookValidations.validatePurpose("Comprender ecuaciones de primer grado"))
    }

    // --- validateInstructions ---

    @Test
    fun validateInstructions_instruccionesVacias_retornaError() {
        // ARRANGE & ACT & ASSERT
        assertEquals("Las instrucciones son obligatorias", LogbookValidations.validateInstructions(""))
        assertEquals("Las instrucciones son obligatorias", LogbookValidations.validateInstructions("   "))
    }

    @Test
    fun validateInstructions_instruccionesValidas_retornaNull() {
        // ARRANGE & ACT & ASSERT
        assertNull(LogbookValidations.validateInstructions("Resolver los ejercicios del libro"))
    }

    // --- isLogbookFormValid ---

    @Test
    fun isLogbookFormValid_todosLosCamposValidos_retornaTrue() {
        // ARRANGE
        val month = "15/03/2025"
        val title = "Título Bitácora"
        val purpose = "Propósito Pedagógico"
        val instructions = "Instrucciones de trabajo"

        // ACT
        val result = LogbookValidations.isLogbookFormValid(month, title, purpose, instructions)

        // ASSERT
        assertTrue(result)
    }

    @Test
    fun isLogbookFormValid_campoVacioOConError_retornaFalse() {
        // ARRANGE & ACT & ASSERT
        assertFalse(LogbookValidations.isLogbookFormValid("", "Título", "Propósito", "Instrucciones"))
        assertFalse(LogbookValidations.isLogbookFormValid("15/03/2025", "", "Propósito", "Instrucciones"))
        assertFalse(LogbookValidations.isLogbookFormValid("15/03/2025", "Título", "", "Instrucciones"))
        assertFalse(LogbookValidations.isLogbookFormValid("15/03/2025", "Título", "Propósito", ""))

        assertFalse(LogbookValidations.isLogbookFormValid("15/03/2025", "Título", "Propósito", "Instrucciones", monthError = "Error"))
        assertFalse(LogbookValidations.isLogbookFormValid("15/03/2025", "Título", "Propósito", "Instrucciones", titleError = "Error"))
        assertFalse(LogbookValidations.isLogbookFormValid("15/03/2025", "Título", "Propósito", "Instrucciones", purposeError = "Error"))
        assertFalse(LogbookValidations.isLogbookFormValid("15/03/2025", "Título", "Propósito", "Instrucciones", instructionsError = "Error"))
    }
}
