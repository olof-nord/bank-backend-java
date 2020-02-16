package componenttest

import componenttest.setup.BaseComponentTest
import componenttest.setup.httpclient.HttpClient

import java.time.LocalDateTime

class CustomerAPITest extends BaseComponentTest {

    def "Should successfully add a customer"() {

        given: "A user"
        def firstName = "Olof"
        def lastName = "Nord"

        when: "The request is sent"
        HttpClient.Response addUserResponse = HttpClient.request().withUrl("/customers")
            .withHeaders(["Content-Type": "application/json"])
            .withBody([
                "first_name": firstName,
                "last_name": lastName,
                "email": "address@email.com",
                "date_of_birth": "1970-01-01"
            ])
            .create()
            .post()

        then: "The request is successful"
        addUserResponse.statusCodeValue == 201
        addUserResponse.json.first_name == firstName
        addUserResponse.json.last_name == lastName

        and: "Dynamic values are correct"
        addUserResponse.json.id != null
        LocalDateTime.now().isAfter(LocalDateTime.parse(addUserResponse.json.created as String))
        addUserResponse.json.nationality == "German"
        addUserResponse.json.addresses == null

    }

    def "Should throw an error if required fields are missing"() {

        given: "A new user without lastname data"
        def firstName = "Olof"
        def lastName = null

        when: "The request is sent"
        HttpClient.Response addUserResponse = HttpClient.request().withUrl("/customers")
            .withHeaders(["Content-Type": "application/json"])
            .withBody([
                "first_name": firstName,
                "last_name": lastName,
                "email": "address@email.com",
                "date_of_birth": "1970-01-01"
            ])
            .create()
            .post()

        then: "The request is unsuccessful"
        addUserResponse.statusCodeValue == 400

    }
}
