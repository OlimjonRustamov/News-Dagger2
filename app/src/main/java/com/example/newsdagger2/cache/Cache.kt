package com.example.newsdagger2.cache

import android.content.Context
import android.content.SharedPreferences

class Cache private constructor(context: Context) {

    init {
        preferences = context.getSharedPreferences("details", Context.MODE_PRIVATE)
    }

    fun saveFirstEntrance() {
        val t = preferences.getBoolean("entrance", false)
        if (!t) {
            editor = preferences.edit().putBoolean("entrance", true)
            editor!!.apply()
        }
    }

    fun getFirstEntrance():Boolean {
        return preferences.getBoolean("entrance", false)
    }

    fun saveNightMode(night_mode: Boolean) {
        editor = preferences.edit().putBoolean("night_mode", night_mode)
        editor!!.apply()
    }

    fun getNightMode():Boolean {
        return preferences.getBoolean("night_mode", false)
    }

//    fun savePhone(phone: PhoneModel) {
//        editor = preferences.edit()
//        val size = preferences.getInt("soni", 0)
//        editor!!.putString("company$size", phone.company)
//        editor!!.putString("name$size", phone.name)
//        editor!!.putString("description$size", phone.description)
//
//        editor!!.putInt("soni", size + 1)
//        editor!!.apply()
//    }
//
//    fun hammasi(): ArrayList<PhoneModel> {
//        val taomlar = ArrayList<PhoneModel>()
//        val size = preferences.getInt("soni", 0)
//        for (i in 0 until size) {
//            taomlar.add(
//                PhoneModel(
//                    preferences.getString("company$i", "")!!,
//                    preferences.getString("name$i", "")!!,
//                    preferences.getString("description$i", "")!!
//                )
//            )
//        }
//        return taomlar
//    }

    fun clear() {
        preferences.edit().clear().apply()
    }

    companion object {
        var instance: Cache? = null
            private set
        private lateinit var preferences: SharedPreferences
        private var editor: SharedPreferences.Editor? = null

        fun init(context: Context) {
            if (instance == null) {
                instance = Cache(context)
            }
        }
    }

}