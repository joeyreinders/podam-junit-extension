package tech.reinders.podam.junit

import java.lang.reflect.AnnotatedElement
import java.lang.reflect.Field
import java.lang.reflect.Parameter

object TypeUtil {
    fun getType(annotatedElement: AnnotatedElement): Class<*> {
        return if (annotatedElement is Field) {
            annotatedElement.type
        } else if (annotatedElement is Parameter) {
            annotatedElement.type
        } else {
            throw UnsupportedOperationException("Doesn't yet support element: $annotatedElement")
        }
    }
}