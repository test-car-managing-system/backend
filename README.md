# 🚀 시험차량 관리 시스템

---
## 🏛 아키텍처 구조도
![Architecture 2 (Confirmed)](https://github.com/test-car-managing-system/backend/assets/72291860/f081d715-e8a4-46f9-861b-991b7b7b226c)

<br>

---
## 🔧 사용 기술
`Java` `Spring Boot` `MySQL` `Docker` `AWS`

`TypeScript` `ReactJS`

<br>

---
## 📦 프로젝트 구조

```bash
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── testcar
│   │   │           └── car
│   │   │               ├── common # 어노테이션, 예외처리 등 공통 유틸
│   │   │               ├── config # 설정 빈
│   │   │               └── domains # 도메인 별 패키지
│   │   │                   └── ** # 각 도메인
│   │   │                       ├── entity # 엔티티
│   │   │                       ├── exception # ErrorCode 정의
│   │   │                       ├── model # DTO, VO 정의
│   │   │                       ├── util # 유틸
│   │   │                       ├── repository # 레포지토리 디렉토리
│   │   │                       │   └── **Repository.java # 레포지토리 클래스
│   │   │                       ├── **Service.java # 서비스 클래스
│   │   │                       └── **Controller.java # 컨트롤러 클래스
│   │   └── resources
│   │       └── db.migration # Flyway를 이용한 DB 마이그레이션 파일
│   └── test
│       └── java
│           └── com
│               └── testcar
│                   └── car
│                       ├── common # 테스트용 엔티티, DTO 생성 팩토리 정의
│                       └── domains # 도메인 별 테스트
│                           └── ** # 각 도메인
│                               ├── entity # 엔티티 테스트
│                               ├── request # 요청 DTO 생성 팩토리 정의
│                               └── **Test.java # 단위 테스트 클래스
```

<br>

---

## 🦚 git branch 전략
```bash
main: release 전용
dev: 개발 서버 배포 전용
feat: 기능 개발
fix: 버그 수정
refactor: 리팩토링
chore: 기타 작업
docs: 문서 작업
test: 테스트 코드 구현
```

- 각 브랜치는 dev 브랜치에서 생성
- 작업 후 dev 브랜치로 PR 후 merge

<br>

---
## 🖋 화면 디자인 설계
🔗 [Figma](https://www.figma.com/file/sBxrjClbNfFWDCmohg1Cux/%EC%9D%B4%EB%85%B8%EB%B2%A0%EC%9D%B4%EC%85%98-%EC%95%84%EC%B9%B4%EB%8D%B0%EB%AF%B8-x-%ED%98%84%EB%8C%80%EC%98%A4%ED%86%A0%EC%97%90%EB%B2%84-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8?type=design&node-id=23%3A3687&mode=design&t=cGuDgMdYVUxJIRzT-1)

![디자인 설계서](https://github.com/test-car-managing-system/backend/assets/72291860/cb9287d8-6a78-44f4-a2d2-06658d7b1f0f)

<br>

---
## 🗃️ 데이터 베이스 설계

### 물리 ERD
![물리 ERD](https://github.com/test-car-managing-system/backend/assets/72291860/b5110847-a5f0-4ca3-a86d-88a80ab9725f)

### 논리 ERD
![논리 ERD](https://github.com/test-car-managing-system/backend/assets/72291860/1b320f1b-f600-4ee3-9ca7-68a364018547)

<br>

---
## 📚 API Docs
![Swagger](https://github.com/test-car-managing-system/backend/assets/72291860/a5351cd3-b387-4c54-9ed8-55f6943e2bce)

Swagger 라이브러리를 이용하여 API Docs를 작성하였습니다.

<br>

---
## 🖥️ 화면 구현

![화면스크린샷1](https://github.com/test-car-managing-system/backend/assets/72291860/d86430f7-be14-414d-ae40-2e0147a8531d)
![화면스크린샷2](https://github.com/test-car-managing-system/backend/assets/72291860/0f4bc788-9709-4d26-bd93-8eae03606f84)
![화면스크린샷3](https://github.com/test-car-managing-system/backend/assets/72291860/0cfe3152-0791-4165-b83b-4bef15331f5e)
![화면스크린샷4](https://github.com/test-car-managing-system/backend/assets/72291860/1308bd35-68ad-490e-bbad-c7dbee1d80ab)

<br>

---
## 📊 테스트 커버리지

![테스트 커버리지](https://github.com/test-car-managing-system/backend/assets/72291860/5b52c997-c16f-4951-9267-ae317951de84)

- Jacoco 를 이용한 테스트 커버리지 측정 결과
- 엔티티 및 서비스 레이어에 대한 단위 테스트 커버리지 100%를 달성하였습니다.
