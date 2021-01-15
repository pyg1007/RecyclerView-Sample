package com.weather.recyclerview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.weather.recyclerview.R
import com.weather.recyclerview.data.Animal

class AnimalAdapter(
    private var animalsData: MutableList<Animal>,
    private val listener: ItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
        fun onItemLongClick(view: View, position: Int)
    }

    inner class CatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(animalsData: Animal) {
            itemView.findViewById<ImageView>(R.id.type).setImageResource(R.mipmap.cat)
            itemView.findViewById<TextView>(R.id.animalName).text = animalsData.name
            itemView.findViewById<TextView>(R.id.phoneNumber).text = animalsData.phoneNumber

            itemView.findViewById<ConstraintLayout>(R.id.parentView).setOnClickListener {
                listener.onItemClick(it, adapterPosition)
            }

            itemView.findViewById<ConstraintLayout>(R.id.parentView).setOnLongClickListener {
                listener.onItemLongClick(it, adapterPosition)
                true
            }
        }
    }

    inner class DogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(animalsData: Animal) {
            itemView.findViewById<ImageView>(R.id.type).setImageResource(R.mipmap.dog)
            itemView.findViewById<TextView>(R.id.animalName).text = animalsData.name
            itemView.findViewById<TextView>(R.id.phoneNumber).text = animalsData.phoneNumber

            itemView.findViewById<ConstraintLayout>(R.id.parentView).setOnClickListener {
                listener.onItemClick(it, adapterPosition)
            }

            itemView.findViewById<ConstraintLayout>(R.id.parentView).setOnLongClickListener {
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
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.catlistdata, parent, false)
            CatViewHolder(view)
        } else {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.doglistdata, parent, false)
            DogViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        //List is Null or Empty size = 0 else List size
        return if (animalsData.isNullOrEmpty()) 0 else animalsData.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //BindViewHolder
        if (getItemViewType(position) == 0) {
            if(holder is CatViewHolder)
                holder.bind(animalsData = animalsData[position])
        } else {
            if (holder is DogViewHolder)
                holder.bind(animalsData = animalsData[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        val type = animalsData[position].type

        return if (type == "Cat")
            0
        else
            1
    }

    //Add List Data
    fun animalsDataAdd(animal: Animal) {
        animalsData.add(animal)
        notifyDataSetChanged()
    }

    //Delete List Data
    fun animalsDataDelete(position: Int) {
        animalsData.removeAt(position)
        notifyItemRemoved(position)
    }
}