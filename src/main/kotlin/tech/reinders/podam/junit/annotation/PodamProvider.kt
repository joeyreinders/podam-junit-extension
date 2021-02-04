package tech.reinders.podam.junit.annotation

import tech.reinders.podam.junit.DefaultPodamPovider
import tech.reinders.podam.junit.PodamSupplier
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class PodamProvider(val supplier: KClass<out PodamSupplier> = DefaultPodamPovider::class)