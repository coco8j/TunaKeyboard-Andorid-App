package dev.patrickgold.florisboard.app.tunaKeyboard

import dev.patrickgold.florisboard.ime.text.keyboard.TextKey

data class Coordinate(val x: Float, val y: Float)

object KeyHistoryManager {
    private val keyHistoryData: MutableMap<Int, MutableList<Coordinate>> = mutableMapOf()

    fun addToHistory(key: TextKey, coordinate: Coordinate): Unit {
        val keyCode = key.computedData.code
        val (centerX, centerY) = key.touchBounds.center
        val coordinates = keyHistoryData.getOrPut(keyCode) { mutableListOf(Coordinate(centerX, centerY)) }
        coordinates.add(coordinate)
    }

    fun getHistory(keyCode: Int): List<Coordinate>? {
        return keyHistoryData[keyCode]
    }

    fun getAllHistory(): Map<Int, List<Coordinate>> {
        return keyHistoryData
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
                    val pressedCoordinates = coordinates[i]
                        // TODO: 딥러닝 모델 연결, 업그레이드(fitting) 로직 연결
//                    model = create_model()
//                    model.fit(pressedCoordinates, targetCoordinates, epochs=10)
                }
            }
        }
    }

    // For log
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
}
