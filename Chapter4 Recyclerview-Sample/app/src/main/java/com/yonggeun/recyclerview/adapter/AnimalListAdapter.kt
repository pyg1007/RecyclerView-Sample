package com.yonggeun.recyclerview.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yonggeun.recyclerview.R
import com.yonggeun.recyclerview.data.Animal
import com.yonggeun.recyclerview.databinding.CatlistdataBinding
import com.yonggeun.recyclerview.databinding.DoglistdataBinding
import com.yonggeun.recyclerview.util.BaseDiffUtil

/**
 * RecyclerView
 * Class: AnimalListAdapter
 * Created by pyg10.
 * Created On 2021-01-15.
 * Description:
 */
class AnimalListAdapter(private val listener: ItemClickListener): ListAdapter<Animal, RecyclerView.ViewHolder>(BaseDiffUtil<Animal>()) {

    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
        fun onItemLongClick(view: View, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // Connect Xml
        // Cat: viewType = 0
        // Dog: viewType = 1
        return if (viewType == 0)
            CatViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.catlistdata, parent, false))
        else
            DogViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.doglistdata, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == 0){
            if (holder is CatViewHolder)
                holder.bind()
        }else{
            if (holder is DogViewHolder)
                holder.bind()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).type == "Cat")
            0
        else
            1
    }

    inner class CatViewHolder(private val binding: CatlistdataBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(){
            binding.catData = getItem(adapterPosition)

            binding.parentView.setOnClickListener {
                listener.onItemClick(it, adapterPosition)
            }

            binding.parentView.setOnLongClickListener {
                listener.onItemLongClick(it, adapterPosition)
                true
            }
        }
    }

    inner class DogViewHolder(private val binding: DoglistdataBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(){
            binding.dogData = getItem(adapterPosition)

            binding.parentView.setOnClickListener {
                listener.onItemClick(it, adapterPosition)
            }

            binding.parentView.setOnLongClickListener {
                listener.onItemLongClick(it, adapterPosition)
                true
            }
        }
    }

}