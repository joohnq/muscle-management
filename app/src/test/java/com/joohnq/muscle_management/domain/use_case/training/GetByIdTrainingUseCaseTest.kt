import com.joohnq.muscle_management.domain.entity.Exercise
import com.joohnq.muscle_management.domain.entity.Training
import com.joohnq.muscle_management.domain.repository.TrainingRepository
import com.joohnq.muscle_management.domain.use_case.training.GetByIdTrainingUseCase
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
@RunWith(RobolectricTestRunner::class)
class GetByIdTrainingUseCaseTest {

    private lateinit var repository: TrainingRepository
    private lateinit var useCase: GetByIdTrainingUseCase

    private val trainingId = "t1"
    private val mockTraining = Training(id = trainingId, name = "Chest Day")
    private val mockExercise = Exercise(id = "e1", name = "Bench Press", trainingId = trainingId)

    @Before
    fun setUp() {
        repository = mockk(relaxed = true)
        useCase = GetByIdTrainingUseCase(repository)
    }

    @Test
    fun `should return training with exercises successfully`() = runTest {
        coEvery { repository.getById(trainingId) } returns (mockTraining to listOf(mockExercise))

        val result = useCase(trainingId)

        assertTrue(result.isSuccess)
        val data = result.getOrNull()
        assertNotNull(data)
        assertEquals("Chest Day", data!!.first.name)
        assertEquals("Bench Press", data.second[0].name)

        coVerify(exactly = 1) { repository.getById(trainingId) }
    }

    @Test
    fun `should return failure when training not found`() = runTest {
        coEvery { repository.getById(trainingId) } returns null

        val result = useCase(trainingId)

        assertTrue(result.isFailure)
        assertEquals("Training not found", result.exceptionOrNull()?.message)
    }

    @Test
    fun `should return failure when repository throws exception`() = runTest {
        val exception = RuntimeException("DB error")
        coEvery { repository.getById(trainingId) } throws exception

        val result = useCase(trainingId)

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}