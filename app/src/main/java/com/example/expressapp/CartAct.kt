package com.example.expressapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_cart.*

class CartAct : AppCompatActivity() {
    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        var url = AppInfo.web + "show_cart.php?mobile=" + AppInfo.mobile
        var list = ArrayList<Cart>()
        var rq = Volley.newRequestQueue(this)
        var jar = JsonArrayRequest(
            Request.Method.GET,url,
            null, {
                AppInfo.total = 0.0
                for (x in 0..it.length()-1) {
                    list.add(Cart(
                        it.getJSONObject(x).getInt("id"),
                        it.getJSONObject(x).getString("name"),
                        it.getJSONObject(x).getInt("qty"),
                        it.getJSONObject(x).getDouble("price")))
                    AppInfo.total += list[x].price * list[x].qty
                }
                cart_total.text = AppInfo.total .toString() + " JOD"
                var adp = CartAdapter(this,list)
                cart_rv.adapter = adp
                cart_rv.layoutManager = LinearLayoutManager(this)

            },
            {  })
        rq.add(jar)

        cart_confirm.setOnClickListener {

            var m = getSystemService(Context.LOCATION_SERVICE)
                    as LocationManager
            var s = object : LocationListener {
                override fun onLocationChanged(p0: Location) {

                    var url = AppInfo.web + "add_bill.php"
                    var rq = Volley.newRequestQueue(applicationContext)
                    var sr = object: StringRequest(Request.Method.POST,url,
                        Response.Listener {
                            startActivity(
                                Intent(applicationContext,
                                ConfirmAct::class.java)
                            )
                            finish()
                        },
                        Response.ErrorListener {  })
                    {
                        override fun getParams(): MutableMap<String, String> {

                            var map = HashMap<String,String>()
                            map.put("mobile",AppInfo.mobile)
                            map.put("lon",p0.longitude.toString())
                            map.put("lat",p0.latitude.toString())

                            return map
                        }
                    }

                    rq.add(sr)


                    m.removeUpdates(this)
                }

                override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

                }

                override fun onProviderEnabled(provider: String) {

                }

                override fun onProviderDisabled(provider: String) {

                }
            }

            m.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                1,0f,s)


        }
    }
}