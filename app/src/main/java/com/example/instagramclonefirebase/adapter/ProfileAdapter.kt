package com.example.instagramclonefirebase.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramclonefirebase.R
import com.squareup.picasso.Picasso

class ProfileAdapter(val profileImageList : ArrayList<String>) : RecyclerView.Adapter<ProfileAdapter.MyViewHolder>() {

    class MyViewHolder(view : View) : RecyclerView.ViewHolder(view){

        var recyclerViewImage : ImageView? = null

        init {

            recyclerViewImage = view.findViewById(R.id.profile_card_image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inf = LayoutInflater.from(parent.context)
        val view = inf.inflate(R.layout.profile_card,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        Picasso.get().load(profileImageList[position]).into(holder.recyclerViewImage)
    }

    override fun getItemCount(): Int {
        return profileImageList.size
    }
}