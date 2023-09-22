# Generating attack load with Python

**Prerequisites:**

* Python 3 or higher

First-time setup of load generator:

```console
cd loadgen
./setup.sh
```

**Note:** the load generator defaults to <http://insecure-bank:8080/insecure-bank/> as running Insecure Bank instance and will generate load between 22:00 and 7:00 UTC per default.

Run load generator ([insecure-bank-loadgen.py](../loadgen/insecure-bank-loadgen.py)):

```console
cd loadgen
./run.sh
```

You can also send the load to another endpoint via the `--endpoint` parameter, e.g. as follows:

```console
cd loadgen
./run.sh --endpoint http://localhost:8080
```

You can also change the timeframe of sending load via the `--utc_start_hour` and `--utc_end_hour` parameter, e.g. as follows:

```console
cd loadgen
./run.sh --utc_start_hour 9 --utc_end_hour 18
```
