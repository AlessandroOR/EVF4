package com.alessandroorozco.evf4.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import com.alessandroorozco.evf4.databinding.ActivityAgregarBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AgregarActivity : AppCompatActivity() {
    private lateinit var binding:ActivityAgregarBinding
    private lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgregarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firestore = Firebase.firestore
        binding.btnAgregar.setOnClickListener {

            val NombreCerveza:String=binding.txtNomCerveza.text.toString()

            val Tamaño:String=binding.txtTamaO.text.toString()
            val Precio:String=binding.txtPrecio.text.toString()
            if(NombreCerveza.isNotEmpty() && Tamaño.isNotEmpty()&&Precio.isNotEmpty()){ val cerveza= hashMapOf(

                "NombreCerveza" to NombreCerveza,
                "Tamaño" to Tamaño,
                "gender" to Precio
            )
                firestore.collection("cerveza")
                    .add(cerveza)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Se Añadio correctamente con id: ${it.id}",Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "No se Añadio la Cerveza Correctamente", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }
}