package com.example.nome.ui.theme

import androidx.compose.runtime.MutableState

/*when sound is playing and screen is changed. it goes back to default mustableState. Bpm 60 and active button changed
//Resolve Idea
Create a globalDataClass to save and update whenever screen changes. State<Boolean> Slider<Float> BPM<Int>
we can use this globalDataclass to set te bpm and shit when user clicks on preset.
Body() is called n presetnvgraph.kt
create globalStateDataClass. Add parameters to body function to grab globalStateDataClass.
Edit Body() to update all */

data class globalStateDataClass(
    var State: Boolean,
    var Bpm: Int,
    var Slider: Float
)