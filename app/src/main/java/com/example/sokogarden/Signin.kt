package com.example.sokogarden

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.loopj.android.http.RequestParams

class Signin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signin)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//        Find the two edit text a button and a text vow by use of their IDs
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val SigninButton = findViewById<Button>(R.id.signinBtn)
        val signupTextView = findViewById<TextView>(R.id.signupTxt)


//        Set an onclick listener such that when clicked it takes you to the signup page
        signupTextView.setOnClickListener {
            val intent = Intent(applicationContext, Signup::class.java)
            startActivity(intent)
        }

//        On click of the button sign in we need to interact with the api endpoint as we pass the two data info
        SigninButton.setOnClickListener {
            // Validate inputs
            if (email.text.toString().trim().isEmpty()) {
                email.error = "Please enter email"
                return@setOnClickListener
            }
            if (password.text.toString().trim().isEmpty()) {
                password.error = "Please enter password"
                return@setOnClickListener
            }

//            specify the api endpoint
            val api = "https://josephdebug.alwaysdata.net/api/signin"

//            Create a request params that will enable you to hold data in form of a bundle
            val data = RequestParams()

//            Append the data you want to send/ attach the email and password
            data.put("email", email.text.toString().trim())
            data.put("password", password.text.toString().trim())

//            Import the API helper
            val helper = ApiHelper(applicationContext)

//            By use of the function post_login inside of the helper class, post ur data
//            Note: ApiHelper.post_login handles navigation to MainActivity internally upon successful response
            helper.post_login(api, data)


//            clear the input fields after clicking the button
            email.text.clear()
            password.text.clear()

        }
    }
}
