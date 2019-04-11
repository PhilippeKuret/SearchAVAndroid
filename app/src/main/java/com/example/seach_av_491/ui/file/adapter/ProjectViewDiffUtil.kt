package com.example.seach_av_491.ui.file.adapter

import android.support.v7.util.DiffUtil
import com.example.seach_av_491.model.File

class ProjectViewDiffUtil : DiffUtil.ItemCallback<File>() {
    override fun areItemsTheSame(p0: File, p1: File): Boolean {
        return (p0.id == p1.id)
    }

    override fun areContentsTheSame(p0: File, p1: File): Boolean {
        return (p0.id == p1.id && p0.description == p1.description)
    }
}