# 使用官方的OpenJDK 1.8镜像作为基础镜像
FROM openjdk:8-jdk-alpine

# 设置工作目录
WORKDIR /app

# 将构建好的JAR文件复制到镜像中
COPY target/user-center-1.0.0.jar /app/user-center-1.0.0.jar

# 暴露应用程序的端口
EXPOSE 8011

# 定义启动命令
CMD ["java", "-XX:+HeapDumpOnOutOfMemoryError", "-XX:HeapDumpPath=/app/heapdump.hprof", "-XX:+UseG1GC","-Xmx512m", "-Xms512m", "-jar", "user-center-1.0.0.jar"]
