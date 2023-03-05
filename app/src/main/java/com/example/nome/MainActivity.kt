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
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nome.ui.theme.NomeTheme

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
    var sliderPosition by remember {
        mutableStateOf(0f)
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
            valueRange = 0f..100f,
            onValueChange = {sliderPosition = it}
        )

        // buttons +-1 bpm
        Row(
            modifier = Modifier.padding(10.dp)
        ) {
            Button(onClick = { /*TODO*/ }) {
                Text(text = "-1")
            }

            Spacer(modifier = Modifier.width(180.dp))
            
            Button(onClick = { /*TODO*/ }) {
                Text(text = "+1")
            }
        }

        // display BPM
        Text(
            text = "BPM",
            fontSize = 36.sp
        )

        // buttons +-10 bpm
        Row(
            modifier = Modifier.padding(10.dp)
        ) {
            Button(onClick = { /*TODO*/ }) {
                Text(text = "-10")
            }

            Spacer(modifier = Modifier.width(180.dp))

            Button(onClick = { /*TODO*/ }) {
                Text(text = "+10")
            }
        }

        // FAB for play/stop
        FloatingActionButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Filled.PlayArrow, contentDescription = "Start/Stop")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NomeTheme {
        MainScreen()
    }
}