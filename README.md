# Better To Do App Back end

This is the back end to manage meeting and to do.

## Dependencies

* Start Postgresql

```shell
docker run -d --ulimit memlock=-1:-1 -it --rm=true --memory-swappiness=0 --name pgdb -e POSTGRES_USER=pguser -e POSTGRES_PASSWORD=passw0rd -e POSTGRES_DB=bettertodo -p 5432:5432 postgres:10.5
```

or use `docker compose up -d` to start also a pgadmin client, then point to http://localhost:8082/ and use admin@mail.com as user.

* To deploy postgres on OCP

```shell
oc create serviceaccount postgres-sa
oc adm policy add-scc-to-user anyuid -n jbsandbox -z postgres-sa
```

The oc adm command is required only if targeting an OpenShift cluster).

Deploy the postgres container and service:

```shell
oc apply -f src/main/kubernetes/secret.yaml
oc apply -f src/main/kubernetes/deployment-postgres.yaml 
```

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw quarkus:dev
```

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `code-with-quarkus-1.0.0-SNAPSHOT-runner.jar` file in the `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application is now runnable using `java -jar target/code-with-quarkus-1.0.0-SNAPSHOT-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/code-with-quarkus-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.html.

# RESTEasy JAX-RS

<p>A Hello World RESTEasy resource</p>

Guide: https://quarkus.io/guides/rest-json
