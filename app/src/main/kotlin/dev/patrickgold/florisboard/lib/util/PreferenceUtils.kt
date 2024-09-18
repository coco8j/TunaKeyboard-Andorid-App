package dev.patrickgold.florisboard.lib.util

import com.google.gson.Gson
import dev.patrickgold.florisboard.app.florisPreferenceModel
import dev.patrickgold.florisboard.ime.text.keyboard.KeyTilesData
import dev.patrickgold.florisboard.ime.text.keyboard.createDefaultKeyTilesData
import dev.patrickgold.florisboard.lib.FlorisRect

object PreferenceUtils {
    private val gson = Gson()
    private val prefs by florisPreferenceModel()

    fun saveKeyTilesToPreferences(rowIndex: Int, keyIndex: Int, value: FlorisRect): Unit {
        var currentKeyTilesInfo = loadKeyTilesFromPreferences()

        if (currentKeyTilesInfo == null) {
            currentKeyTilesInfo = createDefaultKeyTilesData(5, 10)
        }

        currentKeyTilesInfo.setTouchBounds(keyIndex, rowIndex, value)

        val jsonDataString = gson.toJson(currentKeyTilesInfo)
        prefs.keyboard.keyTilesInfo.set(jsonDataString)
    }

    fun loadKeyTilesFromPreferences(): KeyTilesData? {
        val jsonDataString = prefs.keyboard.keyTilesInfo.get()
        return if (jsonDataString.isNullOrEmpty()) {
            null
        } else {
            gson.fromJson(jsonDataString, KeyTilesData::class.java)
        }
    }
}


