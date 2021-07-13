package com.hamidulloh.nasheedplayer.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hamidulloh.nasheedplayer.databinding.ItemNasheedBinding
import com.hamidulloh.nasheedplayer.model.Nasheed

class NasheedAdapter(
    private val itemClickListener: NasheedItemCallback
) : ListAdapter<Nasheed, NasheedAdapter.ViewHolder>(NasheedDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNasheedBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val nasheed = getItem(position)

        holder.apply {
            binding.apply {
                nName.text = nasheed.name
                nAuthor.text = nasheed.author
                nDuration.text = nasheed.duration

                nImage.setImageResource(nasheed.image)

                root.setOnClickListener {
                    itemClickListener.onItemClick(nasheed)
                }
            }
        }


    }

    class NasheedDiffCallback : DiffUtil.ItemCallback<Nasheed>() {
        override fun areItemsTheSame(oldItem: Nasheed, newItem: Nasheed): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Nasheed, newItem: Nasheed): Boolean {
            return oldItem == newItem
        }
    }

    class NasheedItemCallback(val itemClickListener: (item: Nasheed) -> Unit) {
        fun onItemClick(item: Nasheed) = itemClickListener(item)
    }

    class ViewHolder(val binding: ItemNasheedBinding) : RecyclerView.ViewHolder(binding.root)
}
