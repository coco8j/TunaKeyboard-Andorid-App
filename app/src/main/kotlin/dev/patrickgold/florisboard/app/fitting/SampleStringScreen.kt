package dev.patrickgold.florisboard.app.fitting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dev.patrickgold.florisboard.ime.text.keyboard.TextKeyboard
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.patrickgold.florisboard.app.LocalNavController
import dev.patrickgold.florisboard.app.Routes
import dev.patrickgold.florisboard.app.florisPreferenceModel
import dev.patrickgold.florisboard.lib.observeAsTransformingState
import dev.patrickgold.florisboard.lib.util.PreferenceUtils.getIndexFromPos

data class Coordinate(val x: Float, val y: Float)

data class SampleCharacter(
    val character: String,
    val rowIndex: Int,
    val keyIndex: Int,
    var hasCorrectKey: Boolean = false,
    private val pressedCoordinates: MutableList<Coordinate> = mutableListOf()
) {
    fun logPressedCoordinates(coordinate: Coordinate): Unit {
        pressedCoordinates.add(coordinate)
    }
    fun getPressedCoordinates(): List<Coordinate> {
        return pressedCoordinates
    }
    fun correct(): Unit {
        hasCorrectKey = !hasCorrectKey
    }
    fun equals(keyRowIndex:Int, keyKeyIndex:Int): Boolean {
        return rowIndex == keyRowIndex && keyIndex == keyKeyIndex
    }
}

class SampleStrings (
    private val sampleString: Array<SampleCharacter> = arrayOf(
        SampleCharacter("t", 0, 4),
        SampleCharacter("h", 1, 5),
        SampleCharacter("e", 0, 2),
        SampleCharacter(" ", 3, 4),
        SampleCharacter("q", 0, 0),
        SampleCharacter("u", 0, 6),
        SampleCharacter("i", 0, 7),
        SampleCharacter("c", 2, 3),
        SampleCharacter("k", 1, 7),
        SampleCharacter(" ", 3, 4),
        SampleCharacter("b", 2, 5),
        SampleCharacter("r", 0, 3),
        SampleCharacter("o", 0, 8),
        SampleCharacter("w", 0, 1),
        SampleCharacter("n", 2, 6),
        SampleCharacter(" ", 3, 4),
        SampleCharacter("f", 1, 3),
        SampleCharacter("o", 0, 8),
        SampleCharacter("x", 2, 2),
        SampleCharacter(" ", 3, 4),
        SampleCharacter("j", 1, 6),
        SampleCharacter("u", 0, 6),
        SampleCharacter("m", 2, 7),
        SampleCharacter("p", 0, 9),
        SampleCharacter("s", 1, 1),
        SampleCharacter(" ", 3, 4),
        SampleCharacter("o", 0, 8),
        SampleCharacter("v", 2, 4),
        SampleCharacter("e", 0, 2),
        SampleCharacter("r", 0, 3),
        SampleCharacter(" ", 3, 4),
        SampleCharacter("a", 1, 0),
        SampleCharacter(" ", 3, 4),
        SampleCharacter("l", 1, 8),
        SampleCharacter("a", 1, 0),
        SampleCharacter("z", 2, 1),
        SampleCharacter("y", 0, 5),
        SampleCharacter(" ", 3, 4),
        SampleCharacter("d", 1, 2),
        SampleCharacter("o", 0, 8),
        SampleCharacter("g", 1, 4),
    ),
    private var index: Int = 0
) {
    fun start(): SampleCharacter {
        return sampleString[0]
    }
    fun next(): SampleCharacter {
        index += 1

        if (index >= sampleString.size) {
            return SampleCharacter("TEST_END", -1, -1)
        }
        return sampleString[index]
    }
    fun get():Array<SampleCharacter> {
        return sampleString
    }
}

@Composable
fun StringScreen(keyboard: TextKeyboard)  {
    val prefs by florisPreferenceModel()
    val navController = LocalNavController.current

    val touchX by prefs.touchedKey.posX.observeAsTransformingState { it }
    val touchY by prefs.touchedKey.posY.observeAsTransformingState { it }
    val (rowIndex, keyIndex) = getIndexFromPos(touchX, touchY)
    var key = keyboard.getKeyForPos(touchX, touchY)

    val sampleString by remember { mutableStateOf(SampleStrings()) }
    val strings = sampleString.get()
    var targetString by remember { mutableStateOf<SampleCharacter?>(null) }

    Box {
        if (targetString == null) {
            CustomElevatedButton(
                onClick = {
                    targetString = sampleString.start()
                    key = null
                          },
                text = "Fitting Start"
            )
        } else {
            Column(
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Row {
                    for (character in strings) {
                        Text(
                            text = character.character,
                            color = if (character.hasCorrectKey) Color.Green else Color.Black,
                            fontFamily = WorkSans,
                            fontSize = 18.sp,
                        )
                    }
                }
                if (targetString?.character == "TEST_END") {
                    prefs.deepLearning.hasPreset.set(true)

                    CustomElevatedButton(
                        onClick = {
                            navController.navigate(Routes.Settings.Home)
                            // TODO: 머신러닝에 데이터 전달하여 학습시키기
                        },
                        text = "Fitting End. Start Tuna Keyboard"
                    )
                } else {
                    targetString?.logPressedCoordinates(Coordinate(touchX, touchY))

                    if (key != null) {
                        if (targetString?.equals(rowIndex, keyIndex) == true) {
                            targetString?.correct()
                            targetString = sampleString.next()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CustomElevatedButton(onClick: () -> Unit, text: String) {
    ElevatedButton(
        onClick = onClick,
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = Color(0xFF00CBDB),
            contentColor = Color.White
        ),
        modifier = Modifier.size(width = 300.dp, height = 50.dp)
    ) {
        Text(text)
    }
}
