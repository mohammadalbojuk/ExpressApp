package com.example.expressapp

import android.Manifest
import android.app.ProgressDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//       Permissions
        if(Build.VERSION.SDK_INT >= 23)
            ActivityCompat.requestPermissions(this,
                arrayOf(
                    Manifest.permission.
                ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.SEND_SMS),
                123)

//Remember Me
        var sp = getSharedPreferences("info",
            MODE_PRIVATE)
        if(sp.getString("mobile","")!="")
        {
            AppInfo.mobile = sp.getString("mobile","")!!
            startActivity(Intent(this,
                MenuAct::class.java))
            finish()
        }


//First Time
        login_ft.setOnClickListener {
            startActivity(
                Intent(this,
                SignupAct::class.java)
            )
        }

//Login Button
        login_button.setOnClickListener {

            val pd = ProgressDialog(this)
            pd.setMessage("Please Wait...")
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER)
            pd.show()

            val url = AppInfo.web + "login.php"
            val rq = Volley.newRequestQueue(this)
            val sr = object: StringRequest(
                Request.Method.POST,url,
                Response.Listener {
                    pd.hide()
                    if(it=="0")
                        Toast.makeText(this,"Login Fail",
                            Toast.LENGTH_LONG).show()
                    else{
                        if(login_rem.isChecked)
                        {
                            val sp = getSharedPreferences("info",
                                MODE_PRIVATE)
                            val editor = sp.edit()
                            editor.putString("mobile",
                                login_mobile.text.toString())
                            editor.commit()
                        }


                        AppInfo.mobile = login_mobile.text.toString()
                        startActivity(Intent(this,
                            MenuAct::class.java))
                        finish()}
                },
                Response.ErrorListener {  })
            {
                override fun getParams(): MutableMap<String, String> {

                    var map = HashMap<String,String>()
                    map.put("mobile",login_mobile.text.toString())
                    map.put("password",login_password.text.toString())


                    return map
                }
            }

            rq.add(sr)

        }
    }
}