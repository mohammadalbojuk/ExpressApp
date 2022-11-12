package com.example.expressapp

import android.content.Intent
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.view.menu.MenuAdapter
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import kotlinx.android.synthetic.main.activity_menu.*

class MenuAct : AppCompatActivity(),OnTabSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

//        Tabs Create
        tabs.addOnTabSelectedListener(this)

        var url = AppInfo.web + "get_cat.php"
        var rq = Volley.newRequestQueue(this)
        var jar = JsonArrayRequest(
            Request.Method.GET,url,
            null, {
                for (x in 0..it.length()-1)
                    tabs.addTab(tabs.newTab().setText(
                        it.getJSONObject(x).
                        getString("category"))
                        .setIcon(R.drawable.
                        pngwing_com))
            },
            {  })
        rq.add(jar)

    }

//    Show Menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }


//    Events Menu Item
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item?.itemId == R.id.item_out)
        {
            var sp = getSharedPreferences("info",
                MODE_PRIVATE)
            var editor = sp.edit()
            editor.clear()
            editor.commit()
            startActivity(
                Intent(this,
                MainActivity::class.java)
            )
            finish()
        }
        if(item?.itemId == R.id.item_cart)
            startActivity(Intent(this,
                CartAct::class.java))

        return super.onOptionsItemSelected(item)
    }

//    Tabs Events
    override fun onTabSelected(p0: TabLayout.Tab) {

        var url = AppInfo.web + "get_items.php?category=" + p0.text
        var list = ArrayList<com.example.expressapp.Menu>()
        var rq = Volley.newRequestQueue(this)
        var jar = JsonArrayRequest(Request.Method.GET,url,
            null, {
                for (x in 0..it.length()-1)
                    list.add(Menu(
                        it.getJSONObject(x).getInt("id"),
                        it.getJSONObject(x).getString("name"),
                        it.getJSONObject(x).getDouble("price"),
                        it.getJSONObject(x).getString("photo")))

                var adp = MenuAdapter(this,list)
                menu_rv.adapter = adp
                menu_rv.layoutManager = LinearLayoutManager(this)

            },
            {  })
        rq.add(jar)
    }

    override fun onTabUnselected(p0: TabLayout.Tab) {

    }

    override fun onTabReselected(tab: TabLayout.Tab?) {

    }
}