package functional.tests

import grails.testing.mixin.integration.Integration
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientException
import spock.lang.Issue

@Integration
class TestControllerSpec extends HttpClientSpec {

    @Issue('https://github.com/grails/grails-core/issues/10582')
    void 'test responding after an action triggered by a HTTP 401 response is possible'() {
        when:
        HttpRequest request = HttpRequest.GET("/test/triggerUnauthorized")
        HttpResponse<String> resp = client.toBlocking().exchange(request, Argument.of(String), Argument.of(String))

        then: 'the response is correct'
        HttpClientException e = thrown()
        e.response.status == HttpStatus.UNAUTHORIZED
        e.response.body() == '{"message":"Unauthorized GSON"}'
    }
}
