package com.example.moviehub.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviehub.data.repository.AuthRepository
import com.example.moviehub.data.repository.AuthRepositoryContract
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AuthUiState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val errorMessage: String? = null
)

class AuthViewModel(
    private val repository: AuthRepositoryContract = AuthRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        AuthUiState(
            isLoggedIn = repository.currentUser != null
        )
    )

    val uiState: StateFlow<AuthUiState> =
        _uiState.asStateFlow()

    fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit
    ) {

        if (email.isBlank() || password.isBlank()) {

            _uiState.value = AuthUiState(
                errorMessage =
                    "Unesite email i password."
            )

            return
        }

        viewModelScope.launch {

            _uiState.value = AuthUiState(
                isLoading = true
            )

            val result = repository.login(
                email = email.trim(),
                password = password
            )

            if (result.isSuccess) {

                _uiState.value = AuthUiState(
                    isLoggedIn = true
                )

                onSuccess()

            } else {

                _uiState.value = AuthUiState(
                    errorMessage =
                        result.exceptionOrNull()?.message
                            ?: "Prijava nije uspjela."
                )
            }
        }
    }

    fun register(
        name: String,
        email: String,
        password: String,
        onSuccess: () -> Unit
    ) {

        if (
            name.isBlank() ||
            email.isBlank() ||
            password.isBlank()
        ) {

            _uiState.value = AuthUiState(
                errorMessage =
                    "Unesite ime, email i password."
            )

            return
        }

        if (password.length < 6) {

            _uiState.value = AuthUiState(
                errorMessage =
                    "Password mora imati najmanje 6 znakova."
            )

            return
        }

        viewModelScope.launch {

            _uiState.value = AuthUiState(
                isLoading = true
            )

            val result = repository.register(
                name = name.trim(),
                email = email.trim(),
                password = password
            )

            if (result.isSuccess) {

                /*
                 * Registracija je gotova.
                 *
                 * AuthRepository je već odjavio korisnika.
                 * Zato sada idemo na Login.
                 */
                _uiState.value = AuthUiState(
                    isLoggedIn = false
                )

                onSuccess()

            } else {

                _uiState.value = AuthUiState(
                    errorMessage =
                        result.exceptionOrNull()?.message
                            ?: "Registracija nije uspjela."
                )
            }
        }
    }

    fun clearError() {

        _uiState.value = _uiState.value.copy(
            errorMessage = null
        )
    }

    fun logout() {

        repository.logout()

        _uiState.value = AuthUiState(
            isLoggedIn = false
        )
    }
}