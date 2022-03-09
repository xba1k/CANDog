## CANDog

SpringBoot service to pick up CAN messages retransmitted by [CANBridge](https://github.com/xba1k/CANBridge) and extract certain values for reporting/monitoring/alerting via DataDog.

## Setup

DataDog API/Application keys must be specified via command line parameters, see run.sh and run_dockerized.sh for examples. Service defaults to port 1080, but can be overriden with --listen_port parameter, i.e. 

```
java -jar app.jar --datadog-api-key=${DATADOG_API_KEY} --datadog-app-key=${DATADOG_APP_KEY} --listen_port=8123
```

## Demo

```
~/CANDog$ ./run_dockerized.sh

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v2.6.4)

2022-03-08 04:05:58.985  INFO 1 --- [           main] com.github.xba1k.CANDog.App              : Starting App using Java 11.0.14.1 on 77e659ad034b with PID 1 (/app.jar started by root in /)
2022-03-08 04:05:58.989  INFO 1 --- [           main] com.github.xba1k.CANDog.App              : No active profile set, falling back to 1 default profile: "default"
2022-03-08 04:05:59.768  INFO 1 --- [           main] faultConfiguringBeanFactoryPostProcessor : No bean named 'errorChannel' has been explicitly defined. Therefore, a default PublishSubscribeChannel will be created.
2022-03-08 04:05:59.793  INFO 1 --- [           main] faultConfiguringBeanFactoryPostProcessor : No bean named 'integrationHeaderChannelRegistry' has been explicitly defined. Therefore, a default DefaultHeaderChannelRegistry will be created.
2022-03-08 04:06:00.020  INFO 1 --- [           main] trationDelegate$BeanPostProcessorChecker : Bean 'org.springframework.integration.config.IntegrationManagementConfiguration' of type [org.springframework.integration.config.IntegrationManagementConfiguration] is not eligible for getting processed by all BeanPostProcessors (for example: not eligible for auto-proxying)
2022-03-08 04:06:00.031  INFO 1 --- [           main] trationDelegate$BeanPostProcessorChecker : Bean 'integrationChannelResolver' of type [org.springframework.integration.support.channel.BeanFactoryChannelResolver] is not eligible for getting processed by all BeanPostProcessors (for example: not eligible for auto-proxying)
2022-03-08 04:06:00.140  INFO 1 --- [           main] i.m.c.instrument.push.PushMeterRegistry  : publishing metrics for DatadogMeterRegistry every 1m
2022-03-08 04:06:00.830  INFO 1 --- [           main] o.s.i.endpoint.EventDrivenConsumer       : Adding {logging-channel-adapter:_org.springframework.integration.errorLogger} as a subscriber to the 'errorChannel' channel
2022-03-08 04:06:00.830  INFO 1 --- [           main] o.s.i.channel.PublishSubscribeChannel    : Channel 'application.errorChannel' has 1 subscriber(s).
2022-03-08 04:06:00.831  INFO 1 --- [           main] o.s.i.endpoint.EventDrivenConsumer       : started bean '_org.springframework.integration.errorLogger'
2022-03-08 04:06:00.831  INFO 1 --- [           main] o.s.i.endpoint.EventDrivenConsumer       : Adding {service-activator} as a subscriber to the 'processDatagramFlow.channel#0' channel
2022-03-08 04:06:00.831  INFO 1 --- [           main] o.s.integration.channel.DirectChannel    : Channel 'application.processDatagramFlow.channel#0' has 1 subscriber(s).
2022-03-08 04:06:00.831  INFO 1 --- [           main] o.s.i.endpoint.EventDrivenConsumer       : started bean 'processDatagramFlow.org.springframework.integration.config.ConsumerEndpointFactoryBean#0'; defined in: 'class path resource [com/github/xba1k/CANDog/configuration/Beans.class]'; from source: 'bean method processDatagramFlow'
2022-03-08 04:06:00.832  INFO 1 --- [           main] o.s.i.i.u.UnicastReceivingChannelAdapter : started bean 'processDatagramFlow.ip:udp-inbound-channel-adapter#0'; defined in: 'class path resource [com/github/xba1k/CANDog/configuration/Beans.class]'; from source: 'bean method processDatagramFlow'
2022-03-08 04:06:00.847  INFO 1 --- [           main] com.github.xba1k.CANDog.App              : Started App in 2.434 seconds (JVM running for 3.073)
2022-03-08 04:06:06.865  INFO 1 --- [channel-adapter] o.s.i.h.s.MessagingMethodInvokerHelper   : Overriding default instance of MessageHandlerMethodFactory with provided one.

```
![alt text](https://github.com/xba1k/CANDog/blob/main/datadog.png?raw=true)
