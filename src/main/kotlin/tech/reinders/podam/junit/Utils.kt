package tech.reinders.podam.junit

import org.junit.jupiter.api.extension.ExtensionContext
import tech.reinders.podam.junit.annotation.Podam
import uk.co.jemos.podam.api.PodamFactory
import java.lang.reflect.AnnotatedElement
import java.lang.reflect.Field
import java.lang.reflect.Parameter

private val PODAM_JUNIT = ExtensionContext.Namespace.create("tech.reinders.podam.junit")
internal const val FACTORY = "podamfactory"

fun getStore(extensionContext: ExtensionContext) = extensionContext.getStore(PODAM_JUNIT)

fun initPodamElements(extensionContext: ExtensionContext) {
    extensionContext.requiredTestInstances.allInstances.forEach {
        initPodamElementsOnInstance(it, extensionContext)
    }
}

fun manufacturePojo(element: AnnotatedElement, extensionContext: ExtensionContext): Any {
    val podamFactory = getStore(extensionContext).get(FACTORY, PodamFactory::class.java)
    val annotation = element.getAnnotation(Podam::class.java)
    val genericTypeArgs = annotation.genericTypeArgs.map { type -> type.java }.toTypedArray()
    val javaClassType = if (element is Parameter) {
        element.type
    } else if (element is Field) {
        element.type
    } else {
        throw UnsupportedOperationException("Not yet supported for type ${element::class.qualifiedName}")
    }

    return if (annotation.manufacturePojoWithFullData) {
        podamFactory.manufacturePojoWithFullData(javaClassType, *genericTypeArgs)
    } else {
        podamFactory.manufacturePojo(javaClassType, *genericTypeArgs)
    }
}

fun initPodamElementsOnInstance(instance: Any, extensionContext: ExtensionContext) {
    val podamFactory = getStore(extensionContext).get(FACTORY, PodamFactory::class.java)

    findAllPodamAnnotatedFields(instance).forEach {
        it.isAccessible = true

        val populatedFieldValue = it.get(instance);
        if (populatedFieldValue != null) {
            val annotation = it.getAnnotation(Podam::class.java)
            val genericTypeArgs = annotation.genericTypeArgs.map { type -> type.java }.toTypedArray()
            podamFactory.populatePojo(populatedFieldValue, *genericTypeArgs)
        } else {
            val value = manufacturePojo(it, extensionContext)
            it.set(instance, value);
        }
    }
}

fun findAllPodamAnnotatedFields(instance: Any): Array<Field> {
    val fields = mutableListOf<Field>()

    var clazz = instance.javaClass
    while (clazz.superclass != null) {
        clazz.declaredFields.filter { field -> field.isAnnotationPresent(Podam::class.java) }.forEach { field -> fields.add(field) }
        clazz = clazz.superclass
    }

    return fields.toTypedArray()
}
