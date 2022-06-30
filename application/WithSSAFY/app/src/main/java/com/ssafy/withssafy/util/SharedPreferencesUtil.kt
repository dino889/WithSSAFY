package com.ssafy.withssafy.util

import android.content.Context
import android.content.SharedPreferences
import com.ssafy.withssafy.src.dto.User

class SharedPreferencesUtil (context: Context) {
    val SHARED_PREFERENCES_NAME = "withSsafy_preference"
    val COOKIES_KEY_NAME = "cookies"
    var preferences: SharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    //사용자 정보 저장
    fun addUser(user: User, classRoomId : Int){
        val editor = preferences.edit()
        editor.putInt("id", user.id)
        editor.putString("studentId", user.studentId)
        editor.putInt("classRoomId", classRoomId)
        editor.apply()
    }

    fun getUser(): User {
        val id = preferences.getInt("id", 0)
        val studentId = preferences.getString("studentId", null)
        val classRoomId = preferences.getInt("classRoomId", 0)
        if (id != 0) {
            return User(id, studentId, classRoomId)
        } else {
            return User()
        }
    }

    fun deleteUser(){
        //preference 지우기
        val editor = preferences.edit()
        editor.clear()
        editor.apply()
    }

    fun addUserCookie(cookies: HashSet<String>) {
        val editor = preferences.edit()
        editor.putStringSet(COOKIES_KEY_NAME, cookies)
        editor.apply()
    }

    fun getUserCookie(): MutableSet<String>? {
        return preferences.getStringSet(COOKIES_KEY_NAME, HashSet())
    }

    fun getString(key:String): String? {
        return preferences.getString(key, null)
    }

    fun deleteUserCookie() {
        preferences.edit().remove(COOKIES_KEY_NAME).apply()
    }

    fun setAutoLogin(id: Int) {
        val editor = preferences.edit()
        editor.putInt("auto", id)
        editor.apply()
    }

    fun getAutoLogin() : Int {
        return preferences.getInt("auto", -1)
    }

    fun deleteAutoLogin() {
        preferences.edit().remove("auto").apply()
    }

}