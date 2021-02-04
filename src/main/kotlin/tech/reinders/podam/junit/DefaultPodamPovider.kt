package tech.reinders.podam.junit

import uk.co.jemos.podam.api.PodamFactoryImpl

internal object DefaultPodamPovider : PodamSupplier {
    private val PODAM = PodamFactoryImpl()

    override fun getPodamFactory() = PODAM
}