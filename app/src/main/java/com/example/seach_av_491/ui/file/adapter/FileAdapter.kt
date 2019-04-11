package com.example.seach_av_491.ui.file.adapter

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.seach_av_491.R
import com.example.seach_av_491.model.File
import kotlinx.android.synthetic.main.item_file.view.*

class FileAdapter(private val onFileItemSelectedListener: OnFileItemSelectedListener): ListAdapter<File, FileAdapter.FileItemHolder>(ProjectViewDiffUtil()) {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): FileItemHolder {
        return FileItemHolder(p0)
    }

    override fun onBindViewHolder(p0: FileItemHolder, p1: Int) {
        p0.bind((getItem(p1)))
    }

    override fun submitList(list: List<File>?) {
        super.submitList(if (list != null) ArrayList(list) else null)
    }

    inner class FileItemHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_file)) {

        init {
            itemView.setOnClickListener {
                onFileItemSelectedListener.onFileItemSelectedListener(getItem(adapterPosition))
            }
        }

        fun bind(viewItem: File) {
            itemView.fileTitle.text = this.itemView.resources.getString(R.string.Title, viewItem.title)
            itemView.fileDuration.text = this.itemView.resources.getString(R.string.Duration, viewItem.duration)
            itemView.fileOwner.text = this.itemView.resources.getString(R.string.UploadedUser, viewItem.user.name)

            val requestOptions = RequestOptions().apply {
                fitCenter()
            }

            Glide.with(itemView.context)
                    .load(viewItem.thumbnailPath)
                    .apply(requestOptions)
                    .into(itemView.fileImage)
        }
    }

    interface OnFileItemSelectedListener {
        fun onFileItemSelectedListener(file: File)
    }
}

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}