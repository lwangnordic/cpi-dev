package com.figaf

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class GroovyScriptsTest extends AbstractGroovyTest {


    @Override
    List<String> getIgnoredKeys() {
        List<String> keys = super.getIgnoredKeys()
        keys.addAll(Arrays.asList())
        return keys
    }

}