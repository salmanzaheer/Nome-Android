package com.example.nome.ui

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.media.AudioManager
import android.media.ToneGenerator
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.nome.R
import java.util.*
import java.util.logging.Handler
import kotlin.concurrent.timerTask
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext
import androidx.compose.material.icons.Icons as Icons

private const val MIN_BPM = 39
private const val MAX_BPM = 221

@Composable
fun Body(){
    val ctx = LocalContext.current

    val playPause: MutableState<String> = remember {
        mutableStateOf("Play")
    }
    


    var MetronomeState = false
    var metronome: Timer = Timer("metronome", true)


    val rotationalState by animateFloatAsState(targetValue = if (MetronomeState) 0f else 270f)
    


    //gets local context bc mediaplayer has two parameters which is context and audio
    var sliderPosition by remember {
        mutableStateOf(60f)
    }
    val bpm: MutableState<Int> = remember {
        mutableStateOf(60)
    }
    var delay = (60.0 / bpm.value * 1000).toLong()
    var mainHandler: Handler

    


    //CHANGE BPM TO WORK WITH SLIDER SET TO 60 RN
    fun calculateSleepDuration(bpm: Long): Long {
        return (1000 * (60 / bpm.toDouble())).toLong()
    }
    fun startMetronome(bpm: Long){
        val metronomeTone = ToneGenerator.TONE_PROP_BEEP
        if(!MetronomeState){
            return
        }
        MetronomeState = true


        metronome.schedule(
            timerTask {
                val toneGenerator = ToneGenerator(AudioManager.STREAM_MUSIC, 100)
                toneGenerator.startTone(metronomeTone)
                toneGenerator.release()
            },
            0L,
            calculateSleepDuration(bpm)
        )

        //playPause.value = "Pause"
    }

    fun newBpm(){
        if(!MetronomeState){
            metronome = Timer("metronome", true)
        }
    }

    fun stopMetronome(){

        MetronomeState = false
        metronome.cancel()
        newBpm()

        //playPause.value = "Play"
    }



    //container
    Column (
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        // Active Indicator
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
           /* Image(
              //  bitmap = powerIndicator!!.asImageBitmap(),
                contentDescription = "",
                modifier = Modifier.size(8.dp)
            )*/

        }

        // 4 objects to show beat
        Row () {

        }

        //slider
        Slider(
            value = sliderPosition,
            valueRange = 40f..220f,
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
                if (bpm.value >= MIN_BPM) {
                    bpm.value -= 1
                    sliderPosition = bpm.value.toFloat()
                }
            },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xffE74E35))
            ) {
                Text(text = "-1")
            }

            Spacer(modifier = Modifier.width(180.dp))

            Button(onClick = {
                if (bpm.value <= MAX_BPM) {
                    bpm.value += 1
                    sliderPosition = bpm.value.toFloat()
                }
            },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xffE74E35))
            ) {
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
                if (bpm.value >= MIN_BPM) {
                    bpm.value -= 10
                    sliderPosition = bpm.value.toFloat()
                }
            },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xffE74E35))
            ) {
                Text(text = "-10")
            }

            Spacer(modifier = Modifier.width(180.dp))

            Button(onClick = {
                if (bpm.value <= MAX_BPM) {
                    bpm.value += 10
                    sliderPosition = bpm.value.toFloat()
                }
            },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xffE74E35))
            ) {
                Text(text = "+10")
            }
        }

        //Need a handler to run metronome in seperate thread

        // FAB for play/stop
        //onclick play media works.
        FloatingActionButton(onClick = {

        },
            backgroundColor = Color(0xffE74E35)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically){
                IconButton(
                    onClick = {
                        if(!MetronomeState)
                        {
                            MetronomeState = true
                            startMetronome(bpm.value.toLong())
                        }
                        else{
                            stopMetronome()
                        }
                              },
                    modifier = Modifier.rotate(rotationalState)
                ) {
                    Icon(Icons.Outlined.PlayArrow, contentDescription = "Start/Stop")

                }
            }
        }

        Text(
            text = playPause.value,
            modifier = Modifier
                .size(68.dp)
                .padding(top = 20.dp, bottom = 20.dp, start = 10.dp),
        )

    }
}
