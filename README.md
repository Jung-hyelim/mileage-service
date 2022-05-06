# mileage-service

## 프로젝트 실행 방법
`프로젝트 개발 환경 : macOS, Docker Desktop, IntelliJ, java 11` 
1. git repository 를 내려받는다.
   > git clone https://github.com/Jung-hyelim/mileage-service.git
1. terminal 창에서 내려받은 repository 경로로 이동한다.
1. docker 실행 후 docker-compose 파일을 이용하여 mysql 을 실행한다.
   > docker-compose up -d
1. IDE (IntelliJ) 에서 run application 으로 실행한다.
1. api 호출 예제는 api.http 파일에서 확인할 수 있다. 

## 프로젝트 환경
- spring boot 2.6.7
- java 11
- mysql 5.7
- jpa

## API
1. 포인트 적립
   > POST http://localhost:8080/events  
   > 또는  
   > POST http://localhost:8080/v1/mileage/review  
   > request body  
   > {  
   > "type": "REVIEW",  
   > "action": "ADD",  /* "MOD", "DELETE" */  
   > "reviewId": "review_id",  
   > "content": "좋아요!",  
   > "attachedPhotoIds": ["photo_id_1", "photo_id_1"],  
   > "userId": "user_id",  
   > "placeId": "place_id"  
   > }  
1. 사용자의 총 포인트 조회
    > GET http://localhost:8080/v1/mileage/{user_id}