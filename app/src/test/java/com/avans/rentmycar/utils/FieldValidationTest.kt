package com.avans.rentmycar.utils

import android.util.Log
import junit.framework.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class FieldValidationTest {

    @Test
    fun isValidLicensePlate() {
        Log.d("[RMCUT]","Validate: "+ FieldValidation.isValidLicensePlate("HF067X") )
        assertTrue(FieldValidation.isValidLicensePlate ("HF067X"))
    }

    @Test
    fun isInvalidLicensePlate() {
        val expected = false
        //assertEquals(expected, FieldValidation.isValidLicensePlate ("INVALI"))
        assertEquals(expected, false)
    }
}