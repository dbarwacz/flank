package ftl.args.yml

import ftl.args.AndroidArgs
import ftl.args.yml.errors.ConfigErrorMessageBuilder
import ftl.test.util.TestHelper
import ftl.util.FlankConfigurationException
import org.junit.Assert
import org.junit.Test

class ErrorParserTest {
    private val yamlWithoutDeviceVersion =
        TestHelper.getPath("src/test/kotlin/ftl/args/yml/test_error_yaml_cases/flank-no-device-version.yml")
    private val yamlNoModelName =
        TestHelper.getPath("src/test/kotlin/ftl/args/yml/test_error_yaml_cases/flank-no-model-name.yml")
    private val yamlNoModelNode =
        TestHelper.getPath("src/test/kotlin/ftl/args/yml/test_error_yaml_cases/flank-no-model-name.yml")

    @Test
    fun `parse json mapping error`() {
        val instantionError =
            "Instantiation of [simple type, class ftl.config.Device] value failed for JSON property version due to missing (therefore NULL) value for creator parameter version which is a non-nullable type\n" +
                    " at [Source: (StringReader); line: 23, column: 3] (through reference chain: ftl.args.yml.AndroidGcloudYml[\"gcloud\"]->ftl.args.yml.AndroidGcloudYmlParams[\"device\"]->java.util.ArrayList[4]->ftl.config.Device[\"version\"])"

        val expected = """
Error on parse config: gcloud->device[4]->version
Missing element or value for: 'version'
At line: 23, column: 3
""".trimIndent()
        val buildErrorMessage = ConfigErrorMessageBuilder()
        Assert.assertEquals(expected, buildErrorMessage(instantionError))
    }


    @Test
    fun `return exception with inner message on parse error`() {
        val instantionError =
            "Instantiation oflParams[\"device\"]->java.util.A"
        val expected = "Parse message error: Instantiation oflParams[\"device\"]->java.util.A".trimIndent()
        val buildErrorMessage = ConfigErrorMessageBuilder()

        Assert.assertEquals(expected, buildErrorMessage(instantionError))
    }

    @Test(expected = FlankConfigurationException::class)
    fun `should throw FlankConfigException without device version`() {
        AndroidArgs.load(yamlWithoutDeviceVersion)
    }

    @Test(expected = FlankConfigurationException::class)
    fun `should throw FlankConfigException without model name`() {
        AndroidArgs.load(yamlNoModelName)
    }

    @Test(expected = FlankConfigurationException::class)
    fun `should throw FlankConfigException without model node`() {
        AndroidArgs.load(yamlNoModelNode)
    }
}