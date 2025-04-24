# Build stage
FROM maven:3.8.6 AS build
WORKDIR /app

# 设置 Maven 镜像源为阿里云
RUN mkdir -p /root/.m2 && \
    echo '<?xml version="1.0" encoding="UTF-8"?>' > /root/.m2/settings.xml && \
    echo '<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"' >> /root/.m2/settings.xml && \
    echo '          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"' >> /root/.m2/settings.xml && \
    echo '          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 https://maven.apache.org/xsd/settings-1.0.0.xsd">' >> /root/.m2/settings.xml && \
    echo '    <mirrors>' >> /root/.m2/settings.xml && \
    echo '        <mirror>' >> /root/.m2/settings.xml && \
    echo '            <id>aliyunmaven</id>' >> /root/.m2/settings.xml && \
    echo '            <mirrorOf>*</mirrorOf>' >> /root/.m2/settings.xml && \
    echo '            <name>阿里云公共仓库</name>' >> /root/.m2/settings.xml && \
    echo '            <url>https://maven.aliyun.com/repository/public</url>' >> /root/.m2/settings.xml && \
    echo '        </mirror>' >> /root/.m2/settings.xml && \
    echo '    </mirrors>' >> /root/.m2/settings.xml && \
    echo '</settings>' >> /root/.m2/settings.xml

# 只复制 pom.xml 文件，这一步会缓存依赖
COPY pom.xml .
# 下载依赖到本地缓存
RUN mvn dependency:go-offline

# 复制源代码
COPY src ./src
# 构建应用
RUN mvn clean package -DskipTests

# Run stage
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/SmartAuthority-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"] 