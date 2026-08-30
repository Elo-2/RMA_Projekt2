package com.example.moviehub

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class PasswordConfirmationTest {

    private fun passwordsMatch(
        password: String,
        confirmPassword: String
    ): Boolean {
        return password == confirmPassword
    }

    @Test
    fun identicalPasswords_match() {
        assertTrue(passwordsMatch("123456", "123456"))
    }

    @Test
    fun differentPasswords_doNotMatch() {
        assertFalse(passwordsMatch("123456", "654321"))
    }

    @Test
    fun emptyPasswords_match() {
        assertTrue(passwordsMatch("", ""))
    }

    @Test
    fun passwordAndEmptyConfirmation_doNotMatch() {
        assertFalse(passwordsMatch("123456", ""))
    }

    @Test
    fun emptyPasswordAndConfirmation_doNotMatch() {
        assertFalse(passwordsMatch("", "123456"))
    }

    @Test
    fun sameLongPasswords_match() {
        assertTrue(
            passwordsMatch(
                "verylongpassword123",
                "verylongpassword123"
            )
        )
    }

    @Test
    fun passwordsWithDifferentCase_doNotMatch() {
        assertFalse(
            passwordsMatch(
                "Password123",
                "password123"
            )
        )
    }

    @Test
    fun passwordsWithSpaces_doNotMatch() {
        assertFalse(
            passwordsMatch(
                "123456",
                "123456 "
            )
        )
    }

    @Test
    fun singleCharacterPasswords_match() {
        assertTrue(passwordsMatch("a", "a"))
    }

    @Test
    fun singleCharacterPasswords_doNotMatch() {
        assertFalse(passwordsMatch("a", "b"))
    }

    @Test
    fun passwordsWithNumbers_match() {
        assertTrue(passwordsMatch("123456", "123456"))
    }

    @Test
    fun passwordsWithLetters_match() {
        assertTrue(passwordsMatch("abcdef", "abcdef"))
    }

    @Test
    fun passwordsWithSpecialCharacters_match() {
        assertTrue(passwordsMatch("!@#$%^", "!@#$%^"))
    }

    @Test
    fun passwordsWithSpecialCharacters_doNotMatch() {
        assertFalse(passwordsMatch("!@#$%^", "!@#$%&"))
    }

    @Test
    fun passwordsWithInternalSpaces_match() {
        assertTrue(passwordsMatch("12 3456", "12 3456"))
    }

    @Test
    fun passwordsWithInternalSpaces_doNotMatch() {
        assertFalse(passwordsMatch("12 3456", "123456"))
    }

    @Test
    fun leadingSpaces_makePasswordsDifferent() {
        assertFalse(passwordsMatch("123456", " 123456"))
    }

    @Test
    fun trailingSpaces_makePasswordsDifferent() {
        assertFalse(passwordsMatch("123456", "123456 "))
    }

    @Test
    fun bothPasswordsWithLeadingSpaces_match() {
        assertTrue(passwordsMatch(" 123456", " 123456"))
    }

    @Test
    fun bothPasswordsWithTrailingSpaces_match() {
        assertTrue(passwordsMatch("123456 ", "123456 "))
    }

    @Test
    fun longIdenticalPasswords_match() {
        val password = "abcdefghijklmnopqrstuvwxyz1234567890"

        assertTrue(passwordsMatch(password, password))
    }

    @Test
    fun differentLengthPasswords_doNotMatch() {
        assertFalse(passwordsMatch("123456", "1234567"))
    }

    @Test
    fun sameSixCharacterPasswords_match() {
        assertTrue(passwordsMatch("abcdef", "abcdef"))
    }

    @Test
    fun sameSevenCharacterPasswords_match() {
        assertTrue(passwordsMatch("abcdefg", "abcdefg"))
    }

    @Test
    fun passwordWithNewline_doesNotMatchWithoutNewline() {
        assertFalse(passwordsMatch("123456\n", "123456"))
    }
}