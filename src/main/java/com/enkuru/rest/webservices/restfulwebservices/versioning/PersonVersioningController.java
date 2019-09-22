package com.enkuru.rest.webservices.restfulwebservices.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonVersioningController {

    // Versioning with URI approach
    @GetMapping("v1/person")
    public PersonV1 personV1() {
        return new PersonV1("Bob Charlie");
    }

    @GetMapping("v2/person")
    public PersonV2 personV2() {
        return new PersonV2(new Name("Bob", "Charlie"));
    }

    // Versioning with parameter approach, it means client need to add a query parameter for access a version of the resource
    @GetMapping(value = "/person/param", params = "version=1")
    public PersonV1 paramV1() {
        return new PersonV1("Bob Charlie");
    }

    @GetMapping(value = "/person/param", params = "version=2")
    public PersonV2 paramV2() {
        return new PersonV2(new Name("Bob", "Charlie"));
    }

    // Versioning with header approach, it means client need to add HTTP header to request for access a version of the resource
    @GetMapping(value = "/person/header", headers = "X-API-VERSION=1")
    public PersonV1 header1() {
        return new PersonV1("Bob Charlie");
    }

    @GetMapping(value = "/person/header", headers = "X-API-VERSION=2")
    public PersonV2 headerV2() {
        return new PersonV2(new Name("Bob", "Charlie"));
    }

    // Versioning with content negotiation(typically this option is most used) or accept header approach,
    // it means client need to add HTTP header that called "Accept:`one of produces`" to request for access a version of the resource
    @GetMapping(value = "/person/producer", produces = "application/vnd.company.app-v1+json")
    public PersonV1 producer1() {
        return new PersonV1("Bob Charlie");
    }

    @GetMapping(value = "/person/producer", produces = "application/vnd.company.app-v2+json")
    public PersonV2 producerV2() {
        return new PersonV2(new Name("Bob", "Charlie"));
    }

}
