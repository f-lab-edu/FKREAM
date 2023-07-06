# FKREAM

리셀 서비스 ***KREAM*** 을 클론한 프로젝트입니다.

클라이언트는 Kakao Oven을 사용한 프로토타입으로 제작하여 RestAPI 서버 개발에 집중하였습니다.

### 기술 스택,
Java 11, Spring Boot 2.7., Gradle, MyBatis, Redis, Docker, MySql, MongoDB, Elasticsearch, Kibana, Kafka, Jenkins, Naver Cloud Platform, Pinpoint, NGrinder


## 프로젝트 구조

![image](https://github.com/f-lab-edu/FKREAM/assets/76461625/63e17709-873a-4c69-8b93-fbf8ee575bed)

## 프로젝트 목표

- 객체 지향 원리를 토대로 올바른 코드 작성
- 대용량 트래픽 처리를 고려하여 서비스 기능 구현
- 병목 지점 파악 및 성능 개선
- 새로운 기술을 학습하고 프로젝트 적재적소에 적용

## 프로젝트 고도화 정리

- [실시간 검색 순위 - 엘라스틱 서치 - 카프카](https://www.notion.so/5bf9a15f8ba9480581e2714928c30bb0?pvs=21)
- [카프카를 이용한 통계 DB 적용 (2)](https://www.notion.so/DB-2-18906846010f4928b617f9185a6764fa?pvs=21)
- [카프카를 이용한 통계 DB 적용 (1)](https://www.notion.so/DB-1-879431763d794401a0e8e3e478c0765e?pvs=21)
- [배치 작업 - 스프링 배치](https://www.notion.so/242f131e39c74e309645686059b82a50?pvs=21)
- [동시성 문제](https://www.notion.so/7f880ffc975d4fcbbb85fa222caaaf66?pvs=21)
- [db 커넥션 풀 사이즈에 따른 성능 차이](https://www.notion.so/fdae9e0004c94bdf9798d4070346674e?pvs=21)
- [자동완성 기능](https://www.notion.so/41280b6f6c324a4c9c00a01e3abdf38c?pvs=21)
<br/>

- [nosql과 sql의 차이](https://www.notion.so/nosql-ed1d754ad641428dac6c9457597ce58c?pvs=21)
- [db 샤딩](https://www.notion.so/143f7cea2bae46259157f80daea0abbd?pvs=21)
- [db replication](https://www.notion.so/db-replication-181020a542014cfb9330a3e0bee8ab9c?pvs=21)
- [db 실행 계획 분석 → sql 성능 튜닝  → 인덱스 설정(FullText Index)](https://www.notion.so/6272f4b29f4349689d4487e0de9b0ae2?pvs=21)
<br/>

- [ngrinder 시나리오 → 성능 테스트](https://www.notion.so/Ngrinder-b459741f58d94adf881dcf24f11ff756?pvs=21)
- [글로벌 캐시](https://www.notion.so/Cache-69103c0308a44e739b6c546d8ec01e68?pvs=21)
- [로드밸런싱](https://www.notion.so/4faea793a72b4e3da968eae5735722d1?pvs=21)
- [세션 스토리지 분리](https://www.notion.so/Scalability-f3f253c5e2ed41269fa8abf7915d34cd?pvs=21)
- [scale-out](https://www.notion.so/Scalability-f3f253c5e2ed41269fa8abf7915d34cd?pvs=21)
- [CI/CD](https://www.notion.so/CI-CD-0ac39bb70aa04e1eb4fc97c429d2bfce?pvs=21)
<br/>

- [푸시 메세지 비동기 처리](https://www.notion.so/867e717060f14065b21aa5e9603cad90?pvs=21)
- [aop를 이용한 로직 분리](https://www.notion.so/AOP-b0d55411434843168b5c67ef62bfd18a?pvs=21)
<br/>

## 주요기능

[위키](https://github.com/f-lab-edu/FKREAM/wiki/%E2%9A%99%EF%B8%8F-%EA%B8%B0%EB%8A%A5-%EC%A0%95%EC%9D%98) 에서 자세히 확인할 수 있습니다.

## 공통사항

### 코드 컨벤션

- Google code Style 준수

### 브랜치 전략

GitHub Flow를 사용하여 브랜치를 관리합니다.<br>
Main 브랜치로부터 새로운 Feature 브랜치를 생성하고 Pull Request에 코드 리뷰를 진행한 후 Jenkins를 통한 테스트 이후 Main 브랜치로 merge 됩니다.
![img](https://github.com/f-lab-edu/FKREAM/assets/76461625/b6436e5a-b942-4bae-b998-73747cd08841)


## 프로토타입

### Home

<img width="1226" alt="image" src="https://user-images.githubusercontent.com/76461625/225810968-e84d91f1-9715-464f-a8fe-8ace6e44ef82.png">

### 검색

<img width="800" alt="image" src="https://user-images.githubusercontent.com/76461625/225811215-842adc4f-e7e3-46aa-8e14-1161e7e75374.png">

### SHOP

<img width="320" alt="image" src="https://user-images.githubusercontent.com/76461625/225811377-df105c4a-f529-4a82-a6dc-049290253e48.png">

### 상세 페이지

<img width="320" alt="image" src="https://user-images.githubusercontent.com/76461625/225811594-54c5e439-36c2-4589-b608-314401d39022.png">

### 보유 상품 추가

<img width="320" alt="image" src="https://user-images.githubusercontent.com/76461625/225835800-b71a1223-ee12-4202-99b2-3ddbc1756354.png">

### 구매, 배송, 결제

<img width="824" alt="image" src="https://user-images.githubusercontent.com/76461625/225836184-7cda47c8-ffa5-4712-9bf4-22c6106426f7.png">

### 판매, 주문, 정산

<img width="824" alt="image" src="https://user-images.githubusercontent.com/76461625/225836242-3e613d01-c66a-4f84-839c-c2986d4acd70.png">

### MY 페이지

<img width="1200" alt="image" src="https://user-images.githubusercontent.com/76461625/225837291-c90e01ae-30f9-4b1a-9f24-db13945d561d.png">

### 설정 상세

<img width="1200" alt="image" src="https://user-images.githubusercontent.com/76461625/225837363-d7491ae6-919c-4652-93a7-a90833d6e25a.png">

## DB Diagram

![FKREAM Diagram (1)](https://user-images.githubusercontent.com/76461625/226078421-db17002e-883c-4776-9283-9a7437a12d55.png)




