package com.hind.flickrgallery.businesslogic.models.request.publicfeeds

/**
 * Model class for flickr public feeds service.
 */
data class PublicFeedsRequest(val id:String?,val ids:List<String>?,val tags:List<String>?,val tagmode:TagMode = TagMode.ALL,val lang:FeedLanguage = FeedLanguage.ENGLISH) {
    private val format = "json"

    private fun tagsString() = tags?.joinToString()
    private fun idsString() = ids?.joinToString()

    internal fun toMap():Map<String,Any>{
        val map = mutableMapOf<String,Any>()
        if(id != null){
            map["id"] = id
        }
        val idsString = idsString()
        if( idsString!= null){
            map["ids"] = idsString
        }
        val tagsString = tagsString()
        if(tagsString != null){
            map["tags"] = tagsString
        }
        map["tagMode"] = tagmode.value()
        map["lang"] = lang.value()
        map["format"] = format
        return map.toMap()
    }

}