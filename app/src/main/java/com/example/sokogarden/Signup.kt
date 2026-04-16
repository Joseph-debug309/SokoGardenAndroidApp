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

class Signup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//        Find all views by their Ids
        val username = findViewById<EditText>(R.id.username)
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val phone = findViewById<EditText>(R.id.phone)
        val signupBtn = findViewById<Button>(R.id.signupBtn)
        val signinTextView = findViewById<TextView>(R.id.signinTxt)


//        Set an onclick listener such that when clicked it takes you to the signin page
        signinTextView.setOnClickListener {
            val intent = Intent(applicationContext, Signin::class.java)
            startActivity(intent)
        }

//        ON CLICK OF THE SIGNUP BUTTON WE WANT TO REGISTER A PERSON
        signupBtn.setOnClickListener {
            // Validate inputs
            if (username.text.toString().trim().isEmpty()) {
                username.error = "Please enter username"
                return@setOnClickListener
            }
            if (email.text.toString().trim().isEmpty()) {
                email.error = "Please enter email"
                return@setOnClickListener
            }
            if (password.text.toString().trim().isEmpty()) {
                password.error = "Please enter password"
                return@setOnClickListener
            }
            if (phone.text.toString().trim().isEmpty()) {
                phone.error = "Please enter phone"
                return@setOnClickListener
            }

//           Specify the API endpoint
            val api = "https://josephdebug.alwaysdata.net/api/signup"

//            Create a request params where we are going to hold all the data in form of a bundle
            val data = RequestParams()

//            Add the username, email, password and phone
            data.put("username", username.text.toString().trim())
            data.put("email", email.text.toString().trim())
            data.put("password", password.text.toString().trim())
            data.put("phone", phone.text.toString().trim())

//            Import the helper class
            val helper = ApiHelper(applicationContext)

//            Inside the helper function, access the function post inside of the helper class
//            Note: ApiHelper.post handles navigation to MainActivity internally upon successful response
            helper.post(api, data)

//            clear the input fields after clicking the button
            username.text.clear()
            email.text.clear()
            password.text.clear()
            phone.text.clear()
        }

    }
}