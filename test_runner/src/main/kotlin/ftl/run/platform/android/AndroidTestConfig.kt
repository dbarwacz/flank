package ftl.run.platform.android

import ftl.args.ShardChunks
import ftl.config.FlankRoboDirective

sealed class AndroidTestConfig {

    data class Instrumentation(
        val appApkGcsPath: String,
        val testApkGcsPath: String,
        val testRunnerClass: String?,
        val orchestratorOption: String?,
        // sharding
        val disableSharding: Boolean,
        val testShards: ShardChunks,
        val numUniformShards: Int?
    ) : AndroidTestConfig()

    data class Robo(
        val appApkGcsPath: String,
        val flankRoboDirectives: List<FlankRoboDirective>?,
        val roboScriptGcsPath: String?
    ) : AndroidTestConfig()
}
