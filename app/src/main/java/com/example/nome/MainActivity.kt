package com.example.nome

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.provider.MediaStore.Audio.Media
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.nome.ui.theme.NomeTheme
import kotlin.concurrent.thread


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NomeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    //gets local context bc mediaplayer has two parameters which is context and audio
    val mContext = LocalContext.current
    val mMediaPlayer = MediaPlayer.create(mContext, R.raw.metornome)
    val playingSound: MutableState<Boolean> = remember { mutableStateOf(false)}
    var sliderPosition by remember {
        mutableStateOf(60f)
    }
    val bpm: MutableState<Int> = remember {
        mutableStateOf(60)
    }
    //container
    Column (
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
            ){
        // Active Indicator

        // 4 objects to show beat
        Row () {

        }

        //slider
        Slider(
            value = sliderPosition,
            valueRange = 20f..255f,
            onValueChange = {
                sliderPosition = it
                bpm.value = sliderPosition.toInt()
            }
        )

        // buttons +-1 bpm
        Row(
            modifier = Modifier.padding(10.dp)
        ) {
            Button(onClick = {
                bpm.value -= 1
                sliderPosition = bpm.value.toFloat()
            }) {
                Text(text = "-1")
            }

            Spacer(modifier = Modifier.width(180.dp))
            
            Button(onClick = {
                bpm.value += 1
                sliderPosition = bpm.value.toFloat()
            }) {
                Text(text = "+1")
            }
        }

        // display BPM
        Text(
            text = bpm.value.toString(),
            fontSize = 36.sp,
        )

        // buttons +-10 bpm
        Row(
            modifier = Modifier.padding(10.dp)
        ) {
            Button(onClick = {
                bpm.value -= 10
                sliderPosition = bpm.value.toFloat()
            }) {
                Text(text = "-10")
            }

            Spacer(modifier = Modifier.width(180.dp))

            Button(onClick = {
                bpm.value += 10
                sliderPosition = bpm.value.toFloat()
            }) {
                Text(text = "+10")
            }
        }

        // FAB for play/stop
        //onclick play media works.
        FloatingActionButton(onClick = {
            mMediaPlayer.start()
            if(playingSound.value == true){
                playingSound.value = false
            }
            if(playingSound.value == false){
                playingSound.value = true
            }
        }) {
            while (playingSound.value == true){
                playSound(mContext, 60)
            }
            Icon(Icons.Filled.PlayArrow, contentDescription = "Start/Stop")

        }
    }
}

fun playSound(ctx:Context, bpm: Int){
    val beats = 10
    val mMediaPlayer = MediaPlayer.create(ctx, R.raw.metornome)
    val delay = (60.0 / bpm * 1000).toLong()
    var count = 0
    while(count < beats) {
        mMediaPlayer.start()
        Thread.sleep(delay)
        count++
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NomeTheme {
        MainScreen()
    }
}