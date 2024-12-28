package com.example.labandroidpro.Fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.labandroidpro.FileManagement
import com.example.labandroidpro.R
import java.io.File



class Alarm_list : Fragment {

    val contestos :Context;

    val file: File = FileManagement.getfile()

    constructor(ctx:Context)
    {
        contestos = ctx;
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alarm_list, container, false);
     }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val lista = view.findViewById<ListView>(R.id.Lv_ListaSveglie);
        var adattatore = MyAdapter(view.context);
        lista.setAdapter(adattatore)

    }

}