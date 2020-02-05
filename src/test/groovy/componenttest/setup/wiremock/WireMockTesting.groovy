package componenttest.setup.wiremock

import org.junit.Before
import org.junit.BeforeClass

trait WireMockTesting {

    @BeforeClass
    def setupSpecAllWiremocks() {
        KeycloakAPIMock.instance.start()
    }

    @Before
    def setupAllWireMocks() {
        KeycloakAPIMock.instance.reset()
    }
}
