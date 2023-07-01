package it.move2.eventstore.domain.events

import java.time.Instant
import java.util.UUID

abstract class Event(
    open val aggregateId: String,
    open val payload: String,
) {
    val id: String get() = UUID.randomUUID().toString()

    val name: String get() = this.javaClass.simpleName
    val created: Instant get() = Instant.now()
    val dispatched: Boolean get() = false
}