package it.move2.eventstore

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import it.move2.eventstore.adapters.rabbitmq.RabbitMQConfiguration
import it.move2.eventstore.adapters.rabbitmq.RabbitMQFactory.Companion.queue
import it.move2.eventstore.application.Handler

object Bootstrap {

    private val rabbitMQConfiguration = RabbitMQConfiguration(
        System.getenv("USERNAME"),
        System.getenv("PASSWORD"),
        System.getenv("HOST"),
        System.getenv("VIRTUALHOST"),
        System.getenv("PORT").toInt(),
        System.getenv("QUEUE")
    )

    private val objectMapper = ObjectMapper().registerKotlinModule()

    private val handler = Handler(objectMapper)

    val queue = queue(rabbitMQConfiguration, handler)
}