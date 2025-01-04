package com.will.manga.screenpage

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.will.manga.DbHelper.DbHelper
import com.will.manga.R
import com.will.manga.databinding.ActivityTambahMangaBinding
import com.will.manga.model.ModelManga

class TambahMangaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTambahMangaBinding
    private lateinit var db : DbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahMangaBinding.inflate(layoutInflater)
        setContentView(binding.root)


        db = DbHelper(this)
        binding.btnTambahData.setOnClickListener{
            val judul = binding.txtInputJudul.text.toString()
            val penulis = binding.txtInputPenulis.text.toString()
            val genre = binding.txtInputGenre.text.toString()

            //karena nim --> int jadi kita perlu convert dari string ke int
            //toInt()
            val dataManga = ModelManga(0, judul, penulis, genre )
            db.insertDataMahasiswa(dataManga)
            finish();
            Toast.makeText(this, "Berhasil Tambah Data",
                Toast.LENGTH_LONG).show()
        }
    }
}