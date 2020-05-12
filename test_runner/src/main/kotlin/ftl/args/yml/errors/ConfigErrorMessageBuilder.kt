package ftl.args.yml.errors

import com.fasterxml.jackson.databind.JsonNode
import java.lang.Exception
import javax.swing.tree.TreeNode

class ConfigErrorMessageBuilder {
    private val parseMessage = ErrorParser()
    private val resolveErrorNode = ErrorNodeResolver()
    //region error message elements
    private val messageHeader = "Error on parse config: "
    private val missingElementMessage = "Missing element or value for: '%s'"
    private val atMessage = "At line: %s, column: %s"
    private val errorNodeMessage = "Error node: %s"
    private val referenceChainMessage = "Reference chain: %s"
    //endregion

    private val exceptionTemplate = "Parse message error: %s"

    operator fun invoke(errorMessage: String) =
        try {
            val errorModel = parseMessage(errorMessage)
            StringBuilder(messageHeader).appendln(createReferenceChain(errorModel.referenceChain)).appendln(
                missingElementMessage.format(errorModel.propertyName)
            ).appendln(atMessage.format(errorModel.line, errorModel.column)).toString().trim()
        } catch (error: Exception) {
            exceptionTemplate.format(errorMessage)
        }

    operator fun invoke(errorMessage: String, yamlTreeNode: JsonNode) =
        try {
            val errorModel = parseMessage(errorMessage)
            StringBuilder(messageHeader).appendln(createReferenceChain(errorModel.referenceChain)).appendln(
                missingElementMessage.format(errorModel.propertyName)
            ).appendln(atMessage.format(errorModel.line, errorModel.column))
                .appendln(errorNodeMessage.format(resolveErrorNode(yamlTreeNode, errorModel))).toString().trim()
        } catch (error: Exception) {
            exceptionTemplate.format(errorMessage)
        }

    private fun createReferenceChain(referenceChain: List<String>): String {
        val chainBuilder = StringBuilder()
        referenceChain.forEachIndexed { index, chainPart ->
            chainBuilder.append(appendChainElement(chainPart, index > 0))
        }
        return chainBuilder.toString()
    }

    private fun appendChainElement(chainPart: String, withSeparator: Boolean): String = when {
        chainPart.toIntOrNull() != null -> "[$chainPart]"
        withSeparator -> "->$chainPart"
        else -> chainPart

    }
}