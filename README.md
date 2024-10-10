<p align="center">
            <img width=40% alt="Tuna_keyboard_logo" src="https://github.com/user-attachments/assets/f7f2b760-0556-4df6-b3fb-245e0930b5cd">
</p>

# Tunakeyboard 소개

> **“Tune-a-Keyboard”
> 사용자 입력패턴을 학습하여 맞춤형 레이아웃을 제공하는 안드로이드전용 커스텀 키보드 어플리케이션 입니다.**

<br>

# 기획 의도

> 스마트폰 기종 변경 시 특정 영역에서 오타 발생 빈도가 증가하는 현상을 경험했습니다. 스마트폰 화면의 다양화 및 대형화로 인해 이러한 문제가 더욱 두드러졌습니다. 최신 기종에 내장된 보완 기능이 존재하나, 이는 완전한 해결책이 되지 못했습니다. 이에 따라, 일상적인 불편을 해소하고자 직접 솔루션을 개발하기로 결정했습니다.

# 목차

- [Tunakeyboard 소개](#%EC%86%8C%EA%B0%9C)
- [기획 의도](#%EA%B8%B0%ED%9A%8D-%EC%9D%98%EB%8F%84)
- [기술 스택](#%EA%B8%B0%EC%88%A0-%EC%8A%A4%ED%83%9D)
- [화면 구성](#%ED%99%94%EB%A9%B4-%EA%B5%AC%EC%84%B1)
- [구현 살펴보기](#%EA%B5%AC%ED%98%84-%EC%82%B4%ED%8E%B4%EB%B3%B4%EA%B8%B0)
    - [[1] 사용자 편의성을 위해](#1-%EC%82%AC%EC%9A%A9%EC%9E%90-%ED%8E%B8%EC%9D%98%EC%84%B1%EC%9D%84-%EC%9C%84%ED%95%B4)
        - [**1. 상품성을 고려하자**](#1-%EC%83%81%ED%92%88%EC%84%B1%EC%9D%84-%EA%B3%A0%EB%A0%A4%ED%95%98%EC%9E%90)
        - [**2. 안전하게 만들어 보자**](#2-%EC%95%88%EC%A0%84%ED%95%98%EA%B2%8C-%EB%A7%8C%EB%93%A4%EC%96%B4-%EB%B3%B4%EC%9E%90)
    - [[2] 핵심 로직 소개](#2-%ED%95%B5%EC%8B%AC-%EB%A1%9C%EC%A7%81-%EC%86%8C%EA%B0%9C)
        - [1. 레이아웃 로직에 간섭해 키 버튼 크기 변경하는 방법](#1-%EB%A0%88%EC%9D%B4%EC%95%84%EC%9B%83-%EB%A1%9C%EC%A7%81%EC%97%90-%EA%B0%84%EC%84%AD%ED%95%B4-%ED%82%A4-%EB%B2%84%ED%8A%BC-%ED%81%AC%EA%B8%B0-%EB%B3%80%EA%B2%BD%ED%95%98%EB%8A%94-%EB%B0%A9%EB%B2%95)
        - [2. 사용자에게 맞는 너비 값을 계산 하는 방법 (1) 사용자의 터치 좌표 수집하기](#2-%EC%82%AC%EC%9A%A9%EC%9E%90%EC%97%90%EA%B2%8C-%EB%A7%9E%EB%8A%94-%EB%84%88%EB%B9%84-%EA%B0%92%EC%9D%84-%EA%B3%84%EC%82%B0-%ED%95%98%EB%8A%94-%EB%B0%A9%EB%B2%95-1-%EC%82%AC%EC%9A%A9%EC%9E%90%EC%9D%98-%ED%84%B0%EC%B9%98-%EC%A2%8C%ED%91%9C-%EC%88%98%EC%A7%91%ED%95%98%EA%B8%B0)
        - [3. 사용자에게 맞는 너비 값을 계산 하는 방법 (2) 좌표로부터 넓이 배율 계산하기](#3-%EC%82%AC%EC%9A%A9%EC%9E%90%EC%97%90%EA%B2%8C-%EB%A7%9E%EB%8A%94-%EB%84%88%EB%B9%84-%EA%B0%92%EC%9D%84-%EA%B3%84%EC%82%B0-%ED%95%98%EB%8A%94-%EB%B0%A9%EB%B2%95-2-%EC%A2%8C%ED%91%9C%EB%A1%9C%EB%B6%80%ED%84%B0-%EB%84%93%EC%9D%B4-%EB%B0%B0%EC%9C%A8-%EA%B3%84%EC%82%B0%ED%95%98%EA%B8%B0)
        - [4. 결과](#4-%EA%B2%B0%EA%B3%BC)
    - [[3] 발생한 어려움](#3-%EB%B0%9C%EC%83%9D%ED%95%9C-%EC%96%B4%EB%A0%A4%EC%9B%80)
        - [1. 모델 탑재 관련 (이슈 해결 후 업데이트 예정)](#1-%EB%AA%A8%EB%8D%B8-%ED%83%91%EC%9E%AC-%EA%B4%80%EB%A0%A8-%EC%9D%B4%EC%8A%88-%ED%95%B4%EA%B2%B0-%ED%9B%84-%EC%97%85%EB%8D%B0%EC%9D%B4%ED%8A%B8-%EC%98%88%EC%A0%95)
        - [2. 언어 장벽 관련](#2-%EC%96%B8%EC%96%B4-%EC%9E%A5%EB%B2%BD-%EA%B4%80%EB%A0%A8)

<br>

# 기술 스택

- **개발 언어**: **`Native(Kotlin)`**
- **에디터**: **`Android Studio`**
- **뷰(View)**: **`Jetpack composer` (내장)**
- **상태관리**: **`Shared preference` (내장)**
- **머신러닝**: **`TensorFlow Lite Model` (`python`, `Tensor Flow` 로구현)**

<br>

# 화면 구성

- [시연 영상 🎥](https://youtu.be/Ymns_j6Ag-k)

  ![tuna_keyboard_screens(1010)](https://github.com/user-attachments/assets/4c3ed9c7-45b7-4e1e-a3a8-86de359f0c31)

  좌측에서부터 홈 화면, 테스트 안내, 테스트 화면, 개별 키 커스터마이징 화면 입니다.

  어플리케이션에서는 키보드 셋팅을 위한 설정화면을 제공합니다. 설정은 초기 설정을 위한 ‘테스트’ 와 이후 사용자가 직접 키 레이아웃을 조절할 수 있는 ‘커스터마이징’ 이 있습니다. 또한 키보드는 day / night 테마가 존재합니다.

<br>

# 구현 살펴보기

## [1] 사용자 편의성을 위해

### **1. 상품성을 고려하자**

> 이 프로젝트를 만들기로 했을 때, 실제로 무리 없이 쓸 수 있는 키보드를 만들자는 목표를 세웠습니다.

<br>

1. **‘키보드 같은 것’ 이 아닌 ‘키보드’를 위해: 왜 자바스크립트가 아닌걸까?**

   ‘키보드’ 는 가장 기본적인 ‘입력 방식’ 입니다. 형태가 다양할 수 있지만 어떤 기기든 반드시 지원하는 사용자 인터페이스 입니다. 이를 통제하는 것은 운영체제와 연관이 깊기 때문에 네이티브 언어(java / kotlin)를 사용한 코드 작성이 필수적 입니다.

   일부분 네이티브 언어를 사용해야 한다는 점을 인지한 이후, 실질적 기능을 어디서 구현할지 고민했습니다. 자바스크립트를 공부 했기 때문에 React Native CLI(이하 리액트 네이티브) 를 이용해 키보드를 구현하고 기능을 안드로이드의 `InputMethodService` 를 이용해 연결하자는 계획을 세웠습니다.

   <br>

   여기에는 **3가지 문제점**이 있었습니다.

    - React Native AutoLinking 버그가 실시간으로 발생하다.

      POC 를 진행하던 당시 리액트 네이티브는 0.75.2 버전으로 리액트 네이티브에서 안드로이드 프로젝트를 찾지 못하는 이슈가 있었습니다. 리액트 네이티브와 안드로이드가 연결이 되지 못하는 치명적인 사태가 발생했습니다. 0.75.3 버전에 급하게 패치 되었지만 제가 POC 를 진행하던 기간에는 해결이 되지 못했습니다. ( [이슈로그1](https://github.com/facebook/react-native/issues/46134), [이슈로그2](https://github.com/facebook/react-native/issues/46593) )

    - 키보드는 다양한 언어와 형태의 모드들을 지원해야한다.

      키보드는 각종 언어별, 기본형, 이메일 입력형, ‘이모지’ 와 전화용 번호 키패드 등등… 다양한 형태가 있습니다. 커스텀 키보드는 기존 키보드를 대체하여 사용하게 되기 때문에 사용자가 사용하는 모든 경우에 전부 대응할 수 있어야 합니다. 이 가짓수는 상당했고 목표했던 개발 기간을 고려할때 적절하지 않았습니다.

    - `InputMethodService`가 Deprecated 되다.
      <p align="center">
         <img width="860" alt="errorMessage_deprecated" src="https://github.com/user-attachments/assets/e3caaafd-f993-44b7-b5a3-13c74c9d8282">
      </p>

      이것은 **모든 키를 사용하여 자신 만의보기를** 만들어야한다는 것을 의미하며, **이는 키보드 입력, 삭제 및 전환과 같은 모든 클릭 이벤트를 직접 처리하는** 것을 의미합니다. 종합해보면 ‘운 좋게 빌드가 성공하길 기대하고, 기능은 연결되어 있지만 일부만 호환 가능한, 키보드 같이 생긴 것’ 이 나올 확률이 높았습니다.

    <br>

   사용자가 실제로 쓸 수 있는 프로덕트를 계획한 제 목표와 상반되는 결과물이 예상 되었고, **익숙한 언어를 사용하기 위해 애쓰기 보다, 모든 것을 네이티브 언어를 사용하여 개발하는 것이 적합하다 결론** 내렸습니다.

   <br>

2. **왜 코틀린일까?: 키보드 모듈 선정하기**

   공식 문서에서는 `InputMethodService` 대신 AOSP(안드로이드 오픈소스 프로젝트)를 사용할 것을 권장했습니다. 키보드 자체를 만드는 것이 목표가 아니었기 때문에 오픈소스 키보드 모듈을 찾아보았고 후보로는 FlorisBoard 와 AnySoftKeyboard 가 있었습니다.

   < GitHub 레포지토리 정보 비교 >

   |                      | FlorisBoard | AnySoftKeyboard |
      | -------------------- | ----------- | --------------- |
   | 개발 언어            | Kotlin      | Java            |
   | 최신 버전            | v0.4.0      | v1.11-r1        |
   | 마지막 업데이트      |
   | (작성 시점 기준)     | 3주 전      | 2022.01.06      |
   | (약 1년 9개월 전)    |
   | Stars                | 6K          | 2.9K            |
   | 한글 지원            | ✅          | ❌              |
   | 사용자 개인정보 보호 | ✅          | ✅              |

   < 커뮤니티 비교 > (출처: Redit, F-Droid)

    - AnySoftKeyboard: 메이저 버전을 배포 한 만큼 오래전에 시장에 나와 개발자들 사이에서 익숙함. 준수한 퍼포먼스.
    - FlorisBoard: 베타버전임에도 사용할만한 퍼포먼스. 신생 프로젝트로서 떠오르고 있다는 평.

   결과적으로 **FlorisBoard 를 선택**하였는데 현대적인 퍼포먼스를 보여준다는 평가와 최근까지도 지속적으로 업데이트를 유지하고 있다는 점이 긍정적으로 작용했고, **한글을 지원**한다는 점이 **실사용을 고려했을때** 결정에 큰 도움이 되었습니다. FlorisBoard 가 코틀린 기반이이었기 때문에 자연스럽게 네이티브 언어 중 코틀린을 하게 되었습니다.

<br>

### **2. 안전하게 만들어 보자**

> 키보드를 사용하면서 사용자는 전화번호, 비밀번호, 주민등록번호 등 민감한 정보를 입력하는 경우가 발생하기도 합니다. 이를 고려하여 최대한 안전하게 처리할 수 있는 방법을 고민했습니다.

<br>

**서버가 없는 이유: 딥러닝 모델 선택하기**

> 이 프로젝트에서 서버가 필요하다고 예측되는 유일한 동작은 딥러닝 모델과의 통신이었습니다.

머신러닝 플랫폼 선택시 고려사항

- 필요한 기능 : 추론형 모델
- 제약 사항 : 가급적 데이터 노출이 적은 모델. (온디바이스 > 서버)

키보드를 통해 비밀번호 등 **민감한 정보를 입력했을 때는 어떻게 될까요**? 딥러닝 차곡차곡 쌓은 데이터를 순차적으로 전달하기 때문에 누군가 데이터를 탈취한다면 그대로 **노출될 위험**이 있습니다. 만약 **기기 내에서 이 모든 정보를 처리할 수 있다면** 서버를 이용하는 것 보다 안전할 것입니다.

또한 사용자 별 특화된 입력패턴 학습이 필요하기 때문에 기기별로 모델을 개별적으로 할당할 필요가 있었습니다. 그래서 기본 모델을 탑재하고 스스로 학습할 수 있는 모델을 챙기고 싶었습니다.

만약 휴대용 기기에 탑재되는 부담이 컸다면 서버를 설치하는 것이 적절 했을 것 입니다.
다행히 이런 부분을 충족하는 신뢰할 만한 모델로 **Tensor Flow Lite** 가 있었습니다.

## [2] 핵심 로직 소개

주요 로직

1. 키보드 레이아웃 로직에 간섭하기
2. 터치 좌표 수집하기
3. 터치 좌표로부터 최적화된 레이아웃 계산하기

<br>

### 1. 레이아웃 로직에 간섭해 키 버튼 크기 변경하는 방법

> 키보드 레이아웃이 커스텀 된다는 것은 어떤 의미일까요? 키 하나하나의 크기가 달라질 수 있어야 한다는 것 입니다. 이를 위해 키 레이아웃을 그리는 **로직에 직접적인 간섭**을 하기 위해 모듈화가 아닌 오픈소스 키보드 위에서 직접 작업을 하게 되었습니다.

**시도 1: 키 사각형의 실사값 & 2차 배열 인덱스로 조절하기**

<p align="center">
    <img width=40% alt="key_asset_img" src="https://github.com/user-attachments/assets/485dc164-5cee-44c8-a665-bc3943aba0ca">
</p>
<p align="center"> 키(key)가 가지고 있는 위치 정보 </p>

키 레이아웃 로직은 먼저 '비율 값'으로 계산을 수행합니다. 비율을 통한 조정이 완료된 후, 이 비율값을 실제 수치로 변환하는 과정이 있습니다. 이 단계에서 키는 실제 top, bottom, left, right 값을 갖게 됩니다. 우리는 이 값들을 활용하여 키 버튼 크기를 조정하기로 했습니다.**즉, 모든 레이아웃 로직이 완료된 후, 계산된 실제 값으로 기존 값을 덮어쓰는 방식**을 채택했습니다.

<br>

**<문제상황 발생>**

**⓵ index 기반의 데이터 저장문제: 키보드 모드가 변경되면 레이아웃이 무너진다.**

<p align="center">
    <img width=40% alt="key_layout_collapsed(1)" src="https://github.com/user-attachments/assets/d2bc5b4b-62cc-43e1-bdfa-a8b724a63683">
</p>

사용한 데이터는 2차 배열 형태로, 가장 많이 쓰는 문자 키보드의 34개 키 위치를 담고 있었습니다. 그러나 키보드 모드를 전환하면 키 개수가 변동되어 잘못된 인덱스 값을 참조하게 되었고, 이로 인해 동적인 레이아웃 변화에 전혀 대응하지 못하는 문제가 발생했습니다.

또한 인덱스 값이기 때문에 값을 가져오기 위해선 현재 키의 가로 인덱스, 세로 인덱스 2종의 데이터 값이 항상 필요했습니다. 내부적으로 키는 자신의 인덱스를 기억하지 않기 때문에 인덱스를 찾기위한 반복 탐색을 진행해야 한다는 점이 코드상으로도 비효율적이었습니다.

**⓶ 실사값 고정에 대한 부작용: 이후 가공 동작 및 주변 키의 버튼 영역은 고려되지 않는다.**

<p align="center">
    <img width=40% alt="key_layout_collapsed(2)" src="https://github.com/user-attachments/assets/778c7824-3b72-4e83-9984-7f69bbd17f20">
</p>

top, bottom, left, right 값을 실사값으로 고정시키니, 이후 적용되는 세부 조절 로직이 무시되었습니다. 대표적인 예로, footer에서 버튼을 떨어뜨리는 로직이 적용되지 않아 버튼이 잘리는 현상이 발생했습니다.

또한 키 간 겹침 영역이 생겼습니다. 이를 방지하려면 실사값이 변할 때마다 주변 키 사이즈를 리사이징 하는 로직을 내부적으로 실행해야 하는 비효율이 발생했습니다. 리사이징 로직은 기본으로 사용하는 레이아웃 로직과 흡사한 내용이었습니다. 사실상 같은 로직을 2번씩 도는 거나 다름없는 흐름이었습니다.

  <br>

**시도 2: 비율 가중치 & Map 객체로 조절하기**

<p align="center">
    <img width=60% alt="explain_flayWidthFactor" src="https://github.com/user-attachments/assets/522d99e6-ee69-4ea1-84c6-3b325543b9e4">
</p>

두 번째로 사용할 수 있는 방법은 비율(`flayWidthFactor`)을 활용하는 것입니다. 레이아웃 로직의 초반에 사용하는 비율 값을 역으로 이용하기로 했습니다. **레이아웃 로직이 시작될 때, 새로운 비율값을 계산하여 삽입해주고 이를 바탕으로 이후 레이아웃 연산을 수행하는 방식입니다.** 데이터 저장 방식도 key.code를 키로 갖는 맵 형태로 변경했습니다.

(\* key.code 사용에 대한 부연설명:
자바스크립트의 키 이벤트 코드와 달리, 지원하는 언어에 대해 키 코드가 부여되었습니다.
1부터 65,535까지의 키 코드가 유효하며, shift 등 특수 기능 키의 코드는 음수값을 갖습니다.)

사용하는 비율 값 종류는 총 3가지 입니다.

- `flayWidthFactor`: 각 키의 **실제 너비를 결정하는 요소**로, 키보드 모드와 코드에 따라 값이 달라집니다.
- `flayShrink`: 키보드 모드와 특정 키 코드에 따라 키가 차지하는 공간을 **줄일 수 있는 정도**를 결정합니다.
- `flayGrow`: 키보드 모드와 특정 키 코드에 따라 키가 차지하는 공간을 **확장할 수 있는 정도**를 결정합니다.

`flayWidthFactor` 요소를 이용해 넓이를 조절한 후 `flayShrink` 와 `flayGrow` 값으로 넘치거나 부족한 넓이를 커버할 수 있도록 했습니다. 이런 흐름을 통해 아래와 같은 결과를 얻을 수 있게 됩니다.

<p align="center">
    <img width=40% alt="adjust_key_layout" src="https://github.com/user-attachments/assets/e9bc6024-18bb-4e3c-b7af-b014b0e80843">
</p>
<p align="center"> B 키와 J 키의 너비값이 변경하고 유동적으로 같은 줄에 해당하는 키값 변동을 확인할 수 있습니다. </p>

map 을 통해 원하는 키코드의 값을 O(1) 로 찾을 수 있도록 변경했기 때문에 관련 추가 로직을 작성할때도 보다 탐색 효율이 올라갔으며, 비율값으로 조절을 하기 때문에 기존 오픈소스의 로직과도 무리없이 작동하게 됩니다.

### 2. 사용자에게 맞는 너비 값을 계산 하는 방법 (1) 사용자의 터치 좌표 수집하기

> 오타가 났을 때, 사용자가 상상하며 누른 키보드의 좌표가 있지 않을까? 하는 생각이 들었고, 그 좌표를 모아서 사용해보자는 계획으로 연결 되었습니다.

<p align="center">
    <img width=40% alt="deepLearning_flow" src="https://github.com/user-attachments/assets/b170cef0-df29-49a6-9f3b-e0e5532fc6f9">
</p>
<p align="center"> “사용자가 원래 누르고 싶었던 키를 누를 수 있게 하는 것” 라는 목표에서 거꾸로 추론을 했습니다. </p>

발생한 좌표가 어느 키를 누르려 한 것인지 파악할 수 있다면, 각 키의 최적 위치를 계산할 수 있었습니다. 이를 위해 좌표를 분류하는 데 필요한 요소들은 다음과 같았습니다.

1. 기본형 딥러닝 모델을 학습된 모델로 만들어줄 학습 데이터.
2. 발생한 좌표로부터 유저가 원했던 키를 추론해줄 딥러닝 모델.
3. 비정확한 정보들은 걸러줄 필터링 함수.

<br>

**재료1: 딥러닝에 제공할 기본 학습용 데이터를 수집하기: 테스터 화면을 만들자**

딥러닝 모델을 학습시키기 위해서는 Input(터치 좌표 값)과 Output(결과가 되는 키의 좌표값)이 필요합니다. 딥러닝으로 이를 추론하려는데 값이 필요하다니, 모순처럼 보입니다. 하지만 이는 향후 발전을 위해 꼭 필요한 과정이므로, 사용자에게 통제된 환경을 제공하여 데이터를 수집해야 합니다.

데이터는 충분히 확보하는 것이 이상적이지만, 사용자에게 과도한 테스트는 스트레스와 불편함을 줄 수 있습니다. 따라서 자주 사용하는 "자모음"으로 범위를 좁혀 데이터를 수집하기로 했습니다.

<p align="center">
    <img width=40% alt="test_screen" src="https://github.com/user-attachments/assets/79a6bc4d-3695-4cd5-9fba-c89a9310fef3">
</p>

"The quick brown fox jumps over the lazy dog"는 영문 A부터 Z까지 모든 알파벳을 포함하는 문장입니다. 의미 있는 문장이면서도 모든 문자를 입력할 수 있게 구성되어 있습니다. 이를 통해 미리 정해진 목표 좌표에 대한 실제 터치 좌표를 수집합니다. 이 과정에서 재료3에 해당하는 스크리닝 로직을 적용하여 목표와 지나치게 동떨어진 좌표는 제외합니다.

<br>

**재료2: 추론형 딥러닝 모델을 찾아서**

> TensorFlow Lite에는 다양한 사전 학습 모델이 있습니다. 그러나 키보드와 같은 특수한 좌표에 대한 반환값을 제공하는 형태로 활용할 수 있는 모델은 없었습니다. 따라서 이를 위해 추론형 모델을 직접 만들어야 했습니다.

모델은 주피터 노트북과 유사한 환경을 제공하는 Google Colab에서 파이썬으로 제작되었습니다. 이 모델을 만들 수 있었던 이유는 입력값과 출력값이 일반적인 딥러닝 모델에 비해 매우 단순했기 때문입니다. 목표는 '**시간에 따라 누적되는 데이터**'와 '**다양한 좌표값에 대한 여러 출력**'을 '**예측**'하는 것이었습니다.

시간에 따라 누적되는 데이터는 '시계열 데이터(일정 시간 간격으로 배치된 데이터들)'로 분류됩니다. 이를 처리하기 위해 **장단기 메모리**(Long Short-Term Memory, LSTM)를 사용했습니다. 기본 Tensor Flow 모델에 LSTM 레이어와 여러 겹의 신경망 레이어를 얹어 모델을 구축했습니다. 그리고 이를 Tensor Flow Lite 모델로 변환해 리소스로 파일을 넣어주면 됩니다.

<br>

**재료3: 동떨어진 키값을 필터링 할 유틸함수 만들기**

테스터 화면에서 좌표값을 받거나 아직 충분히 학습되지 않은 딥러닝 모델의 출력값은 함부로 신뢰해서는 안됩니다. 이를 걸러내기 위해 딥러닝 모델을 공부하다 발견한 최근접이웃(KNN) 알고리즘을 사용해서 필터 함수를 만들었습니다. 일정 거리 내로 가깝다면 이웃이고, 그렇지 않다면 이웃이 아니라는 방법입니다.

<p align="center">
    <img width=40% alt="explain_knn" src="https://github.com/user-attachments/assets/bbf316fe-f578-4f6b-9434-84a1d0ed4253">
</p>

기준이 되어줄 일정 거리는 주변 키 1개 만큼의 거리로 상정했습니다. 일반적으로 오타가 나도 의도했던 키의 1칸 이상 떨어진 버튼을 누르진 않기 때문입니다. 예측좌표와 목적 좌표의 거리를 재고, 여기서 너무 동떨어져 있다면 해당 값은 사용하지 않습니다. 거리값은 우리가 피타고라스의 정리에서

필터함수는 2가지 경우에 사용욉니다.

- 테스트 데이터를 통해 학습하는 경우: 딥러닝 모델 통과 ‘전’ 사용. 테스팅 중 실수나 delete 등의 값은 걸러내기 위해 사용합니다.
- 딥러닝 결과 값을 사용하는 경우: 딥러닝 모델의 결과값을 검증하기 위해 통과 ‘후’ 사용. 나온 결과와 좌표값을 수집용 저장소로 옮기기 전 검증을 하기 위해 사용합니다. 딥러닝 모델의 학습도가 충분하지 않을 때 효과적입니다.

<br>

### 3. 사용자에게 맞는 너비 값을 계산 하는 방법 (2) 좌표로부터 넓이 배율 계산하기

딥러닝 모델의 출력값은 필터함수를 거친 후 입력값과 함께 저장됩니다. 데이터는 `(key: key.code, value: 자주 누르는 좌표값)`으로 구성된 map 객체입니다. 자주 누르는 좌표값은 입력되는 좌표값들의 평균으로 계산됩니다. 즉, **특정 키를 가장 자주 누르는 위치**를 저장하게 되는 것입니다.

다음으로, 현재 키 버튼의 중앙값과 자주 누르는 좌표값을 포함하는 최소 넓이를 구합니다.
비례식을 활용하여 수식을 완성했습니다.

```kotlin
현재 키의 넓이 : 현재 키의 넓이 배율 = | (중심점 - 자주누르는 좌표) * 2 | : X
```

X에 해당하는 값이 새로운 넓이 배율(flayWidthFactor)이 됩니다. 이 값은 키보드 레이아웃 로직에 적용되어 사용됩니다.

<br>

### 4. 결과

<p align="center">
    <img width=80% alt="show_before after" src="https://github.com/user-attachments/assets/2de57c53-2a8d-4699-a799-2f426abbf9d8">
</p>
<p align="center"> 개발자의 데이터를 기준으로 한 before & after </p>

터치 좌표를 신뢰할 수 있는 데이터로 변환하는 과정을 거쳐, 이를 기반으로 한 통계 분석을 수행했습니다. 이를 통해 각 버튼의 최적화된 크기를 정밀하게 계산할 수 있었습니다.

이러한 과정의 결과로, 이전에는 주변 키버튼 영역과 혼동될 수 있었던 터치 좌표들이 이제는 사용자가 의도한 키의 정확한 히트박스 영역 내로 안정적으로 인식됩니다. 이는 사용자의 입력 정확도를 크게 향상시키며, 오타 발생 빈도를 현저히 줄이는 효과를 가져왔습니다.

또한, 이 최적화 과정은 사용자의 개인적인 터치 패턴을 지속적으로 학습하여 더욱 정교한 키보드 레이아웃을 제공합니다. 결과적으로, 사용자는 더욱 자연스럽고 편안한 타이핑 경험을 즐길 수 있게 되었습니다.

<br>

## [3] 발생한 어려움

### 1. 모델 탑재 관련 (이슈 해결 후 업데이트 예정)

1. 텐서플로우 라이트 모델 제작 + 탑재시키기 (이슈 해결중\_후에 추가)
2. 수면 밑에서 키보드 좌표 수집하기 (이슈 해결중\_후에 추가)
3. 업데이트 주기 잡기 ( + 모델 학습 어느정도 해야 쓸 만한 수준으로 올라올까)
