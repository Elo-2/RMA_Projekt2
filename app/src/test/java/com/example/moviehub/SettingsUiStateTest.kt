package com.example.moviehub

import com.example.moviehub.viewmodel.SettingsUiState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SettingsUiStateTest {

    @Test
    fun initialState_notificationsAreEnabled() {
        val state = SettingsUiState()

        assertTrue(state.notificationsEnabled)
    }

    @Test
    fun initialState_darkThemeIsDisabled() {
        val state = SettingsUiState()

        assertFalse(state.darkThemeEnabled)
    }

    @Test
    fun state_canEnableDarkTheme() {
        val state = SettingsUiState(
            darkThemeEnabled = true
        )

        assertTrue(state.darkThemeEnabled)
    }

    @Test
    fun state_canDisableNotifications() {
        val state = SettingsUiState(
            notificationsEnabled = false
        )

        assertFalse(state.notificationsEnabled)
    }

    @Test
    fun state_canEnableNotifications() {
        val state = SettingsUiState(
            notificationsEnabled = true
        )

        assertTrue(state.notificationsEnabled)
    }

    @Test
    fun state_canDisableDarkTheme() {
        val state = SettingsUiState(
            darkThemeEnabled = false
        )

        assertFalse(state.darkThemeEnabled)
    }

    @Test
    fun state_canEnableBothSettings() {
        val state = SettingsUiState(
            notificationsEnabled = true,
            darkThemeEnabled = true
        )

        assertTrue(state.notificationsEnabled)
        assertTrue(state.darkThemeEnabled)
    }

    @Test
    fun state_canDisableBothSettings() {
        val state = SettingsUiState(
            notificationsEnabled = false,
            darkThemeEnabled = false
        )

        assertFalse(state.notificationsEnabled)
        assertFalse(state.darkThemeEnabled)
    }

    @Test
    fun state_notificationsFalse_darkThemeTrue() {
        val state = SettingsUiState(
            notificationsEnabled = false,
            darkThemeEnabled = true
        )

        assertFalse(state.notificationsEnabled)
        assertTrue(state.darkThemeEnabled)
    }

    @Test
    fun state_notificationsTrue_darkThemeFalse() {
        val state = SettingsUiState(
            notificationsEnabled = true,
            darkThemeEnabled = false
        )

        assertTrue(state.notificationsEnabled)
        assertFalse(state.darkThemeEnabled)
    }

    @Test
    fun copy_canEnableDarkTheme() {
        val state = SettingsUiState()

        val updatedState = state.copy(
            darkThemeEnabled = true
        )

        assertTrue(updatedState.darkThemeEnabled)
        assertTrue(updatedState.notificationsEnabled)
    }

    @Test
    fun copy_canDisableNotifications() {
        val state = SettingsUiState()

        val updatedState = state.copy(
            notificationsEnabled = false
        )

        assertFalse(updatedState.notificationsEnabled)
        assertFalse(updatedState.darkThemeEnabled)
    }

    @Test
    fun copy_canChangeBothSettings() {
        val state = SettingsUiState()

        val updatedState = state.copy(
            notificationsEnabled = false,
            darkThemeEnabled = true
        )

        assertFalse(updatedState.notificationsEnabled)
        assertTrue(updatedState.darkThemeEnabled)
    }

    @Test
    fun copy_doesNotChangeOriginalState() {
        val state = SettingsUiState()

        val updatedState = state.copy(
            notificationsEnabled = false,
            darkThemeEnabled = true
        )

        assertTrue(state.notificationsEnabled)
        assertFalse(state.darkThemeEnabled)

        assertFalse(updatedState.notificationsEnabled)
        assertTrue(updatedState.darkThemeEnabled)
    }

    @Test
    fun sameStates_areEqual() {
        val first = SettingsUiState(
            notificationsEnabled = true,
            darkThemeEnabled = false
        )

        val second = SettingsUiState(
            notificationsEnabled = true,
            darkThemeEnabled = false
        )

        assertEquals(first, second)
    }

    @Test
    fun differentStates_areNotEqual() {
        val first = SettingsUiState(
            notificationsEnabled = true,
            darkThemeEnabled = false
        )

        val second = SettingsUiState(
            notificationsEnabled = false,
            darkThemeEnabled = true
        )

        assertNotEquals(first, second)
    }

    @Test
    fun notificationsOnlyChange_doesNotChangeDarkTheme() {
        val state = SettingsUiState(
            notificationsEnabled = true,
            darkThemeEnabled = true
        )

        val updatedState = state.copy(
            notificationsEnabled = false
        )

        assertFalse(updatedState.notificationsEnabled)
        assertTrue(updatedState.darkThemeEnabled)
    }

    @Test
    fun darkThemeOnlyChange_doesNotChangeNotifications() {
        val state = SettingsUiState(
            notificationsEnabled = false,
            darkThemeEnabled = false
        )

        val updatedState = state.copy(
            darkThemeEnabled = true
        )

        assertFalse(updatedState.notificationsEnabled)
        assertTrue(updatedState.darkThemeEnabled)
    }

    @Test
    fun bothSettingsCanBeToggled() {
        val state = SettingsUiState(
            notificationsEnabled = true,
            darkThemeEnabled = false
        )

        val updatedState = state.copy(
            notificationsEnabled = false,
            darkThemeEnabled = true
        )

        val finalState = updatedState.copy(
            notificationsEnabled = true,
            darkThemeEnabled = false
        )

        assertTrue(finalState.notificationsEnabled)
        assertFalse(finalState.darkThemeEnabled)
    }
}