package com.example.moviehub

import com.example.moviehub.viewmodel.ProfileUiState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ProfileViewModelTest {

    @Test
    fun initialProfileState_hasNoProfile() {
        val state = ProfileUiState()

        assertNull(state.profile)
    }

    @Test
    fun initialProfileState_isNotLoading() {
        val state = ProfileUiState()

        assertFalse(state.isLoading)
    }

    @Test
    fun initialProfileState_isNotSaving() {
        val state = ProfileUiState()

        assertFalse(state.isSaving)
    }

    @Test
    fun initialProfileState_isNotChangingPassword() {
        val state = ProfileUiState()

        assertFalse(state.isChangingPassword)
    }

    @Test
    fun initialProfileState_hasNoSuccessMessage() {
        val state = ProfileUiState()

        assertNull(state.successMessage)
    }

    @Test
    fun initialProfileState_hasNoErrorMessage() {
        val state = ProfileUiState()

        assertNull(state.errorMessage)
    }

    @Test
    fun loadingState_canBeEnabled() {
        val state = ProfileUiState(
            isLoading = true
        )

        assertTrue(state.isLoading)
    }

    @Test
    fun savingState_canBeEnabled() {
        val state = ProfileUiState(
            isSaving = true
        )

        assertTrue(state.isSaving)
    }

    @Test
    fun changingPasswordState_canBeEnabled() {
        val state = ProfileUiState(
            isChangingPassword = true
        )

        assertTrue(state.isChangingPassword)
    }

    @Test
    fun errorMessage_canBeStored() {
        val state = ProfileUiState(
            errorMessage = "Greška"
        )

        assertEquals("Greška", state.errorMessage)
    }

    @Test
    fun successMessage_canBeStored() {
        val state = ProfileUiState(
            successMessage = "Uspješno"
        )

        assertEquals("Uspješno", state.successMessage)
    }

    @Test
    fun profileState_canBeCopiedWithNewName() {
        val state = ProfileUiState()

        val updatedState = state.copy(
            successMessage = "Profil je uspješno sačuvan."
        )

        assertNull(state.successMessage)
        assertEquals(
            "Profil je uspješno sačuvan.",
            updatedState.successMessage
        )
    }

    @Test
    fun errorState_canBeReplacedBySuccessState() {
        val state = ProfileUiState(
            errorMessage = "Greška"
        )

        val updatedState = state.copy(
            errorMessage = null,
            successMessage = "Uspješno"
        )

        assertNull(updatedState.errorMessage)
        assertEquals("Uspješno", updatedState.successMessage)
    }
}