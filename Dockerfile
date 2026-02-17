FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copier les fichiers Maven
COPY pom.xml .
COPY src ./src

# Construire le jar de l'application (sans exécuter les tests pour accélérer)
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app

# Copier le jar généré depuis l'étape de build
COPY --from=build /app/target/clubgit-backend-0.0.1-SNAPSHOT.jar app.jar

# Back4App fournit généralement la variable d'environnement PORT
ENV PORT=8080
EXPOSE 8080

# D démarrer l'application en utilisant le port fourni (ou 8080 par défaut)
ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT}"]

