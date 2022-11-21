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

```````

## Spring Boot - MySQL Create Container Together
### Spring Boot Dockerfile 

``````
FROM openjdk:17
COPY target/couponear-0.0.1-SNAPSHOT.jar  /couponear-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/couponear-0.0.1-SNAPSHOT.jar"]
``````
### MySQL Database Dockerfile

``````
FROM mysql
COPY /db_couponer/db_create.sql /docker-entrypoint-initdb.d/schema.sql
``````
### docker-compose 
``````
version: '3.9'

services:
  service-backend:
    restart: on-failure
    container_name: "couponearapp"
    build:
      dockerfile: Dockerfile
      context: .
    ports:
      - "8080:8080"
    environment:
      spring.datasource.url: "jdbc:mysql://mysqldb:3306/couponeardockerdb?allowPublicKeyRetrieval=true&autoReconnect=true&useSSL=false" 
    depends_on:
      - mysqldb 

  mysqldb:
    stdin_open: true
    container_name: "couponearsql"
    image: mysql
    ports: 
      - "3306:3306"
    volumes:
      - ./db/:/data/db
    
    build:
      dockerfile: DBDockerfile
      context: .

    environment:
      - MYSQL_ROOT_PASSWORD=rootpass
      - MYSQL_DATABASE=couponeardockerdb
      - MYSQL_PASSWORD=rootpass
``````
