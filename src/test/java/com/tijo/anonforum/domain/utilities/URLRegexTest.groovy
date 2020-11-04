package com.tijo.anonforum.domain.utilities

import spock.lang.Specification
import spock.lang.Unroll

class URLRegexTest extends Specification{
    @Unroll("#link should return #value")
    def "Should correctly verify URL's"(){
        expect:
            value == URLregexp.VerifyURL(link)
        where:
            link << ["https://fossbytes.com/wp-content/uploads/2020/05/78-billion-line-text-file.jpg","http://bruh","cefsgasf"]
            value << [true,false,false]
    }
}
