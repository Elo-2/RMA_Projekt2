package com.example.moviehub

import com.example.moviehub.data.remote.chat.ChatMessage
import com.example.moviehub.data.remote.chat.ChatRepositoryContract
import com.example.moviehub.viewmodel.ChatViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ChatViewModelTest {

    private lateinit var testDispatcher: TestDispatcher

    @Before
    fun setUp() {
        testDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun initialState_hasEmptyMessages() = runTest {
        val viewModel = ChatViewModel(FakeChatRepository())

        advanceUntilIdle()

        assertTrue(viewModel.uiState.value.messages.isEmpty())
    }

    @Test
    fun initialState_hasEmptyMessageText() = runTest {
        val viewModel = ChatViewModel(FakeChatRepository())

        assertEquals(
            "",
            viewModel.uiState.value.messageText
        )
    }

    @Test
    fun initialState_isLoading() = runTest {
        val repository = FakeChatRepository()
        val viewModel = ChatViewModel(repository)

        assertTrue(viewModel.uiState.value.isLoading)
    }

    @Test
    fun updateMessageText_updatesState() = runTest {
        val viewModel = ChatViewModel(FakeChatRepository())

        viewModel.updateMessageText("Hello")

        assertEquals(
            "Hello",
            viewModel.uiState.value.messageText
        )
    }

    @Test
    fun updateMessageText_canBeChangedMultipleTimes() = runTest {
        val viewModel = ChatViewModel(FakeChatRepository())

        viewModel.updateMessageText("Hello")
        assertEquals("Hello", viewModel.uiState.value.messageText)

        viewModel.updateMessageText("How are you?")
        assertEquals("How are you?", viewModel.uiState.value.messageText)

        viewModel.updateMessageText("Goodbye")
        assertEquals("Goodbye", viewModel.uiState.value.messageText)
    }

    @Test
    fun observeMessages_updatesMessages() = runTest {
        val repository = FakeChatRepository()
        val viewModel = ChatViewModel(repository)

        advanceUntilIdle()

        val messages = listOf(
            ChatMessage(
                id = "1",
                userId = "user1",
                userName = "Elo",
                message = "Hello",
                timestamp = 100L
            ),
            ChatMessage(
                id = "2",
                userId = "user2",
                userName = "Test",
                message = "Hi",
                timestamp = 200L
            )
        )

        repository.emitMessages(messages)

        advanceUntilIdle()

        assertEquals(
            messages,
            viewModel.uiState.value.messages
        )
    }

    @Test
    fun observeMessages_stopsLoadingAfterMessagesArrive() = runTest {
        val repository = FakeChatRepository()
        val viewModel = ChatViewModel(repository)

        advanceUntilIdle()

        repository.emitMessages(
            listOf(
                ChatMessage(
                    id = "1",
                    userId = "user1",
                    userName = "Elo",
                    message = "Hello",
                    timestamp = 100L
                )
            )
        )

        advanceUntilIdle()

        assertFalse(
            viewModel.uiState.value.isLoading
        )
    }

    @Test
    fun observeMessages_canReceiveEmptyList() = runTest {
        val repository = FakeChatRepository()
        val viewModel = ChatViewModel(repository)

        advanceUntilIdle()

        repository.emitMessages(emptyList())

        advanceUntilIdle()

        assertTrue(
            viewModel.uiState.value.messages.isEmpty()
        )

        assertFalse(
            viewModel.uiState.value.isLoading
        )
    }

    @Test
    fun sendMessage_emptyMessage_doesNotCallRepository() = runTest {
        val repository = FakeChatRepository()
        val viewModel = ChatViewModel(repository)

        advanceUntilIdle()

        viewModel.sendMessage(
            userId = "user1",
            userName = "Elo"
        )

        advanceUntilIdle()

        assertEquals(
            0,
            repository.sendMessageCallCount
        )
    }

    @Test
    fun sendMessage_spacesOnly_doesNotCallRepository() = runTest {
        val repository = FakeChatRepository()
        val viewModel = ChatViewModel(repository)

        advanceUntilIdle()

        viewModel.updateMessageText("     ")

        viewModel.sendMessage(
            userId = "user1",
            userName = "Elo"
        )

        advanceUntilIdle()

        assertEquals(
            0,
            repository.sendMessageCallCount
        )
    }

    @Test
    fun sendMessage_callsRepository() = runTest {
        val repository = FakeChatRepository()
        val viewModel = ChatViewModel(repository)

        advanceUntilIdle()

        viewModel.updateMessageText("Hello")

        viewModel.sendMessage(
            userId = "user1",
            userName = "Elo"
        )

        advanceUntilIdle()

        assertEquals(
            1,
            repository.sendMessageCallCount
        )
    }

    @Test
    fun sendMessage_passesCorrectUserId() = runTest {
        val repository = FakeChatRepository()
        val viewModel = ChatViewModel(repository)

        advanceUntilIdle()

        viewModel.updateMessageText("Hello")

        viewModel.sendMessage(
            userId = "user123",
            userName = "Elo"
        )

        advanceUntilIdle()

        assertEquals(
            "user123",
            repository.lastUserId
        )
    }

    @Test
    fun sendMessage_passesCorrectUserName() = runTest {
        val repository = FakeChatRepository()
        val viewModel = ChatViewModel(repository)

        advanceUntilIdle()

        viewModel.updateMessageText("Hello")

        viewModel.sendMessage(
            userId = "user123",
            userName = "Elo"
        )

        advanceUntilIdle()

        assertEquals(
            "Elo",
            repository.lastUserName
        )
    }

    @Test
    fun sendMessage_passesMessageToRepository() = runTest {
        val repository = FakeChatRepository()
        val viewModel = ChatViewModel(repository)

        advanceUntilIdle()

        viewModel.updateMessageText("Hello world")

        viewModel.sendMessage(
            userId = "user123",
            userName = "Elo"
        )

        advanceUntilIdle()

        assertEquals(
            "Hello world",
            repository.lastMessage
        )
    }

    @Test
    fun sendMessage_trimsMessageBeforeSending() = runTest {
        val repository = FakeChatRepository()
        val viewModel = ChatViewModel(repository)

        advanceUntilIdle()

        viewModel.updateMessageText("   Hello world   ")

        viewModel.sendMessage(
            userId = "user123",
            userName = "Elo"
        )

        advanceUntilIdle()

        assertEquals(
            "Hello world",
            repository.lastMessage
        )
    }

    @Test
    fun sendMessage_clearsMessageTextAfterSending() = runTest {
        val repository = FakeChatRepository()
        val viewModel = ChatViewModel(repository)

        advanceUntilIdle()

        viewModel.updateMessageText("Hello")

        viewModel.sendMessage(
            userId = "user123",
            userName = "Elo"
        )

        advanceUntilIdle()

        assertEquals(
            "",
            viewModel.uiState.value.messageText
        )
    }

    @Test
    fun sendMessage_multipleMessages_callsRepositoryMultipleTimes() = runTest {
        val repository = FakeChatRepository()
        val viewModel = ChatViewModel(repository)

        advanceUntilIdle()

        viewModel.updateMessageText("First")

        viewModel.sendMessage(
            userId = "user1",
            userName = "Elo"
        )

        viewModel.updateMessageText("Second")

        viewModel.sendMessage(
            userId = "user1",
            userName = "Elo"
        )

        advanceUntilIdle()

        assertEquals(
            2,
            repository.sendMessageCallCount
        )

        assertEquals(
            "Second",
            repository.lastMessage
        )
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
private class FakeChatRepository : ChatRepositoryContract {

    private val messagesFlow =
        MutableStateFlow<List<ChatMessage>>(emptyList())

    var sendMessageCallCount = 0

    var lastUserId: String? = null
    var lastUserName: String? = null
    var lastMessage: String? = null

    override fun getMessages(): Flow<List<ChatMessage>> {
        return messagesFlow
    }

    override fun sendMessage(
        userId: String,
        userName: String,
        message: String
    ) {
        sendMessageCallCount++

        lastUserId = userId
        lastUserName = userName
        lastMessage = message
    }

    fun emitMessages(messages: List<ChatMessage>) {
        messagesFlow.value = messages
    }
}