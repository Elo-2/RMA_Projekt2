package com.example.moviehub.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

data class UserProfile(
    val uid: String = "",
    val name: String = "",
    val email: String = ""
)

class ProfileRepository {

    private val firebaseAuth = FirebaseAuth.getInstance()

    private val database = FirebaseDatabase.getInstance(
        "https://moviehub-9e302-default-rtdb.europe-west1.firebasedatabase.app"
    )

    suspend fun getProfile(): Result<UserProfile> {
        return try {

            val user = firebaseAuth.currentUser
                ?: return Result.failure(
                    Exception("Korisnik nije prijavljen.")
                )

            /*
             * Firebase Authentication podaci.
             *
             * Ovo koristimo kao osnovne podatke profila.
             */
            val uid = user.uid
            val email = user.email ?: ""
            val authName = user.displayName ?: ""

            /*
             * Pokušavamo učitati dodatne podatke iz Realtime Database.
             */
            val snapshot = database
                .getReference("users")
                .child(uid)
                .get()
                .await()

            val databaseName = snapshot
                .child("name")
                .getValue(String::class.java)

            val databaseEmail = snapshot
                .child("email")
                .getValue(String::class.java)

            val finalName = when {
                !databaseName.isNullOrBlank() -> databaseName
                authName.isNotBlank() -> authName
                else -> "Korisnik"
            }

            val finalEmail = when {
                !databaseEmail.isNullOrBlank() -> databaseEmail
                email.isNotBlank() -> email
                else -> ""
            }

            /*
             * Ako je korisnik postojao u Authentication,
             * ali nije imao podatke u Realtime Database,
             * automatski ih napravimo.
             */
            if (!snapshot.exists()) {

                database
                    .getReference("users")
                    .child(uid)
                    .setValue(
                        mapOf(
                            "uid" to uid,
                            "name" to finalName,
                            "email" to finalEmail
                        )
                    )
                    .await()
            }

            Result.success(
                UserProfile(
                    uid = uid,
                    name = finalName,
                    email = finalEmail
                )
            )

        } catch (exception: Exception) {

            /*
             * Ako Realtime Database trenutno nije dostupna,
             * i dalje možemo prikazati podatke iz Firebase Authentication.
             */
            val user = firebaseAuth.currentUser

            if (user != null) {

                Result.success(
                    UserProfile(
                        uid = user.uid,
                        name = user.displayName ?: "Korisnik",
                        email = user.email ?: ""
                    )
                )

            } else {

                Result.failure(exception)
            }
        }
    }

    suspend fun updateName(name: String): Result<Unit> {
        return try {

            val user = firebaseAuth.currentUser
                ?: return Result.failure(
                    Exception("Korisnik nije prijavljen.")
                )

            val cleanName = name.trim()

            val profileUpdates =
                UserProfileChangeRequest.Builder()
                    .setDisplayName(cleanName)
                    .build()

            user.updateProfile(profileUpdates)
                .await()

            database
                .getReference("users")
                .child(user.uid)
                .child("name")
                .setValue(cleanName)
                .await()

            Result.success(Unit)

        } catch (exception: Exception) {

            Result.failure(exception)
        }
    }

    suspend fun changePassword(password: String): Result<Unit> {
        return try {

            val user = firebaseAuth.currentUser
                ?: return Result.failure(
                    Exception("Korisnik nije prijavljen.")
                )

            user.updatePassword(password)
                .await()

            Result.success(Unit)

        } catch (exception: Exception) {

            Result.failure(exception)
        }
    }

    fun logout() {
        firebaseAuth.signOut()
    }
}