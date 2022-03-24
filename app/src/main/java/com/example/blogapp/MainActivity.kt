package com.example.blogapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = FirebaseFirestore.getInstance()

        /*//consultar informacion de un solo document
        
        db.collection("ciudades").document("LA").get().addOnSuccessListener { documet ->
            documet?.let {
                Log.d("Firebase", "documentSnapshop data: ${documet.data}")
                val ciudad = documet.toObject(Ciudad::class.java)

                Log.d("Firebase", "population: ${ciudad?.population}")
                Log.d("Firebase", "color: ${ciudad?.color}")
            }

        }.addOnFailureListener { error ->
            Log.e("FirebaseError", error.toString())
        }*/
        
        //consultar y ver en tiempo real cambios 
        db.collection("ciudades").document("LA").addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
            documentSnapshot?.let { document->
                Log.d("Firebase", "documentSnapshop data: ${document.data}")
                val ciudad = document.toObject(Ciudad::class.java)

                Log.d("Firebase", "population: ${ciudad?.population}")
                Log.d("Firebase", "color: ${ciudad?.color}")


            }
        }

        //Ingresar Informacion
        db.collection("ciudades").document("NY").set(Ciudad(300000,"red")).addOnSuccessListener {
            Log.d("Firebase", "se guardo correctamente la ciudad")
        }.addOnFailureListener { error->
            Log.e("FirebaseError", error.toString())
        }
    }
}

    data class  Ciudad(val population:Int = 0,
                       val color:String ="")