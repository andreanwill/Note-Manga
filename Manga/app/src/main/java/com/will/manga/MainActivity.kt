package com.will.manga

import android.content.Intent
import android.os.Bundle
import android.provider.Telephony.Mms.Intents
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.will.manga.DbHelper.DbHelper
import com.will.manga.adapter.MangaAdapter
import com.will.manga.databinding.ActivityMainBinding
import com.will.manga.screenpage.TambahMangaActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var db : DbHelper
    private lateinit var MangaAdapter: MangaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DbHelper(this)
        MangaAdapter = MangaAdapter(db.getAllDataManga(),this)

        binding.rvDataMahasiswa.layoutManager = LinearLayoutManager(this)
        binding.rvDataMahasiswa.adapter = MangaAdapter

        binding.btnPageTambah.setOnClickListener{
            val intent = Intent(this, TambahMangaActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        val newManga = db.getAllDataManga()
        MangaAdapter.refreshData(newManga)
    }
}