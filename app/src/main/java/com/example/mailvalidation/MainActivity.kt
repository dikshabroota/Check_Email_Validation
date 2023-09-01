package com.example.mailvalidation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mailvalidation.ui.theme.MailValidationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val appState = rememberMailBoxState()
            val response = appState.response.collectAsState()
            var emailState by remember{
                mutableStateOf("")
            }

            MailValidationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    EmailScreen(value = emailState,
                        onValueChange = {
                            emailState = it
                        },
                        onButtonClicked = {
                            if(emailState.isNotEmpty()){
                                appState.checkEmail(emailState = emailState)
                            }
                        },
                        state = appState)
                }
            }
            Log.d("email","${response.value}")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailScreen(value: String, onValueChange: (String) -> Unit={},
                onButtonClicked: ()-> Unit={}, state : MailBoxState){

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
        OutlinedTextField(value = value, onValueChange = onValueChange)
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = " ${state.valid.collectAsState().value}",
                modifier = Modifier.padding(8.dp),
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = " ${state.mxRecord.collectAsState().value}",
                modifier = Modifier.padding(8.dp),
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = " ${state.disposable.collectAsState().value}",
                modifier = Modifier.padding(8.dp),
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = " ${state.free.collectAsState().value}",
                modifier = Modifier.padding(8.dp),
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = " ${state.score.collectAsState().value}",
                modifier = Modifier.padding(8.dp),
                fontWeight = FontWeight.ExtraBold
            )
        }

        OutlinedButton(onClick = onButtonClicked) {
            Text(text = "Check Email")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MailValidationTheme {
        EmailScreen(value = "", state = rememberMailBoxState())
    }
}