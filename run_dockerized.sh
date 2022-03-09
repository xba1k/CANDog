#!/bin/bash

# Assume the keys are set as environment variables first, then default
DATADOG_API_KEY=${DATADOG_API_KEY:-your_api_key}
DATADOG_APP_KEY=${DATADOG_APP_KEY:-your_app_key}

# use -p 1080:1080/udp or 
# port forwarding will obscure the source of the datagrams, thus host networking is preferred

docker run --network host -it --rm -e DATADOG_API_KEY -e DATADOG_APP_KEY xba1k/candog:latest
