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
        assertEquals("La fecha es obligatoria", LogbookValidations.validateMonth(""))
        assertEquals("La fecha es obligatoria", LogbookValidations.validateMonth("   "))
    }

    @Test
    fun validateMonth_formatoIncorrecto_retornaErrorFormato() {
        assertEquals("El formato debe ser DD/MM/AAAA (ej. 15/03/2025)", LogbookValidations.validateMonth("15-03-2025"))
        assertEquals("El formato debe ser DD/MM/AAAA (ej. 15/03/2025)", LogbookValidations.validateMonth("15/3/2025"))
        assertEquals("El formato debe ser DD/MM/AAAA (ej. 15/03/2025)", LogbookValidations.validateMonth("2025/03/15"))
    }

    @Test
    fun validateMonth_diaFueraDeRango_retornaErrorDia() {
        assertEquals("El día debe estar entre 01 y 31", LogbookValidations.validateMonth("00/03/2025"))
        assertEquals("El día debe estar entre 01 y 31", LogbookValidations.validateMonth("32/03/2025"))
    }

    @Test
    fun validateMonth_mesFueraDeRango_retornaErrorMes() {
        assertEquals("El mes debe estar entre 01 y 12", LogbookValidations.validateMonth("15/00/2025"))
        assertEquals("El mes debe estar entre 01 y 12", LogbookValidations.validateMonth("15/13/2025"))
    }

    @Test
    fun validateMonth_anioAnteriorA2025_retornaErrorAnio() {
        assertEquals("El año no puede ser anterior a 2025", LogbookValidations.validateMonth("15/03/2024"))
    }

    @Test
    fun validateMonth_fechaValida_retornaNull() {
        assertNull(LogbookValidations.validateMonth("15/03/2025"))
    }

    // --- validateTitle ---

    @Test
    fun validateTitle_tituloVacioOCorto_retornaError() {
        assertEquals("El título es obligatorio", LogbookValidations.validateTitle(""))
        assertEquals("El título debe tener al menos 4 caracteres", LogbookValidations.validateTitle("Mat"))
        assertNull(LogbookValidations.validateTitle("Matemáticas"))
    }

    // --- validatePurpose ---

    @Test
    fun validatePurpose_propositoVacioOCorto_retornaError() {
        assertEquals("El propósito es obligatorio", LogbookValidations.validatePurpose(""))
        assertEquals("El propósito debe tener al menos 4 caracteres", LogbookValidations.validatePurpose("Fin"))
        assertNull(LogbookValidations.validatePurpose("Propósito pedagógico"))
    }

    // --- validateInstructions ---

    @Test
    fun validateInstructions_instruccionesVaciasOCortas_retornaError() {
        assertEquals("Las instrucciones son obligatorias", LogbookValidations.validateInstructions(""))
        assertEquals("Las instrucciones deben tener al menos 4 caracteres", LogbookValidations.validateInstructions("Lee"))
        assertNull(LogbookValidations.validateInstructions("Instrucciones detalladas"))
    }

    // --- isLogbookFormValid ---

    @Test
    fun isLogbookFormValid_todosLosCamposValidos_retornaTrue() {
        val result = LogbookValidations.isLogbookFormValid("15/03/2025", "Título", "Propósito", "Instrucciones")
        assertTrue(result)
    }

    @Test
    fun isLogbookFormValid_campoCortoOInvalido_retornaFalse() {
        assertFalse(LogbookValidations.isLogbookFormValid("15/03/2025", "Tip", "Propósito", "Instrucciones"))
        assertFalse(LogbookValidations.isLogbookFormValid("15/03/2025", "Título", "", "Instrucciones"))
    }
}
