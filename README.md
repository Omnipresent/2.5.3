This app shows error with plugin `:rabbitmq:1.0.0` and grails `2.5.3`

This app runs fine with `grails dev run-app`

However, with `grails test run-app` it gives the following error:

```shell
| Error log4j:WARN No appenders could be found for logger (org.apache.catalina.core.StandardContext).
| Error log4j:WARN Please initialize the log4j system properly.
| Error log4j:WARN See http://logging.apache.org/log4j/1.2/faq.html#noconfig for more info.
| Server running. Browse to http://localhost:8080/2.5.3
```

If the `:rabbitmq:1.0.0` plugin is removed from `BuildConfig.groovy` then `grails test run-app` runs as expected without errors.