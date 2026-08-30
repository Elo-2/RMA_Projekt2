package com.example.moviehub.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviehub.data.repository.ProfileRepository
import com.example.moviehub.data.repository.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ProfileUiState(
    val profile: UserProfile? = null,
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val isChangingPassword: Boolean = false,
    val successMessage: String? = null,
    val errorMessage: String? = null
)

class ProfileViewModel(
    private val repository: ProfileRepository = ProfileRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        ProfileUiState(
            isLoading = true
        )
    )

    val uiState: StateFlow<ProfileUiState> =
        _uiState.asStateFlow()

    init {
        loadProfile()
    }

    fun loadProfile() {

        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            val result = repository.getProfile()

            if (result.isSuccess) {

                _uiState.value = _uiState.value.copy(
                    profile = result.getOrNull(),
                    isLoading = false,
                    errorMessage = null
                )

            } else {

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage =
                        result.exceptionOrNull()?.message
                            ?: "Učitavanje profila nije uspjelo."
                )
            }
        }
    }

    fun updateName(name: String) {

        if (name.isBlank()) {

            _uiState.value = _uiState.value.copy(
                errorMessage = "Ime ne može biti prazno.",
                successMessage = null
            )

            return
        }

        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(
                isSaving = true,
                errorMessage = null,
                successMessage = null
            )

            val result = repository.updateName(
                name = name.trim()
            )

            if (result.isSuccess) {

                val currentProfile =
                    _uiState.value.profile

                _uiState.value = _uiState.value.copy(
                    isSaving = false,
                    profile = currentProfile?.copy(
                        name = name.trim()
                    ),
                    successMessage =
                        "Profil je uspješno sačuvan.",
                    errorMessage = null
                )

            } else {

                _uiState.value = _uiState.value.copy(
                    isSaving = false,
                    errorMessage =
                        result.exceptionOrNull()?.message
                            ?: "Spremanje profila nije uspjelo."
                )
            }
        }
    }

    fun changePassword(
        password: String,
        confirmPassword: String
    ) {

        if (password.isBlank() ||
            confirmPassword.isBlank()
        ) {

            _uiState.value = _uiState.value.copy(
                errorMessage =
                    "Unesite novu lozinku i potvrdu lozinke.",
                successMessage = null
            )

            return
        }

        if (password.length < 6) {

            _uiState.value = _uiState.value.copy(
                errorMessage =
                    "Lozinka mora imati najmanje 6 znakova.",
                successMessage = null
            )

            return
        }

        if (password != confirmPassword) {

            _uiState.value = _uiState.value.copy(
                errorMessage =
                    "Lozinke se ne podudaraju.",
                successMessage = null
            )

            return
        }

        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(
                isChangingPassword = true,
                errorMessage = null,
                successMessage = null
            )

            val result =
                repository.changePassword(password)

            if (result.isSuccess) {

                _uiState.value = _uiState.value.copy(
                    isChangingPassword = false,
                    successMessage =
                        "Lozinka je uspješno promijenjena.",
                    errorMessage = null
                )

            } else {

                _uiState.value = _uiState.value.copy(
                    isChangingPassword = false,
                    errorMessage =
                        result.exceptionOrNull()?.message
                            ?: "Promjena lozinke nije uspjela."
                )
            }
        }
    }

    fun clearMessages() {

        _uiState.value = _uiState.value.copy(
            errorMessage = null,
            successMessage = null
        )
    }

    fun logout() {

        repository.logout()

        _uiState.value = ProfileUiState(
            profile = null,
            isLoading = false
        )
    }
}