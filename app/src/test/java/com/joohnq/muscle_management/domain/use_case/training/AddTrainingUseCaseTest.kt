import com.joohnq.muscle_management.domain.entity.Exercise
import com.joohnq.muscle_management.domain.entity.Training
import com.joohnq.muscle_management.domain.exception.TrainingException
import com.joohnq.muscle_management.domain.exception.ValidationException
import com.joohnq.muscle_management.domain.repository.TrainingRepository
import com.joohnq.muscle_management.domain.use_case.training.AddTrainingUseCase
import com.joohnq.muscle_management.domain.validator.ExerciseValidator
import com.joohnq.muscle_management.domain.validator.TrainingValidator
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
class AddTrainingUseCaseTest {

    private lateinit var repository: TrainingRepository
    private lateinit var useCase: AddTrainingUseCase

    private val validTraining = Training(id = "t1", name = "Chest Day")
    private val validExercise = Exercise(
        id = "e1",
        name = "Bench Press",
        trainingId = "t1",
        image = "https://example.com/image.jpg"
    )

    @Before
    fun setUp() {
        repository = mockk()
        useCase = AddTrainingUseCase(repository, TrainingValidator, ExerciseValidator)
    }

    @Test
    fun `should add training successfully when valid`() = runTest {
        coEvery { repository.add(any(), any()) } returns Unit

        val result = useCase(validTraining, listOf(validExercise))

        assertTrue(result.isSuccess)
        coVerify(exactly = 1) { repository.add(validTraining, listOf(validExercise)) }
    }

    @Test
    fun `should fail when training name is empty`() = runTest {
        val invalidTraining = validTraining.copy(name = "")

        val result = useCase(invalidTraining, listOf(validExercise))

        assertTrue(result.isFailure)
        val ex = result.exceptionOrNull()
        assertTrue(ex is ValidationException)
        assertTrue((ex as ValidationException).errors.first() is TrainingException.EmptyTrainingName)
        coVerify(exactly = 0) { repository.add(any(), any()) }
    }

    @Test
    fun `should fail when exercise name is empty`() = runTest {
        val invalidExercise = validExercise.copy(name = "")

        val result = useCase(validTraining, listOf(invalidExercise))

        assertTrue(result.isFailure)
        val ex = result.exceptionOrNull()
        assertTrue(ex is ValidationException)
        assertTrue((ex as ValidationException).errors.first() is TrainingException.InvalidExerciseName)
        coVerify(exactly = 0) { repository.add(any(), any()) }
    }

    @Test
    fun `should fail when exercise image is invalid`() = runTest {
        val invalidExercise = validExercise.copy(image = "invalid_url")

        val result = useCase(validTraining, listOf(invalidExercise))

        assertTrue(result.isFailure)
        val ex = result.exceptionOrNull()
        assertTrue(ex is ValidationException)
        assertTrue((ex as ValidationException).errors.first() is TrainingException.InvalidExerciseImage)
        coVerify(exactly = 0) { repository.add(any(), any()) }
    }

    @Test
    fun `should propagate repository exception`() = runTest {
        coEvery { repository.add(any(), any()) } throws RuntimeException("DB error")

        val result = useCase(validTraining, listOf(validExercise))

        assertTrue(result.isFailure)
        assertEquals("DB error", result.exceptionOrNull()?.message)
    }
}