import com.joohnq.muscle_management.domain.exception.AuthException
import com.joohnq.muscle_management.domain.exception.ValidationException
import com.joohnq.muscle_management.domain.repository.AuthRepository
import com.joohnq.muscle_management.domain.use_case.auth.SignInUseCase
import com.joohnq.muscle_management.domain.validator.EmailValidator
import com.joohnq.muscle_management.domain.validator.PasswordValidator
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class SignInUseCaseTest {

    private lateinit var repository: AuthRepository
    private lateinit var useCase: SignInUseCase

    @Before
    fun setUp() {
        repository = mockk(relaxed = true)
        useCase = SignInUseCase(
            repository = repository,
            emailValidator = EmailValidator,
            passwordValidator = PasswordValidator
        )
    }

    @Test
    fun `should return success when email and password are valid`() = runTest {
        coEvery { repository.signIn("user@example.com", "123456") } returns Unit

        val result = useCase("user@example.com", "123456")

        assertTrue(result.isSuccess)
        coVerify(exactly = 1) { repository.signIn("user@example.com", "123456") }
    }

    @Test
    fun `should fail when email is empty`() = runTest {
        val result = useCase("", "123456")

        assertTrue(result.isFailure)
        val exception = result.exceptionOrNull()
        assertTrue(exception is ValidationException)
        assertTrue((exception as ValidationException).errors.any { it is AuthException.EmptyEmailException })
    }

    @Test
    fun `should fail when email is invalid`() = runTest {
        val result = useCase("invalid-email", "123456")

        assertTrue(result.isFailure)
        val exception = result.exceptionOrNull()
        assertTrue(exception is ValidationException)
        assertTrue((exception as ValidationException).errors.any { it is AuthException.InvalidEmailException })
    }

    @Test
    fun `should fail when password is empty`() = runTest {
        val result = useCase("user@example.com", "")

        assertTrue(result.isFailure)
        val exception = result.exceptionOrNull()
        assertTrue(exception is ValidationException)
        assertTrue((exception as ValidationException).errors.any { it is AuthException.EmptyPasswordException })
    }

    @Test
    fun `should fail when repository throws exception`() = runTest {
        val runtimeException = RuntimeException("Network error")
        coEvery { repository.signIn(any(), any()) } throws runtimeException

        val result = useCase("user@example.com", "123456")

        assertTrue(result.isFailure)
        assertEquals(AuthException.GenericAuthException().message, result.exceptionOrNull()?.message)
    }
}