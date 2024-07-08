package com.mr.simple_soundboard

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mr.simple_soundboard.ui.theme.Simple_SoundboardTheme

// TODO: release media player

class MainActivity : ComponentActivity() {
    private val mediaPlayer = MediaPlayer().apply {
        setOnPreparedListener { start() }
        setOnCompletionListener { reset() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Simple_SoundboardTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    SfxButtons(
                        R.raw.wow,
                        R.raw.dice_roll,
                        R.raw.glitch03
                    )
                }
            }
        }
    }

    @Composable
    fun SfxButtons(
        sound1: Int,
        sound2: Int,
        sound3: Int,
        modifier: Modifier = Modifier
    ) {
        Column(
            modifier = modifier
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SfxButton(sound = sound1, soundName = "Sound1")
            SfxButton(sound = sound2, soundName = "Sound2")
            SfxButton(sound = sound3, soundName = "Sound3")
        }
    }

    @Composable
    fun SfxButton(sound: Int, soundName: String) {
        Button(onClick = {
            mediaPlayer.run {
                reset()
                setDataSource(resources.openRawResourceFd(sound))
                prepareAsync()
            }
        }) {
            Text(text = soundName, fontSize = 24.sp)
        }
    }
}