# Etapa 1: Build
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Runtime com Tomcat
FROM tomcat:10.1-jdk21
WORKDIR /usr/local/tomcat

# Remover aplicações padrão do Tomcat
RUN rm -rf webapps/*

# Copiar o WAR para o Tomcat
COPY --from=build /app/target/devolvefacil.war webapps/ROOT.war

# Variáveis de ambiente
ENV JAVA_OPTS="-Dspring.profiles.active=prod"

EXPOSE 8080
CMD ["catalina.sh", "run"] 