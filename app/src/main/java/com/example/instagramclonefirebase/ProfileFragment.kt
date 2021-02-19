package com.example.instagramclonefirebase

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagramclonefirebase.adapter.InstagramAdapter
import com.example.instagramclonefirebase.adapter.ProfileAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_feed.*
import kotlinx.android.synthetic.main.fragment_feed.rv
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    private lateinit var auth : FirebaseAuth
    private lateinit var db : FirebaseFirestore

    var userImageFromFB : ArrayList<String> = ArrayList()

    var adapter : ProfileAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profile_img.setImageResource(R.drawable.f)
        username_profile.setText("kaantpll")

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        getDb()

        var layoutManager = GridLayoutManager(context,2)
        rv.layoutManager = layoutManager

        adapter = ProfileAdapter(userImageFromFB)
        rv.adapter = adapter

        username_profile.setText(auth.currentUser!!.email.toString())

    }

    private fun getDb() {

        db.collection("posts").orderBy("date",Query.Direction.DESCENDING).addSnapshotListener{
            snapshot,e ->

            if(e != null){
                Toast.makeText(context,e.message.toString(), Toast.LENGTH_SHORT).show()
            }
            else{
                if(snapshot !=null){

                    if(!snapshot.isEmpty){
                        userImageFromFB.clear()

                        var doc = snapshot.documents

                        for(document in doc){
                            val imageProfile = document.get("downloadUrl") as String

                            userImageFromFB.add(imageProfile)

                            adapter?.notifyDataSetChanged()
                        }
                    }

                }
            }
        }

    }
}