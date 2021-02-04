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
        TODO("Not yet implemented")
    }

    override fun beforeEach(context: ExtensionContext?) {
        val podamFactory = PodamExtension.retrievePodamSupplier(context!!).objectInstance!!.getPodamFactory()

        val store = getStore(context)
        store.put(PODAMS, emptySet<Any>())
        store.put(FACTORY, podamFactory);

        initPodamElements(context)
    }

    companion object {
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
                annotation.get().supplier
            }
        }
    }
}