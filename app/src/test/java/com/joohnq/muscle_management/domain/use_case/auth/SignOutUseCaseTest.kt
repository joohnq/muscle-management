package com.joohnq.muscle_management.domain.use_case.auth

import com.joohnq.muscle_management.domain.repository.AuthRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SignOutUseCaseTest {

    private lateinit var repository: AuthRepository
    private lateinit var useCase: SignOutUseCase

    @Before
    fun setUp() {
        repository = mockk()
        useCase = SignOutUseCase(repository)
    }

    @Test
    fun `should return success when signOut completes without exception`() = runTest {
        coEvery { repository.signOut() } returns Unit

        val result = useCase()

        assertTrue(result.isSuccess)
        assertNull(result.exceptionOrNull())
        coVerify(exactly = 1) { repository.signOut() }
    }

    @Test
    fun `should return failure when signOut throws exception`() = runTest {
        val exception = RuntimeException("Network error")
        coEvery { repository.signOut() } throws exception

        val result = useCase()

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
        coVerify(exactly = 1) { repository.signOut() }
    }
}