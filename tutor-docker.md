### DOCKER NOTES 

`````
docker-compose build
docker-compose up

``````

docker-compse.yaml
``````yaml
services:
  service-backend:
    container_name: tutor-scheduling
    build:
      dockerfile: Dockerfile
      context: .
    ports:
      - "8088:8080"
    networks:
      backend:
        aliases:
          - "service-backend"
networks:
  backend:
    driver: bridge
``````

Dockerfile

``````
FROM openjdk:17
COPY /target/tutor-scheduling-0.0.1-SNAPSHOT.jar /tutor-scheduling-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/tutor-scheduling-0.0.1-SNAPSHOT.jar"
