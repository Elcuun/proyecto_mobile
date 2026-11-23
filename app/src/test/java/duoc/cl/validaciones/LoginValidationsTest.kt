package duoc.cl.validaciones

import duoc.cl.data.local.Role
import org.junit.Assert.*
import org.junit.Test

/**
 * Clase de pruebas unitarias para validar las reglas de negocio de autenticación y registro
 * implementadas en [LoginValidations].
 */
class LoginValidationsTest {

    /**
     * Prueba la validación del correo electrónico en el inicio de sesión.
     * Verifica que rechace correos vacíos o sin '@' y acepte correos con formato válido.
     */
    @Test
    fun testLoginEmailValidation() {
        // Valida que un correo vacío retorne un mensaje de error
        assertNotNull(LoginValidations.validateLoginEmail(""))
        // Valida que un correo sin el carácter '@' retorne un error
        assertNotNull(LoginValidations.validateLoginEmail("invalidemail"))
        // Valida que un correo correcto retorne null (sin errores)
        assertNull(LoginValidations.validateLoginEmail("docente@duoc.cl"))
    }

    /**
     * Prueba la validación de la contraseña en el inicio de sesión.
     * Verifica que rechace contraseñas vacías.
     */
    @Test
    fun testLoginPasswordValidation() {
        // Valida que una contraseña vacía retorne un mensaje de error
        assertNotNull(LoginValidations.validateLoginPassword(""))
        // Valida que una contraseña no vacía retorne null (sin errores)
        assertNull(LoginValidations.validateLoginPassword("123456"))
    }

    /**
     * Prueba la validación de los nombres y apellidos en el registro de usuarios.
     * Verifica que los campos obligatorios rechacen textos vacíos.
     */
    @Test
    fun testRegisterNameValidation() {
        // Valida nombre vacío (debe retornar error) y nombre válido (debe retornar null)
        assertNotNull(LoginValidations.validateFirstName(""))
        assertNull(LoginValidations.validateFirstName("María"))

        // Valida apellido vacío (debe retornar error) y apellido válido (debe retornar null)
        assertNotNull(LoginValidations.validateLastName(""))
        assertNull(LoginValidations.validateLastName("González"))
    }

    /**
     * Prueba la validación de la contraseña en el registro de usuarios.
     * Verifica que la contraseña no esté vacía y cumpla con la longitud mínima (6 caracteres).
     */
    @Test
    fun testRegisterPasswordValidation() {
        // Valida contraseña vacía
        assertNotNull(LoginValidations.validateRegisterPassword(""))
        // Valida contraseña demasiado corta (< 6 caracteres)
        assertNotNull(LoginValidations.validateRegisterPassword("123"))
        // Valida contraseña correcta (>= 6 caracteres)
        assertNull(LoginValidations.validateRegisterPassword("123456"))
    }

    /**
     * Prueba la validación de la confirmación de contraseña en el registro.
     * Verifica que la confirmación no esté vacía y coincida exactamente con la contraseña principal.
     */
    @Test
    fun testConfirmPasswordValidation() {
        // Valida confirmación vacía
        assertNotNull(LoginValidations.validateConfirmPassword("123456", ""))
        // Valida contraseñas que no coinciden
        assertNotNull(LoginValidations.validateConfirmPassword("123456", "654321"))
        // Valida contraseñas idénticas
        assertNull(LoginValidations.validateConfirmPassword("123456", "123456"))
    }

    /**
     * Prueba la validación de selección de curso según el rol del usuario.
     * Verifica que sea obligatorio seleccionar un curso si el rol es ALUMNO,
     * y opcional/null para otros roles (Profesor, Apoderado).
     */
    @Test
    fun testCourseSelectionValidation() {
        // Alumno sin curso seleccionado (debe retornar error)
        assertNotNull(LoginValidations.validateCourseSelection(Role.ALUMNO, null))
        // Alumno con curso seleccionado (debe retornar null)
        assertNull(LoginValidations.validateCourseSelection(Role.ALUMNO, 1L))
        // Profesor sin curso (debe retornar null)
        assertNull(LoginValidations.validateCourseSelection(Role.PROFESOR, null))
        // Apoderado sin curso (debe retornar null)
        assertNull(LoginValidations.validateCourseSelection(Role.APODERADO, null))
    }
}
