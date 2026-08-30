package com.example.moviehub

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class PasswordValidationTest {

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }

    @Test
    fun passwordWithLessThanSixCharacters_isInvalid() {
        assertFalse(isValidPassword("12345"))
    }

    @Test
    fun passwordWithSixCharacters_isValid() {
        assertTrue(isValidPassword("123456"))
    }

    @Test
    fun passwordWithMoreThanSixCharacters_isValid() {
        assertTrue(isValidPassword("password123"))
    }

    @Test
    fun emptyPassword_isInvalid() {
        assertFalse(isValidPassword(""))
    }

    @Test
    fun passwordWithSevenCharacters_isValid() {
        assertTrue(isValidPassword("1234567"))
    }

    @Test
    fun passwordWithFiveCharacters_isInvalid() {
        assertFalse(isValidPassword("abcde"))
    }

    @Test
    fun passwordWithExactlyTenCharacters_isValid() {
        assertTrue(isValidPassword("1234567890"))
    }

    @Test
    fun passwordWithSpaces_isValidWhenLengthIsSix() {
        assertTrue(isValidPassword("123 45"))
    }

    @Test
    fun passwordWithOnlySpaces_isValidWhenLengthIsSix() {
        assertTrue(isValidPassword("      "))
    }

    @Test
    fun oneCharacterPassword_isInvalid() {
        assertFalse(isValidPassword("a"))
    }

    @Test
    fun twoCharacterPassword_isInvalid() {
        assertFalse(isValidPassword("ab"))
    }

    @Test
    fun threeCharacterPassword_isInvalid() {
        assertFalse(isValidPassword("abc"))
    }

    @Test
    fun fourCharacterPassword_isInvalid() {
        assertFalse(isValidPassword("abcd"))
    }

    @Test
    fun exactlySixNumbers_isValid() {
        assertTrue(isValidPassword("123456"))
    }

    @Test
    fun exactlySixLetters_isValid() {
        assertTrue(isValidPassword("abcdef"))
    }

    @Test
    fun exactlySixSpecialCharacters_isValid() {
        assertTrue(isValidPassword("!@#$%^"))
    }

    @Test
    fun sevenNumbers_isValid() {
        assertTrue(isValidPassword("1234567"))
    }

    @Test
    fun sevenLetters_isValid() {
        assertTrue(isValidPassword("abcdefg"))
    }

    @Test
    fun passwordWithInternalSpace_isValidWhenLengthIsSix() {
        assertTrue(isValidPassword("12 345"))
    }

    @Test
    fun passwordWithLeadingSpace_isValidWhenLengthIsSix() {
        assertTrue(isValidPassword(" 12345"))
    }

    @Test
    fun passwordWithTrailingSpace_isValidWhenLengthIsSix() {
        assertTrue(isValidPassword("12345 "))
    }

    @Test
    fun longPassword_isValid() {
        assertTrue(
            isValidPassword(
                "thisisaverylongpassword123456789"
            )
        )
    }

    @Test
    fun passwordWithMixedCharacters_isValid() {
        assertTrue(isValidPassword("Ab12!@"))
    }

    @Test
    fun passwordWithNewline_isValidWhenLengthIsSix() {
        assertTrue(isValidPassword("12345\n"))
    }

    @Test
    fun passwordWithTab_isValidWhenLengthIsSix() {
        assertTrue(isValidPassword("12345\t"))
    }
}