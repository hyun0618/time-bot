# Java 21을 기반으로 하는 이미지 사용
FROM eclipse-temurin:21-jdk

# 작업 디렉토리 설정
WORKDIR /app

# 전체 프로젝트 복사
COPY . .

# gradlew 실행 권한 부여
RUN chmod +x ./gradlew

# 프로젝트 빌드
RUN ./gradlew build

EXPOSE 8080

# 앱 실행 (jar 파일 이름 정확히 지정!)
CMD ["java", "-Duser.timezone=Asia/Seoul", "-jar", "build/libs/time-bot-0.0.1-SNAPSHOT.jar"]
