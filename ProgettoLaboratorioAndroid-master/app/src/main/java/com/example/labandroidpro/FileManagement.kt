package com.example.labandroidpro

import android.util.Log
import java.io.File

class FileManagement {

    companion object{
        var path:String="";


        fun nuovo(string:File) {
            // Create your directory:
            val directory = File(string,"tmp")
            directory.mkdirs()
            val file = File(directory,"test.txt")
            path = directory.toString()+"/"+"text.txt"
        }

        fun GetnumeroAlarm(): Int {
            return getAlarms().size
        }

        fun getfile():File {
            var file:File = File(path);
            return file;
        }

        fun saveAlarm(input: Alarm ){
            var output=input.getName()+"║"
            var file:File = File(path)
            var giorni = input.getDays()
            var days=""
            var i=0
            for (giorno in giorni) {
                if(giorno)
                    days+="T"
                else
                    days+="F"
                i++
                if(i<7)
                    days+="█"
            }
            output+=days+"║"
            output+=input.getLatitude().toString()+"║"
            output+=input.getLongitude().toString()+"║"
            output+=input.getRadius().toString()+"║"
            if(input.getActive())
                output+="T"+"║"
            else
                output+="F"+"║"
            output+=input.getAddress()
            file.appendText(output+"\n")

        }

        fun getLastAlarm():Alarm{
            if(getAlarms().isNotEmpty())
                return getAlarms()[getAlarms().size-1]
            else
                return Alarm()
        }

        fun getAlarms(): List<Alarm>{
            var file:File = File(path)
            val output=mutableListOf<Alarm>()
            try{
                var max=file.readText().split("\n").size-1
                var i=0
                while(i<max) {
                    var input=file.readText().split("\n")[i]
                    var temp=Alarm()
                    var sett: Array<Boolean> = arrayOf(false, false, false, false, false, false, false)
                    temp.setName(input.split("║")[0])
                    var j=0
                    for(day in input.split("║")[1].split("█")){
                        if(day.equals("T"))
                           sett[j]=true
                        else
                            sett[j]=false
                        j++
                    }
                    temp.setDays(sett)
                    temp.setLatitude(input.split("║")[2].toDouble())
                    temp.setLongitude(input.split("║")[3].toDouble())
                    temp.setRadius(input.split("║")[4].toDouble())
                    if(input.split("║")[5].equals("T"))
                        temp.setActive(true)
                    else
                        temp.setActive(false)
                    temp.setAddress(input.split("║")[6])
                    output.add(temp)
                    i++
                }
            }catch (e: Exception){
                Log.e("FileManagement", e.toString())
            }
            return output
        }

        fun getAlarmById(i: Int): Alarm{
            return getAlarms()[i]
        }

        fun getAlarmByName(n: String):Alarm{
            var alarms= getAlarms()
            var output=Alarm()
            var i=0
            for(i in alarms.indices){
                if(alarms[i].getName().toLowerCase().equals(n.toLowerCase())){
                    output=alarms[i]
                    break
                }
            }
            return output
        }

        fun updateAlarmByName(name: String, input: Alarm){
            var file:File = File(path)
            if(!getAlarmByName(name).getName().equals("")){
                var alarms= getAlarms()
                file.writeText("")
                for(i in alarms.indices){
                    if(alarms[i].getName().toLowerCase().equals(name.toLowerCase())){
                        alarms[i].setActive(input.getActive())
                        alarms[i].setLatitude(input.getLatitude())
                        alarms[i].setLongitude(input.getLongitude())
                        alarms[i].setRadius(input.getRadius())
                        alarms[i].setDays(input.getDays())
                        alarms[i].setAddress(input.getAddress())
                        alarms[i].setName(name)
                    }
                    var output=alarms[i].getName()+"║"
                    var giorni = alarms[i].getDays()
                    var days=""
                    var j=0
                    for (giorno in giorni) {
                        if(giorno)
                            days+="T"
                        else
                            days+="F"
                        j++
                        if(j<7)
                            days+="█"
                    }
                    output+=days+"║"
                    output+=alarms[i].getLatitude().toString()+"║"
                    output+=alarms[i].getLongitude().toString()+"║"
                    output+=alarms[i].getRadius().toString()+"║"
                    if(alarms[i].getActive())
                        output+="T"+"║"
                    else
                        output+="F"+"║"
                    output+=alarms[i].getAddress()
                    file.appendText(output+"\n")
                }
            }
            else{
                saveAlarm(input)
            }
        }

        fun updateAlarmById(id: Int, input: Alarm){
            var file:File = File(path)
            if(!getAlarmById(id).getName().equals("")){
                var alarms= getAlarms()
                file.writeText("")
                alarms[id].setActive(input.getActive())
                alarms[id].setLatitude(input.getLatitude())
                alarms[id].setLongitude(input.getLongitude())
                alarms[id].setRadius(input.getRadius())
                alarms[id].setDays(input.getDays())
                alarms[id].setName(input.getName())
                alarms[id].setAddress(input.getAddress())
                for(i in alarms.indices){
                    var output=alarms[i].getName()+"║"
                    var giorni = alarms[i].getDays()
                    var days=""
                    var j=0
                    for (giorno in giorni) {
                        if(giorno)
                            days+="T"
                        else
                            days+="F"
                        j++
                        if(j<7)
                            days+="█"
                    }
                    output+=days+"║"
                    output+=alarms[i].getLatitude().toString()+"║"
                    output+=alarms[i].getLongitude().toString()+"║"
                    output+=alarms[i].getRadius().toString()+"║"
                    if(alarms[i].getActive())
                        output+="T"+"║"
                    else
                        output+="F"+"║"
                    output+=alarms[i].getAddress()
                    file.appendText(output+"\n")
                }
            }
            else{
                saveAlarm(input)
            }
        }

        fun deleteAlarmByName(name: String){
            var file:File = File(path)
            if(!getAlarmByName(name).getName().equals("")){
                var alarms= getAlarms()
                file.writeText("")
                for(i in alarms.indices){
                    if(!alarms[i].getName().toLowerCase().equals(name.toLowerCase())){
                        var output=alarms[i].getName()+"║"
                        var giorni = alarms[i].getDays()
                        var days=""
                        var j=0
                        for (giorno in giorni) {
                            if(giorno)
                                days+="T"
                            else
                                days+="F"
                            j++
                            if(j<7)
                                days+="█"
                        }
                        output+=days+"║"
                        output+=alarms[i].getLatitude().toString()+"║"
                        output+=alarms[i].getLongitude().toString()+"║"
                        output+=alarms[i].getRadius().toString()+"║"
                        if(alarms[i].getActive())
                            output+="T"+"║"
                        else
                            output+="F"+"║"
                        output+=alarms[i].getAddress()
                        file.appendText(output+"\n")
                    }
                }
            }
        }

        fun deleteAlarmById(id: Int){
            var file:File = File(path)
            if(!getAlarmById(id).getName().equals("")){
                var alarms= getAlarms()
                file.writeText("")
                for(i in alarms.indices){
                    if(i!=id){
                        var output=alarms[i].getName()+"║"
                        var giorni = alarms[i].getDays()
                        var days=""
                        var j=0
                        for (giorno in giorni) {
                            if(giorno)
                                days+="T"
                            else
                                days+="F"
                            j++
                            if(j<7)
                                days+="█"
                        }
                        output+=days+"║"
                        output+=alarms[i].getLatitude().toString()+"║"
                        output+=alarms[i].getLongitude().toString()+"║"
                        output+=alarms[i].getRadius().toString()+"║"
                        if(alarms[i].getActive())
                            output+="T"+"║"
                        else
                            output+="F"+"║"
                        output+=alarms[i].getAddress()
                        file.appendText(output+"\n")
                    }
                }
            }
        }
    }
}