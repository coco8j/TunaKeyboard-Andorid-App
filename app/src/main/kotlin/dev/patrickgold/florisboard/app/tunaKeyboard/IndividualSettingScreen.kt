package dev.patrickgold.florisboard.app.tunaKeyboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.patrickgold.florisboard.app.florisPreferenceModel
import dev.patrickgold.florisboard.ime.text.keyboard.TextKeyboard
import dev.patrickgold.florisboard.keyboardManager
import dev.patrickgold.florisboard.lib.compose.FlorisOutlinedBox
import dev.patrickgold.florisboard.lib.compose.FlorisScreen
import dev.patrickgold.florisboard.lib.observeAsTransformingState

@Composable
fun KeySettingScreen() = FlorisScreen {
    title = "Tuna Setting"
    previewFieldVisible = true

    val minW = 0.3
    val maxW = 2.0
    val stepIncrement = 15.0
    var width by remember { mutableStateOf(1.56f) }
    var height by remember { mutableStateOf(140.0f) }

    val prefs by florisPreferenceModel()
    val customFlayWidthFactor = prefs.customFlayValues.customFlayWidthFactor
    var tempData: String = remember { customFlayWidthFactor.get() }

    val context = LocalContext.current
    val keyboardManager by context.keyboardManager()
    val evaluator by keyboardManager.activeEvaluator.collectAsState()
    val keyboard = evaluator.keyboard as TextKeyboard
    var keyLabel by remember { mutableStateOf(" ") }
    val touchX by prefs.touchedKey.posX.observeAsTransformingState { it }
    val touchY by prefs.touchedKey.posY.observeAsTransformingState { it }
    val key = keyboard.getKeyForPos(touchX, touchY)

    if (key != null) {
        height = key.touchBounds.height
        width = key.flayWidthFactor
    }

    LaunchedEffect(touchX, touchY) {
        val touchedKey = keyboard.getKeyForPos(touchX, touchY)
        keyLabel = touchedKey?.label ?: " "
    }

    content {
        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                text = "Chose Key and Fix!",
                fontFamily = TrainOne,
                fontSize = 30.sp,
                style = MaterialTheme.typography.displaySmall.copy(textAlign = TextAlign.Center),
                )
            Spacer(modifier = Modifier.size(20.dp))
            FlorisOutlinedBox(
                modifier = Modifier.size(80.dp).padding(vertical = 8.dp, horizontal = 16.dp),
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = keyLabel,
                        style = MaterialTheme.typography.displaySmall,
                    )
                }
                Spacer(modifier = Modifier.size(10.dp))
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                val widthNumber: Float = width ?: 1.56f
                Text("너비 ${"%.1f".format(widthNumber)}")
                Text("너비를 설정합니다.")
            }
            Spacer(modifier = Modifier.size(10.dp))
            Slider(
                value = width,
                valueRange = minW.toFloat()..maxW.toFloat(),
                steps = ((maxW.toFloat() - minW.toFloat()) / stepIncrement.toFloat()).toInt(),
                onValueChange = { newValue ->
                    width = newValue
                    CustomFactor.setFlayWidth(key!!.computedData.code, newValue)
                },
                onValueChangeFinished = { CustomFactor.updateAllFlayData() },
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
            Spacer(modifier = Modifier.size(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                TextButton(onClick = {
                    if (tempData != customFlayWidthFactor.get()) {
                        customFlayWidthFactor.set(tempData)
                        CustomFactor.refresh()
                    }
                }) {
                    Text("되돌리기")
                }
                TextButton(onClick = {
                    //TODO: 완전 초기화 로직적용. 유저에게 경고 띄우기
                    prefs.deepLearning.hasPreset.set(false)
                }) {
                    Text("초기화")
                }
                TextButton(onClick = {
                    tempData = customFlayWidthFactor.get()
                }) {
                    Text("저장하기")
                }
            }
        }
    }
}
