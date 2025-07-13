package com.concertmania.api.common.exception

class EntityNotPersistedException(
    entityName: String?
) : IllegalStateException("$entityName has not been persisted yet. ID is null.")
