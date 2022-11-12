package com.example.expressapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.cart_layout.view.*

class CartAdapter(var con: Context, var list:ArrayList<Cart>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CartHolder(
            LayoutInflater.from(con).
        inflate(R.layout.cart_layout,parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        (holder as CartHolder).show(list[position].name,
            list[position].qty,list[position].price)

        (holder as CartHolder).itemView.
        cart_photo.setOnClickListener {
            var url = AppInfo.web + "del_cart.php"
            var rq = Volley.newRequestQueue(con)
            var sr = object: StringRequest(
                Request.Method.POST,url,
                Response.Listener {
                    con.startActivity(
                        Intent(con,
                        CartAct::class.java)
                    )
                    (con as CartAct).finish()
                },
                Response.ErrorListener {  })
            {
                override fun getParams(): MutableMap<String, String> {

                    var map = HashMap<String,String>()
                    map.put("mobile",AppInfo.mobile)
                    map.put("itemid",list[position].itemid.toString())


                    return map
                }
            }

            rq.add(sr)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class CartHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView)
    {
        fun show(nm:String,qty:Int,pr:Double)
        {
            itemView.cart_name.text = nm
            itemView.cart_qty.text = qty.toString()
            itemView.cart_price.text = pr.toString()

        }
    }
}