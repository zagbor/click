# Используем официальный образ OpenJDK 17 как базовый
FROM openjdk:17-jdk-slim AS build

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем файл pom.xml и загружаем зависимости
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Копируем исходный код приложения
COPY src ./src

# Собираем приложение
RUN mvn clean package -DskipTests

# Используем более легкий образ для финального контейнера
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем jar-файл из предыдущего этапа
COPY --from=build /app/target/*.jar app.jar

# Открываем порт 8080
EXPOSE 8080

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "app.jar"]
