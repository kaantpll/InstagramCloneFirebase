package com.example.instagramclonefirebase

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_upload.*
import java.sql.Timestamp
import java.util.*


class UploadActivity : AppCompatActivity() {

    var selecTedPicture : Uri? = null
    private lateinit var db : FirebaseFirestore
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()


        imageView2.setOnClickListener {
            image_permission()
        }



    }

     fun image_permission() {

         if (ContextCompat.checkSelfPermission(
                 this,
                 Manifest.permission.READ_EXTERNAL_STORAGE
             ) != PackageManager.PERMISSION_GRANTED
         ) {
             ActivityCompat.requestPermissions(
                 this,
                 arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                 1
             )
         } else {
             val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
             startActivityForResult(intent, 2)
         }
     }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        if (requestCode == 1) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent,2)
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if(requestCode == 2 && resultCode == Activity.RESULT_OK && data != null){
            selecTedPicture = data.data


            try {

                if(selecTedPicture !=null){
                    if(Build.VERSION.SDK_INT >= 28){
                        val source = ImageDecoder.createSource(contentResolver, selecTedPicture!!)
                        val bitmap = ImageDecoder.decodeBitmap(source)
                        imageView2.setImageBitmap(bitmap)
                    }
                    else{
                        val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver,selecTedPicture)
                        imageView2.setImageBitmap(bitmap)
                    }
                }

            }catch (e: Exception){
                e.printStackTrace()
            }

        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    fun uploadButton(view : View){

        val uuid = UUID.randomUUID()
        val imageName = "$uuid.jpg"

        val storage = FirebaseStorage.getInstance()
        val reference = storage.reference
        val imagesReferance = reference.child("images").child(imageName)

        if(selecTedPicture != null){
            imagesReferance.putFile(selecTedPicture!!).addOnSuccessListener { snapshot->
                val uploadedPicture = FirebaseStorage.getInstance().reference.child("images").child(imageName)
                uploadedPicture.downloadUrl.addOnSuccessListener { uri->
                    val downLoadUrl = uri.toString()
                    println(downLoadUrl)

                    val postMap = hashMapOf(
                        "downloadUrl" to downLoadUrl,
                        "useremail" to auth.currentUser!!.email.toString(),
                        "comment" to comment_text.text.toString(),
                        "date" to com.google.firebase.Timestamp.now()
                    )


                    db.collection("posts").add(postMap).addOnCompleteListener { task->
                        if(task.isComplete && task.isSuccessful){
                            Toast.makeText(this,"Yüklendi",Toast.LENGTH_SHORT).show()

                        }
                    }.addOnFailureListener { e->Toast.makeText(applicationContext,e.message.toString(),Toast.LENGTH_LONG).show() }
                }
            }
        }

    }


}