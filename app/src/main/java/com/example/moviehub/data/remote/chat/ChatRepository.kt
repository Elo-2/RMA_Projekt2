package com.example.moviehub.data.remote.chat

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class ChatRepository : ChatRepositoryContract {

    private val database = FirebaseDatabase.getInstance(
        "https://moviehub-9e302-default-rtdb.europe-west1.firebasedatabase.app/"
    )

    private val messagesReference = database
        .getReference("chat_messages")

    override fun getMessages(): Flow<List<ChatMessage>> = callbackFlow {

        val listener = object : com.google.firebase.database.ValueEventListener {

            override fun onDataChange(
                snapshot: com.google.firebase.database.DataSnapshot
            ) {
                val messages = snapshot.children
                    .mapNotNull { child ->
                        try {
                            child.getValue(ChatMessage::class.java)
                        } catch (e: Exception) {
                            Log.e(
                                "MovieHubChat",
                                "Greška prilikom čitanja poruke",
                                e
                            )
                            null
                        }
                    }
                    .sortedBy {
                        it.timestamp
                    }

                Log.d(
                    "MovieHubChat",
                    "Učitano poruka: ${messages.size}"
                )

                trySend(messages)
            }

            override fun onCancelled(
                error: com.google.firebase.database.DatabaseError
            ) {
                Log.e(
                    "MovieHubChat",
                    "Firebase čitanje otkazano: ${error.message}",
                    error.toException()
                )

                close(error.toException())
            }
        }

        messagesReference.addValueEventListener(listener)

        awaitClose {
            messagesReference.removeEventListener(listener)
        }
    }

    override fun sendMessage(
        userId: String,
        userName: String,
        message: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        val cleanMessage = message.trim()

        if (cleanMessage.isBlank()) {
            onError(
                IllegalArgumentException(
                    "Poruka ne može biti prazna."
                )
            )
            return
        }

        Log.d(
            "MovieHubChat",
            "Pokušavam poslati poruku: $cleanMessage"
        )

        Log.d(
            "MovieHubChat",
            "Korisnik: $userId"
        )

        Log.d(
            "MovieHubChat",
            "Firebase Database URL: https://moviehub-9e302-default-rtdb.europe-west1.firebasedatabase.app/"
        )

        val messageReference = messagesReference.push()

        val messageId = messageReference.key

        if (messageId == null) {
            onError(
                Exception(
                    "Firebase nije mogao generisati ID poruke."
                )
            )
            return
        }

        val chatMessage = ChatMessage(
            id = messageId,
            userId = userId,
            userName = userName,
            message = cleanMessage,
            timestamp = System.currentTimeMillis()
        )

        Log.d(
            "MovieHubChat",
            "ID nove poruke: $messageId"
        )

        messageReference
            .setValue(chatMessage)
            .addOnSuccessListener {

                Log.d(
                    "MovieHubChat",
                    "PORUKA USPJEŠNO POSLANA"
                )

                Log.d(
                    "MovieHubChat",
                    "Poruka je upisana u /chat_messages/$messageId"
                )

                onSuccess()
            }
            .addOnFailureListener { exception ->

                Log.e(
                    "MovieHubChat",
                    "GREŠKA PRILIKOM SLANJA PORUKE",
                    exception
                )

                Log.e(
                    "MovieHubChat",
                    "Poruka greške: ${exception.message}"
                )

                onError(exception)
            }
    }
}