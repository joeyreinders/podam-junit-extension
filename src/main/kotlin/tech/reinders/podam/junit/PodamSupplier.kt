package tech.reinders.podam.junit

import uk.co.jemos.podam.api.PodamFactory

interface PodamSupplier {
    fun getPodamFactory() : PodamFactory
}