package com.example.nome.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
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
import android.os.Build
import android.os.Handler
import android.provider.Settings.Global.getString
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.Close
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat
import com.example.nome.R
import com.example.nome.ui.dialogue.OnlinePresetDialog
import com.example.nome.ui.notification.Notification
import com.example.nome.ui.theme.NomeTheme
import com.example.nome.ui.theme.globalStateDataClass
import kotlinx.coroutines.delay
import java.util.Timer
import kotlin.concurrent.timerTask

var MetronomeState = false
var metronome: Timer = Timer("metronome", true)
var lastBpm: Int = 0

@Composable
fun Body(globalStates: globalStateDataClass) {
    val service = Notification(context = LocalContext.current)
    //gets local context bc mediaplayer has two parameters which is context and audio
    var sliderPosition by remember {
        mutableStateOf(globalStates.Slider) //calls by globalStates object values
    }
    val bpm: MutableState<Int> = remember {
        mutableStateOf(globalStates.Bpm) //calls by globalStates object values
    }

    var isPlaying by remember {
         mutableStateOf(globalStates.State) //calls by globalStates object values
    }

    // Added a lot of update lines so globalStates will keep up and wont reset to default when going to new other screen.

    var delay = (60.0 / bpm.value * 1000).toLong()
    var mainHandler: Handler


    //container
    Column (
        modifier = Modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ){
        // Active Indicator
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .padding(5.dp)
                    .size(25.dp)
                    .clip(CircleShape)
                    .background(if (!isPlaying) Color.Gray else Color.Red)
            ) {
            }
        }

        Spacer(modifier = Modifier.height(6.dp))


        // display BPM
        Text(
            text = globalStates.Bpm.toString(),
            fontSize = 80.sp,
            style = MaterialTheme.typography.h1
        )

        Spacer(modifier = Modifier.height(25.dp))


        //slider
        //Fix slider to work with globalStateDataClass Observer
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
                globalStates.Bpm = bpm.value
                globalStates.Slider = sliderPosition
                if(MetronomeState){
                    stopMetronome()
                    Handler().postDelayed({startMetronome(bpm.value.toLong())}, 100)
                }
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        // buttons +-1 bpm
        Row(
            modifier = Modifier.padding(10.dp)
        ) {
            Button(
                modifier = Modifier.size(80.dp),
                onClick = {
                    if(bpm.value > 20){
                        bpm.value -= 1
                        globalStates.Bpm = bpm.value
                        sliderPosition = bpm.value.toFloat()
                        globalStates.Slider = sliderPosition
                        if(MetronomeState){
                            stopMetronome()
                            globalStates.State = MetronomeState
                            Handler().postDelayed({startMetronome(bpm.value.toLong())}, 100)
                        }
                    }
                },
                shape = CircleShape
            ) {
                Text(text = "-1", fontSize = 20.sp)
            }

            Spacer(modifier = Modifier.width(180.dp))

            Button(
                modifier = Modifier.size(80.dp),
                onClick = {
                    if(bpm.value < 254){
                        bpm.value += 1
                        globalStates.Bpm = bpm.value
                        sliderPosition = bpm.value.toFloat()
                        globalStates.Slider = sliderPosition
                        if(MetronomeState){
                            stopMetronome()
                            globalStates.State = MetronomeState
                            Handler().postDelayed({startMetronome(bpm.value.toLong())}, 100)
                        }
                    }
                },
                shape = CircleShape,
            ) {
                Text(
                    text = "+1",
                    fontSize = 20.sp
                )
            }
        }


        // FAB for play/stop
        //onclick play media works.
        FloatingActionButton(
            modifier = Modifier.size(100.dp),
            onClick = {
                isPlaying = !isPlaying
                globalStates.State = isPlaying
                if(!MetronomeState)
                {
                    MetronomeState = true
                    globalStates.State = MetronomeState
                    lastBpm = bpm.value
                    startMetronome(bpm.value.toLong())
                    globalStates.State = MetronomeState
                    isPlaying = globalStates.State
                    service.showNotification()
                }
                else{
                    stopMetronome()
                    globalStates.State = MetronomeState
                }
            },
            backgroundColor = if (!isPlaying) Color.Green else Color.Red
        )
        {
            Icon(
                if (!isPlaying) Icons.Filled.PlayArrow else Icons.Filled.Close,
                contentDescription = if (!isPlaying) "Pause" else "Play",
                tint = Color.White,
                modifier = Modifier.size(50.dp)
            )
        }


        // buttons +-10 bpm
        Row(
            modifier = Modifier.padding(10.dp)
        ) {
            Button(
                modifier = Modifier.size(80.dp),
                onClick = {
                    if(bpm.value > 31){
                        bpm.value -= 10
                        globalStates.Bpm = bpm.value
                        sliderPosition = bpm.value.toFloat()
                        globalStates.Slider = sliderPosition
                        if(MetronomeState){
                            stopMetronome()
                            globalStates.State = MetronomeState
                            Handler().postDelayed({startMetronome(bpm.value.toLong())}, 100)
                        }
                    } else if(bpm.value < 31 && bpm.value != 20) {
                        bpm.value = 20
                        globalStates.Bpm = bpm.value
                        sliderPosition = bpm.value.toFloat()
                        globalStates.Slider = sliderPosition
                        if(MetronomeState){
                            stopMetronome()
                            globalStates.State = MetronomeState
                            Handler().postDelayed({startMetronome(bpm.value.toLong())}, 100)
                        }
                    }
                },
                shape = CircleShape
            ) {
                Text(text = "-10", fontSize = 20.sp)
            }

            Spacer(modifier = Modifier.width(180.dp))

            Button(
                modifier = Modifier.size(80.dp),
                onClick = {
                    if(bpm.value < 244){
                        bpm.value += 10
                        globalStates.Bpm = bpm.value
                        sliderPosition = bpm.value.toFloat()
                        globalStates.Slider = sliderPosition
                        if(MetronomeState){
                            stopMetronome()
                            globalStates.State = MetronomeState
                            Handler().postDelayed({startMetronome(bpm.value.toLong())}, 100)
                        }
                    } else if(bpm.value > 244 && bpm.value != 255) {
                        bpm.value = 255
                        globalStates.Bpm = bpm.value
                        sliderPosition = bpm.value.toFloat()
                        globalStates.Slider = sliderPosition
                        if(MetronomeState){
                            stopMetronome()
                            globalStates.State = MetronomeState
                            Handler().postDelayed({startMetronome(bpm.value.toLong())}, 100)
                        }
                    }
                },
                shape = CircleShape
            ) {
                Text(text = "+10", fontSize = 20.sp)
            }
        }

        //Need a handler to run metronome in seperate thread


    }
}

@Composable
fun TickAnimation(bpm: Int) {
    var backgroundColor by remember { mutableStateOf(Color.Gray)}
    var tickCount by remember {
        mutableStateOf(0)
    }
    var isPlaying by remember { mutableStateOf(true)}
    var totalTicks by remember { mutableStateOf(0)}
    var tickDuration by remember {
        mutableStateOf(0)
    }

    LaunchedEffect(isPlaying) {
        totalTicks = (60 * 1000 / bpm).toInt()
        tickDuration = totalTicks / 2

        while (isPlaying) {
            tickCount++
            if (tickCount % tickDuration == 0) {
                backgroundColor = if (backgroundColor == Color.Gray) Color.Green else Color.Gray
            }
            if (tickCount >= totalTicks) {
                tickCount = 0
            }
            delay(1)
        }

    }
    Box(
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape)
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "${tickCount * 60 / totalTicks}",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


//CHANGE BPM TO WORK WITH SLIDER SET TO 60 RN
fun calculateSleepDuration(bpm: Long): Long {
    return (1000 * (60 / bpm.toDouble())).toLong()
}
fun startMetronome(bpm: Long){
    val MetronomeTone = ToneGenerator.TONE_PROP_BEEP

    MetronomeState = true

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


