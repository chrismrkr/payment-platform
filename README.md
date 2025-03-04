# payment-platform
My Payment Platform Exercise

Implementation of Pay Platform Exercise Example

https://okkkk-aanng.tistory.com/44

## Quick Start
### 1. Build Docker Images
#### 1.1 Build Pay Platform Server Image
- ```git clone https://github.com/chrismrkr/payment-platform.git```
- ```./gradlew clean build -x test```
- ```docker build -t pay-platform-was ./```
#### 1.2 Build Mock PG Server Image
- ```git clone https://github.com/chrismrkr/sturdy-octo-potato.git```
- ```./gradlew clean build -x test```
- ```docker build -t pg-was ./```

### 2. Start

- ```docker compose up```

## How to Demo
- Do [Quick Start]
- Click -> http://localhost/payment



