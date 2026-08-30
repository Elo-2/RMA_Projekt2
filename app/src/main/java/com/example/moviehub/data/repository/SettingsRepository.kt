package com.example.moviehub.data.repository

import android.content.Context

class SettingsRepository(
    context: Context
) {

    private val preferences = context.getSharedPreferences(
        "moviehub_settings",
        Context.MODE_PRIVATE
    )

    fun isNotificationsEnabled(): Boolean {
        return preferences.getBoolean(
            "notifications_enabled",
            true
        )
    }

    fun setNotificationsEnabled(enabled: Boolean) {
        preferences.edit()
            .putBoolean(
                "notifications_enabled",
                enabled
            )
            .apply()
    }

    fun isDarkThemeEnabled(): Boolean {
        return preferences.getBoolean(
            "dark_theme_enabled",
            false
        )
    }

    fun setDarkThemeEnabled(enabled: Boolean) {
        preferences.edit()
            .putBoolean(
                "dark_theme_enabled",
                enabled
            )
            .apply()
    }
}