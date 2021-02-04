package tech.reinders.podam.junit.annotation

import kotlin.reflect.KClass

internal annotation class Podam(val manufacturePojoWithFullData: Boolean = false,
                                val genericTypeArgs: Array<KClass<*>> = [])