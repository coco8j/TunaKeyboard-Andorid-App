/*
 * Copyright (C) 2021 Patrick Goldinger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.patrickgold.florisboard.ime.keyboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import dev.patrickgold.florisboard.lib.FlorisRect

/**
 * Abstract class describing the smallest computed unit in a computed keyboard. Each key represents exactly one key
 * displayed in the UI. It allows to save the absolute location within the parent keyboard, save touch and visual
 * bounds, managing the state (enabled, pressed, visibility) as well as layout sizing factors. Each key in this IME
 * inherits from this base key class. This allows for a inter-operable usage of a key without knowing the exact
 * subclass upfront.
 *
 * @property data The base key data this key represents.This can be anything - from a basic text key to an emoji key
 *  to a complex selector.
 */
/**
 * 컴퓨터 키보드에서 가장 작은 계산 단위를 설명하는 추상 클래스입니다. 각 키는 정확히 UI에 표시되는 하나의 키를 나타냅니다.
 * 부모 키보드 내에서 절대 위치를 저장하고, 터치 및 시각적
 * 바운드를 저장하고 상태(활성화, 눌림, 표시 여부)와 레이아웃 크기 요소를 관리할 수 있습니다.
 * 이 IME의 각 키는 이 기본 키 클래스에서 상속됩니다. 따라서 정확한 하위 클래스를 미리 알지 못해도 키를 상호 운용적으로 사용할 수 있습니다.
 * 하위 클래스를 미리 알지 못해도 상호 운용이 가능합니다.
 *
 * @property 데이터 이 키가 나타내는 기본 키 데이터로, 기본 텍스트 키부터 이모티콘 키, 복잡한 선택기 키
 * 부터 복잡한 선택기까지 무엇이든 될 수 있습니다.
 */
abstract class Key(open val data: AbstractKeyData) {
    /**
     * Specifies whether this key is enabled or not.
     */
    /**
     * 이 키가 활성화(사용 가능)되어 있는지를 나타냅니다.
     * `true`일 경우 키를 사용할 수 있고, `false`일 경우 키가 비활성화됩니다.
     */
    open var isEnabled: Boolean by mutableStateOf(true)

    /**
     * Specifies whether this key is actively pressed or not. Is used by the parent keyboard view to draw the key
     * differently to indicate this state.
     */
    open var isPressed: Boolean by mutableStateOf(false)

    /**
     * Specifies whether this key is visible or not. Is used by the parent keyboard view to omit this key in the
     * layout and drawing process. A `false`-value is equivalent to `VISIBILITY_GONE` on Android's View class.
     */
    open var isVisible: Boolean by mutableStateOf(true)

    /**
     * The touch bounds of this key. All bounds defined here are absolute coordinates within the parent keyboard.
     */
    /**
     * 이 키의 터치 가능한 영역을 정의합니다. 이 값은 부모(키보드)에서의 절대 좌표로 나타나며,
     * 사용자가 키를 터치할 수 있는 범위를 지정합니다.
     */
    open val touchBounds: FlorisRect = FlorisRect.empty()

    /**
     * The visible bounds of this key. All bounds defined here are absolute coordinates within the parent keyboard.
     */
    /**
     * 이 키가 화면에 표시되는 영역을 정의합니다. 이 값은 부모(키보드)에서의 절대 좌표로 나타나며,
     * 화면에 그려지는 키의 실제 크기와 위치를 지정합니다.
     */
    open val visibleBounds: FlorisRect = FlorisRect.empty()

    /**
     * Specifies how much this key is willing to shrink if too many keys are in a keyboard row. A value of 0.0
     * indicates that the key does not want to shrink in such scenario. This value should not be set manually, only
     * by the key's compute method and is used in the layout process to determine the real key width.
     */
    /**
     * 키보드의 한 행에 너무 많은 키가 있을 때, 이 키가 얼마나 축소될 수 있는지를 나타냅니다.
     * 0.0은 이 키가 축소되지 않기를 원한다는 의미입니다. 이 값은 수동으로 설정하지 않고,
     * 키의 레이아웃 계산 과정에서 자동으로 결정됩니다.
     */
    open var flayShrink: Float = 0f

    /**
     * Specifies how much this key is willing to grow if too few keys are in a keyboard row. A value of 0.0
     * indicates that the key does not want to grow in such scenario. This value should not be set manually, only
     * by the key's compute method and is used in the layout process to determine the real key width.
     */
    /**
     * 키보드 행에 키가 너무 적을 경우 이 키가 얼마나 커질지 지정합니다. 값이 0.0이면
     * 값은 이러한 시나리오에서 키가 커지지 않음을 나타냅니다. 이 값은 수동으로 설정해서는 안 되며, 키의 계산에 의해서만
     * 키의 계산 메서드에 의해 계산되며 레이아웃 프로세스에서 실제 키 너비를 결정하는 데 사용됩니다.
     */
    open var flayGrow: Float = 0f

    /**
     * Specifies the relative proportional width this key aims to get in respective to the keyboard view's desired key
     * width. A value of 1.0 indicates that the key wants to be exactly as wide as the desired key width, a value of
     * 0.0 is basically equivalent to setting [isVisible] to false. This value should not be set manually, only
     * by the key's compute method and is used in the layout process to determine the real key width.
     */
    /**
     * 키보드 보기의 원하는 키에 대해 이 키가 목표로 하는 상대적인 비례 폭을 지정합니다.
     * 너비를 지정합니다. 값이 1.0이면 키가 원하는 키 너비만큼 정확히 넓어지길 원한다는 뜻입니다.
     * 0.0은 기본적으로 [isVisible]을 false로 설정하는 것과 같습니다. 이 값은 수동으로 설정해서는 안 되며, 키의 계산을 통해서만
     * 키의 계산 메서드에 의해 계산되며 레이아웃 프로세스에서 실제 키 너비를 결정하는 데 사용됩니다.
     * 여기서 키 값을 동적으로 설정하면 각 키별 배율 설정이 가능합니다.
     */
    open var flayWidthFactor: Float = 0f

    /**
     * The computed UI label of this key. This value is used by the keyboard view to temporarily save the label string
     * for UI rendering and should not be set manually.
     */
    /**
     * 이 키의 계산된 UI 레이블입니다. 이 값은 키보드 보기에서 레이블 문자열을 임시로 저장하는 데 사용됩니다.
     * 을 임시 저장하는 데 사용되며 수동으로 설정해서는 안 됩니다.
     */
    open var label: String? = null

    /**
     * The computed UI hint label of this key. This value is used by the keyboard view to temporarily save the hint
     * label string for UI rendering and should not be set manually.
     */
    open var hintedLabel: String? = null

    /**
     * The computed ImageVector of this key. This value is used by the keyboard view to temporarily save the
     * ImageVector for UI rendering and should not be set manually.
     */
    open var foregroundImageVector: ImageVector? = null
}
