package com.example.bolkarassignment.response

import android.util.Log

data class DataBody(
    val _id : String? = null,
    val roomid : String? = null,
    val topic : String? = null,
    val host : PeopleResponseBody? = null,
    val __v : Int = 0,
    val members : List<PeopleResponseBody> = listOf(),
    val speakers : List<PeopleResponseBody> = listOf(),
    val moderators : List<PeopleResponseBody> = listOf(),
    val start_time : String? = null,
    val total_members : Int = 0,
    val appId : String? = null,
    val token : String? = null,
    val raiseAllow : Boolean = false,
    val type : Int = 0,
    val volume : Int = 0,
    val version : Int = 0,
    val modHistory : List<String> = listOf()
) {

    //Combined list of speakers, moderators and host for top recycler view
    val mainList : List<PeopleResponseBody>
        get() {
            val combinedList : MutableList<PeopleResponseBody> = (speakers + moderators) as MutableList<PeopleResponseBody>
            if (host != null) {
                combinedList.add(host)
            }
            Log.d("combinedList", "The list is $combinedList")
            return combinedList
        }


}
