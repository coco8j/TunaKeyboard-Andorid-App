package dev.patrickgold.florisboard.ime.text.keyboard

import dev.patrickgold.florisboard.lib.FlorisRect

class KeyTileData (
    var touchBounds: FlorisRect,
    var frequency: Int
)

class KeyTilesData(
    private val keyTiles: Array<Array<KeyTileData>>
) {
    override fun toString(): String {
        return "(${keyTiles.contentDeepToString()})"
    }

    fun rows(): Iterator<Array<KeyTileData>> {
        return keyTiles.iterator()
    }

    fun setTouchBounds(rowIndex: Int, keyIndex: Int, value: FlorisRect) {
        if (rowIndex in keyTiles.indices && keyIndex in keyTiles[rowIndex].indices) {
            keyTiles[rowIndex][keyIndex].touchBounds = value
        }
    }

    operator fun get(rowIndex: Int, keyIndex: Int): KeyTileData? {
        return if (rowIndex in keyTiles.indices && keyIndex in keyTiles[rowIndex].indices) {
            keyTiles[rowIndex][keyIndex]
        } else {
            null
        }
    }
}

fun createDefaultKeyTilesData(): KeyTilesData {
    val keyTiles = arrayOf(
        arrayOf( // 첫 번째 줄
            KeyTileData(touchBounds = FlorisRect.new(), frequency = 0),
            KeyTileData(touchBounds = FlorisRect.new(), frequency = 0),
            KeyTileData(touchBounds = FlorisRect.new(), frequency = 0),
            KeyTileData(touchBounds = FlorisRect.new(), frequency = 0),
            KeyTileData(touchBounds = FlorisRect.new(), frequency = 0),
            KeyTileData(touchBounds = FlorisRect.new(), frequency = 0),
            KeyTileData(touchBounds = FlorisRect.new(), frequency = 0),
            KeyTileData(touchBounds = FlorisRect.new(), frequency = 0),
            KeyTileData(touchBounds = FlorisRect.new(), frequency = 0),
            KeyTileData(touchBounds = FlorisRect.new(), frequency = 0)
        ),
        arrayOf( // 두 번째 줄
            KeyTileData(touchBounds = FlorisRect.new(), frequency = 0),
            KeyTileData(touchBounds = FlorisRect.new(), frequency = 0),
            KeyTileData(touchBounds = FlorisRect.new(), frequency = 0),
            KeyTileData(touchBounds = FlorisRect.new(), frequency = 0),
            KeyTileData(touchBounds = FlorisRect.new(), frequency = 0),
            KeyTileData(touchBounds = FlorisRect.new(), frequency = 0),
            KeyTileData(touchBounds = FlorisRect.new(), frequency = 0),
            KeyTileData(touchBounds = FlorisRect.new(), frequency = 0),
            KeyTileData(touchBounds = FlorisRect.new(), frequency = 0)
        ),
        arrayOf( // 세 번째 줄
            KeyTileData(touchBounds = FlorisRect.new(), frequency = 0),
            KeyTileData(touchBounds = FlorisRect.new(), frequency = 0),
            KeyTileData(touchBounds = FlorisRect.new(), frequency = 0),
            KeyTileData(touchBounds = FlorisRect.new(), frequency = 0),
            KeyTileData(touchBounds = FlorisRect.new(), frequency = 0),
            KeyTileData(touchBounds = FlorisRect.new(), frequency = 0),
            KeyTileData(touchBounds = FlorisRect.new(), frequency = 0),
            KeyTileData(touchBounds = FlorisRect.new(), frequency = 0),
            KeyTileData(touchBounds = FlorisRect.new(), frequency = 0)
        ),
        arrayOf( // 네 번째 줄
            KeyTileData(touchBounds = FlorisRect.new(), frequency = 0),
            KeyTileData(touchBounds = FlorisRect.new(), frequency = 0),
            KeyTileData(touchBounds = FlorisRect.new(), frequency = 0),
            KeyTileData(touchBounds = FlorisRect.new(), frequency = 0),
            KeyTileData(touchBounds = FlorisRect.new(), frequency = 0),
            KeyTileData(touchBounds = FlorisRect.new(), frequency = 0)
        )
    )

    return KeyTilesData(keyTiles)
}
