package com.example.nome

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Handler
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nome.ui.theme.NomeTheme
import java.util.Timer
import kotlin.concurrent.timerTask

var MetronomeState = false
var metronome: Timer = Timer("metronome", true)

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
    var sliderPosition by remember {
        mutableStateOf(60f)
    }
    val bpm: MutableState<Int> = remember {
        mutableStateOf(60)
    }
    var delay = (60.0 / bpm.value * 1000).toLong()
    var mainHandler: Handler

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
            colors = SliderDefaults.colors(
                thumbColor = Color(0xffE74E35),
                activeTrackColor = Color(0xFFE2634F),
                inactiveTrackColor = Color(0xFFD3ACA5)
            ),

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

        //Need a handler to run metronome in seperate thread

        // FAB for play/stop
        //onclick play media works.
        FloatingActionButton(onClick = {
            if(!MetronomeState)
            {
                MetronomeState = true

                startMetronome(bpm.value.toLong())
            }
            else{
                stopMetronome()
            }
        }
        )
        {
            Icon(Icons.Filled.PlayArrow, contentDescription = "Start/Stop")
        }
    }
}

/*fun playSound(ctx:Context, bpm: Int){
    val beats = 10
    val mMediaPlayer = MediaPlayer.create(ctx, R.raw.metornome)
    val delay = (60.0 / bpm * 1000).toLong()
    var count = 0
    while(count < beats) {
        mMediaPlayer.start()
        Thread.sleep(delay)
        count++
    }
}*/
//CHANGE BPM TO WORK WITH SLIDER SET TO 60 RN
fun calculateSleepDuration(bpm: Long): Long {
    return (1000 * (60 / bpm.toDouble())).toLong()
}
fun startMetronome(bpm: Long){
    val MetronomeTone = ToneGenerator.TONE_PROP_BEEP
    if(!MetronomeState){
        return
    }
    MetronomeState = true

    /*timerTask {
        val toneGenerator = ToneGenerator(AudioManager.STREAM_MUSIC, 100)
        toneGenerator.startTone(MetronomeTone)
        toneGenerator.release()
    }*/

    metronome.schedule(
        timerTask {
            val toneGenerator = ToneGenerator(AudioManager.STREAM_MUSIC, 100)
            toneGenerator.startTone(MetronomeTone)
            toneGenerator.release()
        },
        0L,
        calculateSleepDuration(bpm)
    )
}

fun stopMetronome(){
    MetronomeState = false
    metronome.cancel()
    newBpm()
}

fun newBpm(){
    if(!MetronomeState){
        metronome = Timer("metronome", true)
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NomeTheme {
        MainScreen()
    }
}