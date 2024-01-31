# Sử dụng image cơ sở chứa Java 11
FROM adoptopenjdk:11-jdk-hotspot

# Thiết lập thư mục làm việc
WORKDIR /app

# Sao chép các tệp cần thiết
COPY build.gradle .
COPY gradlew .
COPY gradle gradle

# Tải dependencies
RUN ./gradlew dependencies

# Sao chép mã nguồn
COPY src src

# Xây dựng ứng dụng
RUN ./gradlew build

# Mở cổng 8080 để ứng dụng có thể truy cập được
EXPOSE 8080

# Tìm tên file JAR và chạy ứng dụng Spring Boot khi container được khởi chạy
CMD ["sh", "-c", "java -jar $(find build/libs -name '*.jar' -print -quit)"]