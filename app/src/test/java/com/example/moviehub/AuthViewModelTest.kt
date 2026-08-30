package com.example.moviehub

import com.example.moviehub.data.repository.AuthRepositoryContract
import com.example.moviehub.viewmodel.AuthViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class AuthViewModelTest {

    @Test
    fun initialState_isCorrect() {
        val viewModel = AuthViewModel(FakeAuthRepository())

        assertFalse(viewModel.uiState.value.isLoading)
        assertFalse(viewModel.uiState.value.isLoggedIn)
        assertNull(viewModel.uiState.value.errorMessage)
    }

    @Test
    fun initialState_loggedIn() {
        val repository = FakeAuthRepository()
        repository.currentUser = Any()

        val viewModel = AuthViewModel(repository)

        assertTrue(viewModel.uiState.value.isLoggedIn)
    }

    @Test
    fun login_emptyEmail_showsError() {
        val viewModel = AuthViewModel(FakeAuthRepository())

        viewModel.login(
            email = "",
            password = "password",
            onSuccess = {}
        )

        assertEquals(
            "Unesite email i password.",
            viewModel.uiState.value.errorMessage
        )
    }

    @Test
    fun login_emptyPassword_showsError() {
        val viewModel = AuthViewModel(FakeAuthRepository())

        viewModel.login(
            email = "test@test.com",
            password = "",
            onSuccess = {}
        )

        assertEquals(
            "Unesite email i password.",
            viewModel.uiState.value.errorMessage
        )
    }

    @Test
    fun login_spacesOnlyEmail_showsError() {
        val viewModel = AuthViewModel(FakeAuthRepository())

        viewModel.login(
            email = "     ",
            password = "password",
            onSuccess = {}
        )

        assertEquals(
            "Unesite email i password.",
            viewModel.uiState.value.errorMessage
        )
    }

    @Test
    fun login_spacesOnlyPassword_showsError() {
        val viewModel = AuthViewModel(FakeAuthRepository())

        viewModel.login(
            email = "test@test.com",
            password = "     ",
            onSuccess = {}
        )

        assertEquals(
            "Unesite email i password.",
            viewModel.uiState.value.errorMessage
        )
    }

    @Test
    fun login_bothFieldsEmpty_showsError() {
        val viewModel = AuthViewModel(FakeAuthRepository())

        viewModel.login(
            email = "",
            password = "",
            onSuccess = {}
        )

        assertEquals(
            "Unesite email i password.",
            viewModel.uiState.value.errorMessage
        )
    }

    @Test
    fun login_validData_callsRepository() {
        val repository = FakeAuthRepository()
        val viewModel = AuthViewModel(repository)

        viewModel.login(
            email = "test@test.com",
            password = "password",
            onSuccess = {}
        )

        Thread.sleep(100)

        assertEquals(
            "test@test.com",
            repository.lastLoginEmail
        )

        assertEquals(
            "password",
            repository.lastLoginPassword
        )
    }

    @Test
    fun login_trimsEmail() {
        val repository = FakeAuthRepository()
        val viewModel = AuthViewModel(repository)

        viewModel.login(
            email = "  test@test.com  ",
            password = "password",
            onSuccess = {}
        )

        Thread.sleep(100)

        assertEquals(
            "test@test.com",
            repository.lastLoginEmail
        )
    }

    @Test
    fun login_success_setsLoggedIn() {
        val repository = FakeAuthRepository()
        val viewModel = AuthViewModel(repository)

        viewModel.login(
            email = "test@test.com",
            password = "password",
            onSuccess = {}
        )

        Thread.sleep(100)

        assertTrue(viewModel.uiState.value.isLoggedIn)
    }

    @Test
    fun login_success_stopsLoading() {
        val repository = FakeAuthRepository()
        val viewModel = AuthViewModel(repository)

        viewModel.login(
            email = "test@test.com",
            password = "password",
            onSuccess = {}
        )

        Thread.sleep(100)

        assertFalse(viewModel.uiState.value.isLoading)
    }

    @Test
    fun login_success_callsCallback() {
        val repository = FakeAuthRepository()
        val viewModel = AuthViewModel(repository)

        var callbackCalled = false

        viewModel.login(
            email = "test@test.com",
            password = "password",
            onSuccess = {
                callbackCalled = true
            }
        )

        Thread.sleep(100)

        assertTrue(callbackCalled)
    }

    @Test
    fun login_failure_showsError() {
        val repository = FakeAuthRepository()

        repository.loginResult = Result.failure(
            Exception("Pogrešan email ili password.")
        )

        val viewModel = AuthViewModel(repository)

        viewModel.login(
            email = "test@test.com",
            password = "wrong",
            onSuccess = {}
        )

        Thread.sleep(100)

        assertEquals(
            "Pogrešan email ili password.",
            viewModel.uiState.value.errorMessage
        )
    }

    @Test
    fun login_failure_staysLoggedOut() {
        val repository = FakeAuthRepository()

        repository.loginResult = Result.failure(
            Exception("Greška")
        )

        val viewModel = AuthViewModel(repository)

        viewModel.login(
            email = "test@test.com",
            password = "wrong",
            onSuccess = {}
        )

        Thread.sleep(100)

        assertFalse(viewModel.uiState.value.isLoggedIn)
    }

    @Test
    fun login_failure_stopsLoading() {
        val repository = FakeAuthRepository()

        repository.loginResult = Result.failure(
            Exception("Greška")
        )

        val viewModel = AuthViewModel(repository)

        viewModel.login(
            email = "test@test.com",
            password = "wrong",
            onSuccess = {}
        )

        Thread.sleep(100)

        assertFalse(viewModel.uiState.value.isLoading)
    }

    @Test
    fun login_failureWithoutMessage_usesDefaultMessage() {
        val repository = FakeAuthRepository()

        repository.loginResult = Result.failure(
            Exception()
        )

        val viewModel = AuthViewModel(repository)

        viewModel.login(
            email = "test@test.com",
            password = "wrong",
            onSuccess = {}
        )

        Thread.sleep(100)

        assertEquals(
            "Prijava nije uspjela.",
            viewModel.uiState.value.errorMessage
        )
    }

    @Test
    fun register_emptyFields_showsError() {
        val viewModel = AuthViewModel(FakeAuthRepository())

        viewModel.register(
            name = "",
            email = "",
            password = "",
            onSuccess = {}
        )

        assertEquals(
            "Unesite ime, email i password.",
            viewModel.uiState.value.errorMessage
        )
    }

    @Test
    fun register_emptyName_showsError() {
        val viewModel = AuthViewModel(FakeAuthRepository())

        viewModel.register(
            name = "",
            email = "test@test.com",
            password = "password",
            onSuccess = {}
        )

        assertEquals(
            "Unesite ime, email i password.",
            viewModel.uiState.value.errorMessage
        )
    }

    @Test
    fun register_emptyEmail_showsError() {
        val viewModel = AuthViewModel(FakeAuthRepository())

        viewModel.register(
            name = "Elo",
            email = "",
            password = "password",
            onSuccess = {}
        )

        assertEquals(
            "Unesite ime, email i password.",
            viewModel.uiState.value.errorMessage
        )
    }

    @Test
    fun register_emptyPassword_showsError() {
        val viewModel = AuthViewModel(FakeAuthRepository())

        viewModel.register(
            name = "Elo",
            email = "test@test.com",
            password = "",
            onSuccess = {}
        )

        assertEquals(
            "Unesite ime, email i password.",
            viewModel.uiState.value.errorMessage
        )
    }

    @Test
    fun register_spacesOnlyName_showsError() {
        val viewModel = AuthViewModel(FakeAuthRepository())

        viewModel.register(
            name = "     ",
            email = "test@test.com",
            password = "password",
            onSuccess = {}
        )

        assertEquals(
            "Unesite ime, email i password.",
            viewModel.uiState.value.errorMessage
        )
    }

    @Test
    fun register_spacesOnlyEmail_showsError() {
        val viewModel = AuthViewModel(FakeAuthRepository())

        viewModel.register(
            name = "Elo",
            email = "     ",
            password = "password",
            onSuccess = {}
        )

        assertEquals(
            "Unesite ime, email i password.",
            viewModel.uiState.value.errorMessage
        )
    }

    @Test
    fun register_spacesOnlyPassword_showsError() {
        val viewModel = AuthViewModel(FakeAuthRepository())

        viewModel.register(
            name = "Elo",
            email = "test@test.com",
            password = "     ",
            onSuccess = {}
        )

        assertEquals(
            "Unesite ime, email i password.",
            viewModel.uiState.value.errorMessage
        )
    }

    @Test
    fun register_validData_callsRepository() {
        val repository = FakeAuthRepository()
        val viewModel = AuthViewModel(repository)

        viewModel.register(
            name = "Elo",
            email = "test@test.com",
            password = "password",
            onSuccess = {}
        )

        Thread.sleep(100)

        assertEquals(
            "Elo",
            repository.lastRegisterName
        )

        assertEquals(
            "test@test.com",
            repository.lastRegisterEmail
        )

        assertEquals(
            "password",
            repository.lastRegisterPassword
        )
    }

    @Test
    fun register_trimsName() {
        val repository = FakeAuthRepository()
        val viewModel = AuthViewModel(repository)

        viewModel.register(
            name = "  Elo  ",
            email = "test@test.com",
            password = "password",
            onSuccess = {}
        )

        Thread.sleep(100)

        assertEquals(
            "Elo",
            repository.lastRegisterName
        )
    }

    @Test
    fun register_trimsEmail() {
        val repository = FakeAuthRepository()
        val viewModel = AuthViewModel(repository)

        viewModel.register(
            name = "Elo",
            email = "  test@test.com  ",
            password = "password",
            onSuccess = {}
        )

        Thread.sleep(100)

        assertEquals(
            "test@test.com",
            repository.lastRegisterEmail
        )
    }

    @Test
    fun register_success_setsLoggedIn() {
        val repository = FakeAuthRepository()
        val viewModel = AuthViewModel(repository)

        viewModel.register(
            name = "Elo",
            email = "test@test.com",
            password = "password",
            onSuccess = {}
        )

        Thread.sleep(100)

        assertTrue(viewModel.uiState.value.isLoggedIn)
    }

    @Test
    fun register_success_stopsLoading() {
        val repository = FakeAuthRepository()
        val viewModel = AuthViewModel(repository)

        viewModel.register(
            name = "Elo",
            email = "test@test.com",
            password = "password",
            onSuccess = {}
        )

        Thread.sleep(100)

        assertFalse(viewModel.uiState.value.isLoading)
    }

    @Test
    fun register_success_callsCallback() {
        val repository = FakeAuthRepository()
        val viewModel = AuthViewModel(repository)

        var callbackCalled = false

        viewModel.register(
            name = "Elo",
            email = "test@test.com",
            password = "password",
            onSuccess = {
                callbackCalled = true
            }
        )

        Thread.sleep(100)

        assertTrue(callbackCalled)
    }

    @Test
    fun register_failure_showsError() {
        val repository = FakeAuthRepository()

        repository.registerResult = Result.failure(
            Exception("Email već postoji.")
        )

        val viewModel = AuthViewModel(repository)

        viewModel.register(
            name = "Elo",
            email = "test@test.com",
            password = "password",
            onSuccess = {}
        )

        Thread.sleep(100)

        assertEquals(
            "Email već postoji.",
            viewModel.uiState.value.errorMessage
        )
    }

    @Test
    fun register_failure_staysLoggedOut() {
        val repository = FakeAuthRepository()

        repository.registerResult = Result.failure(
            Exception("Greška")
        )

        val viewModel = AuthViewModel(repository)

        viewModel.register(
            name = "Elo",
            email = "test@test.com",
            password = "password",
            onSuccess = {}
        )

        Thread.sleep(100)

        assertFalse(viewModel.uiState.value.isLoggedIn)
    }

    @Test
    fun register_failure_stopsLoading() {
        val repository = FakeAuthRepository()

        repository.registerResult = Result.failure(
            Exception("Greška")
        )

        val viewModel = AuthViewModel(repository)

        viewModel.register(
            name = "Elo",
            email = "test@test.com",
            password = "password",
            onSuccess = {}
        )

        Thread.sleep(100)

        assertFalse(viewModel.uiState.value.isLoading)
    }

    @Test
    fun register_failureWithoutMessage_usesDefaultMessage() {
        val repository = FakeAuthRepository()

        repository.registerResult = Result.failure(
            Exception()
        )

        val viewModel = AuthViewModel(repository)

        viewModel.register(
            name = "Elo",
            email = "test@test.com",
            password = "password",
            onSuccess = {}
        )

        Thread.sleep(100)

        assertEquals(
            "Registracija nije uspjela.",
            viewModel.uiState.value.errorMessage
        )
    }

    @Test
    fun login_invalidData_doesNotCallRepository() {
        val repository = FakeAuthRepository()
        val viewModel = AuthViewModel(repository)

        viewModel.login(
            email = "     ",
            password = "password",
            onSuccess = {}
        )

        assertNull(repository.lastLoginEmail)
        assertNull(repository.lastLoginPassword)
    }

    @Test
    fun register_invalidData_doesNotCallRepository() {
        val repository = FakeAuthRepository()
        val viewModel = AuthViewModel(repository)

        viewModel.register(
            name = "     ",
            email = "test@test.com",
            password = "password",
            onSuccess = {}
        )

        assertNull(repository.lastRegisterName)
        assertNull(repository.lastRegisterEmail)
        assertNull(repository.lastRegisterPassword)
    }

    @Test
    fun clearError_removesError() {
        val viewModel = AuthViewModel(FakeAuthRepository())

        viewModel.login(
            email = "",
            password = "",
            onSuccess = {}
        )

        assertEquals(
            "Unesite email i password.",
            viewModel.uiState.value.errorMessage
        )

        viewModel.clearError()

        assertNull(viewModel.uiState.value.errorMessage)
    }

    @Test
    fun clearError_keepsLoginState() {
        val repository = FakeAuthRepository()
        repository.currentUser = Any()

        val viewModel = AuthViewModel(repository)

        viewModel.clearError()

        assertTrue(viewModel.uiState.value.isLoggedIn)
    }

    @Test
    fun logout_logsOut() {
        val repository = FakeAuthRepository()
        repository.currentUser = Any()

        val viewModel = AuthViewModel(repository)

        viewModel.logout()

        assertTrue(repository.logoutCalled)
        assertFalse(viewModel.uiState.value.isLoggedIn)
    }

    @Test
    fun logout_clearsError() {
        val viewModel = AuthViewModel(FakeAuthRepository())

        viewModel.login(
            email = "",
            password = "",
            onSuccess = {}
        )

        assertEquals(
            "Unesite email i password.",
            viewModel.uiState.value.errorMessage
        )

        viewModel.logout()

        assertNull(viewModel.uiState.value.errorMessage)
    }
}

private class FakeAuthRepository : AuthRepositoryContract {

    override var currentUser: Any? = null

    var loginResult: Result<Unit> = Result.success(Unit)
    var registerResult: Result<Unit> = Result.success(Unit)

    var lastLoginEmail: String? = null
    var lastLoginPassword: String? = null

    var lastRegisterName: String? = null
    var lastRegisterEmail: String? = null
    var lastRegisterPassword: String? = null

    var logoutCalled = false

    override suspend fun login(
        email: String,
        password: String
    ): Result<Unit> {
        lastLoginEmail = email
        lastLoginPassword = password
        return loginResult
    }

    override suspend fun register(
        name: String,
        email: String,
        password: String
    ): Result<Unit> {
        lastRegisterName = name
        lastRegisterEmail = email
        lastRegisterPassword = password
        return registerResult
    }

    override fun logout() {
        logoutCalled = true
        currentUser = null
    }
}