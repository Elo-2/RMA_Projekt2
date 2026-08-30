package com.example.moviehub.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.moviehub.data.repository.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class SettingsUiState(
    val notificationsEnabled: Boolean = true,
    val darkThemeEnabled: Boolean = false
)

class SettingsViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository = SettingsRepository(
        application.applicationContext
    )

    private val _uiState = MutableStateFlow(
        SettingsUiState(
            notificationsEnabled = repository.isNotificationsEnabled(),
            darkThemeEnabled = repository.isDarkThemeEnabled()
        )
    )

    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    fun setNotificationsEnabled(enabled: Boolean) {
        repository.setNotificationsEnabled(enabled)

        _uiState.value = _uiState.value.copy(
            notificationsEnabled = enabled
        )
    }

    fun setDarkThemeEnabled(enabled: Boolean) {
        repository.setDarkThemeEnabled(enabled)

        _uiState.value = _uiState.value.copy(
            darkThemeEnabled = enabled
        )
    }
}