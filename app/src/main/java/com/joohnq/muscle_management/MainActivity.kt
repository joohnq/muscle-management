package com.joohnq.muscle_management

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.joohnq.muscle_management.ui.MuscleManagement
import com.joohnq.muscle_management.ui.theme.MuscleManagementTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MuscleManagementTheme {
               MuscleManagement()
            }
        }
    }
}