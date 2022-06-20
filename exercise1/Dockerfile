FROM maven:3.8-openjdk-17-slim AS prepare
WORKDIR /workspace
COPY . .

FROM prepare AS test
RUN mvn test

FROM test AS build
ARG version=1.0.0
RUN mvn -DskipTests -Drevision=${version} install

FROM openjdk:17
ARG version=1.0.0
WORKDIR /workspace
COPY --from=build /workspace/exercise1-app/target/exercise1-app-${version}-jar-with-dependencies.jar ./exercise1-app.jar
COPY exercise1-app/src/main/resources/logging.properties ./
RUN chown -R 1000:1000 /workspace
USER 1000

CMD ["sh", "-c", "java -jar exercise1-app.jar"]
