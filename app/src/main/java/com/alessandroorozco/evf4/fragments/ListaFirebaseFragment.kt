package com.alessandroorozco.evf4.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alessandroorozco.evf4.activity.AgregarActivity
import com.alessandroorozco.evf4.databinding.FragmentListaFirebaseBinding
import com.google.firebase.firestore.FirebaseFirestore

class ListaFirebaseFragment : Fragment() {

    private lateinit var binding: FragmentListaFirebaseBinding
    private lateinit var firestore: FirebaseFirestore

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        binding= FragmentListaFirebaseBinding.inflate(inflater,container,false)

        binding.addButton.setOnClickListener {
            val intent = Intent(activity, AgregarActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }
}