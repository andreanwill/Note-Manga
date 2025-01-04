package com.will.manga.DbHelper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.will.manga.model.ModelManga

class DbHelper (context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // below is the method for creating a database by a sqlite query
    override fun onCreate(db: SQLiteDatabase) {
        // below is a sqlite query, where column names
        // along with their data types is given
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                JUDUL + " TEXT," +
                PENULIS + " TEXT," +
                GENRE + " TEXT" + ")")

        // we are calling sqlite
        // method for executing our query
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        // this method is to check if table already exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    // This method is for adding data in our database
    fun addName(nama_mahasiswa : String, nim : String, jurusan : String) {

        // below we are creating
        // a content values variable
        val values = ContentValues()

        // we are inserting our values
        // in the form of key-value pair
        values.put(JUDUL, nama_mahasiswa)
        values.put(PENULIS, nim)
        values.put(GENRE, jurusan)

        // here we are creating a
        // writable variable of
        // our database as we want to
        // insert value in our database
        val db = this.writableDatabase

        // all values are inserted into database
        db.insert(TABLE_NAME, null, values)

        // at last we are
        // closing our database
        db.close()
    }

    // below method is to get
    // all data from our database
    fun getName(): Cursor? {

        // here we are creating a readable
        // variable of our database
        // as we want to read value from it
        val db = this.readableDatabase

        // below code returns a cursor to
        // read data from the database
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null)

    }
    fun insertDataMahasiswa(mhs: ModelManga) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(JUDUL, mhs.judul)
            put(PENULIS, mhs.penulis)
            put(GENRE, mhs.genre)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun updateMahasiswa(mhs: ModelManga){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(JUDUL, mhs.judul)
            put(PENULIS, mhs.penulis)
            put(GENRE, mhs.genre)
        }

        val whereClause = "$ID_COL = ?"
        val whereArgs = arrayOf(mhs.id.toString())

        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()
    }

    fun getMhsByid(mhsId : Int): ModelManga{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $ID_COL = $mhsId"
        val cursor  = db.rawQuery(query, null)
        cursor.moveToNext()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(ID_COL))
        val judul = cursor.getString(cursor.getColumnIndexOrThrow(JUDUL))
        val penulis = cursor.getString(cursor.getColumnIndexOrThrow(PENULIS))
        val genre = cursor.getString(cursor.getColumnIndexOrThrow(GENRE))

        cursor.close()
        db.close()
        return ModelManga(id, judul, penulis, genre)
    }

    fun deleteMahasiswa(mhsId : Int){
        val db = writableDatabase
        val whereClause = "$ID_COL = ?"
        val whereArgs = arrayOf(mhsId.toString())

        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()
    }

    // get all data
    fun getAllDataManga() :List<ModelManga>{
        val mangalist = mutableListOf<ModelManga>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query,null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(ID_COL))
            val judulManga = cursor.getString(cursor.getColumnIndexOrThrow(JUDUL))
            val penulis = cursor.getString(cursor.getColumnIndexOrThrow(PENULIS))
            val genre = cursor.getString(cursor.getColumnIndexOrThrow(GENRE))

            Log.d("DatabaseHelper","Fecthed ID : $id, judulManga: $judulManga")
            val nManga = ModelManga(id, judulManga , penulis, genre)
            mangalist.add(nManga)
        }
        cursor.close()
        db.close()
        return mangalist
    }
//    fun getAllData() : MutableList<ModelMahasiswa>{
//        if(!databaseOpen){
//            database = INSTANCE.writableDatabase
//            databaseOpen = true // db is open
//
//            Log.i("Database", "Database open")
//        }
//        val data: MutableList<ModelMahasiswa> = ArrayList()
//        val cursor = database.rawQuery("SELECT  * FROM" + TABLE_NAME, null)
//        cursor.use { cur ->
//            if(cursor.moveToFirst()){
//                do {
//                    val mahasiswa = ModelMahasiswa()
//                    mahasiswa.id = cur.getInt(cur.getColumnIndex(ID_COL))
//                    mahasiswa.nama  = cur.getString(cur.getColumnIndex(NAMA_MAHASISWA))
//                    mahasiswa.nim = cur.getString(cur.getColumnIndex(NIM))
//                    mahasiswa.jurusan = cur.getString(cur.getColumnIndex(JURUSAN))
//                    data.add(mahasiswa)
//                }while(cursor.moveToNext())
//            }
//        }
//
//        return data
//
//    }


    companion object{
        // here we have defined variables for our database

        // below is variable for database name
        private val DATABASE_NAME = "DB_MANGA"

        // below is the variable for database version
        private val DATABASE_VERSION = 1

        // below is the variable for table name
        val TABLE_NAME = "tb_manga"

        // below is the variable for id column
        val ID_COL = "id"
        val JUDUL = "judul"
        val PENULIS = "penulis"
        val GENRE = "genre"

        private lateinit var database : SQLiteDatabase
        private var databaseOpen : Boolean = false
        private lateinit var INSTANCE : DbHelper



    }

}