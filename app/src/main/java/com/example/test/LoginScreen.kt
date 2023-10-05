package com.example.test

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import com.example.test.Modle.GeneralData
import com.example.test.databinding.ActivityLoginScreenBinding

class LoginScreen : AppCompatActivity() {
    lateinit var binding:ActivityLoginScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityLoginScreenBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        var user=binding.editTextTextPersonName
        var pass=binding.editTextTextPassword


        var sharedPreferences:SharedPreferences=getSharedPreferences("myData",Context.MODE_PRIVATE)
        var userName=sharedPreferences.getString("u","None")
        var Password =sharedPreferences.getString("p","")
        user.editText?.setText(userName)
        pass.editText?.setText(Password)



        binding.button.setOnClickListener {

            if(user.editText!!.text.toString()=="saif"&& pass.editText!!.text.toString()=="123456" )
            {
                saveIsCheckedLogin(true)

                var intent=Intent(this,MainActivity::class.java)
                startActivity(intent)
                GeneralData.isChcekedLogin=true
            }
            else
            {
                Toast.makeText(this, "The userNmae or Passwor is fault",Toast.LENGTH_SHORT).show()
            }

        }
        binding.checkBoxid.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                // Get a reference to SharedPreferences
                val sharedPreferences: SharedPreferences = getSharedPreferences("myData", Context.MODE_PRIVATE)

                // Get the values of user and pass from your EditTexts (assuming you have EditTexts with IDs user and pass)
                val userValue = user
                val passValue = pass

                // Edit the SharedPreferences
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString("u", userValue.editText?.text.toString())
                editor.putString("p", passValue.editText?.text.toString())

                // Apply the changes
                editor.apply()

                // Show a toast message
                Toast.makeText(this, "Data Saved", Toast.LENGTH_SHORT).show()
            }
        }


}

    private fun saveIsCheckedLogin(isChecked: Boolean) {
        val sharedPreferences: SharedPreferences =getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isChcekedLogin", isChecked)
        editor.apply()
    }

}