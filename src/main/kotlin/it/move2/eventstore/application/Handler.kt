package it.move2.eventstore.application

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import it.move2.job.JobOffer

class Handler(private val objectMapper: ObjectMapper) {
    fun apply(json: String) {
        val jobOffers: List<JobOffer> = objectMapper.readValue(json)
        println(jobOffers.size)
    }
}