# Etapa 1: Build (com Maven)
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copiar pom.xml e baixar dependências antes (cache)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar código fonte e buildar
COPY src ./src
RUN mvn clean package -Dmaven.test.skip=true

# Etapa 2: Runtime (imagem menor)
FROM amazoncorretto:21-alpine
WORKDIR /app

# Instalar curl para healthcheck
RUN apk add --no-cache curl

# Copiar o jar do estágio anterior
COPY --from=build /app/target/*.jar app.jar

# Expor porta (ajusta se não for 8080)
EXPOSE 8080

# Rodar aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
