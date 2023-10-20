package com.gunay.kotlinnotebook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.gunay.kotlinnotebook.databinding.ActivityMainBinding
import com.gunay.kotlinnotebook.roomdb.Notes
import com.gunay.kotlinnotebook.roomdb.NotesDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding


    private val mDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)





        val db = Room.databaseBuilder(applicationContext, NotesDatabase::class.java, "Notes").build()
        val notesDao = db.notesDao()



        mDisposable.add(
            notesDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse))

    }

    private fun handleResponse(noteList: List<Notes>) {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val not = noteList
        val adapter = NoteAdapter(not)
        binding.recyclerView.adapter = adapter


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.add_new,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.new_note){
            val intent = Intent(this, NoteActivity::class.java)
            intent.putExtra("infotwo", "new")

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        mDisposable.clear()
    }
}



