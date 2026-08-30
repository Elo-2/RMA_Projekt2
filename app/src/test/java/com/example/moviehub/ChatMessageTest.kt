package com.example.moviehub

import com.example.moviehub.data.remote.chat.ChatMessage
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ChatMessageTest {

    @Test
    fun defaultMessage_hasEmptyId() {
        val message = ChatMessage()

        assertEquals("", message.id)
    }

    @Test
    fun defaultMessage_hasEmptyUserId() {
        val message = ChatMessage()

        assertEquals("", message.userId)
    }

    @Test
    fun defaultMessage_hasEmptyUserName() {
        val message = ChatMessage()

        assertEquals("", message.userName)
    }

    @Test
    fun defaultMessage_hasEmptyMessage() {
        val message = ChatMessage()

        assertEquals("", message.message)
    }

    @Test
    fun defaultMessage_hasZeroTimestamp() {
        val message = ChatMessage()

        assertEquals(0L, message.timestamp)
    }

    @Test
    fun chatMessage_hasCorrectId() {
        val message = ChatMessage(id = "123")

        assertEquals("123", message.id)
    }

    @Test
    fun chatMessage_hasCorrectUserId() {
        val message = ChatMessage(userId = "user1")

        assertEquals("user1", message.userId)
    }

    @Test
    fun chatMessage_hasCorrectUserName() {
        val message = ChatMessage(userName = "Elo")

        assertEquals("Elo", message.userName)
    }

    @Test
    fun chatMessage_hasCorrectMessage() {
        val message = ChatMessage(message = "Pozdrav")

        assertEquals("Pozdrav", message.message)
    }

    @Test
    fun chatMessage_hasCorrectTimestamp() {
        val message = ChatMessage(timestamp = 1000L)

        assertEquals(1000L, message.timestamp)
    }

    @Test
    fun chatMessage_canContainLongMessage() {
        val message = ChatMessage(
            message = "Ovo je duža poruka za testiranje."
        )

        assertTrue(message.message.length > 10)
    }

    @Test
    fun chatMessage_canContainEmptyMessage() {
        val message = ChatMessage(message = "")

        assertEquals("", message.message)
    }

    @Test
    fun chatMessage_canContainZeroTimestamp() {
        val message = ChatMessage(timestamp = 0L)

        assertEquals(0L, message.timestamp)
    }

    @Test
    fun chatMessage_canContainNegativeTimestamp() {
        val message = ChatMessage(timestamp = -100L)

        assertEquals(-100L, message.timestamp)
    }

    @Test
    fun chatMessage_canContainDifferentUserNames() {
        val first = ChatMessage(userName = "Elo")
        val second = ChatMessage(userName = "Admin")

        assertNotEquals(first.userName, second.userName)
    }

    @Test
    fun chatMessage_canContainDifferentMessages() {
        val first = ChatMessage(message = "Pozdrav")
        val second = ChatMessage(message = "Zdravo")

        assertNotEquals(first.message, second.message)
    }

    @Test
    fun chatMessage_canContainDifferentIds() {
        val first = ChatMessage(id = "1")
        val second = ChatMessage(id = "2")

        assertNotEquals(first.id, second.id)
    }

    @Test
    fun chatMessage_canContainDifferentUsers() {
        val first = ChatMessage(userId = "user1")
        val second = ChatMessage(userId = "user2")

        assertNotEquals(first.userId, second.userId)
    }

    @Test
    fun sameMessages_areEqual() {
        val first = ChatMessage(
            id = "1",
            userId = "user1",
            userName = "Elo",
            message = "Pozdrav",
            timestamp = 1000L
        )

        val second = ChatMessage(
            id = "1",
            userId = "user1",
            userName = "Elo",
            message = "Pozdrav",
            timestamp = 1000L
        )

        assertEquals(first, second)
    }

    @Test
    fun differentMessages_areNotEqual() {
        val first = ChatMessage(
            id = "1",
            message = "Pozdrav"
        )

        val second = ChatMessage(
            id = "2",
            message = "Zdravo"
        )

        assertNotEquals(first, second)
    }

    @Test
    fun copy_keepsSameValues() {
        val original = ChatMessage(
            id = "1",
            userId = "user1",
            userName = "Elo",
            message = "Pozdrav",
            timestamp = 1000L
        )

        val copy = original.copy()

        assertEquals(original, copy)
    }

    @Test
    fun copy_canChangeMessage() {
        val original = ChatMessage(
            id = "1",
            message = "Pozdrav"
        )

        val copy = original.copy(
            message = "Nova poruka"
        )

        assertEquals("Pozdrav", original.message)
        assertEquals("Nova poruka", copy.message)
    }

    @Test
    fun copy_canChangeUserName() {
        val original = ChatMessage(
            userName = "Elo"
        )

        val copy = original.copy(
            userName = "Admin"
        )

        assertEquals("Elo", original.userName)
        assertEquals("Admin", copy.userName)
    }

    @Test
    fun copy_canChangeTimestamp() {
        val original = ChatMessage(
            timestamp = 1000L
        )

        val copy = original.copy(
            timestamp = 2000L
        )

        assertEquals(1000L, original.timestamp)
        assertEquals(2000L, copy.timestamp)
    }

    @Test
    fun chatMessage_isNotNull() {
        val message = ChatMessage()

        assertNotNull(message)
    }

    @Test
    fun chatMessage_canHaveAllValues() {
        val message = ChatMessage(
            id = "123",
            userId = "user1",
            userName = "Elo",
            message = "Pozdrav",
            timestamp = 1000L
        )

        assertEquals("123", message.id)
        assertEquals("user1", message.userId)
        assertEquals("Elo", message.userName)
        assertEquals("Pozdrav", message.message)
        assertEquals(1000L, message.timestamp)
    }

    @Test
    fun chatMessage_toString_containsMessageData() {
        val message = ChatMessage(
            id = "123",
            userId = "user1",
            userName = "Elo",
            message = "Pozdrav",
            timestamp = 1000L
        )

        val text = message.toString()

        assertTrue(text.contains("123"))
        assertTrue(text.contains("user1"))
        assertTrue(text.contains("Elo"))
        assertTrue(text.contains("Pozdrav"))
    }
}