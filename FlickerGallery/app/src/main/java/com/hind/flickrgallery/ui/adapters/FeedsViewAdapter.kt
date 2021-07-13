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
import com.hind.flickrgallery.databinding.PublicFeedsItemBinding
import com.hind.flickrgallery.ui.viewmodels.PublicFeedsItemViewModel

class FeedsViewAdapter(feeds: List<Feed>) : RecyclerView.Adapter<FeedsViewAdapter.ViewHolder>() {

    private var _feeds:List<Feed> = feeds
    private var _itemClickListener:ItemClickListener? = null

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
        val inflater = LayoutInflater.from(parent.context)
        val binding = PublicFeedsItemBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Bind view holder
        holder.bind(_feeds[position],_itemClickListener)
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

    /**
     * View holder class for  public feeds item.
     * @param binding Binding for item.
     */
    class ViewHolder(private val binding: PublicFeedsItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item: Feed,clickListener: ItemClickListener?){
            binding.viewModel = PublicFeedsItemViewModel(item)
            //If click listener is set
            binding.root.setOnClickListener {
                clickListener?.onItemClick(item)
            }
        }
    }

}