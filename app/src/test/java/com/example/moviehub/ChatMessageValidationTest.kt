package com.example.moviehub

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ChatMessageValidationTest {

    private fun isValidMessage(message: String): Boolean {
        return message.trim().isNotEmpty()
    }

    @Test
    fun normalMessage_isValid() {
        assertTrue(isValidMessage("Pozdrav"))
    }

    @Test
    fun emptyMessage_isInvalid() {
        assertFalse(isValidMessage(""))
    }

    @Test
    fun spacesOnlyMessage_isInvalid() {
        assertFalse(isValidMessage("     "))
    }

    @Test
    fun tabsOnlyMessage_isInvalid() {
        assertFalse(isValidMessage("\t\t"))
    }

    @Test
    fun newLinesOnlyMessage_isInvalid() {
        assertFalse(isValidMessage("\n\n"))
    }

    @Test
    fun mixedWhitespace_isInvalid() {
        assertFalse(isValidMessage(" \t\n "))
    }

    @Test
    fun messageWithSpacesAroundText_isValid() {
        assertTrue(isValidMessage("  Pozdrav  "))
    }

    @Test
    fun singleCharacterMessage_isValid() {
        assertTrue(isValidMessage("A"))
    }

    @Test
    fun singleNumberMessage_isValid() {
        assertTrue(isValidMessage("1"))
    }

    @Test
    fun singleSymbolMessage_isValid() {
        assertTrue(isValidMessage("!"))
    }

    @Test
    fun numbersMessage_isValid() {
        assertTrue(isValidMessage("123456"))
    }

    @Test
    fun messageWithNumbers_isValid() {
        assertTrue(isValidMessage("Pozdrav 123"))
    }

    @Test
    fun messageWithSpecialCharacters_isValid() {
        assertTrue(isValidMessage("Pozdrav! Kako si?"))
    }

    @Test
    fun messageWithBosnianCharacters_isValid() {
        assertTrue(isValidMessage("Ćao, čujemo se sutra."))
    }

    @Test
    fun messageWithEmoji_isValid() {
        assertTrue(isValidMessage("Pozdrav 😀"))
    }

    @Test
    fun messageWithOneSpaceBetweenWords_isValid() {
        assertTrue(isValidMessage("Pozdrav svima"))
    }

    @Test
    fun messageWithManySpacesBetweenWords_isValid() {
        assertTrue(isValidMessage("Pozdrav     svima"))
    }

    @Test
    fun messageWithLeadingSpaces_isValid() {
        assertTrue(isValidMessage("     Pozdrav"))
    }

    @Test
    fun messageWithTrailingSpaces_isValid() {
        assertTrue(isValidMessage("Pozdrav     "))
    }

    @Test
    fun messageWithLeadingAndTrailingSpaces_isValid() {
        assertTrue(isValidMessage("     Pozdrav     "))
    }

    @Test
    fun longMessage_isValid() {
        val message = "Ovo je veoma duga poruka koja služi za testiranje validacije chat poruke."

        assertTrue(isValidMessage(message))
    }

    @Test
    fun twoCharacterMessage_isValid() {
        assertTrue(isValidMessage("Hi"))
    }

    @Test
    fun decimalNumberMessage_isValid() {
        assertTrue(isValidMessage("12.50"))
    }

    @Test
    fun punctuationMessage_isValid() {
        assertTrue(isValidMessage("..."))
    }

    @Test
    fun questionMarkMessage_isValid() {
        assertTrue(isValidMessage("?"))
    }

    @Test
    fun exclamationMarkMessage_isValid() {
        assertTrue(isValidMessage("!"))
    }

    @Test
    fun slashMessage_isValid() {
        assertTrue(isValidMessage("/"))
    }

    @Test
    fun zeroMessage_isValid() {
        assertTrue(isValidMessage("0"))
    }

    @Test
    fun negativeNumberMessage_isValid() {
        assertTrue(isValidMessage("-123"))
    }

    @Test
    fun mixedCharactersMessage_isValid() {
        assertTrue(isValidMessage("User_123!"))
    }
}