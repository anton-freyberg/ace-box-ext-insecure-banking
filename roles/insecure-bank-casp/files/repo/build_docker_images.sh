#!/bin/bash

docker build -t insecure-bank .
docker build -t insecure-bank-loadgen -f Dockerfile.loadgen .
docker build -t insecure-bank-log4shell -f Dockerfile.log4shell .
