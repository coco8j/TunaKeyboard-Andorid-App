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
        var keyTilesData = loadKeyTilesFromPreferences()

        if (keyTilesData == null) {
            keyTilesData = createDefaultKeyTilesData()
        }

        keyTilesData.setTouchBounds(rowIndex, keyIndex, value)
        val jsonDataString = gson.toJson(keyTilesData)

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

    fun getIndexFromPos(x: Float, y: Float): Pair<Int, Int> {
        val keyTilesData = loadKeyTilesFromPreferences()
        if (keyTilesData != null) {
            for ((r, row) in keyTilesData.rows().withIndex()) {
                for ((k, key) in row.withIndex()) {
                    if (key.touchBounds.contains(x, y)) {
                        return Pair(r, k)
                    }
                }
            }
        }
       return Pair(-1, -1)
    }

}


