package com.will.manga.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.will.manga.DbHelper.DbHelper
import com.will.manga.R
import com.will.manga.detail.DetailActivity
import com.will.manga.model.ModelManga
import com.will.manga.screenpage.UpdateActivity

class MangaAdapter(
    private var listManga: List<ModelManga>,
    context: Context
): RecyclerView.Adapter<MangaAdapter.MangaViewHolder>() {

    private val db : DbHelper = DbHelper(context)

    class MangaViewHolder (itemView: View) :RecyclerView.ViewHolder(itemView) {
        val txtJudul : TextView = itemView.findViewById(R.id.txtJudul)
        val txtPenulis : TextView = itemView.findViewById(R.id.txtPenulis)
        val txtGenre : TextView = itemView.findViewById(R.id.txtGenre)

        val btnEDIT : ImageView = itemView.findViewById(R.id.imgEdit)
        val btnDelete : ImageView = itemView.findViewById(R.id.imgDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MangaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_data_manga,
            parent,false)
        return MangaViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listManga.size
    }

    override fun onBindViewHolder(holder: MangaViewHolder, position: Int) {
        val nMahasiswa = listManga[position]
        holder.txtJudul.text = nMahasiswa.judul
        holder.txtPenulis.text = nMahasiswa.penulis
        holder.txtGenre.text = nMahasiswa.genre

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra("judul", nMahasiswa.judul)
            intent.putExtra("penulis", nMahasiswa.penulis)
            intent.putExtra("genre", nMahasiswa.genre)
            holder.itemView.context.startActivity(intent)
        }

        holder.btnDelete.setOnClickListener{
            db.deleteMahasiswa(nMahasiswa.id)
            refreshData(db.getAllDataManga())
            Toast.makeText(holder.itemView.context,
                "Berhasil Delete Data ${nMahasiswa.judul}", Toast.LENGTH_LONG
            ).show()
        }

        holder.btnEDIT.setOnClickListener{
            val intent = Intent(holder.itemView.context, UpdateActivity::class.java).apply {
                putExtra("id_mhs", nMahasiswa.id)
            }
            holder.itemView.context.startActivity(intent)
        }
    }

    fun refreshData(newManga: List<ModelManga>) {
        listManga = newManga
        notifyDataSetChanged()
    }
}