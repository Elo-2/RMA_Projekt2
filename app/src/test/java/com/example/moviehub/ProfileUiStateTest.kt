package com.example.moviehub

import com.example.moviehub.data.repository.UserProfile
import com.example.moviehub.viewmodel.ProfileUiState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ProfileUiStateTest {

    @Test
    fun initialState_hasNoProfile() {
        val state = ProfileUiState()

        assertNull(state.profile)
    }

    @Test
    fun initialState_isNotLoading() {
        val state = ProfileUiState()

        assertFalse(state.isLoading)
    }

    @Test
    fun initialState_isNotSaving() {
        val state = ProfileUiState()

        assertFalse(state.isSaving)
    }

    @Test
    fun initialState_isNotChangingPassword() {
        val state = ProfileUiState()

        assertFalse(state.isChangingPassword)
    }

    @Test
    fun initialState_hasNoMessages() {
        val state = ProfileUiState()

        assertNull(state.successMessage)
        assertNull(state.errorMessage)
    }

    @Test
    fun state_canContainProfile() {
        val profile = UserProfile(
            uid = "123",
            name = "Elo",
            email = "elo@test.com"
        )

        val state = ProfileUiState(profile = profile)

        assertEquals("123", state.profile?.uid)
        assertEquals("Elo", state.profile?.name)
        assertEquals("elo@test.com", state.profile?.email)
    }

    @Test
    fun state_canBeLoading() {
        val state = ProfileUiState(isLoading = true)

        assertTrue(state.isLoading)
    }

    @Test
    fun state_canBeSaving() {
        val state = ProfileUiState(isSaving = true)

        assertTrue(state.isSaving)
    }

    @Test
    fun state_canBeChangingPassword() {
        val state = ProfileUiState(isChangingPassword = true)

        assertTrue(state.isChangingPassword)
    }

    @Test
    fun state_canContainSuccessAndErrorMessages() {
        val state = ProfileUiState(
            successMessage = "Uspješno",
            errorMessage = "Greška"
        )

        assertEquals("Uspješno", state.successMessage)
        assertEquals("Greška", state.errorMessage)
    }
}