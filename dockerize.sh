#!/bin/bash

./build.sh

docker build --build-arg JAR_FILE=app/build/libs/app.jar -t xba1k/candog .
