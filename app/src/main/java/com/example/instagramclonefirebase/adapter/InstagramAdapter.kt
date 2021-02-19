package com.example.instagramclonefirebase.adapter

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramclonefirebase.R
import com.squareup.picasso.Picasso

class InstagramAdapter(val emailList : ArrayList<String>, val commentList : ArrayList<String>, val imageList : ArrayList<String>) : RecyclerView.Adapter<InstagramAdapter.MyViewHolder>() {

    class MyViewHolder(view : View) : RecyclerView.ViewHolder(view){

        var recyclerViewText : TextView? = null
        var recyclerViewCommentText : TextView? = null
        var recyclerViewImageVeiw : ImageView? = null


        init {
            recyclerViewText = view.findViewById(R.id.userName_card)
            recyclerViewCommentText = view.findViewById(R.id.comment_card)
            recyclerViewImageVeiw = view.findViewById(R.id.card_image)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inf = LayoutInflater.from(parent.context)
        val view = inf.inflate(R.layout.card_item,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.recyclerViewText?.text = emailList[position]
        holder.recyclerViewCommentText?.text = commentList[position]
        Picasso.get().load(imageList[position]).into(holder.recyclerViewImageVeiw)
    }

    override fun getItemCount(): Int {
        return emailList.size
    }
}