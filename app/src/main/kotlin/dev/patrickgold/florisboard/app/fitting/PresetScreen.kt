package dev.patrickgold.florisboard.app.fitting

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.patrickgold.florisboard.R
import dev.patrickgold.florisboard.app.LocalNavController
import dev.patrickgold.florisboard.app.Routes
import dev.patrickgold.florisboard.app.florisPreferenceModel
import dev.patrickgold.florisboard.ime.text.keyboard.TextKeyboard
import dev.patrickgold.florisboard.keyboardManager
import dev.patrickgold.florisboard.lib.compose.FlorisErrorCard
import dev.patrickgold.florisboard.lib.compose.FlorisScreen
import dev.patrickgold.florisboard.lib.compose.FlorisWarningCard
import dev.patrickgold.florisboard.lib.compose.stringRes
import dev.patrickgold.florisboard.lib.util.InputMethodUtils

val TrainOne = FontFamily(
    Font(R.font.train_one_regular)
)

val WorkSans = FontFamily(
    Font(R.font.work_sans)
)

@Composable
fun FittingScreen() = FlorisScreen {
    title = "Tuna Setting"
    navigationIconVisible = false
    previewFieldVisible = true

    val prefs by florisPreferenceModel()
    val navController = LocalNavController.current
    val context = LocalContext.current
    val keyboardManager by context.keyboardManager()
    val evaluator by keyboardManager.activeEvaluator.collectAsState()
    val keyboard = evaluator.keyboard as TextKeyboard

    content {
        val isFlorisBoardEnabled by InputMethodUtils.observeIsFlorisboardEnabled(foregroundOnly = true)
        val isFlorisBoardSelected by InputMethodUtils.observeIsFlorisboardSelected(foregroundOnly = true)
        if (!isFlorisBoardEnabled) {
            FlorisErrorCard(
                modifier = Modifier.padding(8.dp),
                showIcon = false,
                text = stringRes(R.string.settings__home__ime_not_enabled),
                onClick = { InputMethodUtils.showImeEnablerActivity(context) },
            )
        } else if (!isFlorisBoardSelected) {
            FlorisWarningCard(
                modifier = Modifier.padding(8.dp),
                showIcon = false,
                text = stringRes(R.string.settings__home__ime_not_selected),
                onClick = { InputMethodUtils.showImePicker(context) },
            )
        }
        val hasPreset = prefs.deepLearning.hasPreset.get()
            Column(
                modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.padding(50.dp),
                    text = "Tuna Keyboard",
                    fontFamily = TrainOne,
                    fontSize = 30.sp
                )
                Image(
                    painter = painterResource(id = R.drawable.logo_tuna_keyboard),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.fillMaxWidth(0.5f)
                )
                StringScreen(keyboard = keyboard)
                Spacer(modifier = Modifier.size(10.dp))
                BlackNavButton(
                    onClick = {navController.navigate(Routes.Settings.TunaSettings)} ,
                    text = "Individual key Setting",
                )
            }

    }
}

@Composable
fun BlackNavButton(onClick: () -> Unit, text: String, enabled: Boolean = true) {
    ElevatedButton(
        onClick = onClick,
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = Color.Black,
            contentColor = Color.White
        ),
        modifier = Modifier.size(width = 300.dp, height = 50.dp),
        enabled = enabled
    ) {
        Text(text)
    }
}
