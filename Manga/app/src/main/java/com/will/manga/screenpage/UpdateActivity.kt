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
import com.will.manga.databinding.ActivityUpdateBinding
import com.will.manga.model.ModelManga

class UpdateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateBinding
    private lateinit var db : DbHelper
    private var mhId : Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DbHelper(this)

        mhId = intent.getIntExtra("id_mhs", -1)
        if(mhId == -1){
            finish()
            return
        }

        val mhs = db.getMhsByid(mhId)
        binding.etEditJudul.setText(mhs.judul)
        binding.etEditPenulis.setText(mhs.penulis)
        binding.etEditGenre.setText(mhs.genre)

        binding.btnEdit.setOnClickListener{
            val newNama = binding.etEditJudul.text.toString()
            val newNIM = binding.etEditPenulis.text.toString()
            val newJurusan = binding.etEditGenre.text.toString()

            val updateMhs = ModelManga(mhId, newNama, newNIM, newJurusan)
            db.updateMahasiswa(updateMhs)
            finish()
            Toast.makeText(this, "Update Success", Toast.LENGTH_LONG).show()
        }
    }
}