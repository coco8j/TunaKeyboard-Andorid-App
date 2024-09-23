package dev.patrickgold.florisboard.app.settings.tunaSettings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.patrickgold.florisboard.R
import dev.patrickgold.florisboard.app.florisPreferenceModel
import dev.patrickgold.florisboard.ime.text.keyboard.TextKeyboard
import dev.patrickgold.florisboard.keyboardManager
import dev.patrickgold.florisboard.lib.compose.FlorisOutlinedBox
import dev.patrickgold.florisboard.lib.compose.FlorisScreen
import dev.patrickgold.florisboard.lib.observeAsTransformingState
import dev.patrickgold.florisboard.lib.util.PreferenceUtils.getIndexFromPos
import dev.patrickgold.florisboard.lib.util.PreferenceUtils.saveKeyTilesToPreferences

val TrainOne = FontFamily(
    Font(R.font.train_one_regular)
)

@Composable
fun TuneKeyboardLayout() = FlorisScreen {
    title = "Tuna Setting"
    previewFieldVisible = true

    val prefs by florisPreferenceModel()
    val context = LocalContext.current
    val keyboardManager by context.keyboardManager()
    val evaluator by keyboardManager.activeEvaluator.collectAsState()
    val keyboard = evaluator.keyboard as TextKeyboard

    //TODO: 키가 중심점을 벗어나서 설정되지 않게하기
    val min = 20.0
    val max = 300.0
    val stepIncrement = 10.0

    var width by remember { mutableStateOf(0.5f) }
    var height by remember { mutableStateOf(0.2f) }
    var previousWidth by remember { mutableStateOf(0f) }
    var previousHeight by remember { mutableStateOf(0f) }

    val keyLabel by prefs.touchedKey.keyLabel.observeAsTransformingState { it }
    val touchX by prefs.touchedKey.posX.observeAsTransformingState { it }
    val touchY by prefs.touchedKey.posY.observeAsTransformingState { it }

    val (rowIndex, keyIndex) = getIndexFromPos(touchX, touchY)
    val key = keyboard.getKeyForPos(touchX, touchY)

    if (key != null) {
        width = key.touchBounds.width
        height = key.touchBounds.height
    }

    content {
        Column(
            modifier = Modifier.fillMaxWidth().padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Chose Key and Fix!",
                fontFamily = TrainOne,
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.weight(10f))
            FlorisOutlinedBox(
                modifier = Modifier.size(80.dp).padding(vertical = 8.dp, horizontal = 16.dp),
            ) {
                Text(
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 4.dp),
                    text = keyLabel,
                    style = MaterialTheme.typography.displaySmall,
                )
                Spacer(modifier = Modifier.weight(1f))
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text("너비 $width")
                Text("너비를 설정합니다.")
            }
            Spacer(modifier = Modifier.weight(1f))
            Slider(
                value = width,
                valueRange = min.toFloat()..max.toFloat(),
                steps = ((max.toFloat() - min.toFloat()) / stepIncrement.toFloat()).toInt() - 1,
                onValueChange = { newValue ->
                    previousWidth = width
                    width = newValue
                    key?.touchBounds?.width = newValue
                },
                onValueChangeFinished = {
                    key?.let {
                        val widthDifference = width - previousWidth

                        val oldLeft = it.touchBounds.left
                        val oldRight = it.touchBounds.right

                        it.touchBounds.left = oldLeft - (widthDifference / 2)
                        it.touchBounds.right = oldRight + (widthDifference / 2)

                        saveKeyTilesToPreferences(rowIndex, keyIndex, it.touchBounds)
                    }
                },
                colors = SliderDefaults.colors(
                    thumbColor = MaterialTheme.colorScheme.primary,
                    activeTrackColor = MaterialTheme.colorScheme.primary,
                    activeTickColor = Color.Transparent,
                    inactiveTrackColor = MaterialTheme.colorScheme.onSurface.copy(
                        alpha = SliderDefaults.colors().inactiveTrackColor.alpha,
                    ),
                    inactiveTickColor = Color.Transparent,
                ),
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text("높이 $height")
                Text("높이를 설정합니다.")
            }
            Spacer(modifier = Modifier.weight(1f))
            Slider(
                value = height,
                valueRange = min.toFloat()..max.toFloat(),
                steps = ((max.toFloat() - min.toFloat()) / stepIncrement.toFloat()).toInt() - 1,
                onValueChange = { newValue ->
                    previousHeight = height
                    height = newValue
                    key?.touchBounds?.height = newValue
                },
                onValueChangeFinished = {
                    key?.let {
                        val heightDifference = height - previousHeight

                        val oldTop = it.touchBounds.top
                        val oldBottom = it.touchBounds.bottom

                        it.touchBounds.top = oldTop - (heightDifference / 2)
                        it.touchBounds.bottom = oldBottom + (heightDifference / 2)

                        saveKeyTilesToPreferences(rowIndex, keyIndex, it.touchBounds)
                    }
                },
                colors = SliderDefaults.colors(
                    thumbColor = MaterialTheme.colorScheme.primary,
                    activeTrackColor = MaterialTheme.colorScheme.primary,
                    activeTickColor = Color.Transparent,
                    inactiveTrackColor = MaterialTheme.colorScheme.onSurface.copy(
                        alpha = SliderDefaults.colors().inactiveTrackColor.alpha,
                    ),
                    inactiveTickColor = Color.Transparent,
                ),
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                // TODO: 초기화 버튼 구현
                TextButton(onClick = {
//                    prefs.keyboard.hasKeyTilesAllSettled.set(false)
//                    prefs.keyboard.keyTilesInfo.reset()
                }) {
                    Text("초기화")
                }
                Text("너비를 설정합니다.")
            }
        }
    }
}
