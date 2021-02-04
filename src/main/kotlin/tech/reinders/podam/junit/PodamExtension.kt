package tech.reinders.podam.junit

import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ParameterContext
import org.junit.jupiter.api.extension.ParameterResolver
import org.junit.platform.commons.support.AnnotationSupport.findAnnotation
import tech.reinders.podam.junit.annotation.Podam
import tech.reinders.podam.junit.annotation.PodamProvider
import java.util.*
import kotlin.reflect.KClass

class PodamExtension : BeforeEachCallback, ParameterResolver {
    override fun supportsParameter(parameterContext: ParameterContext?, extensionContext: ExtensionContext?): Boolean {
        return parameterContext!!.isAnnotated(Podam::class.java)
    }

    override fun resolveParameter(parameterContext: ParameterContext?, extensionContext: ExtensionContext?): Any {
        initPodamFactoryIfNeeded(extensionContext!!)
        return manufacturePojo(parameterContext!!.parameter, extensionContext)
    }

    override fun beforeEach(context: ExtensionContext?) {
        initPodamFactoryIfNeeded(context!!)

        initPodamElements(context)
    }

    private fun initPodamFactoryIfNeeded(context: ExtensionContext) {
        val store = getStore(context)
        if (store.get(FACTORY) == null) {
            val supplierClass = retrievePodamSupplier(context)
            var supplier = supplierClass.objectInstance
            if (supplier == null) {
                val constructor = supplierClass.java.getDeclaredConstructor()
                constructor.isAccessible = true
                supplier = constructor.newInstance()
            }

            store.put(FACTORY, supplier!!.getPodamFactory())
        }
    }

    private companion object {
        private fun retrievePodamSupplier(context: ExtensionContext): KClass<out PodamSupplier> {
            var currentContext = context
            var annotation: Optional<PodamProvider>
            do {
                annotation = findAnnotation(currentContext.element, PodamProvider::class.java)
                if (!currentContext.parent.isPresent) {
                    break
                }
                currentContext = currentContext.parent.get()
            } while (!annotation.isPresent && currentContext !== context.root)

            return if(! annotation.isPresent) {
                DefaultPodamPovider::class
            } else {
                annotation.get().value
            }
        }
    }
}