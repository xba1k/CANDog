## CANDog

SpringBoot service to pick up CAN messages retransmitted by CANBridge and extract certain values for reporting/monitoring/alerting via DataDog.

## Status

Currently in active development...

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

2022-03-07 21:58:09.252  INFO 1 --- [           main] com.github.xba1k.CANDog.App              : Starting App using Java 11.0.14.1 on 7e79b59dff1a with PID 1 (/app.jar started by root in /)
2022-03-07 21:58:09.256  INFO 1 --- [           main] com.github.xba1k.CANDog.App              : No active profile set, falling back to 1 default profile: "default"
2022-03-07 21:58:09.800  INFO 1 --- [           main] faultConfiguringBeanFactoryPostProcessor : No bean named 'errorChannel' has been explicitly defined. Therefore, a default PublishSubscribeChannel will be created.
2022-03-07 21:58:09.817  INFO 1 --- [           main] faultConfiguringBeanFactoryPostProcessor : No bean named 'integrationHeaderChannelRegistry' has been explicitly defined. Therefore, a default DefaultHeaderChannelRegistry will be created.
2022-03-07 21:58:09.999  INFO 1 --- [           main] trationDelegate$BeanPostProcessorChecker : Bean 'org.springframework.integration.config.IntegrationManagementConfiguration' of type [org.springframework.integration.config.IntegrationManagementConfiguration] is not eligible for getting processed by all BeanPostProcessors (for example: not eligible for auto-proxying)
2022-03-07 21:58:10.010  INFO 1 --- [           main] trationDelegate$BeanPostProcessorChecker : Bean 'integrationChannelResolver' of type [org.springframework.integration.support.channel.BeanFactoryChannelResolver] is not eligible for getting processed by all BeanPostProcessors (for example: not eligible for auto-proxying)
2022-03-07 21:58:10.071  INFO 1 --- [           main] o.s.m.export.datadog.DatadogRegistry     : started collecting metrics every PT1M reporting to https://app.datadoghq.com/api/v1/series?api_key=your_api_key
2022-03-07 21:58:10.446  INFO 1 --- [           main] o.s.i.endpoint.EventDrivenConsumer       : Adding {logging-channel-adapter:_org.springframework.integration.errorLogger} as a subscriber to the 'errorChannel' channel
2022-03-07 21:58:10.446  INFO 1 --- [           main] o.s.i.channel.PublishSubscribeChannel    : Channel 'application.errorChannel' has 1 subscriber(s).
2022-03-07 21:58:10.447  INFO 1 --- [           main] o.s.i.endpoint.EventDrivenConsumer       : started bean '_org.springframework.integration.errorLogger'
2022-03-07 21:58:10.447  INFO 1 --- [           main] o.s.i.endpoint.EventDrivenConsumer       : Adding {service-activator} as a subscriber to the 'processDatagramFlow.channel#0' channel
2022-03-07 21:58:10.447  INFO 1 --- [           main] o.s.integration.channel.DirectChannel    : Channel 'application.processDatagramFlow.channel#0' has 1 subscriber(s).
2022-03-07 21:58:10.447  INFO 1 --- [           main] o.s.i.endpoint.EventDrivenConsumer       : started bean 'processDatagramFlow.org.springframework.integration.config.ConsumerEndpointFactoryBean#0'; defined in: 'class path resource [com/github/xba1k/CANDog/configuration/Beans.class]'; from source: 'bean method processDatagramFlow'
2022-03-07 21:58:10.448  INFO 1 --- [           main] o.s.i.i.u.UnicastReceivingChannelAdapter : started bean 'processDatagramFlow.ip:udp-inbound-channel-adapter#0'; defined in: 'class path resource [com/github/xba1k/CANDog/configuration/Beans.class]'; from source: 'bean method processDatagramFlow'
2022-03-07 21:58:10.460  INFO 1 --- [           main] com.github.xba1k.CANDog.App              : Started App in 1.731 seconds (JVM running for 2.233)
2022-03-07 21:59:01.245  INFO 1 --- [trics-datadog-0] o.s.m.export.datadog.DatadogRegistry     : successfully sent 46 metrics to datadog
2022-03-07 22:00:02.138  INFO 1 --- [trics-datadog-0] o.s.m.export.datadog.DatadogRegistry     : successfully sent 46 metrics to datadog                                                     
2022-03-07 22:01:03.064  INFO 1 --- [trics-datadog-1] o.s.m.export.datadog.DatadogRegistry     : successfully sent 46 metrics to datadog
```
