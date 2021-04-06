package com.hamidulloh.nasheedplayer.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hamidulloh.nasheedplayer.databinding.ItemNasheedBinding
import com.hamidulloh.nasheedplayer.model.Nasheed

class NasheedAdapter : ListAdapter<Nasheed, NasheedAdapter.ViewHolder>(NasheedDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNasheedBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val nasheed = getItem(position)

        holder.binding.name.text = nasheed.name
        holder.binding.cover.setImageResource(nasheed.cover)
    }

    class NasheedDiffCallback : DiffUtil.ItemCallback<Nasheed>() {
        override fun areItemsTheSame(oldItem: Nasheed, newItem: Nasheed): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Nasheed, newItem: Nasheed): Boolean {
            return oldItem == newItem
        }
    }

    class ViewHolder(val binding: ItemNasheedBinding)
        : RecyclerView.ViewHolder(binding.root)
}