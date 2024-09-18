package dev.patrickgold.florisboard.ime.text.keyboard

import dev.patrickgold.florisboard.lib.FlorisRect

// visibleBounds 는 TextKeyboardLayout 에 따라 후처리하게 되어있음
// TODO: 최소 값 방어 코드 있어야 함.
class KeyTileData (
    var touchBounds : FlorisRect,
    var frequency: Int,
)

class KeyTilesData (
    private val keyTiles: Array<Array<KeyTileData>>
) {
    override fun toString(): String {
        return "(${keyTiles.contentDeepToString()})"
    }

    operator fun get(rowIndex: Int, keyIndex: Int): KeyTileData? {
        return if (rowIndex in keyTiles.indices && keyIndex in keyTiles[rowIndex].indices) {
            keyTiles[keyIndex][rowIndex]
        } else {
            null
        }
    }

    fun set(rowIndex: Int, keyIndex: Int, value: FlorisRect) {
        keyTiles[rowIndex][keyIndex].touchBounds = value
    }
}
