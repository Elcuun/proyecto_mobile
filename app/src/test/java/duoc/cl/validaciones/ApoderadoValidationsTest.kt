package duoc.cl.validaciones

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test

/**
 * Pruebas unitarias para [ApoderadoValidations] siguiendo el patrón Arrange-Act-Assert (AAA)
 * de acuerdo a las directrices de Guía Testing Android.
 */
class ApoderadoValidationsTest {

    @Test
    fun validateStudentSearchQuery_ambosCamposVacios_retornaMensajeDeError() {
        // ARRANGE
        val name = ""
        val email = ""

        // ACT
        val result = ApoderadoValidations.validateStudentSearchQuery(name, email)

        // ASSERT
        assertNotNull(result)
        assertEquals("Debe ingresar al menos el nombre o el correo del estudiante.", result)
    }

    @Test
    fun validateStudentSearchQuery_ambosCamposSoloEspacios_retornaMensajeDeError() {
        // ARRANGE
        val name = "   "
        val email = "  \t "

        // ACT
        val result = ApoderadoValidations.validateStudentSearchQuery(name, email)

        // ASSERT
        assertNotNull(result)
        assertEquals("Debe ingresar al menos el nombre o el correo del estudiante.", result)
    }

    @Test
    fun validateStudentSearchQuery_soloNombrePresente_retornaNull() {
        // ARRANGE
        val name = "Ignacia Soto"
        val email = ""

        // ACT
        val result = ApoderadoValidations.validateStudentSearchQuery(name, email)

        // ASSERT
        assertNull(result)
    }

    @Test
    fun validateStudentSearchQuery_soloEmailPresente_retornaNull() {
        // ARRANGE
        val name = ""
        val email = "estudiante@duoc.cl"

        // ACT
        val result = ApoderadoValidations.validateStudentSearchQuery(name, email)

        // ASSERT
        assertNull(result)
    }

    @Test
    fun validateStudentSearchQuery_ambosCamposPresentes_retornaNull() {
        // ARRANGE
        val name = "Ignacia Soto"
        val email = "estudiante@duoc.cl"

        // ACT
        val result = ApoderadoValidations.validateStudentSearchQuery(name, email)

        // ASSERT
        assertNull(result)
    }
}
