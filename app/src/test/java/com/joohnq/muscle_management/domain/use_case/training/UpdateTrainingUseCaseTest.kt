import com.joohnq.muscle_management.domain.entity.Exercise
import com.joohnq.muscle_management.domain.entity.Training
import com.joohnq.muscle_management.domain.exception.ValidationException
import com.joohnq.muscle_management.domain.repository.TrainingRepository
import com.joohnq.muscle_management.domain.use_case.training.UpdateTrainingUseCase
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
class UpdateTrainingUseCaseTest {

    private lateinit var repository: TrainingRepository
    private lateinit var useCase: UpdateTrainingUseCase

    private val trainingValidator = TrainingValidator
    private val exerciseValidator = ExerciseValidator

    private val validTraining = Training(id = "t1", name = "Leg Day")
    private val validExercise = Exercise(id = "e1", name = "Squat", trainingId = "t1", image = "https://valid.url/image.png")

    @Before
    fun setup() {
        repository = mockk(relaxed = true)
        useCase = UpdateTrainingUseCase(repository, trainingValidator, exerciseValidator)
    }

    @Test
    fun `should update training successfully with valid training and exercise`() = runTest {
        coEvery { repository.update(validTraining, listOf(validExercise)) } returns Unit

        val result = useCase(validTraining, listOf(validExercise))

        assertTrue(result.isSuccess)
        coVerify(exactly = 1) { repository.update(validTraining, listOf(validExercise)) }
    }

    @Test
    fun `should fail when training name is empty`() = runTest {
        val invalidTraining = validTraining.copy(name = "")

        val result = useCase(invalidTraining, listOf(validExercise))

        assertTrue(result.isFailure)
        val exception = result.exceptionOrNull()
        assertTrue(exception is ValidationException)
        val errors = (exception as ValidationException).errors
        assertTrue(errors.any { it is com.joohnq.muscle_management.domain.exception.TrainingException.EmptyTrainingName })
    }

    @Test
    fun `should fail when exercise name is empty`() = runTest {
        val invalidExercise = validExercise.copy(name = "")

        val result = useCase(validTraining, listOf(invalidExercise))

        assertTrue(result.isFailure)
        val exception = result.exceptionOrNull()
        assertTrue(exception is ValidationException)
        val errors = (exception as ValidationException).errors
        assertTrue(errors.any { it is com.joohnq.muscle_management.domain.exception.TrainingException.InvalidExerciseName })
    }

    @Test
    fun `should fail when exercise image url is invalid`() = runTest {
        val invalidExercise = validExercise.copy(image = "invalid_url")

        val result = useCase(validTraining, listOf(invalidExercise))

        assertTrue(result.isFailure)
        val exception = result.exceptionOrNull()
        assertTrue(exception is ValidationException)
        val errors = (exception as ValidationException).errors
        assertTrue(errors.any { it is com.joohnq.muscle_management.domain.exception.TrainingException.InvalidExerciseImage })
    }

    @Test
    fun `should fail when repository update throws exception`() = runTest {
        val runtimeException = RuntimeException("Update error")
        coEvery { repository.update(validTraining, listOf(validExercise)) } throws runtimeException

        val result = useCase(validTraining, listOf(validExercise))

        assertTrue(result.isFailure)
        assertEquals(runtimeException, result.exceptionOrNull())
    }
}