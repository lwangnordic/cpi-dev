package com.figaf

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class GroovyScriptsTest extends AbstractGroovyTest {

    @ParameterizedTest
    @ValueSource(strings = [
            "src/test/resources/test-data-files/script10_v1/processData/test-data-1.json",
            "src/test/resources/test-data-files/script10_v1/processData/test-data-2.json",
            "src/test/resources/test-data-files/script10_v1/processData/test-data-3.json"
    ])
    void test_script10_v1Groovy(String testDataFile) {
        String groovyScriptPath = "src/main/resources/script/script10_v1.groovy"
        basicGroovyScriptTest(groovyScriptPath, testDataFile, "processData", getIgnoredKeysPrefixes(), getIgnoredKeys())
    }

    @ParameterizedTest
    @ValueSource(strings = [
            "src/test/resources/test-data-files/parseEdi/processData/test-data-1.json",
            "src/test/resources/test-data-files/parseEdi/processData/test-data-3.json",
            "src/test/resources/test-data-files/parseEdi/processData/test-data-2.json",
            "src/test/resources/test-data-files/parseEdi/processData/test-data-6.json",
            "src/test/resources/test-data-files/parseEdi/processData/test-data-5.json",
            "src/test/resources/test-data-files/parseEdi/processData/test-data-4.json",
            "src/test/resources/test-data-files/parseEdi/processData/test-data-7.json",
            "src/test/resources/test-data-files/parseEdi/processData/test-data-8.json",
            "src/test/resources/test-data-files/parseEdi/processData/test-data-9.json"
    ])
    void test_parseEdiGroovy(String testDataFile) {
        String groovyScriptPath = "src/main/resources/script/parseEdi.groovy"
        basicGroovyScriptTest(groovyScriptPath, testDataFile, "processData", getIgnoredKeysPrefixes(), getIgnoredKeys())
    }


    @Override
    List<String> getIgnoredKeys() {
        List<String> keys = super.getIgnoredKeys()
        keys.addAll(Arrays.asList())
        return keys
    }

}