package duoc.cl.validaciones

import duoc.cl.data.local.Role
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test

/**
 * Pruebas unitarias para [LoginValidations] siguiendo el patrón Arrange-Act-Assert (AAA)
 * de acuerdo a las directrices de Guía Testing Android.
 */
class LoginValidationsTest {

    // --- validateLoginEmail ---

    @Test
    fun validateLoginEmail_correoVacio_retornaError() {
        // ARRANGE & ACT & ASSERT
        assertEquals("El correo es obligatorio", LoginValidations.validateLoginEmail(""))
        assertEquals("El correo es obligatorio", LoginValidations.validateLoginEmail("   "))
    }

    @Test
    fun validateLoginEmail_correoSinArroba_retornaErrorFormato() {
        // ARRANGE & ACT & ASSERT
        assertEquals("Ingrese un correo válido", LoginValidations.validateLoginEmail("usuarioduoc.cl"))
    }

    @Test
    fun validateLoginEmail_correoValido_retornaNull() {
        // ARRANGE & ACT & ASSERT
        assertNull(LoginValidations.validateLoginEmail("docente@duoc.cl"))
        assertNull(LoginValidations.validateLoginEmail("estudiante.soto@duoc.cl"))
    }

    // --- validateLoginPassword ---

    @Test
    fun validateLoginPassword_contraseniaVacia_retornaError() {
        // ARRANGE & ACT & ASSERT
        assertEquals("La contraseña es obligatoria", LoginValidations.validateLoginPassword(""))
        assertEquals("La contraseña es obligatoria", LoginValidations.validateLoginPassword("   "))
    }

    @Test
    fun validateLoginPassword_contraseniaValida_retornaNull() {
        // ARRANGE & ACT & ASSERT
        assertNull(LoginValidations.validateLoginPassword("123456"))
    }

    // --- validateFirstName & validateLastName ---

    @Test
    fun validateFirstName_nombreVacio_retornaError() {
        // ARRANGE & ACT & ASSERT
        assertEquals("El nombre es obligatorio", LoginValidations.validateFirstName(""))
        assertEquals("El nombre es obligatorio", LoginValidations.validateFirstName("   "))
        assertNull(LoginValidations.validateFirstName("María"))
    }

    @Test
    fun validateLastName_apellidoVacio_retornaError() {
        // ARRANGE & ACT & ASSERT
        assertEquals("El apellido es obligatorio", LoginValidations.validateLastName(""))
        assertEquals("El apellido es obligatorio", LoginValidations.validateLastName("   "))
        assertNull(LoginValidations.validateLastName("González"))
    }

    // --- validateRegisterEmail ---

    @Test
    fun validateRegisterEmail_correoVacioOSinArroba_retornaError() {
        // ARRANGE & ACT & ASSERT
        assertEquals("El correo es obligatorio", LoginValidations.validateRegisterEmail(""))
        assertEquals("Ingrese un correo válido", LoginValidations.validateRegisterEmail("correosin-arroba.com"))
        assertNull(LoginValidations.validateRegisterEmail("estudiante@duoc.cl"))
    }

    // --- validateRegisterPassword ---

    @Test
    fun validateRegisterPassword_vacioOCorta_retornaError() {
        // ARRANGE & ACT & ASSERT
        assertEquals("La contraseña es obligatoria", LoginValidations.validateRegisterPassword(""))
        assertEquals("La contraseña debe tener al menos 6 caracteres", LoginValidations.validateRegisterPassword("12345"))
        assertNull(LoginValidations.validateRegisterPassword("123456"))
    }

    // --- validateConfirmPassword ---

    @Test
    fun validateConfirmPassword_vacioODiferente_retornaError() {
        // ARRANGE & ACT & ASSERT
        assertEquals("Confirme su contraseña", LoginValidations.validateConfirmPassword("123456", ""))
        assertEquals("Las contraseñas no coinciden", LoginValidations.validateConfirmPassword("123456", "654321"))
        assertNull(LoginValidations.validateConfirmPassword("123456", "123456"))
    }

    // --- validateCourseSelection ---

    @Test
    fun validateCourseSelection_alumnoSinCurso_retornaError() {
        // ARRANGE & ACT & ASSERT
        assertEquals("Debe seleccionar un curso", LoginValidations.validateCourseSelection(Role.ALUMNO, null))
    }

    @Test
    fun validateCourseSelection_alumnoConCurso_retornaNull() {
        // ARRANGE & ACT & ASSERT
        assertNull(LoginValidations.validateCourseSelection(Role.ALUMNO, 1L))
    }

    @Test
    fun validateCourseSelection_profesorOApoderadoSinCurso_retornaNull() {
        // ARRANGE & ACT & ASSERT
        assertNull(LoginValidations.validateCourseSelection(Role.PROFESOR, null))
        assertNull(LoginValidations.validateCourseSelection(Role.APODERADO, null))
    }
}
