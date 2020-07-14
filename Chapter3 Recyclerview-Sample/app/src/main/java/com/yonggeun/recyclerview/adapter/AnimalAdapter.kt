package com.yonggeun.recyclerview.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yonggeun.recyclerview.BR
import com.yonggeun.recyclerview.data.Animal
import com.yonggeun.recyclerview.databinding.CatlistdataBinding
import com.yonggeun.recyclerview.databinding.DoglistdataBinding

// Use LiveData<MutableList<Animal>>
// RecyclerView Xml app:item
// value = item
@BindingAdapter("item")
fun bindItem(recyclerView: RecyclerView, items: MutableList<Animal>){
    Log.e("TAG: ", "TAG")
    recyclerView.adapter?.run {
        if (this is AnimalAdapter){
            this.update(items)
        }
    }
}

class AnimalAdapter(
    private var animalsData: MutableList<Animal>,
    private val listener: ItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
        fun onItemLongClick(view: View, position: Int)
    }

    inner class CatViewHolder(private val binding: CatlistdataBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(animalsData: Animal) {

            binding.setVariable(BR.CatData, animalsData)

            itemView.setOnClickListener {
                listener.onItemClick(it, adapterPosition)
            }

            itemView.setOnLongClickListener {
                listener.onItemLongClick(it, adapterPosition)
                true
            }
        }
    }

    inner class DogViewHolder(private val binding: DoglistdataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(animalsData: Animal) {

            binding.setVariable(BR.DogData, animalsData)

            itemView.setOnClickListener {
                listener.onItemClick(it, adapterPosition)
            }

            itemView.setOnLongClickListener {
                listener.onItemLongClick(it, adapterPosition)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // Connect Xml
        // Cat: viewType = 0
        // Dog: viewType = 1
        return if (viewType == 0) {
            val catBinding = CatlistdataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            CatViewHolder(catBinding)
        } else {
            val dogBinding = DoglistdataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            DogViewHolder(dogBinding)
        }
    }

    override fun getItemCount(): Int {
        //List is Null or Empty size = 0 else List size
        return if (animalsData.isNullOrEmpty()) 0 else animalsData.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //BindViewHolder
        if (getItemViewType(position) == 0) {
            val catHolder = holder as CatViewHolder
            catHolder.bind(animalsData = animalsData[position])
        } else {
            val dogHolder = holder as DogViewHolder
            dogHolder.bind(animalsData = animalsData[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        val type = animalsData[position].type

        return if (type == "Cat")
            0
        else
            1
    }

    fun update(items: MutableList<Animal>){
        animalsData = items
        notifyDataSetChanged()
    }

}