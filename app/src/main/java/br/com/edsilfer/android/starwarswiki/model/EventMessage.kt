package br.com.edsilfer.android.starwarswiki.model

import br.com.edsilfer.android.starwarswiki.model.enum.EventCatalog

/**
 * Created by ferna on 2/18/2017.
 */
data class EventMessage(
        val event: EventCatalog,
        val payload: Any?,
        val isSuccess: Boolean = true
)
