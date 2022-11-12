package com.example.expressapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.paypal.android.sdk.payments.PayPalConfiguration
import com.paypal.android.sdk.payments.PayPalPayment
import com.paypal.android.sdk.payments.PayPalService
import com.paypal.android.sdk.payments.PaymentActivity
import kotlinx.android.synthetic.main.activity_confirm.*
import java.math.BigDecimal

class ConfirmAct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm)

//        PayPal
        btn_paypal.setOnClickListener {

            var config= PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId(AppInfo.cid)

            var i= Intent(this, PayPalService::class.java)
            i.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config)
            startService(i)

            var payment= PayPalPayment(
                BigDecimal.valueOf(AppInfo.total),
                "USD","Express App",
                PayPalPayment.PAYMENT_INTENT_SALE)

            var obj=Intent(this, PaymentActivity::class.java)
            obj.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config)
            obj.putExtra(PaymentActivity.EXTRA_PAYMENT,payment)
            startActivityForResult(obj,123)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 123)
        {

            if(resultCode==RESULT_OK){
                Toast.makeText(this,"Thank You",
                    Toast.LENGTH_LONG).show()

                var url = AppInfo.web + "edit_bill.php"
                var rq = Volley.newRequestQueue(applicationContext)
                var sr = object: StringRequest(
                    Request.Method.POST,url,
                    Response.Listener {

                    },
                    Response.ErrorListener {  })
                {
                    override fun getParams(): MutableMap<String, String> {

                        var map = HashMap<String,String>()
                        map.put("mobile",AppInfo.mobile)


                        return map
                    }
                }

                rq.add(sr)
            }
            else
                Toast.makeText(this,"Sorry !!",
                    Toast.LENGTH_LONG).show()
        }

    }
}