package com.example.expressapp

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_signup.*

class SignupAct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        signup_button.setOnClickListener {
            var pd = ProgressDialog(this)
            pd.setMessage("Please Wait...")
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER)
            pd.show()

            var url = AppInfo.web + "add_users.php"
            var rq = Volley.newRequestQueue(this)
            var sr = object:StringRequest(
                Request.Method.POST,url,
                {
                    pd.hide()
                    startActivity(
                        Intent(this,
                        MenuAct::class.java)
                    )
                    finish()
                },
                {  })

            {
                override fun getParams(): MutableMap<String, String> {

                    var map = HashMap<String,String>()
                    map.put("mobile",signup_mobile.text.toString())
                    map.put("password",signup_password.text.toString())
                    map.put("name",signup_name.text.toString())

                    return map
                }
            }

            rq.add(sr)
        }
    }
}