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
│   │   │               ├── infra # 외부 api 연동 서비스
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
## 📜 요구사항 정의서
![요구사항 정의서](https://github.com/test-car-managing-system/backend/assets/72291860/81e086ad-66fe-4065-9ba5-7fe1f2f1196e)

<br>

---
## 🖋 화면 디자인 설계
🔗 [Figma](https://www.figma.com/file/sBxrjClbNfFWDCmohg1Cux/%EC%9D%B4%EB%85%B8%EB%B2%A0%EC%9D%B4%EC%85%98-%EC%95%84%EC%B9%B4%EB%8D%B0%EB%AF%B8-x-%ED%98%84%EB%8C%80%EC%98%A4%ED%86%A0%EC%97%90%EB%B2%84-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8?type=design&node-id=23%3A3687&mode=design&t=cGuDgMdYVUxJIRzT-1)

![디자인 설계서](https://github.com/test-car-managing-system/backend/assets/72291860/cb9287d8-6a78-44f4-a2d2-06658d7b1f0f)
![대시보드](https://github.com/test-car-managing-system/backend/assets/72291860/6f4b180d-7c24-46c7-97d9-6eb127810492)
![시험차량 상세](https://github.com/test-car-managing-system/backend/assets/72291860/4ce9a28f-aeb9-468a-96f5-49ed3f5765b7)

<br>

---
## 🗃️ 데이터 베이스 설계

### 테이블 정의서
![테이블 정의서](https://github.com/test-car-managing-system/backend/assets/72291860/853b46e4-d58c-4af7-ae17-eac41e0e9d1a)

### 인덱스 정의서
![인덱스 정의서](https://github.com/test-car-managing-system/backend/assets/72291860/00c05fec-5cdb-4685-aeef-2c79e9a37cc1)

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

- ### 대시보드
  
![1](https://github.com/test-car-managing-system/backend/assets/72291860/361cb83e-134e-4f73-9a6c-f968dd23fbf6)

- ### 시험차량 대여
  
![2](https://github.com/test-car-managing-system/backend/assets/72291860/1ff91bb3-8429-4f22-ba9d-928506114217)

- ### 대여 이력

![3](https://github.com/test-car-managing-system/backend/assets/72291860/c27c8d92-a594-4f9a-85c2-e2100193b256)

- ### 시험 수행 이력
  
![4](https://github.com/test-car-managing-system/backend/assets/72291860/89565fbb-2da4-4e92-8a79-076d7fdf57b7)

- ### 차량 관리
  
![5](https://github.com/test-car-managing-system/backend/assets/72291860/ac40364e-91e1-4b4f-bb3c-7412f0fb7e88)

- ### 차량 등록
  
![6](https://github.com/test-car-managing-system/backend/assets/72291860/d3314ffc-0702-48eb-8d39-b33dd85c3962)

- ### 재고 관리

![7](https://github.com/test-car-managing-system/backend/assets/72291860/1d784b49-3d7a-4354-aab8-d655af329bc7)

- ### 시험장 예약
  
![8](https://github.com/test-car-managing-system/backend/assets/72291860/6b341f4e-e9c4-4f3f-8ec8-469047d4ef91)

- ### 시험장 예약 이력
  
![9](https://github.com/test-car-managing-system/backend/assets/72291860/e1a27313-af2c-4ef9-b292-f581f30c0546)

- ### 시험장 관리

![10](https://github.com/test-car-managing-system/backend/assets/72291860/26cc3dfc-b195-4548-9a08-17b629aa051a)

- ### 주유소 관리

![11](https://github.com/test-car-managing-system/backend/assets/72291860/122e2f2e-1d19-4346-8c6d-d736bf57d969)

- ### 주유 이력 관리

![12](https://github.com/test-car-managing-system/backend/assets/72291860/4f94f3f6-2c04-4dfb-9812-47cdd98f79c9)

- ### 지출 내역 관리

![13](https://github.com/test-car-managing-system/backend/assets/72291860/934845d6-c496-4702-8eb2-20e1143c984b)

- ### 사용자 조회

![14](https://github.com/test-car-managing-system/backend/assets/72291860/276f4b39-c49a-4e80-8849-3e3d050c9b39)

<br>

---
## 📊 테스트

### 통합 테스트 시나리오
![통합 테스트 시나리오](https://github.com/test-car-managing-system/backend/assets/72291860/cc2ce4b3-723f-44cc-80ba-213a66039838)

### 단위 테스트
![테스트 커버리지](https://github.com/test-car-managing-system/backend/assets/72291860/5b52c997-c16f-4951-9267-ae317951de84)

- Jacoco 를 이용한 테스트 커버리지 측정 결과
- 엔티티 및 서비스 레이어에 대한 단위 테스트 커버리지 100%를 달성하였습니다.
