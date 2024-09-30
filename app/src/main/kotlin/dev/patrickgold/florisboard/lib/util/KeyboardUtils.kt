package dev.patrickgold.florisboard.lib.util

object KeyboardUtil {
    object KeyboardUtil {

        /**
         * 초기 설정된 각 키의 flayWidth, width, 그리고 줄의 최대 넓이 및 각 키의 필수 좌표(주어지거나 주어지지 않음)를 기반으로,
         * 각 키의 새로운 flayWidth를 계산하여 반환하는 함수.
         * @param currentFlayWidth 초기 설정된 flayWidth
         * @param keyWidths 각 키의 초기 넓이 리스트 (없을 경우 함수가 종료됨)
         * @param keyboardWidth 키보드 넓이 (0 또는 음수일 경우 함수가 종료됨)
         * @param keyCoordinates 각 키의 필수 좌표 리스트 (null일 수 있음)
         * @return 각 키의 새로운 flayWidth 리스트 (초기 조건이 충족되지 않으면 빈 리스트 반환)
         */
        fun calculateNewFlayWidths(
            currentFlayWidth: Float,
            keyWidths: List<Float>?,
            keyboardWidth: Float,
            keyCoordinates: List<Float>?
        ): List<Float> {
            // keyWidths가 null이거나 빈 리스트일 경우, keyboardWidth가 0 이하일 경우 바로 리턴
            if (keyWidths == null || keyWidths.isEmpty() || keyboardWidth <= 0f) {
                return emptyList()
            }

            val newFlayWidths = mutableListOf<Float>() // 새로운 flayWidth를 저장할 리스트

            // 필수 포함 좌표를 순회하며 값을 계산
            if (keyCoordinates != null && keyCoordinates.isNotEmpty()) {
                for (i in 0 until keyWidths.size) {
                    val newFlayWidth = keyWidths[i] / keyboardWidth  // 좌표와 관련된 비율로 새로운 flayWidth 계산
                    newFlayWidths.add(newFlayWidth)
                }
            } else {
                // 필수 좌표가 주어지지 않은 경우 초기 설정된 flayWidth를 그대로 적용
                for (i in keyWidths.indices) {
                    newFlayWidths.add(currentFlayWidth)  // 초기 설정된 flayWidth 사용
                }
            }

            return newFlayWidths
        }
    }

}
