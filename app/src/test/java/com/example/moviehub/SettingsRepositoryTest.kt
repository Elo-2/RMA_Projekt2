package com.example.moviehub

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.moviehub.data.repository.SettingsRepository
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.robolectric.RobolectricTestRunner
import org.junit.runner.RunWith

@RunWith(RobolectricTestRunner::class)
class SettingsRepositoryTest {

    private lateinit var context: Context
    private lateinit var repository: SettingsRepository

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

        repository = SettingsRepository(context)
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
    fun notificationsEnabled_defaultValue_isTrue() {
        assertTrue(repository.isNotificationsEnabled())
    }

    @Test
    fun notificationsEnabled_canBeDisabled() {
        repository.setNotificationsEnabled(false)

        assertFalse(repository.isNotificationsEnabled())
    }

    @Test
    fun notificationsEnabled_canBeEnabled() {
        repository.setNotificationsEnabled(false)
        repository.setNotificationsEnabled(true)

        assertTrue(repository.isNotificationsEnabled())
    }

    @Test
    fun notificationsEnabled_multipleChanges_keepLatestValue() {
        repository.setNotificationsEnabled(false)
        assertFalse(repository.isNotificationsEnabled())

        repository.setNotificationsEnabled(true)
        assertTrue(repository.isNotificationsEnabled())

        repository.setNotificationsEnabled(false)
        assertFalse(repository.isNotificationsEnabled())
    }

    @Test
    fun darkThemeEnabled_defaultValue_isFalse() {
        assertFalse(repository.isDarkThemeEnabled())
    }

    @Test
    fun darkThemeEnabled_canBeEnabled() {
        repository.setDarkThemeEnabled(true)

        assertTrue(repository.isDarkThemeEnabled())
    }

    @Test
    fun darkThemeEnabled_canBeDisabled() {
        repository.setDarkThemeEnabled(true)
        repository.setDarkThemeEnabled(false)

        assertFalse(repository.isDarkThemeEnabled())
    }

    @Test
    fun darkThemeEnabled_multipleChanges_keepLatestValue() {
        repository.setDarkThemeEnabled(true)
        assertTrue(repository.isDarkThemeEnabled())

        repository.setDarkThemeEnabled(false)
        assertFalse(repository.isDarkThemeEnabled())

        repository.setDarkThemeEnabled(true)
        assertTrue(repository.isDarkThemeEnabled())
    }

    @Test
    fun notificationsAndDarkTheme_areStoredIndependently() {
        repository.setNotificationsEnabled(false)
        repository.setDarkThemeEnabled(true)

        assertFalse(repository.isNotificationsEnabled())
        assertTrue(repository.isDarkThemeEnabled())
    }

    @Test
    fun settings_arePersistedBetweenRepositoryInstances() {
        repository.setNotificationsEnabled(false)
        repository.setDarkThemeEnabled(true)

        val secondRepository = SettingsRepository(context)

        assertFalse(secondRepository.isNotificationsEnabled())
        assertTrue(secondRepository.isDarkThemeEnabled())
    }

    @Test
    fun notificationsEnabled_canBeSetToSameValue() {
        repository.setNotificationsEnabled(true)

        assertTrue(repository.isNotificationsEnabled())
    }

    @Test
    fun darkThemeEnabled_canBeSetToSameValue() {
        repository.setDarkThemeEnabled(false)

        assertFalse(repository.isDarkThemeEnabled())
    }
}