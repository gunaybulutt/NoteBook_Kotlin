package com.gunay.kotlinnotebook

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gunay.kotlinnotebook.databinding.RowRecyclerBinding
import com.gunay.kotlinnotebook.roomdb.Notes

class NoteAdapter(val NoteList: List<Notes>): RecyclerView.Adapter<NoteAdapter.NoteHolder>() {

    class NoteHolder(val binding: RowRecyclerBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val binding = RowRecyclerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NoteHolder(binding)
    }

    override fun getItemCount(): Int {
        return NoteList.size
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        holder.binding.recyclerText.text = NoteList.get(position).note_head
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context,NoteActivity::class.java)
            intent.putExtra("Not",NoteList.get(position))
            intent.putExtra("info", "old")
            holder.itemView.context.startActivity(intent)
        }
    }
}