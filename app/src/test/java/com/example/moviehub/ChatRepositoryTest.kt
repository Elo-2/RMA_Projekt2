package com.example.moviehub

import com.example.moviehub.data.remote.chat.ChatMessage
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ChatRepositoryTest {

    @Test
    fun message_hasCorrectId() {
        val message = createMessage()

        assertEquals("message1", message.id)
    }

    @Test
    fun message_hasCorrectUserId() {
        val message = createMessage()

        assertEquals("user1", message.userId)
    }

    @Test
    fun message_hasCorrectUserName() {
        val message = createMessage()

        assertEquals("Elo", message.userName)
    }

    @Test
    fun message_hasCorrectText() {
        val message = createMessage()

        assertEquals("Hello MovieHub", message.message)
    }

    @Test
    fun message_hasCorrectTimestamp() {
        val message = createMessage()

        assertEquals(1000L, message.timestamp)
    }

    @Test
    fun messageId_isNotEmpty() {
        val message = createMessage()

        assertTrue(message.id.isNotEmpty())
    }

    @Test
    fun messageUserId_isNotEmpty() {
        val message = createMessage()

        assertTrue(message.userId.isNotEmpty())
    }

    @Test
    fun messageUserName_isNotEmpty() {
        val message = createMessage()

        assertTrue(message.userName.isNotEmpty())
    }

    @Test
    fun messageText_isNotEmpty() {
        val message = createMessage()

        assertTrue(message.message.isNotEmpty())
    }

    @Test
    fun messageTimestamp_isPositive() {
        val message = createMessage()

        assertTrue(message.timestamp > 0)
    }

    @Test
    fun emptyMessage_hasDefaultId() {
        val message = ChatMessage()

        assertEquals("", message.id)
    }

    @Test
    fun emptyMessage_hasDefaultUserId() {
        val message = ChatMessage()

        assertEquals("", message.userId)
    }

    @Test
    fun emptyMessage_hasDefaultUserName() {
        val message = ChatMessage()

        assertEquals("", message.userName)
    }

    @Test
    fun emptyMessage_hasDefaultText() {
        val message = ChatMessage()

        assertEquals("", message.message)
    }

    @Test
    fun emptyMessage_hasDefaultTimestamp() {
        val message = ChatMessage()

        assertEquals(0L, message.timestamp)
    }

    @Test
    fun message_copyCanChangeId() {
        val message = createMessage()

        val changed = message.copy(id = "message2")

        assertEquals("message2", changed.id)
        assertEquals("message1", message.id)
    }

    @Test
    fun message_copyCanChangeUserId() {
        val message = createMessage()

        val changed = message.copy(userId = "user2")

        assertEquals("user2", changed.userId)
        assertEquals("user1", message.userId)
    }

    @Test
    fun message_copyCanChangeUserName() {
        val message = createMessage()

        val changed = message.copy(userName = "Marko")

        assertEquals("Marko", changed.userName)
        assertEquals("Elo", message.userName)
    }

    @Test
    fun message_copyCanChangeText() {
        val message = createMessage()

        val changed = message.copy(message = "New message")

        assertEquals("New message", changed.message)
        assertEquals("Hello MovieHub", message.message)
    }

    @Test
    fun message_copyCanChangeTimestamp() {
        val message = createMessage()

        val changed = message.copy(timestamp = 5000L)

        assertEquals(5000L, changed.timestamp)
        assertEquals(1000L, message.timestamp)
    }

    @Test
    fun message_copyKeepsUnchangedValues() {
        val message = createMessage()

        val changed = message.copy(message = "Changed")

        assertEquals(message.id, changed.id)
        assertEquals(message.userId, changed.userId)
        assertEquals(message.userName, changed.userName)
        assertEquals(message.timestamp, changed.timestamp)
    }

    @Test
    fun differentMessages_haveDifferentIds() {
        val first = createMessage()
        val second = createMessage().copy(id = "message2")

        assertNotEquals(first.id, second.id)
    }

    @Test
    fun differentMessages_canHaveDifferentUsers() {
        val first = createMessage()
        val second = createMessage().copy(userId = "user2")

        assertNotEquals(first.userId, second.userId)
    }

    @Test
    fun differentMessages_canHaveDifferentTexts() {
        val first = createMessage()
        val second = createMessage().copy(message = "Different")

        assertNotEquals(first.message, second.message)
    }

    @Test
    fun messagesCanBeStoredInList() {
        val messages = listOf(
            createMessage(),
            createMessage().copy(
                id = "message2",
                message = "Second message"
            ),
            createMessage().copy(
                id = "message3",
                message = "Third message"
            )
        )

        assertEquals(3, messages.size)
    }

    @Test
    fun messagesCanBeSortedAscendingByTimestamp() {
        val messages = listOf(
            createMessage().copy(timestamp = 3000L),
            createMessage().copy(timestamp = 1000L),
            createMessage().copy(timestamp = 2000L)
        )

        val sorted = messages.sortedBy { it.timestamp }

        assertEquals(1000L, sorted[0].timestamp)
        assertEquals(2000L, sorted[1].timestamp)
        assertEquals(3000L, sorted[2].timestamp)
    }

    @Test
    fun messagesCanBeSortedDescendingByTimestamp() {
        val messages = listOf(
            createMessage().copy(timestamp = 1000L),
            createMessage().copy(timestamp = 3000L),
            createMessage().copy(timestamp = 2000L)
        )

        val sorted = messages.sortedByDescending { it.timestamp }

        assertEquals(3000L, sorted[0].timestamp)
        assertEquals(2000L, sorted[1].timestamp)
        assertEquals(1000L, sorted[2].timestamp)
    }

    @Test
    fun messagesListCanBeEmpty() {
        val messages = emptyList<ChatMessage>()

        assertTrue(messages.isEmpty())
    }

    @Test
    fun messagesListCanContainOneMessage() {
        val messages = listOf(createMessage())

        assertEquals(1, messages.size)
        assertNotNull(messages.first())
    }

    @Test
    fun messagesListCanContainMultipleMessages() {
        val messages = listOf(
            createMessage(),
            createMessage().copy(id = "message2"),
            createMessage().copy(id = "message3"),
            createMessage().copy(id = "message4"),
            createMessage().copy(id = "message5")
        )

        assertEquals(5, messages.size)
    }

    @Test
    fun messageWithSpacesCanBeStored() {
        val message = createMessage().copy(
            message = "  Hello MovieHub  "
        )

        assertEquals("  Hello MovieHub  ", message.message)
    }

    @Test
    fun blankMessageCanBeDetected() {
        val message = createMessage().copy(
            message = "   "
        )

        assertTrue(message.message.isBlank())
    }

    @Test
    fun normalMessageIsNotBlank() {
        val message = createMessage()

        assertFalse(message.message.isBlank())
    }

    @Test
    fun timestampCanBeZero() {
        val message = createMessage().copy(timestamp = 0L)

        assertEquals(0L, message.timestamp)
    }

    @Test
    fun timestampCanBeLarge() {
        val message = createMessage().copy(
            timestamp = Long.MAX_VALUE
        )

        assertEquals(Long.MAX_VALUE, message.timestamp)
    }

    @Test
    fun messageEqualityWorksCorrectly() {
        val first = createMessage()
        val second = createMessage()

        assertEquals(first, second)
    }

    @Test
    fun changedMessageIsNotEqualToOriginal() {
        val first = createMessage()
        val second = createMessage().copy(
            message = "Different"
        )

        assertNotEquals(first, second)
    }

    @Test
    fun changedIdMakesMessagesDifferent() {
        val first = createMessage()
        val second = createMessage().copy(
            id = "different"
        )

        assertNotEquals(first, second)
    }

    @Test
    fun changedTimestampMakesMessagesDifferent() {
        val first = createMessage()
        val second = createMessage().copy(
            timestamp = 2000L
        )

        assertNotEquals(first, second)
    }

    @Test
    fun messageCanContainNumbers() {
        val message = createMessage().copy(
            message = "Movie 123"
        )

        assertEquals("Movie 123", message.message)
    }

    @Test
    fun messageCanContainSpecialCharacters() {
        val message = createMessage().copy(
            message = "Hello! 😊"
        )

        assertEquals("Hello! 😊", message.message)
    }

    @Test
    fun userNameCanContainSpaces() {
        val message = createMessage().copy(
            userName = "Elo MovieHub"
        )

        assertEquals("Elo MovieHub", message.userName)
    }

    private fun createMessage(): ChatMessage {
        return ChatMessage(
            id = "message1",
            userId = "user1",
            userName = "Elo",
            message = "Hello MovieHub",
            timestamp = 1000L
        )
    }
}