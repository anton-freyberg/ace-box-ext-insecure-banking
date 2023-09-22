# Generating attack load with Docker

**Prerequisites:**

* Docker

**Note:** the load generator defaults to <http://insecure-bank:8080/insecure-bank/> as running Insecure Bank instance and will generate load between 22:00 and 7:00 UTC per default.

Build loadgen Docker image:

```console
docker build -t insecure-bank-loadgen -f Dockerfile.loadgen .
```

Create and run Docker container (requires Docker network, as described [here](building_running_docker.md)):

```console
docker run -d --name insecure-bank-loadgen --network insecure-bank-net insecure-bank-loadgen
```

You can also send the load to another endpoint via the `INSECURE_BANK_ENDPOINT` environment variable, e.g. as follows:

```console
docker run -d --name insecure-bank-loadgen --env INSECURE_BANK_ENDPOINT=http://localhost:8080 insecure-bank-loadgen
```

You can also change the timeframe of sending load via the `LOADGEN_UTC_START_HOUR` and `LOADGEN_UTC_END_HOUR` environment variables, e.g. as follows:

```console
docker run -d --name insecure-bank-loadgen --network insecure-bank-net --env LOADGEN_UTC_START_HOUR=9 --env LOADGEN_UTC_END_HOUR=18 insecure-bank-loadgen
```

## Gathering information about what's happening inside

Investigating the output and logs of the load generator can then be achieved by issuing this command:

```console
docker logs -f insecure-bank-loadgen
```
