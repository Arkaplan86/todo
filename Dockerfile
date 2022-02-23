FROM openjdk:8
ADD target/docker-todo.jar docker-todo.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "docker-todo.jar"]
