package com.example.notesappfull

import android.content.Context
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_row.view.*

class RecyclerViewAdapter (var notes:List<Notes>,val mainActivity: MainActivity) : RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder>() {

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent,false))
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val note = notes[position]


        holder.itemView.apply {
            noteTV.text = note.note

            updateBu.setOnClickListener{

                mainActivity.dilog("update",note.note,note.pk)
//                 cardView.isVisible = false

            }

            deleteBu.setOnClickListener{
            mainActivity.dilog("delete",note.note,note.pk)
                cardView.isVisible = false

            }

        }
    }

    override fun getItemCount() = notes.size

    fun update(note: ArrayList<Notes>){
        this.notes = note
        notifyDataSetChanged()
    }

}

