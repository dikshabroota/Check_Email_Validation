package com.example.mailvalidation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Composable
fun rememberMailBoxState(
    coroutineScope: CoroutineScope = rememberCoroutineScope())
        = remember(coroutineScope) {
    MailBoxState(coroutineScope = coroutineScope)
}

class MailBoxState(private val coroutineScope: CoroutineScope) {

    private val _response = MutableStateFlow(EmailResponse())
    val response : StateFlow<EmailResponse>
        get() = _response

    private val _valid = MutableStateFlow("")
    val valid : StateFlow<String>
        get() = _valid

    private val _disposable = MutableStateFlow("")
    val disposable : StateFlow<String>
        get() = _disposable

    private val _free = MutableStateFlow("")
    val free : StateFlow<String>
        get() = _free

    private val _score = MutableStateFlow("")
    val score : StateFlow<String>
        get() = _score

    private val _mxRecord = MutableStateFlow("")
    val mxRecord : StateFlow<String>
        get() = _mxRecord

    fun checkEmail(emailState:String){
        try{
            coroutineScope.launch{
                _response.value = Api.service.checkEmail(key = Api.API_KEY, email = emailState)
                Log.d("email2", "${response.value}")

                _valid.value = if(response.value.format_valid) "Format is valid" else "Format is invalid"
                _disposable.value = if(response.value.disposable) "Email is disposable" else "Email is not disposable"
                _free.value = if(response.value.free) "Domain is free" else "Domain is paid"
                 _mxRecord.value = if(response.value.mx_found) "Domain can receive mails" else "Domain cannot receive mails"
                _score.value = "Score is ${response.value.score}"
            }

        }catch (e:Exception){

        }
    }
}