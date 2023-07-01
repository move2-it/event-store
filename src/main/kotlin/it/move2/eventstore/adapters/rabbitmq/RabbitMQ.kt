package it.move2.eventstore.adapters.rabbitmq

import com.rabbitmq.client.*
import it.move2.eventstore.application.Handler
import it.move2.eventstore.ports.Queue
import kotlin.text.Charsets.UTF_8

data class RabbitMQConfiguration(
    val username: String,
    val password: String,
    val host: String,
    val virtualHost: String,
    val port: Int,
    val queue: String
)

class RabbitMQFactory {
    companion object {
        private fun connectionFactory(configuration: RabbitMQConfiguration) = ConnectionFactory().apply {
            username = configuration.username
            password = configuration.password
            host = configuration.host
            virtualHost = configuration.virtualHost
            port = configuration.port
        }

        private fun channel(configuration: RabbitMQConfiguration) =
            connectionFactory(configuration).newConnection().createChannel()

        fun queue(configuration: RabbitMQConfiguration, handler: Handler) =
            RabbitMQ(configuration.queue, channel(configuration), handler)
    }
}

class RabbitMQ(
    private val queue: String, private val channel: Channel, private val handler: Handler
) : Queue {

    override fun sub() {
        val consumer = object : DefaultConsumer(channel) {
            override fun handleDelivery(
                consumerTag: String?, envelope: Envelope?, properties: AMQP.BasicProperties?, body: ByteArray?
            ) {
                val json = String(body!!, UTF_8)
                handler.apply(json)
            }
        }

        channel.basicConsume(queue, true, consumer)
    }
}