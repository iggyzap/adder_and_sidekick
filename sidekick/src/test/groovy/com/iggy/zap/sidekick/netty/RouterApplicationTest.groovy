package com.iggy.zap.sidekick.netty

import com.iggy.zap.adder.AdderApplication
import groovyx.net.http.RESTClient
import spock.lang.Specification

class RouterApplicationTest extends Specification {
    static AdderApplication runningApp
    static RouterApplication router

    RESTClient client = new RESTClient("http://localhost:18080")


    def setupSpec() {
        runApp()
        runRouter()
    }

    def cleanupSpec() {
        cleanupApp()
        stopRouter()
    }


    static runApp() {
        if (runningApp == null) {
            runningApp = new AdderApplication()
            runningApp.run("server")
        }
    }

    static cleanupApp() {
        if (runningApp != null) {
            runningApp = null
        }
    }

    static runRouter() {
        if (router == null) {
            router = new RouterApplication("127.0.0.1", 8080, 18080).init()
            new Thread({ router.run() }).start()
        }
    }

    static stopRouter() {
        if (router != null) {
            router.shutdown()
            router = null
        }
    }

    def "Actual back end app called"() {
        when:
            def r = client.get([path: '/+/1/2'])
        then:
            r.status == 200
            r.data.getText() == '3'
    }

}
