package com.example.expressapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.meal_layout.view.*

class MenuAdapter(var con:Context, var list: ArrayList<Menu>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return MenuHolder(LayoutInflater.from(con).inflate(R.layout.meal_layout,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder as MenuHolder).show(list[position].name,list[position].price,list[position].photo)
        (holder as MenuHolder).itemView.menu_add.setOnClickListener {
            AppInfo.itemid = list[position].id
            var obj = QtyFragment()
            obj.show((con as MenuAct).supportFragmentManager,"Qty")
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MenuHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        fun show(nm:String,pr:Double,ph:String){
            itemView.menu_name.text = nm
            itemView.menu_price.text = pr.toString()
            var photo = ph.replace(" ","%20")
            Picasso.get().load(AppInfo.web + "images/" +
                    photo).into(itemView.menu_photo)
        }
    }
}