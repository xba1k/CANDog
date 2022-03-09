#!/bin/bash

# Assume the keys are set as environment variables first, then default
DATADOG_API_KEY=${DATADOG_API_KEY:-your_api_key}
DATADOG_APP_KEY=${DATADOG_APP_KEY:-your_app_key}

./gradlew bootRun -Pargs=--datadog-app-key=${DATADOG_APP_KEY},--datadog-api-key=${DATADOG_API_KEY}
