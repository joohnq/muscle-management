package com.joohnq.muscle_management.domain.use_case.auth

import com.joohnq.muscle_management.domain.repository.AuthRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetUserIdUseCaseTest {

    private lateinit var repository: AuthRepository
    private lateinit var useCase: GetUserIdUseCase

    @Before
    fun setUp() {
        repository = mockk()
        useCase = GetUserIdUseCase(repository)
    }

    @Test
    fun `should return user id when repository returns id`() = runTest {
        coEvery { repository.getUserId() } returns "user123"

        val result = useCase()

        assertTrue(result.isSuccess)
        assertEquals("user123", result.getOrNull())
    }

    @Test
    fun `should return null when repository returns null`() = runTest {
        coEvery { repository.getUserId() } returns null

        val result = useCase()

        assertTrue(result.isSuccess)
        assertNull(result.getOrNull())
    }

    @Test
    fun `should return failure when repository throws exception`() = runTest {
        val exception = RuntimeException("Something went wrong")
        coEvery { repository.getUserId() } throws exception

        val result = useCase()

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}