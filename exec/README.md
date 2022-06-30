# 💻 With SSAFY 💻

 🚩 모든 싸피인들의 편리한 올인원패키지 서비스, 다양한 익명 커뮤니티와 한번에 확인할 수 있는 커리큘럼 및 스터디와 팀빌딩까지 다른 플랫폼을 사용하지 않고 편리하게 서비스를 이용할 수 있는 애플리케이션

🔗 [위드싸피 사이트](https://k6d201.p.ssafy.io/)

## 빌드 및 배포

### 🔎 개발환경

    💡 Infra
    - AWS EC2
    - Ubuntu 20.04
    - nginx 1.18.0
    - Jenkins 2.289.2

    💡 Frontend - Android Native
    - Android Studio Bumblebee | 2021.1.1 Patch 3
    - MVVM Pattern
    - Android SDK MIN 26
    - Android SDK Target 30
    - FCM

    💡 Backend - Springboot
    - Spring boot 2.4.5
    - Lombok
    - JPA
    - QueryDSL
    - Gradle 7.4.1
    - JAVA 8


### 🔎 Frontend [Android APK 설치 방법] [참고](https://sbnet.co.kr/install-apk-on-android/)

    1. D201 배포사이트에서 APK파일을 다운로드합니다.

    2. 다운로드 받은 APK 파일을 사용자 휴대폰에 업로드합니다.
    - 기종에 따라 방식이 다를 수 있습니다. 

    3. 휴대폰에서 APK 파일을 찾아 클릭 후 설치를 진행해주세요.


### 🔎 Frontend [Android 실행 방법 및 앱 빌드] [설치경로](https://developer.android.com/studio?gclid=Cj0KCQjw1a6EBhC0ARIsAOiTkrHs2pne0fbirqMfuaqgSYhktBtCr_y7qyEZ9YptQ6pHlX8BuYxiIAEaAmIIEALw_wcB&gclsrc=aw.ds)

    1. Android Studio를 설치합니다.

    ⁂ 다운로드 시, Android Studio Bumblebee 2021.1.1 Patch 3 Windows 버전을 권장드립니다.
    
    2. Android Studio를 실행 후, File > Open에서 Client 프로젝트를 선택한 후 'OK'를 클릭해주세요.
    
    3.Local 서버인 경우에는 접속 서버 주소를 변경해주세요.
    - app > java > com.ssafy.withssafy > config > ApplicationClass.kt 경로로 이동하여 SERVER_URL 값을 본인의 IP주소로 변경해주세요.
    ⁂ 단, 접속하려는 휴대폰과 실행하려는 프로젝트의 IP주소가 동일해야합니다.

    4. Android Version 8.0, API Level 28 이상인 기종 연결

    5. 실행할 휴대폰을 선택한 후 RUN을 클릭해 빌드해주세요

### 🔎 휴대폰 연결 방법

    1. 에뮬레이터 연결 방식
    
    - 안드로이드 스튜디오 우측 상단의 AVD Manager를 클릭합니다
    - 좌측 하단 Create Virtual Device를 클릭합니다.
    - Phone 메뉴에서 자신이 원하는 기종을 하나 선택한 후, NEXT를 합니다
    - 설치하고자 하는 API Level을 선택한 후, NEXT를 합니다.
    - Device 이름을 설정한 후, Finish하면 AVD가 생성됩니다.
    - 생성된 에뮬레이터를 실행한 후, 사용하시면 됩니다.

    2. 안드로이드폰 연결 방식

    - USB 케이블을 이용해 휴대폰과 컴퓨터를 연결합니다.
    - 핸드폰 내 설정 > 휴대전화 정보 > 소프트웨어 정보 > 빌드번호를 7번 탭합니다.
    - 핸드폰 내 설정 > 휴대전화 정보 밑의 개발자 옵션에서 USB 디버깅을 허용합니다.
    - 빌드 후, 앱을 실행해 사용하시면 됩니다.


### 🔎 [E-R Diagram](./withssafyerd.png)

### 🔎 프로젝트 내 외부서비스 정보

### 🔎 [DataBase 덤프 파일 최신본](./Dump20220517.sql)

### 🔎 [시연시나리오](./사용자시나리오.pdf)

    


