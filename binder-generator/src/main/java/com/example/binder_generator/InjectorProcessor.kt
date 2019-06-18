package com.example.binder_generator

import com.example.binder_annotations.AndroidInjectable
import com.example.binder_annotations.AndroidInjectableModule
import com.google.auto.service.AutoService
import java.io.File
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.lang.model.type.MirroredTypeException
import javax.lang.model.type.TypeMirror


@AutoService(Processor::class) // For registering the service
@SupportedSourceVersion(SourceVersion.RELEASE_8) // to support Java 8
@SupportedOptions(InjectorProcessor.KAPT_KOTLIN_GENERATED_OPTION_NAME)
class InjectorProcessor : AbstractProcessor() {

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(AndroidInjectableModule::class.java.name)
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latest()
    }


    override fun process(set: MutableSet<out TypeElement>?, roundEnvironment: RoundEnvironment?): Boolean {

        roundEnvironment?.getElementsAnnotatedWith(AndroidInjectableModule::class.java)
                ?.forEach {
                    val className = it.simpleName.toString()
                    val pack = processingEnv.elementUtils.getPackageOf(it).toString()
                    val injections = mutableListOf<Injection>()
                    roundEnvironment.getElementsAnnotatedWith(AndroidInjectable::class.java)
                            .forEach {
                                val name = it.simpleName.toString()
                                val moduleName = it.getInjectableModule()
                                val packageName = processingEnv.elementUtils.getPackageOf(it).toString()
                                if (!moduleName.isNullOrEmpty() && !packageName.isNullOrEmpty() && !name.isNullOrEmpty()) {
                                    injections.add(Injection(name, moduleName, packageName))
                                }
                            }
                    generateClass(className, pack, injections)
                }
        return true
    }

    private fun generateClass(className: String, pack: String, injections: List<Injection>) {
        val fileName = "Generated_$className"
        val fileContent = InjectorClassBuilder(fileName, pack, injections).getContent()

        val kaptKotlinGeneratedDir = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME]
        val file = File(kaptKotlinGeneratedDir, "$fileName.kt")

        file.writeText(fileContent)
    }

    private fun Element.getInjectableModule(): String? {
        try {
            val tag = getAnnotation(AndroidInjectable::class.java)
            return tag.module.simpleName
        } catch (mte: MirroredTypeException) {
            return mte.typeMirror.toString().split(".").last()
        }
    }

    private fun TypeMirror.asInjectableModule(): Element {
        val typeUtils = processingEnv.typeUtils
        return typeUtils.asElement(this)
    }

    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }
}