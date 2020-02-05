package componenttest.setup

import componenttest.setup.db.DatabaseTesting
import componenttest.setup.httpclient.HttpClientTesting
import componenttest.setup.wiremock.WireMockTesting
import info.olof.bank.backend.BackendApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@ActiveProfiles("test")
@SpringBootTest(classes = BackendApplication, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BaseComponentTest extends Specification implements DatabaseTesting, HttpClientTesting, WireMockTesting {

}
