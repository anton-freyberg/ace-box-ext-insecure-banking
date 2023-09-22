# Building and running with Docker

**Prerequisites:**

* Docker

Build Docker image:

```console
docker build -t insecure-bank .
```

Create and run Docker container:

```console
docker run -p 8080:8080 -d --name insecure-bank insecure-bank
```

You can then access the bank application at <http://localhost:8080/insecure-bank/>

## Making it available to other Docker containers

If you want to make it available to other Docker containers, e.g. for the Docker load generator (see [here](load_generation_docker.md)) a separate Docker network has to be created, as follows:

```console
docker network create insecure-bank-net
```

And the Docker container has to be created and run as follows:

```console
docker run -p 8080:8080 -d --name insecure-bank --network insecure-bank-net insecure-bank
```

## Gathering information about what's happening inside

Investigating the output and logs of the application can then be achieved by issuing these commands (in separate terminals):

```console
docker logs -f insecure-bank
docker exec insecure-bank tail -f /root/target/cargo/configurations/tomcat9x/logs/insecure-bank.log
```
