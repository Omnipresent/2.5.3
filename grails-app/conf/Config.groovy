import javax.naming.Context
import javax.naming.InitialContext

// import org.springframework.cloud.CloudFactory
// import org.springframework.cloud.CloudException

// def cloud = null
// def env = System.getenv()
// try {
    // cloud = new CloudFactory().cloud
// } catch (CloudException) {}
def ENV_NAME = "MY_CONFIG"
if(!grails.config.locations || !(grails.config.locations instanceof List)) {
    grails.config.locations = []
}

// this loads your tomcat host config file
try {
    def exConfig = ((Context)(new InitialContext().lookup("java:comp/env"))).lookup("grailsExtConfFile")
    println "Including configuration file specified in tomcat context: " + exConfig
    grails.config.locations << exConfig
} catch (Exception e) {
    println("External configuration lookup failed: " + e.getMessage())
}

println "Trying to find external config file"
if(System.getProperty(ENV_NAME)) {
    println "Including configuration file specified on command line: " + System.getProperty(ENV_NAME);
    grails.config.locations << "file:" + System.getProperty(ENV_NAME)
} else if(System.getenv(ENV_NAME)) {
    println "Including configuration file specified in environment: " + System.getenv(ENV_NAME);
    grails.config.locations << "file:" + System.getenv(ENV_NAME)
} else {
    println "No external configuration file defined."
}
print "Done finding external config file"

// locations to search for config files that get merged into the main config;
// config files can be ConfigSlurper scripts, Java properties files, or classes
// in the classpath in ConfigSlurper format

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }
// Added by the Spring Security Core plugin:


grails.plugin.springsecurity.userLookup.userDomainClassName = 'com.something.scanner.security.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'com.something.scanner.security.UserRole'
grails.plugin.springsecurity.authority.className = 'com.something.scanner.security.Role'
grails.plugin.springsecurity.password.algorithm = 'bcrypt'
grails.plugin.springsecurity.securityConfigType = 'Annotation'
grails.plugin.springsecurity.apf.storeLastUsername = true
grails.plugin.springsecurity.ui.password.minLength = 8
grails.plugin.springsecurity.ui.password.maxLength = 64
//grails.plugin.springsecurity.secureChannel.definition = [ '/**': 'REQUIRES_SECURE_CHANNEL' ]
//grails.plugin.springsecurity.secureChannel.useHeaderCheckChannelSecurity = true
grails.plugin.springsecurity.successHandler.defaultTargetUrl = "/request/create"
grails.plugin.springsecurity.successHandler.alwaysUseDefault = true

grails.plugin.springsecurity.controllerAnnotations.staticRules = [
        '/':                          ['permitAll'],
        '/index':                     ['permitAll'],
        '/index.gsp':                 ['permitAll'],
        '/assets/**':                 ['permitAll'],
        '/**/js/**':                  ['permitAll'],
        '/simple/**':                 ['permitAll'],
        '/contact/**':                 ['permitAll'],
        '/**/css/**':                 ['permitAll'],
        '/**/bootstrap.css.map/**':   ['permitAll'],
        '/**/images/**':              ['permitAll'],
        '/**/favicon.ico':            ['permitAll'],
        '/request/**':                  ['permitAll'],
        '/register/**':                     ['permitAll'],
        '/cq5/**':                      ['permitAll'],
        '/evernote/**':                 ['permitAll']
]

grails.plugins.twitterbootstrap.fixtaglib = true
grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = true
grails.mime.types = [
    all:           '*/*',
    atom:          'application/atom+xml',
    css:           'text/css',
    csv:           'text/csv',
    form:          'application/x-www-form-urlencoded',
    html:          ['text/html','application/xhtml+xml'],
    js:            'text/javascript',
    json:          ['application/json', 'text/json'],
    multipartForm: 'multipart/form-data',
    rss:           'application/rss+xml',
    text:          'text/plain',
    xml:           ['text/xml', 'application/xml']
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = ['/images/*', '/css/*', '/js/*', '/plugins/*']

// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = ['com.aerstone.scanner']
// whether to disable processing of multi part requests
grails.web.disable.multipart=false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false


def catalinaBase = System.properties.getProperty('catalina.base')
if (!catalinaBase) catalinaBase = '.'   // just in case
def logDirectory = "${catalinaBase}/logs"

// default for all environments
log4j = { root ->
    appenders {
    }

    error   'org.codehaus.groovy.grails.web.servlet',  //  controllers
            'org.codehaus.groovy.grails.web.pages', //  GSP
            'org.codehaus.groovy.grails.web.sitemesh', //  layouts
            'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
            'org.codehaus.groovy.grails.web.mapping', // URL mapping
            'org.codehaus.groovy.grails.commons', // core / classloading
            'org.codehaus.groovy.grails.plugins', // plugins
            'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
            'org.springframework', 'org.hibernate'

    debug   'com.something.ldap', 'com.something.scanner.helper', 'grails.app.services.com.something.scanner', 'com.something.scanner'

    root.level = org.apache.log4j.Level.INFO
}



environments {
    development {
        grails.logging.jul.usebridge = true
        grails.serverURL = "http://localhost:8080/2.5.3"
        rabbitmq {
            connectionfactory {
                username = "guest"
                password = "guest"
                hostname = 'localhost'
                port = '15672'
            }
            queues = {
                exchange name: 'amq.direct', type: direct, durable: true, autoDelete: false, {
                    myrequestqueue durable: true  //, concurrentConsumers:10
                    mybatchqueue durable: true
                }
            }
        }
    }
    test {
        grails.logging.jul.usebridge = true
        version = 1.1
        grails.serverURL = "http://localhost:8080/2.5.3"
        log4j = { root ->
            appenders {
               console name: 'stdout', layout: pattern(conversionPattern: "%d [%t] %-5p %c %x - %m%n")
            }
            warn    'org.codehaus.groovy.grails.web.servlet',  //  controllers
                    'org.codehaus.groovy.grails.web.pages', //  GSP
                    'org.codehaus.groovy.grails.web.sitemesh', //  layouts
                    'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
                    'org.codehaus.groovy.grails.web.mapping', // URL mapping
                    'org.codehaus.groovy.grails.commons', // core / classloading
                    'org.codehaus.groovy.grails.plugins', // plugins
                    'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
                    'org.springframework',
                    'org.hibernate'
            debug   'com.something.ldap', 'com.something.scanner.helper'
            root.level = org.apache.log4j.Level.DEBUG
        }
    }
}
