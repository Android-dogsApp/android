//package com.example.dogsandddapters
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//
////
////import android.view.LayoutInflater
////import android.view.View
////import android.view.ViewGroup
////import android.widget.TextView
////import androidx.recyclerview.widget.RecyclerView
////
////class DogAdapter(private val dogBreeds: List<DogBreed>) : RecyclerView.Adapter<DogAdapter.ViewHolder>() {
////
////    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
////        val breedTextView: TextView = itemView.findViewById(R.id.breedTextView)
////        val subBreedsTextView: TextView = itemView.findViewById(R.id.subBreedsTextView)
////    }
////
////    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
////        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_rest_api, parent, false)
////        return ViewHolder(view)
////    }
////
////    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
////        val dogBreed = dogBreeds[position]
////
////        holder.breedTextView.text = dogBreed.breed
////        holder.subBreedsTextView.text = dogBreed.subBreeds.joinToString(", ")
////    }
////
////    override fun getItemCount(): Int {
////        return dogBreeds.size
////    }
////}
//class DogAdapter(private val dogBreeds: DogBreed) : RecyclerView.Adapter<DogBreedViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogBreedViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_rest_api, parent, false)
//        return DogBreedViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: DogBreedViewHolder, position: Int) {
//        val breedName = dogBreeds.message.keys.toList()[position]
//        val subBreeds = dogBreeds.message[breedName] ?: emptyList()
//
//        holder.breedNameTextView.text = breedName
//        holder.subBreedsTextView.text = subBreeds.joinToString(", ")
//    }
//
//    override fun getItemCount(): Int = dogBreeds.message.size
//}
