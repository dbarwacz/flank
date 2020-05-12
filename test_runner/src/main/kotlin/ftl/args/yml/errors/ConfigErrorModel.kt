package ftl.args.yml.errors

data class ConfigErrorModel(val propertyName: String, val line: Int, val column: Int, val referenceChain: List<String>)