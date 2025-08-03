import com.joohnq.muscle_management.domain.repository.TrainingRepository
import com.joohnq.muscle_management.domain.use_case.training.DeleteTrainingUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@OptIn(ExperimentalCoroutinesApi::class)
class DeleteTrainingUseCaseTest {

    private lateinit var repository: TrainingRepository
    private lateinit var useCase: DeleteTrainingUseCase

    @Before
    fun setUp() {
        repository = mockk(relaxed = true)
        useCase = DeleteTrainingUseCase(repository)
    }

    @Test
    fun `should delete training successfully`() = runTest {
        coEvery { repository.delete("trainingId") } returns Unit

        val result = useCase("trainingId")

        assertTrue(result.isSuccess)
        coVerify(exactly = 1) { repository.delete("trainingId") }
    }

    @Test
    fun `should return failure when repository throws exception`() = runTest {
        val exception = RuntimeException("Delete failed")
        coEvery { repository.delete("trainingId") } throws exception

        val result = useCase("trainingId")

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}