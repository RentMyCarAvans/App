package com.avans.rentmycar.utils

import android.util.Log
import android.util.Patterns
import androidx.core.util.PatternsCompat
import java.util.regex.Pattern

object FieldValidation {

    /**
     * checking pattern of email
     * @param email input email
     * @return true if matches with email address else false
     */
    fun isValidEmail(email: String): Boolean {
        return PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()
    }

    /**
     * checking pattern of birthdate yyyy-mm-dd
     * @param birthdate input
     * @return true if matches with valid pattern else false
     */
    fun isValidBirthDate(birthdate: String): Boolean {
        val pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}")
        val matcher = pattern.matcher(birthdate)
        return matcher.matches()    }
    /**
     * checking is string contain any number
     * @param string value to check
     * @return true if contain else false
     */
    fun isStringContainNumber(text: String): Boolean {
        val pattern = Pattern.compile(".*\\d.*")
        val matcher = pattern.matcher(text)
        return matcher.matches()
    }

    /**
     * checking if if string contain upper and lower case value
     * @param string to check
     * @return true if contain else false
     */
    fun isStringLowerAndUpperCase(text: String): Boolean {
        val lowerCasePattern = Pattern.compile(".*[a-z].*")
        val upperCasePattern = Pattern.compile(".*[A-Z].*")
        val lowerCasePatterMatcher = lowerCasePattern.matcher(text)
        val upperCasePatterMatcher = upperCasePattern.matcher(text)
        return if (!lowerCasePatterMatcher.matches()) {
            false
        } else upperCasePatterMatcher.matches()
    }

    /**
     * checking is string contain any special character
     * @param string to check
     * @return return true if contain else false.
     */
    fun isStringContainSpecialCharacter(text: String): Boolean {
        val specialCharacterPattern = Pattern.compile("[^a-zA-Z0-9 ]")
        val specialCharacterMatcher = specialCharacterPattern.matcher(text)
        return specialCharacterMatcher.find()
    }

    fun isValidLicensePlate(licensePlate: String): Boolean {
        val listOfLicensePlatePatterns: MutableList<String> = ArrayList()

        // patterns of Dutch licenseplate
        // source: https://www.rdw.nl/particulier/voertuigen/auto/de-kentekenplaat/cijfers-en-letters-op-de-kentekenplaat
        listOfLicensePlatePatterns.add("^([A-Z]{2})([A-Z]{2})(\\d{2})$") // XX-XX-99
        listOfLicensePlatePatterns.add("^(\\d{2})([A-Z]{2})([A-Z]{2})$") // 99-XX-XX
        listOfLicensePlatePatterns.add("^(\\d{2})([A-Z]{3})(\\d{1})$") // 99-XXX-9
        listOfLicensePlatePatterns.add("^(\\d{1})([A-Z]{3})(\\d{2})$") // 9-XXX-99
        listOfLicensePlatePatterns.add("^([A-Z]{2})(\\d{3})([A-Z]{1})$") // XX-999-X
        listOfLicensePlatePatterns.add("^([A-Z]{1})(\\d{3})([A-Z]{2})$") // X-999-XX
        listOfLicensePlatePatterns.add("^([A-Z]{3})(\\d{2})([A-Z]{1})$") // XXX-99-X
        listOfLicensePlatePatterns.add("^([A-Z]{1})(\\d{2})([A-Z]{3})$") // X-99-XXX
        listOfLicensePlatePatterns.add("^(\\d{1})([A-Z]{2})(\\d{3})$") // 9-XX-999
        listOfLicensePlatePatterns.add("^(\\d{3})([A-Z]{2})(\\d{1})$") // 999-XX-9
        listOfLicensePlatePatterns.add("^(\\d{3})(\\d{2})([A-Z]{1})$") // 999-99-X
        listOfLicensePlatePatterns.add("^([A-Z]{3})(\\d{2})(\\d{1})$") // XXX-99-9
        listOfLicensePlatePatterns.add("^([A-Z]{3})([A-Z]{2})(\\d{1})$") // XXX-XX-9

        // Check if a given liceseplate matches a pattern
        for (pattern in listOfLicensePlatePatterns) {
            if (Pattern.matches(pattern, licensePlate)) {
                return true
            }
        }
        return false
    }

}