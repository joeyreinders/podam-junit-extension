package tech.reinders.podam.junit

import org.junit.jupiter.api.extension.ExtensionContext
import tech.reinders.podam.junit.annotation.Podam
import uk.co.jemos.podam.api.PodamFactory
import java.lang.reflect.Type
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.declaredMembers
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.jvm.isAccessible

private val PODAM_JUNIT = ExtensionContext.Namespace.create("tech.reinders.podam.junit")
internal const val FACTORY = "podamfactory"
internal const val PODAMS = "podams"

fun getStore(extensionContext: ExtensionContext) = extensionContext.getStore(PODAM_JUNIT)

fun initPodamElements(extensionContext: ExtensionContext) {
    extensionContext.requiredTestInstances.allInstances.forEach {
        initPodamElementsOnInstance(it, extensionContext)
    }
}

fun initPodamElementsOnInstance(instance: Any, extensionContext: ExtensionContext) {
    val podamFactory = getStore(extensionContext).get(FACTORY, PodamFactory::class.java)
    instance::class.declaredMemberProperties.stream().forEach {
        val annotation = it.findAnnotation<Podam>()
        if(annotation != null) {
            val genericTypeArgs = annotation.genericTypeArgs.map { type -> type.java }.toTypedArray()


            lateinit var value : Any
            if(annotation.manufacturePojoWithFullData) {
                value = podamFactory.manufacturePojoWithFullData(it.javaClass, *genericTypeArgs)
            } else {
                value = podamFactory.manufacturePojo(it.javaClass, *genericTypeArgs)
            }

            if(! it.isAccessible) {
                it.isAccessible = true
            }

            it.get()
        }
    }
}

