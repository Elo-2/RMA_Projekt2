package com.example.moviehub

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.moviehub.viewmodel.SettingsViewModel
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class SettingsViewModelTest {

    private lateinit var context: Context
    private lateinit var viewModel: SettingsViewModel

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()

        context.getSharedPreferences(
            "moviehub_settings",
            Context.MODE_PRIVATE
        )
            .edit()
            .clear()
            .commit()

        viewModel = SettingsViewModel(
            ApplicationProvider.getApplicationContext()
        )
    }

    @After
    fun tearDown() {
        context.getSharedPreferences(
            "moviehub_settings",
            Context.MODE_PRIVATE
        )
            .edit()
            .clear()
            .commit()
    }

    @Test
    fun initialState_notificationsAreEnabled() {
        val state = viewModel.uiState.value

        assertTrue(state.notificationsEnabled)
    }

    @Test
    fun initialState_darkThemeIsDisabled() {
        val state = viewModel.uiState.value

        assertFalse(state.darkThemeEnabled)
    }

    @Test
    fun setNotificationsEnabled_false_updatesState() {
        viewModel.setNotificationsEnabled(false)

        assertFalse(viewModel.uiState.value.notificationsEnabled)
    }

    @Test
    fun setNotificationsEnabled_true_updatesState() {
        viewModel.setNotificationsEnabled(false)
        viewModel.setNotificationsEnabled(true)

        assertTrue(viewModel.uiState.value.notificationsEnabled)
    }

    @Test
    fun setNotificationsEnabled_multipleChanges_keepLatestValue() {
        viewModel.setNotificationsEnabled(false)
        assertFalse(viewModel.uiState.value.notificationsEnabled)

        viewModel.setNotificationsEnabled(true)
        assertTrue(viewModel.uiState.value.notificationsEnabled)

        viewModel.setNotificationsEnabled(false)
        assertFalse(viewModel.uiState.value.notificationsEnabled)
    }

    @Test
    fun setDarkThemeEnabled_true_updatesState() {
        viewModel.setDarkThemeEnabled(true)

        assertTrue(viewModel.uiState.value.darkThemeEnabled)
    }

    @Test
    fun setDarkThemeEnabled_false_updatesState() {
        viewModel.setDarkThemeEnabled(true)
        viewModel.setDarkThemeEnabled(false)

        assertFalse(viewModel.uiState.value.darkThemeEnabled)
    }

    @Test
    fun setDarkThemeEnabled_multipleChanges_keepLatestValue() {
        viewModel.setDarkThemeEnabled(true)
        assertTrue(viewModel.uiState.value.darkThemeEnabled)

        viewModel.setDarkThemeEnabled(false)
        assertFalse(viewModel.uiState.value.darkThemeEnabled)

        viewModel.setDarkThemeEnabled(true)
        assertTrue(viewModel.uiState.value.darkThemeEnabled)
    }

    @Test
    fun notificationAndThemeSettings_areIndependent() {
        viewModel.setNotificationsEnabled(false)
        viewModel.setDarkThemeEnabled(true)

        assertFalse(viewModel.uiState.value.notificationsEnabled)
        assertTrue(viewModel.uiState.value.darkThemeEnabled)
    }

    @Test
    fun notificationAndThemeSettings_canBothBeDisabled() {
        viewModel.setNotificationsEnabled(false)
        viewModel.setDarkThemeEnabled(false)

        assertFalse(viewModel.uiState.value.notificationsEnabled)
        assertFalse(viewModel.uiState.value.darkThemeEnabled)
    }

    @Test
    fun settingsArePersistedAndLoadedByNewViewModel() {
        viewModel.setNotificationsEnabled(false)
        viewModel.setDarkThemeEnabled(true)

        val newViewModel = SettingsViewModel(
            ApplicationProvider.getApplicationContext()
        )

        assertFalse(newViewModel.uiState.value.notificationsEnabled)
        assertTrue(newViewModel.uiState.value.darkThemeEnabled)
    }

    @Test
    fun uiState_containsCurrentSettings() {
        viewModel.setNotificationsEnabled(false)
        viewModel.setDarkThemeEnabled(true)

        val state = viewModel.uiState.value

        assertFalse(state.notificationsEnabled)
        assertTrue(state.darkThemeEnabled)
    }
}