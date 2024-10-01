package dev.patrickgold.florisboard.lib.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.patrickgold.jetpref.datastore.model.PreferenceData

fun <T> restoreFromJson(preference: PreferenceData<String>, typeToken: TypeToken<T>): T? {
    return try {
        val gson = Gson()
        val json = preference.get()
        gson.fromJson(json, typeToken.type)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun <T> setAsJson(preference: PreferenceData<String>, objectData: T): Unit {
    val gson = Gson()
    val jsonString = gson.toJson(objectData)
    preference.set(jsonString)
}

