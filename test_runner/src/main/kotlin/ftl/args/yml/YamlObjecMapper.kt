package ftl.args.yml

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException

class YamlObjecMapper() : ObjectMapper(YAMLFactory()) {
    override fun <T> readValue(content: String?, valueType: Class<T>?): T {
        try {
            return readValue(content, _typeFactory.constructType(valueType))
        } catch (missingParameterError: MissingKotlinParameterException) {
            throw convertMissingParameterException(missingParameterError, readTree(content))
        }
    }
}