package com.example.mailvalidation

data class EmailResponse(
    val email : String="",
    val did_you_mean : String="",
    val user : String="",
    val domain : String="",
    val format_valid : Boolean=false,
    val mx_found : Boolean=false,
    val smtp_check : Boolean=false,
    val catch_all : Boolean=false,
    val role : Boolean=false,
    val disposable : Boolean=false,
    val free : Boolean=false,
    val score : Double=0.0 )