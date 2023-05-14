@file:Suppress("UNUSED_EXPRESSION")

package com.example.nome.ui.screens

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.media.AudioManager
import android.media.Image
import android.media.ToneGenerator
import android.media.ToneGenerator.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.nome.R
import com.example.nome.R.drawable
import com.example.nome.R.drawable.language
import com.example.nome.ui.nav.PresetsNavGraph
import com.example.nome.ui.nav.Routes
import java.util.*
import java.util.logging.Handler
import kotlin.concurrent.timerTask



@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val nav = rememberNavController()
    Scaffold (
        topBar = {
            TopBar()
        },
        bottomBar = {
            BottomBar(nav = nav)
        }
            ) {
        PresetsNavGraph(nav)
    }
}


@Composable
fun TopBar(){
    TopAppBar(
        title = { Text("Nome") }
    )
}

@Composable
fun BottomBar (
    nav: NavHostController
) {
    val backStateEntry = nav.currentBackStackEntryAsState()
    val currentDestination = backStateEntry.value?.destination

    BottomNavigation (
        elevation = 16.dp
    ) {
        // MainScreen
        BottomNavigationItem(
            selected = currentDestination?.route == Routes.MainScreen.route,
            onClick = {
                      nav.navigate(Routes.MainScreen.route) {
                          popUpTo(Routes.MainScreen.route)
                      }
            },
            icon = {
                Icon(Icons.Default.Home, contentDescription = "")
            },
            label = {
                Text("Home1")
            }
        )
        // user presets
        BottomNavigationItem(
            selected = currentDestination?.route == Routes.UserPresetScreen.route,
            onClick = {
                      nav.navigate(Routes.UserPresetScreen.route) {
                          popUpTo(Routes.UserPresetScreen.route)
                         }
                      },
            icon = {
                Icon(Icons.Default.Face, contentDescription = "")
            },
            label = {
                Text("Your Presets")
            }
        )
        // online presets
        BottomNavigationItem(
            selected = currentDestination?.route == Routes.OnlinePresetScreen.route,
            onClick = {
                      nav.navigate(Routes.OnlinePresetScreen.route) {
                          popUpTo(Routes.OnlinePresetScreen.route)
                      }
            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.language),
                    contentDescription = "Online presets icon",
                    modifier = Modifier.size(24.dp)
                )
            },
            label = {
                Text("Online Presets")
            }
        )
    }

}


