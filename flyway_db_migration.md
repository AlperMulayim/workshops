````` 
<dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-core</artifactId>
</dependency>

`````

````` yml
spring:
  application:
    name: pledgepoolapi
  datasource:
    url: jdbc:postgresql://localhost:5432/pledgepooldb
    username: admin
    password: adminpass
    driver-class-name: org.postgresql.Driver
  flyway:
    enabled: true
    baseline-on-migrate: true
    locations: classpath:db/migration
    schemas: public
    validate-on-migrate: true

`````

Migration scripts need to follow a specific naming convention:

- `V<version>__<description>.sql`

The version is an incremental number (like `1`, `2`, `3`), and the description can be any meaningful name that describes the change (like `create_table_user`).

For example:

- `V1__create_table_user.sql`
- `V2__add_column_to_user_table.sql`
