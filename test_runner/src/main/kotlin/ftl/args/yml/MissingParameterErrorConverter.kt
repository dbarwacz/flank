package ftl.args.yml

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import ftl.args.yml.errors.ConfigErrorMessageBuilder
import ftl.args.yml.errors.ErrorParser
import ftl.util.FlankConfigurationException

fun convertMissingParameterException(missingParameterError: MissingKotlinParameterException, yaml: JsonNode): Throwable {
    val errorMessageBuilder = ConfigErrorMessageBuilder()
    val errorMessage = missingParameterError.message
    return if (errorMessage != null) {
        FlankConfigurationException(errorMessageBuilder(errorMessage, yaml))
    } else {
        missingParameterError
    }
}
