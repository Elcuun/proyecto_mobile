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

    @Test
    fun validateLoginEmail_correoVacio_retornaError() {
        assertEquals("El correo es obligatorio", LoginValidations.validateLoginEmail(""))
        assertEquals("El correo es obligatorio", LoginValidations.validateLoginEmail("   "))
    }

    @Test
    fun validateLoginEmail_correoSinArroba_retornaErrorFormato() {
        assertEquals("Ingrese un correo válido", LoginValidations.validateLoginEmail("usuarioduoc.cl"))
    }

    @Test
    fun validateLoginEmail_correoValido_retornaNull() {
        assertNull(LoginValidations.validateLoginEmail("docente@duoc.cl"))
        assertNull(LoginValidations.validateLoginEmail("estudiante.soto@duoc.cl"))
    }

    @Test
    fun validateLoginPassword_contraseniaVacia_retornaError() {
        assertEquals("La contraseña es obligatoria", LoginValidations.validateLoginPassword(""))
        assertEquals("La contraseña es obligatoria", LoginValidations.validateLoginPassword("   "))
    }

    @Test
    fun validateLoginPassword_contraseniaValida_retornaNull() {
        assertNull(LoginValidations.validateLoginPassword("123456"))
    }

    @Test
    fun validateFirstName_nombreInvalido_retornaError() {
        assertEquals("El nombre es obligatorio", LoginValidations.validateFirstName(""))
        assertEquals("El nombre es obligatorio", LoginValidations.validateFirstName("   "))
        assertEquals("El nombre debe tener al menos 4 caracteres", LoginValidations.validateFirstName("Ana"))
        assertNull(LoginValidations.validateFirstName("María"))
    }

    @Test
    fun validateLastName_apellidoInvalido_retornaError() {
        assertEquals("El apellido es obligatorio", LoginValidations.validateLastName(""))
        assertEquals("El apellido es obligatorio", LoginValidations.validateLastName("   "))
        assertEquals("El apellido debe tener al menos 4 caracteres", LoginValidations.validateLastName("Gil"))
        assertNull(LoginValidations.validateLastName("González"))
    }

    @Test
    fun validateRegisterEmail_correoVacioOSinArroba_retornaError() {
        assertEquals("El correo es obligatorio", LoginValidations.validateRegisterEmail(""))
        assertEquals("Ingrese un correo válido", LoginValidations.validateRegisterEmail("correosin-arroba.com"))
        assertNull(LoginValidations.validateRegisterEmail("estudiante@duoc.cl"))
    }

    @Test
    fun validateRegisterPassword_reglasContrasenia_retornaErroresOExito() {
        assertEquals("La contraseña es obligatoria", LoginValidations.validateRegisterPassword(""))
        assertEquals("La contraseña debe tener al menos 6 caracteres", LoginValidations.validateRegisterPassword("ab12"))
        assertEquals("La contraseña debe tener al menos 2 letras y 2 números", LoginValidations.validateRegisterPassword("123456"))
        assertEquals("La contraseña debe tener al menos 2 letras y 2 números", LoginValidations.validateRegisterPassword("abcdef"))
        assertNull(LoginValidations.validateRegisterPassword("ab1234"))
    }

    @Test
    fun validateConfirmPassword_vacioODiferente_retornaError() {
        assertEquals("Confirme su contraseña", LoginValidations.validateConfirmPassword("ab1234", ""))
        assertEquals("Las contraseñas no coinciden", LoginValidations.validateConfirmPassword("ab1234", "654321"))
        assertNull(LoginValidations.validateConfirmPassword("ab1234", "ab1234"))
    }

    @Test
    fun validateCourseSelection_alumnoSinCurso_retornaError() {
        assertEquals("Debe seleccionar un curso", LoginValidations.validateCourseSelection(Role.ALUMNO, null))
        assertNull(LoginValidations.validateCourseSelection(Role.ALUMNO, 1L))
        assertNull(LoginValidations.validateCourseSelection(Role.PROFESOR, null))
        assertNull(LoginValidations.validateCourseSelection(Role.APODERADO, null))
    }
}
