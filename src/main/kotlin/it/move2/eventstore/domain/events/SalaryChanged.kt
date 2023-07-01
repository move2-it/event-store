package it.move2.eventstore.domain.events

class SalaryChanged(override val aggregateId: String, override val payload: String) :
    Event(aggregateId = aggregateId, payload = payload)