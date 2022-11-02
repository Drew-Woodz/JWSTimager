package com.example.jwstimager.Database

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.jwstimager.R
import java.net.URI.create

class LinkListAdapter : ListAdapter<Link, LinkListAdapter.LinkViewHolder>(LinksComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinkViewHolder {
        return LinkViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: LinkViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.link)
    }

    class LinkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //private val linkItemView: TextView = itemView.findViewById(R.id.textView)

        fun bind(text: String?) {
            //linkItemView.text = text
        }

        companion object {
            fun create(parent: ViewGroup): LinkViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return LinkViewHolder(view)
            }
        }
    }

    class LinksComparator : DiffUtil.ItemCallback<Link>() {
        override fun areItemsTheSame(oldItem: Link, newItem: Link): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Link, newItem: Link): Boolean {
            return oldItem.link == newItem.link
        }
    }
}