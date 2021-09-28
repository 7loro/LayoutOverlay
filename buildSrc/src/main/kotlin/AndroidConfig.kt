object AndroidConfig {
    const val COMPILE_SDK_VERSION = 31
    const val MIN_SDK_VERSION = 21
    const val TARGET_SDK_VERSION = 31
    const val BUILD_TOOLS_VERSION = "30.0.3"
    const val KOTLIN_OPTION_JVM_TARGET = "1.8"

    const val VERSION_CODE = 1
    const val VERSION_NAME = "1.0"

    const val ID = "com.casper.myboilerplate"
    const val TEST_INSTRUMENTATION_RUNNER = "androidx.test.runner.AndroidJUnitRunner"
}

interface BuildType {

    companion object {
        const val RELEASE = "release"
        const val DEBUG = "debug"
    }

    val isMinifyEnabled: Boolean
}

object BuildTypeDebug : BuildType {
    override val isMinifyEnabled = false
}

object BuildTypeRelease : BuildType {
    override val isMinifyEnabled = false
}

object TestOptions {
    const val IS_RETURN_DEFAULT_VALUES = true
}
