package com.example.ahozyainov.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_add_city.*

class AddCityActivity : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_city)

        btnAddCity.setOnClickListener(this)

    }

    override fun onClick(view: View?) {

    }

}
