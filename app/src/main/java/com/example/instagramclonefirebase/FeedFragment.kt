package com.example.instagramclonefirebase

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagramclonefirebase.adapter.InstagramAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_feed.*
import java.security.Timestamp

class FeedFragment : Fragment() {

    private lateinit var auth : FirebaseAuth
    private lateinit var db : FirebaseFirestore


    var userEmailFromFB : ArrayList<String> = ArrayList()
    var userCommentFromFB : ArrayList<String> = ArrayList()
    var userImageFromFB : ArrayList<String> = ArrayList()

    var adapter : InstagramAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        getDatabase()

        var layoutManeger = LinearLayoutManager(context)
        rv.layoutManager = layoutManeger

        adapter = InstagramAdapter(userEmailFromFB,userCommentFromFB,userImageFromFB)
        rv.adapter = adapter

    }

    private fun getDatabase() {
        db.collection("posts").orderBy("date",Query.Direction.DESCENDING).addSnapshotListener{snapshot,e ->
            if(e != null){
                Toast.makeText(context,e.message.toString(),Toast.LENGTH_SHORT).show()
            }
            else{
                if(snapshot !=null){
                    if(!snapshot.isEmpty){
                        userImageFromFB.clear()
                        userCommentFromFB.clear()
                        userEmailFromFB.clear()

                        val doc = snapshot.documents

                        for(document in doc){
                            val comment = document.get("comment") as String
                            val user = document.get("useremail") as String
                            val downloadUrl = document.get("downloadUrl") as String
                            val date = document.get("date")

                            userEmailFromFB.add(user)
                            userCommentFromFB.add(comment)
                            userImageFromFB.add(downloadUrl)

                            adapter!!.notifyDataSetChanged()

                        }

                    }
                }
            }
        }
    }


}
