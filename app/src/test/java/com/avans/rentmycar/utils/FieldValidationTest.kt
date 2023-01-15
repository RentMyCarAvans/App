package com.avans.rentmycar.utils



import org.junit.Assert.assertFalse
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class FieldValidationTest {

    @Test
    fun isValidLicensePlateTest() {
        assertTrue(FieldValidation.isValidLicensePlate ("HF067X"))
    }

    @Test
    fun isInvalidLicensePlateTest() {
        val expected = false
        assertEquals(expected, FieldValidation.isValidLicensePlate ("INVALI"))
    }

    @Test
    fun isInValidLicensePlateBecauseOfDashesTest() {
        assertFalse(FieldValidation.isValidLicensePlate ("HF-067-X"))
    }

    @Test
    fun isValidEmailTest() {
        val validEmail = "rob@rentmycar.com"
        val invalidEmail = "rob#rentmycar,com"

        assertTrue("Asserting true for valid email", FieldValidation.isValidEmail(validEmail))
        assertFalse("Asserting false for invalid email", FieldValidation.isValidEmail(invalidEmail))
    }

    @Test
    fun isValidBirthDate() {
        val validBirthDate = "2023-01-01"
        val invalidBirthdate = "01-01-2023"

        assertTrue("Asserting true for valid birthdate", FieldValidation.isValidBirthDate(validBirthDate))
        assertFalse("Asserting false for invalid birthdate", FieldValidation.isValidBirthDate(invalidBirthdate))
    }
}