package dev.patrickgold.florisboard.app.tunaKeyboard

import com.google.gson.reflect.TypeToken
import dev.patrickgold.florisboard.lib.util.restoreFromJson
import dev.patrickgold.florisboard.app.florisPreferenceModel
import dev.patrickgold.florisboard.lib.util.setAsJson

object CustomFactor {
    private var FlayWidthMap: MutableMap<Int, Float> = mutableMapOf()

    private val prefs by florisPreferenceModel()
    private val assignedPreference = prefs.customFlayValues.customFlayWidthFactor
    private val typeToken = object : TypeToken<MutableMap<Int, Float>>() {}

    init {
        val savedData: MutableMap<Int, Float>? = restoreFromJson(assignedPreference, typeToken)
        if (savedData != null) {
            FlayWidthMap = savedData
        }
    }

    fun getFlayWidth(keyCode: Int): Float? {
        val flayWidth = FlayWidthMap[keyCode]
        return flayWidth?.toFloat()
    }

    fun setFlayWidth(keyCode: Int, value: Float) {
        FlayWidthMap[keyCode] = value
    }

    fun updateAllFlayData(): Unit {
        setAsJson(assignedPreference, FlayWidthMap)
    }

    fun refresh(): Unit {
        val savedData: MutableMap<Int, Float>? = restoreFromJson(assignedPreference, typeToken)
        if (savedData != null) {
            FlayWidthMap = savedData
        }
    }
}
