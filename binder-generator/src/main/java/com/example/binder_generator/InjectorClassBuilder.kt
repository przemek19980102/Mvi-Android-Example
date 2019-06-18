package com.example.binder_generator

import java.lang.StringBuilder

/**
 * Custom Kotlin Class Builder which returns file content string
 * This is for learning purpose only.
 * Use KotlinPoet for production app
 * KotlinPoet can be found at https://github.com/square/kotlinpoet
 */
class InjectorClassBuilder(
        private val className: String,
        private val packageName:String,
        private val injections: List<Injection>) {

    private val imports = mutableListOf<String>()
    private val binds = mutableListOf<String>()

    fun getContent(): String {
        for(injection in injections) {
            imports.add(createImportString(injection))
            binds.add(createBindingString(injection))
        }
        return """
            $packageName

            import dagger.Module
            import dagger.android.ContributesAndroidInjector
            ${imports.joinToLines()}

            @Module
            abstract class $className {

            ${binds.joinToLines()}

            }
        """.trimIndent()

    }

    private fun createImportString(injection: Injection): String {
        return """
                import ${injection.injectablePackageName}.${injection.injectableName}
                import ${injection.injectablePackageName}.${injection.injectableModuleName}
            """.trimIndent()
    }

    private fun createBindingString(injection: Injection): String {
        return """
                @ContributesAndroidInjector(modules = [${injection.injectableModuleName}::class])
                abstract fun bind${injection.injectableName}(): ${injection.injectableName}
        """.trimIndent()
    }

    private fun List<String>.joinToLines() = this.joinToString("\n")
}