# (1) base-image
FROM openjdk:11
#ENV G_CLIENT_ID=878122611174-g9hqmo37ags1ie4e1md9ikmstet5dm65.apps.googleusercontent.com \
#    G_CLIENT_SECRET=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 \
#    JWT_SECRET_KEY=kevin1234123412341234123412341234
#ENV G_CLIENT_ID=${{secrets.G_CLIENT_ID}} \
#    G_CLIENT_SECRET=${{secrets.G_CLIENT_SECRET}} \
#    JWT_SECRET_KEY=${{secrets.JWT_SECRET_KEY}}

# (2) COPY에서 사용될 경로 변수
ARG JAR_FILE=build/libs/*-SNAPSHOT.jar

# (3) jar 빌드 파일을 도커 컨테이너로 복사
COPY ${JAR_FILE} app.jar

# (4) jar 파일 실행
ENTRYPOINT ["java","-jar","/app.jar"]