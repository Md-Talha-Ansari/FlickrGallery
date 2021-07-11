package com.hind.flickrgallery.businesslogic.models.request.publicfeeds

/**
 * Tag modes supported by flicker services.
 * To be used in future for search by tags functionality.
 */
enum class TagMode {
    ALL {
        override fun value(): String {
            return "all"
        }
    },ANY {
        override fun value(): String {
            return "any"
        }
    };

    abstract fun value():String
}