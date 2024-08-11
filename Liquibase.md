# Liquibase

### Dependencies

- liquibase
- mysql
- lombok
- spring-boot-starter-data-jpa

```xml
<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<dependency>
			<groupId>org.liquibase</groupId>
			<artifactId>liquibase-core</artifactId>
		</dependency>

		<dependency>
			<groupId>com.mysql</groupId>
			<artifactId>mysql-connector-j</artifactId>
			<scope>runtime</scope>
		</dependency>
		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<optional>true</optional>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
			<version>3.3.2</version>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>
```

### application.yml - Configuration

```yaml
spring:
  datasource:
    driverClassName: com.mysql.jdbc.Driver
    url: jdbc:mysql://localhost/workshop
    username: root
    password: rootpass
    jpa:
      hibernate.ddl-auto: validate
      generate-ddl: true
      show-sql: true
  liquibase:
    enabled: true
    change-log: classpath:db/changelog/changelog-master.xml

```

### XML files of Liquibase

changelog-master.xml - entry point for liquibase

```xml
<databaseChangeLog
        xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog
             http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.8.xsd">
    <include file="db/changelog/db-init.xml"/>
</databaseChangeLog>
```

db-init.xml - init database change log , creating table. 

```
<databaseChangeLog
        xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog
             http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.8.xsd">
    <changeSet id="1" author="alper">
        <createTable tableName="product">
            <column name="name" type="VARCHAR(10)"/>
        </createTable>
    </changeSet>
</databaseChangeLog>
```

# **Change Types**

Change Types are at the core of changesets and are **unique** to Liquibase. They describe a type of change or action to be executed against a database.

- Change Types are **database independent** and can execute the same changelog for different database vendors.
- Certain Change Types provide automatic rollback of changes:
    - If using a **createTable** Change Type, Liquibase will automatically know to apply the inverse **dropTable** as a rollback action when using the rollback command.

**Liquibase also supports both descriptive Change Types that generate SQL for supported databases and raw SQL.**

**DATABASECHANGELOG** table - tracks each successfully deployed changeset as a **single row** identified by a combination of changeset id, author, and the filename specified in the changelog.

- Liquibase compares the changelog against the tracking table to determine which changesets need to run.
- To avoid database-specific restrictions on key lengths, there is no primary key on the tracking table.

---

**DATABASECHANGELOGLOCK** table - prevents conflicts between concurrent updates. This can happen if multiple developers use the same database instance, or if multiple servers in a cluster **auto-run** Liquibase on startup.

- The table sets the LOCKED column to 1 when an update is currently running.
- Liquibase waits until the lock **releases** before running another update.

# **Global Arguments**

Liquibase commands start with **liquibase** followed by one or more global arguments. They are specified **before** the command:

```
liquibase[global argument][command] [command attribute]
```

**Example of a global argument:**

```
liquibase--changelog-file=dbchangelog.xml[command] [command attribute]
```

In the above example, the **--changelog-file=dbchangelog.xml** global argument references the root changelog file stored in the Liquibase project directory.

---

# **Command Arguments**

Command arguments specify command-specific values and are typically listed **after** the command in the command-line syntax.

**Example of a command-line argument:**

```
liquibase --output-file=mySnapshot.json snapshot --snapshotFormat=json
```

In the above example, we use the command-line value **--snapshotFormat=json**. This tells Liquibase to create the output file in a JSON format.

```xml
<changeSet  id="1"  author="nvoxland">
        <addColumn  tableName="person">
            <column  name="username"  type="varchar(8)"/>
        </addColumn>
    </changeSet>
  <changeSet  id="2"  author="nvoxland">
        <addLookupTable
            existingTableName="person"  existingColumnName="state"
            newTableName="state"  newColumnName="id"  newColumnDataType="char(2)"/>
    </changeSet>
```

```xml
<changeSet  id="1"  author="nvoxland">
 <addColumn  tableName="person">
            <column  name="username"  type="varchar(8)"/>
        </addColumn>
    </changeSet>
```

```xml
 <changeSet  id="2"  author="nvoxland">  
        <sql> 
	      CREATE TABLE Persons (
				    PersonID int,
				    LastName varchar(255),
				    FirstName varchar(255),
				    Address varchar(255),
				    City varchar(255)
				);
        </sql>  
  </changeSet>  
```

![image.png](Liquibase%2081c4a94c24a24947a47d15eb5c96e574/image.png)

### **Changelogs can include a number of components but there are three components that are essential for every Liquibase changelog.**

**They are [  *the changelog header, the changesets, and changeset attributes.* ]**

The correct response is "the changelog header, the changesets, and changeset attributes". The username and password are not changelog components.

1. The author and id are required since more than one person could use the same id value. 2. Id and author combinations only need to be unique in the current file. The id tag does not control the order that the changes are run and changeset attribute requirements apply to ALL changelog formats.

The changelog is a list of changes and users can mix and match changelog formats to update different database environments. The flexibility of Liquibase provides users the ability to tailor which format(s) works best for database deployments.

Keeping Liquibase changelogs in the same repository as your application code allows for your existing version control system to make sure everything remains in sync.

### Rollback Commands

```bash
liquibase history
liquibase --changelog-file=yourchangelog.xml rollback-to-date-sql 20YY-05-01
liquibase --changelog-file=yourchangelog.xml rollback-to-date 20YY-05-01

liquibase tag version1
liquibase --changelog-file=yourchangelog.xml rollback-sql version1
liquibase --changelog-file=yourchangelog.xml rollback  version1
liquibase --changelog-file=yourchangelog.xml rollback-count 3
```

### Diff Commands

```
Find the difference between 2 databases.	

liquibase --outputFile=mydiff.txt --username=<USERNAME> --password=<PASSWORD> --referenceUsername=<USERNAME> --referencePassword=<PASSWORD> diff

liquibase  --changelog-file=file_name.sql  --username=<USERNAME> --password=<PASSWORD> --referenceUsername=<USERNAME> --referencePassword=<PASSWORD> diff-changelog
```

### Snapshot Commands

```bash
The snapshot command will capture the current state of a database. The command is also useful to compare a previous database state to another snapshot, or see changes in the url (target) database.

liquibase snapshot
liquibase --output-file=yourSnapshot.json/xml snapshot --snapshotFormat=json/xml
```

- **update-sql,** snapshot, diff-changelog, and generate-changelog commands do not deploy changes to a database.
- **--log-level** parameter controls the amount of messages generated when executing Liquibase commands.
- **diff command** can be used for several tasks including:
    
    To find missing or unexpected objects in a database.
    
    To compare two databases.
    
    To detect database drift.
    
    To compare a database with a snapshot file.
    
- **history command** will provide a "list" of deployed changesets. The history command will also list the time the database was updated.
- **snapshot command** is useful in capturing the "current" state of the database. This command is also useful to compare a previous database state to another snapshot.

|  The status command. |  Used to list undeployed changesets. |
| --- | --- |
|  The rollback-count command. | Used to revert (undo) a specified number of changesets. |
| The generate-changelog command. | Used to create a changelog file that describes how to re-create the current state of the database. |
| The rollback<tag> command |  Used to revert (undo) changes made to the database based on a specific tag. |
| The history command. |  Used to list deployed changesets. |
|  The diff-changelog command. | Used to create a changelog file to compare two databases. |
| db-doc | Running the db-doc command will generate database documentation in a Javadoc format.  |

![image.png](Liquibase%2081c4a94c24a24947a47d15eb5c96e574/image%201.png)

1. In the CLI run the update-sql command:

```
liquibase --changelog-file=yourchangelog.xml update-sql
```

2. **Inspect** the update-sql command output and correct identified issues in **yourchangelog.xml**.

3. In the CLI run the update command.

```
liquibase --changelog-file=yourchangelog.xml update
```

Verifying

---

```
liquibase history
```

Run the **status --verbose** command to see a list of **undeployed** changesets:

```
liquibase status --verbose
```

---

Run the **diff** command to verify database changes were **applied** to the database, or to see if there are **missing or unexpected changes** detected between two databases:

```
liquibase diff <primary database connection properties> <reference database or snapshot connection properties>
```

[LBF101 Developer Workflow](https://www.youtube.com/watch?v=TiHzsheTFz0)

```
liquibase --changelog-file=dbchangelog.xml db-doc mychangelogDoc
```

---

## Best Practices

```
com/
  example/
    db/
      changelog/
        db-changelog-root.xml
        releases/
          db.changelog-01.00.sql
          db.changelog-01.01.sql
          db.changelog-02.00.xml
    src/
      DatabasePool.java
      AbstractDAO.java
```

```
<?xml version="1.0" encoding="UTF-8"?>
<databaseChangeLog
     xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
     xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
     xmlns:ext="http://www.liquibase.org/xml/ns/dbchangelog-ext"
     xmlns:pro="http://www.liquibase.org/xml/ns/pro"
     xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog
              http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-latest.xsd
              http://www.liquibase.org/xml/ns/dbchangelog-ext http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-ext.xsd
              http://www.liquibase.org/xml/ns/pro http://www.liquibase.org/xml/ns/pro/liquibase-pro-latest.xsd">
<include file="com/example/db/changelog/releases/db.changelog-01.00.sql"/>
    <include file="com/example/db/changelog/releases/db.changelog-01.01.sql"/>
    <include file="com/example/db/changelog/releases/db.changelog-02.00.xml"/>
</databaseChangeLog>
```

Release based loads all files in directory release

```
<?xml version="1.0" encoding="UTF-8"?>
<databaseChangeLog
  xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns:ext="http://www.liquibase.org/xml/ns/dbchangelog-ext"
  xmlns:pro="http://www.liquibase.org/xml/ns/pro"
      xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog
              http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-latest.xsd
           http://www.liquibase.org/xml/ns/dbchangelog-ext http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-ext.xsd
              http://www.liquibase.org/xml/ns/pro http://www.liquibase.org/xml/ns/pro/liquibase-pro-latest.xsd">
<includeAll file="com/example/db/changelog/releases"/>
</databaseChangeLog>
```

|  <changeSet id="1" author="nvoxland" context="test1">
            <comment>Jira-1234</comment>
            <createTable tableName="test_table">
                <column name="id" type="int"/>
            </createTable>
        </changeSet> | - changeSet:
      id:  1
      author:  your.name
      comment: Jira-1234
      changes:
       - createTable:
            tableName:  person
.... |
| --- | --- |
| --liquibase formatted sql
--changeset example:1
--comment: Jira-1234
create table test_table (id int); |  "changeSet": {
                "id": "1",
                "author": "nathan.voxland",
                "comment": "Jira-1234",
                "changes": [
.... |

<aside>
💡 Use different changesets always! Rollback fails or database crashes cause confusion on which data will completed run. Different chagesets prevents the crash caused errors.

</aside>

<aside>
💡 Fix forward by introducing a **new changeset** that addresses the issue safely.  → **TAG**

</aside>

- Consider writing changesets when possible using Liquibase **Change Types** that auto-generate rollback statements. However, be aware that certain Change Types will undo the change but not preserve the data.
- Use the Liquibase **rollback <tag>** in the changeset when you want to override the default rollback approach. The tag can also be used to provide instructions for Change Types that do not have an associated automatic rollback.
- **Validate** your rollback scripts in a development environment early in the process.
- 

Changeset attributes provide changeset uniqueness so users can easily control changes throughout **pipeline deployments**. There are **three attributes** that uniquely identify a changeset: the author, the changelog file name, and the **changeset id**.

Each changeset id needs to be **unique** within a changelog. This allows for the **same author** attribute to be used while avoiding changeset duplication issues in the same file which can occur during editing, source control merges, and other similar operations.

<aside>
💡 Defining the team's changeset ID format early in the Liquibase project will help avoid future changeset identification issues.

</aside>