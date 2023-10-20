package com.gunay.kotlinnotebook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.room.Room
import com.gunay.kotlinnotebook.databinding.ActivityMainBinding
import com.gunay.kotlinnotebook.databinding.ActivityNoteBinding
import com.gunay.kotlinnotebook.roomdb.Notes
import com.gunay.kotlinnotebook.roomdb.NotesDao
import com.gunay.kotlinnotebook.roomdb.NotesDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class NoteActivity : AppCompatActivity() {

    lateinit var binding: ActivityNoteBinding
    private val mDisposable = CompositeDisposable()
    private lateinit var notesDao: NotesDao
    private var info: String? = ""
    private var infotwo: String? = ""
    private var note: Notes? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val db = Room.databaseBuilder(applicationContext, NotesDatabase::class.java, "Notes").build()
        notesDao = db.notesDao()



        val intent = intent //getIntent()
        note = intent.getSerializableExtra("Not") as? Notes
        info = intent.getStringExtra("info")
        infotwo = intent.getStringExtra("infotwo")


        if (info == "old"){
            note?.let {
                binding.noteHeadEditText.setText(it.note_head)
                binding.noteEditText.setText(it.note_text)
            }
        }


    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.save_new_note,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.save_note){
            if (infotwo == "new") {
                val notes = Notes(
                    binding.noteHeadEditText.text.toString(),
                    binding.noteEditText.text.toString()
                )

                mDisposable.add(
                    notesDao.insertAll(notes)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe()
                )


                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }else{

            }
        }

        else if (item.itemId == R.id.delete_note) {
            if (info == "old"){
                if (note != null) {
                    mDisposable.add(
                        notesDao.delete(note!!)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe()
                    )
                }

                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }

        }

        else if (item.itemId == R.id.back_menu) {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}