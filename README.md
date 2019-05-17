## 98Point6 Drop Token Java shim ##
This is provided as a possible starting point for a Java implementation. This code, based on [Dropwizard](http://www.dropwizard.io/1.1.0/docs/), requires maven and Java 1.8.

## Setup H2 DB

Creates an H2 DB populated with the migration scripts in src/main/resources/db/migration

```
gradle flywayMigrate -i
```

## Run

```
./go
```

## Test

```
gradle test --info
```
