package com.example.sokogarden

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.loopj.android.http.RequestParams

class paymentactivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_payment)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//        Find the views by use of their ids
        val txtname = findViewById<TextView>(R.id.txtProductName)
        val txtcost = findViewById<TextView>(R.id.txtProductCost)
        val imgProduct = findViewById<ImageView>(R.id.imgProduct)
        val ProductDescription = findViewById<TextView>(R.id.product_description)


//        Retrieve the data first from the previous activity(mainactivity)
        val name = intent.getStringExtra("product_name")
        val cost = intent.getIntExtra("product_cost", 0)
        val product_photo = intent.getStringExtra("product_photo")
        val description = intent.getStringExtra("product_description")

//        Update the textview with the data passed from the previous activity
        txtname.text = name
        txtcost.text = "kes $cost"
        ProductDescription.text = description

//        Specify the image url
        val imageUrl = "https://josephdebug.alwaysdata.net/static/images/${product_photo}"

        //Load image using Glide, Load Faster with Glide
        Glide.with(this)
            .load(imageUrl )
            .placeholder(R.drawable.ic_launcher_background) // Make sure you have a placeholder image
            .into(imgProduct)

//        find the edittext and paynow using their ids
        val phone = findViewById<EditText>(R.id.phone)
        val btnPay = findViewById<Button>(R.id.pay)

//        Set clicklistener on the paynow button
        btnPay.setOnClickListener {
//            Specify the endpoint for the payment
            val api = "https://josephdebug.alwaysdata.net/api/mpesa_payment"

//            Create request params
            val data = RequestParams()

//            Insert data into the request params
            data.put("amount", cost)
            data.put("phone", phone.text.toString().trim())

//            Import the helper class
            val helper = ApiHelper(applicationContext)

//            Access the post function from the helper class
            helper.post(api, data)

//            Clear details after button click
            phone.text.clear()
        }


    }
}