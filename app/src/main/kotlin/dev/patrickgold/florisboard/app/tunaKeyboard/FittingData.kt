package dev.patrickgold.florisboard.app.tunaKeyboard

import android.os.Environment
import android.util.Log
import com.google.gson.Gson
import dev.patrickgold.florisboard.ime.text.keyboard.TextKey
import dev.patrickgold.florisboard.lib.FlorisRect
import java.io.File

data class Coordinate(val x: Float, val y: Float)

object KeyHistoryManager {
    private val InitialKeyLayoutValue: MutableMap<Int, FlorisRect> = mutableMapOf()
    private val keyHistoryData: MutableMap<Int, MutableList<Coordinate>> = mutableMapOf()

    fun addToHistory(key: TextKey, coordinate: Coordinate): Unit {
        val keyCode = key.computedData.code
        val (centerX, centerY) = key.touchBounds.center
        val coordinates = keyHistoryData.getOrPut(keyCode) { mutableListOf(Coordinate(centerX, centerY)) }
        coordinates.add(coordinate)
    }

    fun putVisibleBounds(key: TextKey): Unit {
        val keyCode = key.computedData.code
        val visibleBounds = key.visibleBounds
        InitialKeyLayoutValue[keyCode] = visibleBounds
    }

    fun getHistory(keyCode: Int): List<Coordinate>? {
        return keyHistoryData[keyCode]
    }

    fun getAllHistory(): Map<Int, List<Coordinate>> {
        return keyHistoryData
    }

    fun getCenter(keyCode: Int): Coordinate? {
        return keyHistoryData[keyCode]?.firstOrNull()
    }

    fun getVisibleBounds(key: TextKey): FlorisRect? {
        val keyCode = key.computedData.code
        return InitialKeyLayoutValue[keyCode]
    }

    fun clearHistory(keyCode: Int) {
        keyHistoryData[keyCode]?.clear()
    }

    fun clearAllHistory() {
        keyHistoryData.clear()
    }

    fun updateKeyHistory() {
        keyHistoryData.forEach { (keyCode, coordinates) ->
            if (coordinates.isNotEmpty()) {
                val targetCoordinates = coordinates.first()

                for (i in 1 until coordinates.size) {
//                    val pressedCoordinates = coordinates[i]
                        // TODO: 딥러닝 모델 연결, 업그레이드(fitting) 로직 연결
//                    model = create_model()
//                    model.fit(pressedCoordinates, targetCoordinates, epochs=10)
                }
            }
        }
    }

    fun getHistoryAsString(): String {
        val builder = StringBuilder()

        keyHistoryData.forEach { (keyCode, coordinates) ->
            builder.append("KeyCode: $keyCode\nCoordinates:\n")
            coordinates.forEach { coordinate ->
                builder.append("  - (x: ${coordinate.x}, y: ${coordinate.y})\n")
            }
            builder.append("\n")
        }

        return builder.toString()
    }

    fun addToHistoryAverageValue(key: TextKey, coordinate: Coordinate): Unit {
        val keyCode = key.computedData.code
        val (centerX, centerY) = key.touchBounds.center
        var coordinates = keyHistoryData.getOrPut(keyCode) { mutableListOf(Coordinate(centerX, centerY)) }
        coordinates.add(coordinate)

        if (coordinates.size >= 2) {
            val lastCoordinate = coordinates.last()
            val secondLastCoordinate = coordinates[coordinates.size - 2]
            val AveragedX = (lastCoordinate.x + secondLastCoordinate.x) / 2
            val AveragedY = (lastCoordinate.y + secondLastCoordinate.y) / 2

            coordinates = mutableListOf(Coordinate(AveragedX, AveragedY))
        }
    }

    /**
     * Dev Only : make a jason file that contains results of deep learning
     */
    fun LogJsonResults(): Unit {
        val gson = Gson()
        val result: String = gson.toJson(keyHistoryData)

        if (result.isNotEmpty()) {
            try {
                val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                var fileIndex = 0
                val file = File(downloadsDir, "keyHistoryData_$fileIndex.json")

                while (file.exists()) {
                    fileIndex++
                   val file = File(downloadsDir, "keyHistoryData_$fileIndex.json")
                }

                file.writeText(result)

                Log.v("checkValue", "JSON data successfully written to file.")
            } catch (e: Exception) {
                Log.e("checkValue", "Error writing JSON to file: ${e.message}")
            }
        } else {
            val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val file = File(downloadsDir, "keyHistoryData.json")

            file.writeText("result")

            Log.v("checkValue", "JSON data successfully written to file.")
        }
    }
}
