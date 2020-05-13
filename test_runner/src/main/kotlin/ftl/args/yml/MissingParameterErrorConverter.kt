package ftl.args.yml

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import ftl.args.yml.errors.ConfigurationErrorMessageBuilder
import ftl.util.FlankConfigurationException

fun convertConfigurationErrorExceptions(missingParameterError: Exception, yaml: JsonNode): Throwable {
    val errorMessageBuilder = ConfigurationErrorMessageBuilder()
    val errorMessage = missingParameterError.message
    return if (errorMessage != null) {
        FlankConfigurationException(errorMessageBuilder(errorMessage, yaml))
    } else {
        missingParameterError
    }
}
