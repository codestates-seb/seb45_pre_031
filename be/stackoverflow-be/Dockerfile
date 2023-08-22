# (1) base-image
FROM openjdk:11

ARG G_CLIENT_ID \
    G_CLIENT_SECRET \
    JWT_SECRET_KEY \
    MYSQL_ID \
    MYSQL_PASSWORD \
    ADMIN_EMAIL

ENV G_CLIENT_ID=${G_CLIENT_ID} \
    G_CLIENT_SECRET=${G_CLIENT_SECRET} \
    JWT_SECRET_KEY=${JWT_SECRET_KEY} \
    MYSQL_ID=${MYSQL_ID} \
    MYSQL_PASSWORD=${MYSQL_PASSWORD} \
    ADMIN_EMAIL=${ADMIN_EMAIL}

# (2) COPY에서 사용될 경로 변수
ARG JAR_FILE=build/libs/*-SNAPSHOT.jar

# (3) jar 빌드 파일을 도커 컨테이너로 복사
COPY ${JAR_FILE} app.jar

# (4) jar 파일 실행
ENTRYPOINT ["java","-jar","/app.jar"]
