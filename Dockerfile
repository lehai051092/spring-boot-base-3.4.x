# Stage 1: Build
FROM maven:3.8.5-openjdk-21 AS builder

# Thiết lập thư mục làm việc
WORKDIR /app

# Sao chép file cấu hình và cài đặt dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Sao chép mã nguồn và build ứng dụng
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run
FROM openjdk:21-jdk-slim

# Thiết lập thư mục làm việc
WORKDIR /app

# Sao chép file JAR từ stage build
COPY --from=builder /app/target/baseproject.jar baseproject.jar

# Expose cổng ứng dụng
EXPOSE 8080

# Chạy ứng dụng
ENTRYPOINT ["java", "-jar", "baseproject.jar"]