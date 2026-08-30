package com.example.moviehub.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

interface AuthRepositoryContract {

    val currentUser: Any?

    suspend fun login(
        email: String,
        password: String
    ): Result<Unit>

    suspend fun register(
        name: String,
        email: String,
        password: String
    ): Result<Unit>

    fun logout()
}

class AuthRepository : AuthRepositoryContract {

    private val firebaseAuth = FirebaseAuth.getInstance()

    private val database = FirebaseDatabase.getInstance(
        "https://moviehub-9e302-default-rtdb.europe-west1.firebasedatabase.app"
    )

    override val currentUser: Any?
        get() = firebaseAuth.currentUser

    override suspend fun login(
        email: String,
        password: String
    ): Result<Unit> {
        return try {
            firebaseAuth
                .signInWithEmailAndPassword(
                    email.trim(),
                    password
                )
                .await()

            Result.success(Unit)

        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    override suspend fun register(
        name: String,
        email: String,
        password: String
    ): Result<Unit> {
        return try {
            val result = firebaseAuth
                .createUserWithEmailAndPassword(
                    email.trim(),
                    password
                )
                .await()

            val user = result.user
                ?: return Result.failure(
                    Exception("Registracija nije uspjela.")
                )

            val cleanName = name.trim()
            val cleanEmail = email.trim()

            val profileUpdates =
                UserProfileChangeRequest.Builder()
                    .setDisplayName(cleanName)
                    .build()

            user.updateProfile(profileUpdates)
                .await()

            database
                .getReference("users")
                .child(user.uid)
                .setValue(
                    mapOf(
                        "uid" to user.uid,
                        "name" to cleanName,
                        "email" to cleanEmail
                    )
                )
                .await()

            /*
             * Nakon uspješne registracije odjavljujemo korisnika.
             *
             * Tako će tok aplikacije biti:
             *
             * Registracija -> Login -> Home
             *
             * Korisnik je već napravljen u Firebase Authentication,
             * ali mora se ponovo prijaviti kroz Login ekran.
             */
            firebaseAuth.signOut()

            Result.success(Unit)

        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }
}