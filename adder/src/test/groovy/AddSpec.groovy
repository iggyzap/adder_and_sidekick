import com.iggy.zap.adder.AdderApplication
import groovyx.net.http.RESTClient
import spock.lang.Specification

class AddSpec extends Specification {

    RESTClient client = new RESTClient("http://localhost:8080")
    RESTClient adminClient = new RESTClient("http://localhost:8081")

    static AdderApplication runningApp

    def setupSpec() {
        runApp()
    }

    def cleanupSpec() {
        cleanupApp()
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

    def "healthckeck"() {
        when:
            def r = adminClient.get([path:'/healthcheck'])
        then:
            r.status == 200
            r.data.simple.healthy
    }

    def "Addition command" () {
        when:
            def r = client.get([path:"/+/1/2"])
        then:
            r.status == 200
            r.data.getText() == '3'
    }

}
