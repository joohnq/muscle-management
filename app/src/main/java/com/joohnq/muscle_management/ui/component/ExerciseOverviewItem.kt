package com.joohnq.muscle_management.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.joohnq.muscle_management.domain.entity.Exercise

@Composable
fun ExerciseOverviewItem(
    exercise: Exercise,
    modifier: Modifier = Modifier,
) {
    var isImageLoaded by remember { mutableStateOf(false) }
    var isErrorExerciseImage by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (exercise.image.isNotEmpty() && !isErrorExerciseImage) {
            AsyncImage(
                model = exercise.image,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                contentScale = ContentScale.Crop,
                onSuccess = {
                    isImageLoaded = true
                },
                onError = {
                    isErrorExerciseImage = true
                }
            )

            if (!isImageLoaded) {
                ExerciseImagePlaceholder(
                    text = exercise.name.take(1).uppercase(),
                    modifier = Modifier.size(64.dp)
                )
            }
        } else {
            ExerciseImagePlaceholder(
                text = exercise.name.take(1).uppercase(),
                modifier = Modifier.size(64.dp)
            )
        }

        Column {
            Text(
                text = exercise.name,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            if (exercise.observations.isNotBlank()) {
                Text(
                    text = exercise.observations,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}