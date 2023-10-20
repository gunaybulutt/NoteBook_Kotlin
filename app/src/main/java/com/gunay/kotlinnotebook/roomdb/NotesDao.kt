package com.gunay.kotlinnotebook.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable


@Dao
interface NotesDao {


    @Query("SELECT * FROM Notes")
    fun getAll(): Flowable<List<Notes>>

    @Insert
    fun insertAll(vararg notes: Notes) : Completable

    @Delete
    fun delete(notes: Notes) : Completable

}