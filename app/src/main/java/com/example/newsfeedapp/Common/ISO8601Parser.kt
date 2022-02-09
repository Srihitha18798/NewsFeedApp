package com.example.newsfeedapp.Common

import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*

object ISO8601Parser{
    fun parse(params: String): Date{
        var input=params
        val df=SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz")
        if (input.endsWith("Z"))
           input= input.substring(0,input.length-1)+"GMT-00:00"

        else
        {
            val inset=6
            val startText=input.subSequence(0,input.length-inset)
            val endText=input.substring(input.length-inset,input.length)

            input=StringBuilder(startText).append("GMT").append(endText).toString()
        }
        return df.parse(input)
    }
    fun toString(date : Date): String{
        val df=SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz")
        val tz=TimeZone.getTimeZone("UTC")

        df.timeZone=tz

        val output=df.format(date)

        val inset0=9
        val inset1=6

        val s0=output.substring(0,output.length-inset0)
        val s1=output.subSequence(output.length-inset1,output.length)

        var result=s0+s1
        result=result.replace("UTC".toRegex(),"+00:00")
        return result

    }
}