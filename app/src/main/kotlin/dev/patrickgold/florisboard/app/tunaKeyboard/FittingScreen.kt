package dev.patrickgold.florisboard.app.tunaKeyboard

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.patrickgold.florisboard.app.LocalNavController
import dev.patrickgold.florisboard.app.Routes
import dev.patrickgold.florisboard.app.florisPreferenceModel
import dev.patrickgold.florisboard.ime.text.key.KeyCode
import dev.patrickgold.florisboard.ime.text.keyboard.TextKeyboard
import dev.patrickgold.florisboard.keyboardManager
import dev.patrickgold.florisboard.lib.compose.FlorisScreen
import dev.patrickgold.florisboard.lib.observeAsTransformingState

class TestCharacter (text: String, isCorrect: Boolean = false) {
    val text = text
    var isCorrect = isCorrect

    fun correct() {
        if (!isCorrect) {
            isCorrect = true
        }
    }
}

val sampleString = "the quick brown fox jumps over the lazy dog"
class TestString () {
    val testString: List<TestCharacter> = sampleString.map { TestCharacter(it.toString()) }
    private var index: Int = 0
        fun get(): List<TestCharacter> {
            return testString
        }
        fun start(): TestCharacter {
            return testString[0]
        }
        fun next(): TestCharacter {
            index += 1
            if (index >= sampleString.length) {
                return TestCharacter("TEST_END")
            }
            return testString[index]
        }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FittingScreen() = FlorisScreen {
    title = "Tuna Setting"
    previewFieldVisible = true

    val prefs by florisPreferenceModel()
    val navController = LocalNavController.current

    val context = LocalContext.current
    val keyboardManager by context.keyboardManager()
    val evaluator by keyboardManager.activeEvaluator.collectAsState()
    val keyboard = evaluator.keyboard as TextKeyboard

    val touchX by prefs.touchedKey.posX.observeAsTransformingState { it }
    val touchY by prefs.touchedKey.posY.observeAsTransformingState { it }
    var key = keyboard.getKeyForPos(touchX, touchY)

    val targetString by remember { mutableStateOf(TestString()) }
    var targetCharacter by remember { mutableStateOf<TestCharacter>(TestCharacter("BEFORE_TEST")) }

    content {
        Column(
            modifier = Modifier.fillMaxWidth().padding( horizontal = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (targetCharacter.text == "BEFORE_TEST") {
                    Text(
                        modifier = Modifier.fillMaxWidth().padding(20.dp).align(Alignment.CenterHorizontally),
                        text = "안내",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 50.sp,
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth().padding(10.dp),
                        text = "반갑습니다! 여기서는 초기 키보드 세팅을 위한 테스트를 진행합니다. 해당 테스트를 통해 조금 더 빠른 맞춤형 키보드 제공이 가능합니다.",
                        fontWeight = FontWeight.Normal,
                        fontSize = 18.sp,
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(10.dp)
                    ) {
                        Text(
                            modifier = Modifier.padding(top = 4.dp),
                            text = "1.",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp,
                        )
                        Text(
                            text = " 다음 화면에서 나타나는 박스 안 문자를 타이핑 해주세요.",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp,
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(10.dp)
                    ) {
                        Text(
                            modifier = Modifier.padding(top = 4.dp),
                            text = "2.",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp,
                        )
                        Text(
                            text = " 틀려도 지울 필요는 없습니다.",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp,
                        )
                    }
                    Spacer(modifier = Modifier.size(30.dp))
                    CustomElevatedButton(
                        onClick = {
                            targetCharacter = targetString.start()
                            key = null
                        },
                        text = "테스트 시작하기"
                    )
                } else {
                    Spacer(modifier = Modifier.size(30.dp))
                Box(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(10.dp)
                        .border(1.dp, Color.Black, RoundedCornerShape(10.dp)),
                    ) {
                    FlowRow(
                        modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(30.dp),
                    ) {
                        for (character in targetString.get()) {
                            Text(
                                text = character.text,
                                color = if (character.isCorrect) Color.Green else Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontFamily = WorkSans,
                                fontSize = 33.sp,
                            )
                        }
                    }
                }
                Text(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
                    text = "혹시 키보드 입력이 되지 않나요?",
                    fontWeight = FontWeight.Normal,
                    )
                Text(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
                    text = "현재 키보드가 Tuna Keyboard인지 확인해 보세요!",
                    fontWeight = FontWeight.Normal,
                  )
                Spacer(modifier = Modifier.size(10.dp))
                if (targetCharacter.text == "TEST_END") {
                    CustomElevatedButton(
                        onClick = {
                            // TODO: 머신러닝에 데이터 전달하여 학습시키기,
                            navController.navigate(Routes.TunaKeyboard.Home)
                            CustomFactor.updateAllFlayData()
                            prefs.deepLearning.hasPreset.set(true)
                                  },
                        text = "홈으로"
                    )
                } else {
                    if (key != null) {
                        KeyHistoryManager.addToHistoryAverageValue(key!!, Coordinate(touchX, touchY))
                        KeyHistoryManager.putVisibleBounds(key!!)

                        if (targetCharacter.text == key!!.label ||
                            (targetCharacter.text == " " && key!!.computedData.code == KeyCode.SPACE)) {
                            targetCharacter.correct()
                            targetCharacter = targetString.next()
                            key = null
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
