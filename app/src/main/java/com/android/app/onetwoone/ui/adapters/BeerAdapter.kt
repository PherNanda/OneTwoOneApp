package com.android.app.onetwoone.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils.loadAnimation
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.app.onetwoone.R
import com.android.app.onetwoone.domain.model.Beer
import com.facebook.drawee.view.SimpleDraweeView

const val TYPE_HEADER = 0
const val TYPE_ITEM = 1

class BeerAdapter(private val exampleList: List<Beer>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var onItemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(item: Beer)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == TYPE_ITEM) {
            PunkViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_home,
                    parent, false
                )
            )
        } else {
            HeaderViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.header_home,
                    parent, false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PunkViewHolder) {
            val currentItem = exampleList[position - 1]
            holder.beerImage.setImageURI(currentItem.imageURL)
            holder.beerName.text = currentItem.name
            holder.beerTagline.text = currentItem.tagline
            holder.beerABV.text = currentItem.abv.toString()

            holder.itemView.animation =
                loadAnimation(holder.itemView.context, R.anim.fade_scale_animation)

            holder.itemView.setOnClickListener {
                onItemClickListener.onItemClick(exampleList[position - 1])
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isPositionHeader(position)) TYPE_HEADER else TYPE_ITEM
    }

    private fun isPositionHeader(position: Int): Boolean {
        return position == 0
    }

    override fun getItemCount() = exampleList.size + 1

    class PunkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val beerImage: SimpleDraweeView = itemView.findViewById(R.id.beer_image)
        val beerName: TextView = itemView.findViewById(R.id.beer_name)
        val beerTagline: TextView = itemView.findViewById(R.id.beer_tagline)
        val beerABV: TextView = itemView.findViewById(R.id.beer_abv)
    }

    class HeaderViewHolder(headerView: View) : RecyclerView.ViewHolder(headerView)
}