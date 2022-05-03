FROM adoptopenjdk/maven-openjdk11:latest AS mvnBuild

WORKDIR /overledger
COPY . .

RUN mvn clean install

FROM adoptopenjdk/openjdk11:jre-11.0.6_10-alpine

WORKDIR /overledger
COPY  --from=mvnBuild /overledger/target/quant-task.jar ./OverLedger.jar

ENTRYPOINT ["java","-jar","OverLedger.jar"]