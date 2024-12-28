package com.example.labandroidpro.Fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat.startActivity
import com.example.labandroidpro.*


class MyAdapter: BaseAdapter {


    var infilater:LayoutInflater;
    var context : Context;
    var lista_Da_Stampare = ArrayList<String>();
    var lista_Da_Attivare = ArrayList<Boolean>();



    constructor(ctx: Context)
    {
        lista_Da_Stampare= RiempiLista();
        this.context = ctx;
        infilater = LayoutInflater.from(context);
    }

    override fun getCount(): Int {
        return FileManagement.GetnumeroAlarm();
    }

    override fun getItem(p0: Int): Any {
        return lista_Da_Stampare[p0];
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }
    fun  RiempiLista(): ArrayList<String>  {
        var listaDaStampare = ArrayList<String>();
        var temp = FileManagement.getAlarms();
        var i =0;
        for(i in temp.indices){
            listaDaStampare.add(temp[i].getName())
            lista_Da_Attivare.add(temp[i].getActive())
        }

        return listaDaStampare;
    }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            var p1 = infilater.inflate(R.layout.alarm_item, null);
            var Nominativo = p1.findViewById<TextView>(R.id.Tv_nome);
            var Info_Button = p1.findViewById<ImageButton>(R.id.Bt_Info);
            var Modify_button = p1.findViewById<ImageButton>(R.id.Bt_Modifica);
            var Delete_Button = p1.findViewById<ImageButton>(R.id.bt_delete);
            var attivo = p1.findViewById<Switch>(R.id.sw_attiva)
            Nominativo.setText(lista_Da_Stampare[p0])
            attivo.isChecked = lista_Da_Attivare[p0]

            attivo.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
                var nome = p0;
                if(isChecked){

                    var nome = Nominativo.text as String
                    var alarm = FileManagement.getAlarmByName(nome)
                    alarm.setActive(true)
                    FileManagement.updateAlarmByName(nome,alarm)
                    Toast.makeText(p1.context, "Elemento attivato", Toast.LENGTH_SHORT).show()

                }else
                {

                    var nome = Nominativo.text as String
                    var alarm = FileManagement.getAlarmByName(nome)
                    alarm.setActive(false)
                    FileManagement.updateAlarmByName(nome,alarm)
                    Toast.makeText(p1.context, "Elemento disattivato", Toast.LENGTH_SHORT).show()
                }
            })

            Info_Button.setOnClickListener {

                val obj = Bundle()
                val stringa = Nominativo.text
                obj.putString("nome", stringa.toString())
                val intent = Intent(context, PopPage::class.java)
                intent.putExtra("nome",stringa)
                startActivity(context,intent,obj)
            }

            Modify_button.setOnClickListener()
            {
                val bundle = Bundle()
                val stringa = Nominativo.text
                bundle.putString("nome", stringa.toString())
                val intent = Intent(context, New_position::class.java)
                intent.putExtra("nome",stringa)
                startActivity(context,intent,bundle)
            }

            Delete_Button.setOnClickListener()
            {
                val stringa = Nominativo.text as String
                FileManagement.deleteAlarmByName(stringa)
                Toast.makeText(
                    context,
                    "Sveglia eliminata, prego aggiornare la pagina",
                    Toast.LENGTH_SHORT
                ).show()
            }


        return p1;
    }
}

