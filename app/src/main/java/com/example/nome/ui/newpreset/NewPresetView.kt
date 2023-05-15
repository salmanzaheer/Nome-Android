package com.example.nome.ui.newpreset

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.nome.model.Preset
import com.example.nome.model.UserPreset
import com.example.nome.ui.nav.Routes

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NewPresetView(
    vm: NewPresetViewModel = viewModel(),
    navController: NavController,
    onAddPreset: (UserPreset) -> Unit
){
    val ctx = LocalContext.current

    val (nameTf, bpmTf) = remember {
        FocusRequester.createRefs()
    }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = true){
        nameTf.requestFocus()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "New Preset",
            fontSize = 36.sp,
            modifier = Modifier
                .padding(16.dp)
                .focusRequester(nameTf)
        )
        OutlinedTextField(
            value = vm.name.value,
            onValueChange = vm::setName,
            placeholder = { Text(text = "Name")},
            label = { Text(text = "Name")},
            singleLine = true,
            modifier = Modifier
                .padding(16.dp)
                .focusRequester(nameTf),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions(
                onNext = { bpmTf.requestFocus() }
            )
        )
        OutlinedTextField(
            value = vm.bpm.value,
            onValueChange = vm::setBpm,
            placeholder = { Text(text = "BPM")},
            label = { Text(text = "BPM")},
            singleLine = true,
            modifier = Modifier
                .padding(16.dp)
                .focusRequester(bpmTf),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {keyboardController?.hide()}
            )
        )
        Button(
            onClick = {
                try {
                    val preset: UserPreset = vm.validate()
                    onAddPreset(preset)
                } catch (e: Exception) {
                    Toast.makeText(ctx, e.toString(), Toast.LENGTH_SHORT).show()
                }

                navController.navigate(Routes.UserPresetScreen.route){
                    popUpTo(Routes.UserPresetScreen.route)
                }
            }
        ) {
          Text(text = "Add Preset")
        }
    }


}