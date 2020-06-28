package com.example.androidbatch


import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.edit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sharedPref = getSharedPreferences("contact", Context.MODE_PRIVATE)
        val key = findViewById<EditText>(R.id.key)
        val value = findViewById<EditText>(R.id.value)
        val dataName = findViewById<TextView>(R.id.name)

        val btn  =  findViewById<Button>(R.id.btn)
        val btn2  =  findViewById<Button>(R.id.btn2)

        val checkKey = findViewById<EditText>(R.id.check_key)
        btn.setOnClickListener {
            val name =  key.text.toString().trim()
            val phone = value.text.toString().trim()


           sharedPref.edit()
               .putString(name, phone)
               .apply()
            Toast.makeText(this, "data added", Toast.LENGTH_LONG).show()
        }

        btn2.setOnClickListener {
            val nameKey = checkKey.text.toString().trim()
            val s =  sharedPref.getString(nameKey, "notFound")
            dataName.text = s
            val number = "tel:" + s.toString()
            val i = Intent(Intent.ACTION_CALL)

            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CALL_PHONE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                i.data = Uri.parse(number)
                startActivity(i)
            }else {
                if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE) == false) {
                    val ar = arrayOf(Manifest.permission.CALL_PHONE)

                    ActivityCompat.requestPermissions(this, ar, 100 )
                }
            }


        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
       when(requestCode) {
           100 -> {
               if(grantResults.isNotEmpty()  && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                   Toast.makeText(this, "permission allowed", Toast.LENGTH_LONG).show()
               } else {
                   Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show()
               }
               return
           }
           else -> return  super.onRequestPermissionsResult(requestCode, permissions, grantResults)


       }
    }
}
