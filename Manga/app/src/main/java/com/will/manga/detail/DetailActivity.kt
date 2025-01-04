package com.will.manga.detail

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.will.manga.R

class DetailActivity : AppCompatActivity() {
    private lateinit var txtDetailJudul : TextView
    private lateinit var txtDetailPenulis : TextView
    private lateinit var txtDetailGenre : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)

        txtDetailJudul = findViewById(R.id.txtDetailJudul)
        txtDetailPenulis = findViewById(R.id.txtDetailPenulis)
        txtDetailGenre = findViewById(R.id.txtDetailGenre)

        val DetailJudul = intent.getStringExtra("judul")
        val DetailPenulis = intent.getStringExtra("penulis")
        val DetailGenre = intent.getStringExtra("genre")

        txtDetailJudul.text = DetailJudul ?: "Judul tidak tersedia"
        txtDetailPenulis.text = DetailPenulis ?: "Penulis tidak tersedia"
        txtDetailGenre.text = DetailGenre ?: "Genre tidak tersedia"

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}