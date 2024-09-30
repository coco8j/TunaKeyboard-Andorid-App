package dev.patrickgold.florisboard.lib.util

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.patrickgold.florisboard.app.florisPreferenceModel
import dev.patrickgold.florisboard.app.tunaKeyboard.Coordinate
import dev.patrickgold.florisboard.app.tunaKeyboard.KeyHistoryManager
import dev.patrickgold.florisboard.ime.text.keyboard.TextKey
import dev.patrickgold.jetpref.datastore.model.PreferenceData
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

//data class tempCustomFlayPrefs(data: <MutableMap<Int, Coordinate>>) :MutableMap<Int, Coordinate>

//K-Nearest Neighbors (KNN) 모델을 통한 오타 감지
// TODO: 각 키의 센터값을 기준으로 판단할 것. 가로와 세로 길이의 차이가 있기 때문에 세로가 가로보다 넓은 오차 허용범위를 가져야함
fun detectTypo(predicted: Coordinate, actual: Coordinate): Boolean {
    val threshold = 10.0  // 오차 허용 범위
    val distance = sqrt(
        (predicted.x - actual.x).toDouble().pow(2.0) +
            (predicted.y - actual.y).toDouble().pow(2.0)
    )
    return distance > threshold
}

fun calculateNewWidthFactor(key: TextKey, commonTouchCoordinate: Coordinate): Float? {
    val visibleBounds = KeyHistoryManager.getVisibleBounds(key)
    if (visibleBounds == null) return null

    val keyLeft = visibleBounds.left
    val keyRight = visibleBounds.right
    if (keyLeft < 0 || keyRight < 0) return null

    val keyCenterX = (keyRight + keyLeft) / 2
    val originalWidth = keyRight - keyLeft

    val standardWidth: Float = 1.56F
    val commonTouchX = commonTouchCoordinate.x

    val newWidth: Float = abs(keyCenterX - commonTouchX) * 2
    val newWidthFactor = ( standardWidth * newWidth) / originalWidth
    Log.v("checkValue", "(안) 새로운 값 체크: $newWidthFactor")

    return if (newWidthFactor > 1.2F) newWidthFactor else null
}


fun updateFrequencyCoordinates(context: Context): Unit {
    val prefs by florisPreferenceModel()
    val frequencyCoordinates = prefs.touchedKey.frequencyCoordinates

   try {
        val inputStream = context.assets.open("raw/common_touch_coordinate.json")
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            val jsonString = bufferedReader.use { it.readText() }

    frequencyCoordinates.set(jsonString)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun getFrequencyCoordinates(): MutableMap<Int, MutableList<Coordinate>> {
    try {
        val prefs by florisPreferenceModel()
        val frequencyCoordinates = prefs.touchedKey.frequencyCoordinates

        val gson = Gson()
        // TODO: 머신 러닝 출력값이 최종적으로 이 형태라면 좀 더 간결하게 쓸 수 있음. TypeToken<MutableMap<Int, Coordinate>>
        val type = object : TypeToken<MutableMap<Int, MutableList<Coordinate>>>() {}.type

        return gson.fromJson(frequencyCoordinates.get(), type)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return mutableMapOf()
}

fun setAsJson(objectData: Any, prefsId: PreferenceData<String>): Unit {
    val gson = Gson()
    val jsonString = gson.toJson(objectData)
    prefsId.set(jsonString)
}
