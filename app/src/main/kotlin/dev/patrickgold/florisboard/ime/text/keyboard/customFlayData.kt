package dev.patrickgold.florisboard.ime.text.keyboard

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.patrickgold.florisboard.app.florisPreferenceModel

class FlayValues {
    var hasCustomFlay: Boolean = false
    var customFlayWidthFactor: Float = 0f
    var customFlayShrink: Float = 0f
    var customFlayGrow: Float = 0f

    fun resetValue(): Unit {
        customFlayWidthFactor = 0f
        customFlayShrink = 0f
        customFlayGrow = 0f

        if (hasCustomFlay) {
            hasCustomFlay = false
        }
    }
}

object CustomFlayData {
    private var keyHistoryData: MutableMap<Int, FlayValues> = mutableMapOf()

    val prefs by florisPreferenceModel()
    val customFlayValuePrefs = prefs.customFlayValues.customFlayWidthFactor

    init {
        val savedData = customFlayValuePrefs.get()
        if (savedData != customFlayValuePrefs.default) {
            keyHistoryData = loadFromPreferences()
        }
    }

    fun getCustomFlayWidthFactor(keyCode: Int): Float? {
        val flayValues = keyHistoryData.getOrPut(keyCode) { FlayValues() }
        val widthFactor = flayValues.customFlayWidthFactor
        return if (widthFactor != 0f) widthFactor else null
    }

    fun setCustomFlayWidthFactor(keyCode: Int, value: Float): Unit {
        val flayValues = keyHistoryData.getOrPut(keyCode) { FlayValues() }

        flayValues.customFlayWidthFactor = value
    }

    fun resetAllCustomFlay(): Unit {
        keyHistoryData = mutableMapOf()
    }

    fun saveToPreferences(mapData: MutableMap<Int, FlayValues> = keyHistoryData): Unit {
        val gson = Gson()
        val json = gson.toJson(mapData)

        prefs.customFlayValues.customFlayWidthFactor.set(json)
    }

    fun loadFromPreferences(): MutableMap<Int, FlayValues> {
        val gson = Gson()
        val json = prefs.customFlayValues.customFlayWidthFactor.get()

        val type = object : TypeToken<MutableMap<Int, FlayValues>>() {}.type
        return gson.fromJson(json, type) ?: mutableMapOf()
    }

    fun rollback(): Unit {
        val gson = Gson()
        val tempDataJson = prefs.customFlayValues.tempData.get()

        val type = object : TypeToken<MutableMap<Int, FlayValues>>() {}.type
        val recoveryData: MutableMap<Int, FlayValues> = gson.fromJson(tempDataJson, type) ?: mutableMapOf()
        keyHistoryData = recoveryData
    }

}
