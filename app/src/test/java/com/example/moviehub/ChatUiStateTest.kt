package com.example.moviehub

import com.example.moviehub.data.remote.chat.ChatMessage
import com.example.moviehub.viewmodel.ChatUiState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ChatUiStateTest {

    private fun createMessage(
        id: String = "1",
        userId: String = "user1",
        userName: String = "Elo",
        message: String = "Pozdrav",
        timestamp: Long = 1000L
    ): ChatMessage {
        return ChatMessage(
            id = id,
            userId = userId,
            userName = userName,
            message = message,
            timestamp = timestamp
        )
    }

    @Test
    fun initialState_hasNoMessages() {
        val state = ChatUiState()

        assertTrue(state.messages.isEmpty())
    }

    @Test
    fun initialState_hasEmptyMessageText() {
        val state = ChatUiState()

        assertEquals("", state.messageText)
    }

    @Test
    fun initialState_isLoading() {
        val state = ChatUiState()

        assertTrue(state.isLoading)
    }

    @Test
    fun state_canContainOneMessage() {
        val message = createMessage()

        val state = ChatUiState(
            messages = listOf(message)
        )

        assertEquals(1, state.messages.size)
        assertEquals("Pozdrav", state.messages.first().message)
    }

    @Test
    fun state_canContainMultipleMessages() {
        val state = ChatUiState(
            messages = listOf(
                createMessage(id = "1", message = "Prva poruka"),
                createMessage(id = "2", message = "Druga poruka"),
                createMessage(id = "3", message = "Treća poruka")
            )
        )

        assertEquals(3, state.messages.size)
    }

    @Test
    fun state_canContainMessageText() {
        val state = ChatUiState(
            messageText = "Pozdrav"
        )

        assertEquals("Pozdrav", state.messageText)
    }

    @Test
    fun state_canStopLoading() {
        val state = ChatUiState(
            isLoading = false
        )

        assertFalse(state.isLoading)
    }

    @Test
    fun state_canBeLoading() {
        val state = ChatUiState(
            isLoading = true
        )

        assertTrue(state.isLoading)
    }

    @Test
    fun state_canContainMessagesAndText() {
        val state = ChatUiState(
            messages = listOf(createMessage()),
            messageText = "Nova poruka",
            isLoading = false
        )

        assertEquals(1, state.messages.size)
        assertEquals("Nova poruka", state.messageText)
        assertFalse(state.isLoading)
    }

    @Test
    fun copy_canUpdateMessageText() {
        val initialState = ChatUiState()

        val updatedState = initialState.copy(
            messageText = "Pozdrav"
        )

        assertEquals("", initialState.messageText)
        assertEquals("Pozdrav", updatedState.messageText)
    }

    @Test
    fun copy_canUpdateMessages() {
        val initialState = ChatUiState()

        val updatedState = initialState.copy(
            messages = listOf(createMessage())
        )

        assertTrue(initialState.messages.isEmpty())
        assertEquals(1, updatedState.messages.size)
    }

    @Test
    fun copy_canUpdateLoadingState() {
        val initialState = ChatUiState()

        val updatedState = initialState.copy(
            isLoading = false
        )

        assertTrue(initialState.isLoading)
        assertFalse(updatedState.isLoading)
    }

    @Test
    fun copy_preservesMessageTextWhenOnlyMessagesChange() {
        val initialState = ChatUiState(
            messageText = "Test"
        )

        val updatedState = initialState.copy(
            messages = listOf(createMessage())
        )

        assertEquals("Test", updatedState.messageText)
    }

    @Test
    fun copy_preservesMessagesWhenOnlyTextChanges() {
        val message = createMessage()

        val initialState = ChatUiState(
            messages = listOf(message)
        )

        val updatedState = initialState.copy(
            messageText = "Test"
        )

        assertEquals(1, updatedState.messages.size)
        assertEquals(message, updatedState.messages.first())
    }

    @Test
    fun copy_preservesLoadingWhenOnlyTextChanges() {
        val initialState = ChatUiState(
            isLoading = false
        )

        val updatedState = initialState.copy(
            messageText = "Test"
        )

        assertFalse(updatedState.isLoading)
    }

    @Test
    fun emptyMessageText_isAllowed() {
        val state = ChatUiState(
            messageText = ""
        )

        assertEquals("", state.messageText)
    }

    @Test
    fun messageText_canContainSpaces() {
        val state = ChatUiState(
            messageText = "   Pozdrav   "
        )

        assertEquals("   Pozdrav   ", state.messageText)
    }

    @Test
    fun messageText_canContainLongText() {
        val text = "A".repeat(500)

        val state = ChatUiState(
            messageText = text
        )

        assertEquals(text, state.messageText)
    }

    @Test
    fun messageText_canContainNumbers() {
        val state = ChatUiState(
            messageText = "123456"
        )

        assertEquals("123456", state.messageText)
    }

    @Test
    fun messageText_canContainSpecialCharacters() {
        val state = ChatUiState(
            messageText = "!@#$%^&*()"
        )

        assertEquals("!@#$%^&*()", state.messageText)
    }

    @Test
    fun messageText_canContainMultipleWords() {
        val state = ChatUiState(
            messageText = "Ovo je test poruka"
        )

        assertEquals("Ovo je test poruka", state.messageText)
    }

    @Test
    fun messagesCanBeCleared() {
        val state = ChatUiState(
            messages = listOf(createMessage())
        )

        val clearedState = state.copy(
            messages = emptyList()
        )

        assertTrue(clearedState.messages.isEmpty())
    }

    @Test
    fun messageTextCanBeCleared() {
        val state = ChatUiState(
            messageText = "Pozdrav"
        )

        val clearedState = state.copy(
            messageText = ""
        )

        assertEquals("", clearedState.messageText)
    }

    @Test
    fun loadingCanChangeFromTrueToFalse() {
        val loadingState = ChatUiState(
            isLoading = true
        )

        val loadedState = loadingState.copy(
            isLoading = false
        )

        assertTrue(loadingState.isLoading)
        assertFalse(loadedState.isLoading)
    }

    @Test
    fun loadedStateCanContainMessages() {
        val state = ChatUiState(
            messages = listOf(createMessage()),
            isLoading = false
        )

        assertEquals(1, state.messages.size)
        assertFalse(state.isLoading)
    }

    @Test
    fun loadingStateCanContainMessages() {
        val state = ChatUiState(
            messages = listOf(createMessage()),
            isLoading = true
        )

        assertEquals(1, state.messages.size)
        assertTrue(state.isLoading)
    }

    @Test
    fun identicalStates_areEqual() {
        val message = createMessage()

        val first = ChatUiState(
            messages = listOf(message),
            messageText = "Test",
            isLoading = false
        )

        val second = ChatUiState(
            messages = listOf(message),
            messageText = "Test",
            isLoading = false
        )

        assertEquals(first, second)
    }

    @Test
    fun differentMessageText_makesStatesDifferent() {
        val first = ChatUiState(
            messageText = "Prva"
        )

        val second = ChatUiState(
            messageText = "Druga"
        )

        assertNotEquals(first, second)
    }

    @Test
    fun differentLoadingState_makesStatesDifferent() {
        val first = ChatUiState(
            isLoading = true
        )

        val second = ChatUiState(
            isLoading = false
        )

        assertNotEquals(first, second)
    }

    @Test
    fun differentMessages_makeStatesDifferent() {
        val first = ChatUiState(
            messages = listOf(
                createMessage(message = "Prva")
            )
        )

        val second = ChatUiState(
            messages = listOf(
                createMessage(message = "Druga")
            )
        )

        assertNotEquals(first, second)
    }

    @Test
    fun messageOrder_isPreserved() {
        val first = createMessage(id = "1", message = "Prva")
        val second = createMessage(id = "2", message = "Druga")

        val state = ChatUiState(
            messages = listOf(first, second)
        )

        assertEquals("Prva", state.messages[0].message)
        assertEquals("Druga", state.messages[1].message)
    }

    @Test
    fun messagesCanContainDifferentUsers() {
        val first = createMessage(
            userId = "user1",
            userName = "Elo"
        )

        val second = createMessage(
            userId = "user2",
            userName = "Test User"
        )

        val state = ChatUiState(
            messages = listOf(first, second)
        )

        assertEquals("user1", state.messages[0].userId)
        assertEquals("user2", state.messages[1].userId)
    }

    @Test
    fun messagesCanHaveDifferentTimestamps() {
        val first = createMessage(timestamp = 1000L)
        val second = createMessage(timestamp = 2000L)

        val state = ChatUiState(
            messages = listOf(first, second)
        )

        assertEquals(1000L, state.messages[0].timestamp)
        assertEquals(2000L, state.messages[1].timestamp)
    }

    @Test
    fun stateCanBeCreatedWithAllFields() {
        val message = createMessage()

        val state = ChatUiState(
            messages = listOf(message),
            messageText = "Nova poruka",
            isLoading = false
        )

        assertEquals(1, state.messages.size)
        assertEquals("Nova poruka", state.messageText)
        assertFalse(state.isLoading)
    }

    @Test
    fun copyCanReplaceAllFields() {
        val initialState = ChatUiState()

        val message = createMessage()

        val updatedState = initialState.copy(
            messages = listOf(message),
            messageText = "Test",
            isLoading = false
        )

        assertEquals(1, updatedState.messages.size)
        assertEquals("Test", updatedState.messageText)
        assertFalse(updatedState.isLoading)
    }

    @Test
    fun originalStateRemainsUnchangedAfterCopy() {
        val original = ChatUiState()

        original.copy(
            messages = listOf(createMessage()),
            messageText = "Changed",
            isLoading = false
        )

        assertTrue(original.messages.isEmpty())
        assertEquals("", original.messageText)
        assertTrue(original.isLoading)
    }
}