package com.mr.simple_soundboard

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mr.simple_soundboard.ui.theme.Simple_SoundboardTheme

// TODO: release media player
// TODO: allow adding additional buttons using sounds from user storage.
// TODO: UI should automatically shift layout to accommodate new buttons.
// TODO: Allow user to drag buttons to new positions on a grid.
// TODO: Save new positions to file, and load on next start.

class MainActivity : ComponentActivity() {
    // media player object that will start on prepared, and that will reset on completion
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
                    // pass sounds here as Uri
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
    // Creates a column of buttons that will each play different sound files
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
            SfxButton(sound = sound1, soundName = stringResource(R.string.wow))
            SfxButton(sound = sound2, soundName = stringResource(R.string.dice))
            SfxButton(sound = sound3, soundName = stringResource(R.string.magnetic))
        }
    }

    @Composable
    // Creates a button that accepts sound Uri and name String.
    // When the button is pressed, mediaplayer object is reset to ensure proper performance,
    // and then given the new data source (Uri). The sound is then played once prepare is completed.
    fun SfxButton(sound: Int, soundName: String) {
        Button(onClick = {
            mediaPlayer.run {
                reset()
                setDataSource(resources.openRawResourceFd(sound))
                prepareAsync()
            }
        }) {
            Text(text = soundName, fontSize = 48.sp)
        }
    }
}