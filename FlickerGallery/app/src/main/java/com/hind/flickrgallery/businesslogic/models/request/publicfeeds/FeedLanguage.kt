package com.hind.flickrgallery.businesslogic.models.request.publicfeeds

/**
 * Languages supported by Flickr services.
 * To be used in future based on user language selection from settings.
 */
enum class FeedLanguage {

    GERMAN {
        override fun value(): String {
            return "de-de"
        }
    },
    ENGLISH {
        override fun value(): String {
            return "en-us"
        }
    },
    SPANISH {
        override fun value(): String {
            return "es-us"
        }
    },
    FRENCH {
        override fun value(): String {
            return "fr-fr"
        }
    },
    ITALIAN {
        override fun value(): String {
            return "it-it"
        }
    },
    KOREAN {
        override fun value(): String {
            return "ko-kr"
        }
    },
    PORTUGUESE {
        override fun value(): String {
            return "pt-br"
        }
    },
    CHINESE{
        override fun value(): String {
            return "zh-hk"
        }
    };


    abstract fun value():String
}