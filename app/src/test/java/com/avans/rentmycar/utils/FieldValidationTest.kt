package com.avans.rentmycar.utils


import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertFalse
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
}