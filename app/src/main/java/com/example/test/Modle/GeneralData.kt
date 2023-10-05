package com.example.test.Modle

import android.app.Application

class GeneralData :  Application(){
    companion object {
        var articlesList: ArrayList<Article> = ArrayList()
        var selectedLanguage:String="us"
        var isChceked:Boolean=true
        var isChcekedMode:Boolean=true
        var isChcekedLogin:Boolean=false


    }}