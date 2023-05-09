package com.example.nome.ui

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nome.model.Preset
import kotlin.reflect.KProperty0


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PresetRow(
    idx: Int,
    preset: Preset,
    onDelete: (Preset) -> Unit,
    onSelect: KProperty0<State<Preset?>>,
) {
    val context = LocalContext.current
    Log.d("TAG", preset.name)
    Card(
        shape = RoundedCornerShape(5.dp),
        elevation = 16.dp,
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 5.dp, bottom = 5.dp)
            .fillMaxWidth()
    ) {
        Row (
            modifier = Modifier
                .combinedClickable(
                    onLongClick = {
                        onDelete(preset)
                    }
                ) {
                    //onSelect(preset)
                }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            Column(
                modifier = Modifier.weight(1.5f)
            ) {
                Row(
                    modifier = Modifier.padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Name:", modifier = Modifier.weight(1.0f))
                    Text(preset.name, modifier = Modifier.weight(2.0f), fontSize = 28.sp, color = MaterialTheme.colors.secondary)
                }
                Row(
                    modifier = Modifier.padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("BPM:", modifier = Modifier.weight(1.0f))
                    Text(
                        preset.BPM.toString(),
                        modifier = Modifier.weight(2.0f),
                        fontSize = 30.sp,
                        color = MaterialTheme.colors.secondary
                    )
                }
                Button({
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(preset.url)
                    context.startActivity(intent)

                }){
                    Text("Listen Now!")
                }

            }
        }

    }
}