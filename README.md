# Taximanager

This is my study project. 

This application helps organisations that provide taxi service to do their work faster and efficiently.

## Technology stack

- Spring Core
- Spring Security
- Spring Web flow
- Hibernate
- H2 database
- Liquibase
- JavaServer Faces

## How to deploy and run application

1. Run command `mvn clean install -f deploy/pom.xml -P RunLiquibase` to create database files (_taximanager.mv.db_, _taximanager.trace.db_) in local directory.
2. Change `web/src/main/java/resoures/jpa.properties` file and write in `hibernate.connection.url` link to database file (without `.mv.db`).
For example `jdbc:h2:D:/Java/taximanager/taximanager`
3. Build whole application
4. Copy `web/target/web-1.0-SNAPSHOT.war` to `<Apache Tomcat folder>/webapps` and rename file to `taximanager.war`
5. Run Tomcat and open the application `http://localhost:8080/taximanager/`

__P.S__ - After deployment there are 3 default user in the system with password `1234Qwer`:
- `+38(000)000-00-00` with role Admin
- `+38(011)111-11-11` with role Driver
- `+38(022)222-22-22` with role Phone operator