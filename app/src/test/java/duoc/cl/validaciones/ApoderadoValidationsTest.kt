package duoc.cl.validaciones

import org.junit.Assert.*
import org.junit.Test

/**
 * Clase de pruebas unitarias para validar las consultas de los apoderados
 * implementada en [ApoderadoValidations].
 */
class ApoderadoValidationsTest {

    /**
     * Prueba la validación de la consulta de búsqueda de estudiantes por parte del apoderado.
     * Verifica que si ambos campos (nombre y correo) están vacíos retorne un mensaje de error,
     * y si al menos uno de los dos está presente permita realizar la búsqueda (retorne null).
     */
    @Test
    fun testStudentSearchQueryValidation() {
        // Ambos campos vacíos (debe retornar error)
        assertNotNull(ApoderadoValidations.validateStudentSearchQuery("", ""))
        // Solo nombre presente (válido, retorna null)
        assertNull(ApoderadoValidations.validateStudentSearchQuery("Ignacia Soto", ""))
        // Solo correo presente (válido, retorna null)
        assertNull(ApoderadoValidations.validateStudentSearchQuery("", "estudiante@duoc.cl"))
        // Nombre y correo presentes (válido, retorna null)
        assertNull(ApoderadoValidations.validateStudentSearchQuery("Ignacia Soto", "estudiante@duoc.cl"))
    }
}
