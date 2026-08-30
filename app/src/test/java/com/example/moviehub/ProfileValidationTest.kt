package com.example.moviehub

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ProfileValidationTest {

    private fun isValidName(name: String): Boolean {
        return name.isNotBlank()
    }

    private fun isValidPassword(password: String): Boolean {
        return password.isNotBlank() && password.length >= 6
    }

    private fun passwordsMatch(
        password: String,
        confirmPassword: String
    ): Boolean {
        return password == confirmPassword
    }

    @Test
    fun validName_returnsTrue() {
        assertTrue(isValidName("Elo"))
    }

    @Test
    fun emptyName_returnsFalse() {
        assertFalse(isValidName(""))
    }

    @Test
    fun spacesOnlyName_returnsFalse() {
        assertFalse(isValidName("     "))
    }

    @Test
    fun validPassword_returnsTrue() {
        assertTrue(isValidPassword("123456"))
    }

    @Test
    fun shortPassword_returnsFalse() {
        assertFalse(isValidPassword("12345"))
    }

    @Test
    fun emptyPassword_returnsFalse() {
        assertFalse(isValidPassword(""))
    }

    @Test
    fun passwordWithExactlySixCharacters_isValid() {
        assertTrue(isValidPassword("abcdef"))
    }

    @Test
    fun matchingPasswords_returnTrue() {
        assertTrue(
            passwordsMatch(
                "123456",
                "123456"
            )
        )
    }

    @Test
    fun differentPasswords_returnFalse() {
        assertFalse(
            passwordsMatch(
                "123456",
                "654321"
            )
        )
    }

    @Test
    fun emptyPasswords_match() {
        assertTrue(
            passwordsMatch(
                "",
                ""
            )
        )
    }

    @Test
    fun nameWithSpacesAroundText_isValid() {
        assertTrue(isValidName("  Elo  "))
    }

    @Test
    fun singleCharacterName_isValid() {
        assertTrue(isValidName("E"))
    }

    @Test
    fun passwordWithMoreThanSixCharacters_isValid() {
        assertTrue(isValidPassword("password123"))
    }

    @Test
    fun fiveCharacterPassword_isInvalid() {
        assertFalse(isValidPassword("abcde"))
    }

    @Test
    fun differentCasePasswords_doNotMatch() {
        assertFalse(
            passwordsMatch(
                "Password123",
                "password123"
            )
        )
    }
}