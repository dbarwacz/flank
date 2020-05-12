package ftl.args.yml.errors

import com.fasterxml.jackson.databind.JsonNode
import javax.swing.tree.TreeNode

class ErrorNodeResolver {
    operator fun invoke(treeNode: JsonNode, errorModel: ConfigErrorModel): String {
        var currentNode: JsonNode = treeNode

        val lastNode = errorModel.referenceChain.last()
        for (chainNode in errorModel.referenceChain) {
            if (chainNode == lastNode) {
                break
            }
            val nodeAsIntValue = chainNode.toIntOrNull()
            currentNode = if (nodeAsIntValue != null) {
                currentNode[nodeAsIntValue]
            } else {
                currentNode[chainNode]
            }
        }
        return currentNode.toPrettyString()
    }
}