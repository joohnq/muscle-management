import com.joohnq.muscle_management.domain.entity.Exercise
import com.joohnq.muscle_management.domain.entity.Training
import com.joohnq.muscle_management.domain.repository.TrainingRepository
import com.joohnq.muscle_management.domain.use_case.training.GetAllTrainingUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetAllTrainingUseCaseTest {

    private lateinit var repository: TrainingRepository
    private lateinit var useCase: GetAllTrainingUseCase

    private val mockTraining = Training(id = "t1", name = "Chest Day")
    private val mockExercise = Exercise(id = "e1", name = "Bench Press", trainingId = "t1")

    @Before
    fun setUp() {
        repository = mockk(relaxed = true)
        useCase = GetAllTrainingUseCase(repository)
    }

    @Test
    fun `should return list of trainings with exercises successfully`() = runTest {
        coEvery { repository.getAll() } returns listOf(mockTraining to listOf(mockExercise))

        val result = useCase()

        assertTrue(result.isSuccess)
        val data = result.getOrNull()
        assertNotNull(data)
        assertEquals(1, data!!.size)
        assertEquals("Chest Day", data[0].first.name)
        assertEquals("Bench Press", data[0].second[0].name)

        coVerify(exactly = 1) { repository.getAll() }
    }

    @Test
    fun `should return failure when repository throws exception`() = runTest {
        val exception = RuntimeException("DB error")
        coEvery { repository.getAll() } throws exception

        val result = useCase()

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}