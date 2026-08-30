package com.example.moviehub

import com.example.moviehub.viewmodel.AuthUiState
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun addition_isCorrect() {
        val firstNumber = 2
        val secondNumber = 2

        val result = firstNumber + secondNumber

        assertFalse(result != 4)
    }

    @Test
    fun authState_isNotLoading() {
        val state = AuthUiState()

        assertFalse(state.isLoading)
    }

    @Test
    fun authState_isNotLoggedIn() {
        val state = AuthUiState()

        assertFalse(state.isLoggedIn)
    }

    @Test
    fun authState_hasNoError() {
        val state = AuthUiState()

        assertNull(state.errorMessage)
    }
}