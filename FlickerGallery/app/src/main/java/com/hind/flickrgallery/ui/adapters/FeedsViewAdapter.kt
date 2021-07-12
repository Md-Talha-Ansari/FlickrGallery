package com.hind.flickrgallery.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hind.flickrgallery.R
import com.hind.flickrgallery.businesslogic.models.response.publicfeeds.Feed

class FeedsViewAdapter(feeds: List<Feed>) : RecyclerView.Adapter<FeedsViewAdapter.ViewHolder>() {

    private var _feeds:List<Feed> = feeds
    private var _itemClickListener:ItemClickListener? = null

    constructor(feeds: List<Feed>,itemClickListener: ItemClickListener?):this(feeds){
        _itemClickListener = itemClickListener
    }

    /**
     * Method to update the feeds list.
     * @param feeds Feeds information.
     */
    fun setFeeds(feeds:List<Feed>){
        _feeds = feeds
        notifyDataSetChanged()
    }

    /**
     * Method to set click listener for each recycle view item.
     * @param itemClickListener listener to set.
     * @see ItemClickListener
     */
    fun setItemClickListener(itemClickListener: ItemClickListener?){
        _itemClickListener = itemClickListener
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.public_feeds_item,parent,false)
        val viewHolder = ViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //set the item click listener for list items
        holder.itemView.setOnClickListener {
            _itemClickListener?.onItemClick(_feeds[position])
        }
        if(position < _feeds.count()){
            Glide.with(holder.itemView)
                .load(_feeds[position].media.m.toUri())
                .centerCrop()
                .into(holder.imageView)
        }
    }

    override fun getItemCount(): Int {
        return _feeds.count()
    }

    /**
     * Item click listener interface.
     */
    interface ItemClickListener{
        /**
         * Callback method to notify click of item.
         * @param item Feed information.
         */
        fun onItemClick(item:Feed)
    }

    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val imageView:ImageView = view.findViewById(R.id.feed_image)
    }

}