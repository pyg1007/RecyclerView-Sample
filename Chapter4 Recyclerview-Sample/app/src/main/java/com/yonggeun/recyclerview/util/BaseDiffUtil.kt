package com.yonggeun.recyclerview.util

import androidx.recyclerview.widget.DiffUtil

/**
 * RecyclerView
 * Class: BaseDiffUtil
 * Created by pyg10.
 * Created On 2021-01-15.
 * Description:
 */
class BaseDiffUtil<T>: DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = oldItem.hashCode() == newItem.hashCode()

}