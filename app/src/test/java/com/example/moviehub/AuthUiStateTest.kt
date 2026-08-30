package com.example.moviehub

import com.example.moviehub.viewmodel.AuthUiState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class AuthUiStateTest {

    @Test
    fun initialState_isNotLoading() {
        val state = AuthUiState()

        assertFalse(state.isLoading)
    }

    @Test
    fun initialState_isNotLoggedIn() {
        val state = AuthUiState()

        assertFalse(state.isLoggedIn)
    }

    @Test
    fun initialState_hasNoError() {
        val state = AuthUiState()

        assertNull(state.errorMessage)
    }

    @Test
    fun state_canBeLoading() {
        val state = AuthUiState(
            isLoading = true
        )

        assertTrue(state.isLoading)
    }

    @Test
    fun state_canBeLoggedIn() {
        val state = AuthUiState(
            isLoggedIn = true
        )

        assertTrue(state.isLoggedIn)
    }

    @Test
    fun state_canContainError() {
        val state = AuthUiState(
            errorMessage = "Greška"
        )

        assertEquals("Greška", state.errorMessage)
    }

    @Test
    fun state_canBeLoadingAndLoggedIn() {
        val state = AuthUiState(
            isLoading = true,
            isLoggedIn = true
        )

        assertTrue(state.isLoading)
        assertTrue(state.isLoggedIn)
    }

    @Test
    fun state_canBeLoadingWithError() {
        val state = AuthUiState(
            isLoading = true,
            errorMessage = "Greška pri učitavanju"
        )

        assertTrue(state.isLoading)
        assertEquals(
            "Greška pri učitavanju",
            state.errorMessage
        )
    }

    @Test
    fun state_canBeLoggedInWithoutError() {
        val state = AuthUiState(
            isLoggedIn = true
        )

        assertTrue(state.isLoggedIn)
        assertNull(state.errorMessage)
    }

    @Test
    fun state_canBeLoggedOutWithError() {
        val state = AuthUiState(
            isLoggedIn = false,
            errorMessage = "Neuspješna prijava"
        )

        assertFalse(state.isLoggedIn)
        assertEquals(
            "Neuspješna prijava",
            state.errorMessage
        )
    }

    @Test
    fun state_copyCanChangeLoading() {
        val initialState = AuthUiState()

        val updatedState = initialState.copy(
            isLoading = true
        )

        assertFalse(initialState.isLoading)
        assertTrue(updatedState.isLoading)
    }

    @Test
    fun state_copyCanChangeLoginStatus() {
        val initialState = AuthUiState()

        val updatedState = initialState.copy(
            isLoggedIn = true
        )

        assertFalse(initialState.isLoggedIn)
        assertTrue(updatedState.isLoggedIn)
    }

    @Test
    fun state_copyCanAddError() {
        val initialState = AuthUiState()

        val updatedState = initialState.copy(
            errorMessage = "Test greška"
        )

        assertNull(initialState.errorMessage)
        assertEquals(
            "Test greška",
            updatedState.errorMessage
        )
    }

    @Test
    fun state_copyCanRemoveError() {
        val initialState = AuthUiState(
            errorMessage = "Greška"
        )

        val updatedState = initialState.copy(
            errorMessage = null
        )

        assertEquals(
            "Greška",
            initialState.errorMessage
        )
        assertNull(updatedState.errorMessage)
    }

    @Test
    fun state_copyPreservesOtherValues() {
        val initialState = AuthUiState(
            isLoading = true,
            isLoggedIn = true,
            errorMessage = "Greška"
        )

        val updatedState = initialState.copy(
            isLoading = false
        )

        assertFalse(updatedState.isLoading)
        assertTrue(updatedState.isLoggedIn)
        assertEquals(
            "Greška",
            updatedState.errorMessage
        )
    }
}