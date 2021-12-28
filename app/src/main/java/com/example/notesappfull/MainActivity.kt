package com.example.notesappfull

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    lateinit var addNoteEt: EditText
    lateinit var addBu : Button
    lateinit var recView: RecyclerView
    var userInput = ""
    private lateinit var rvAdapter: RecyclerViewAdapter
    var notes= ArrayList<Notes>()
    private val databaseHelper by lazy { DatabaseHelper(applicationContext) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addNoteEt = findViewById(R.id.addNoteEt)
        addBu = findViewById(R.id.addBu)
        recView = findViewById(R.id.recView)
        rvAdapter = RecyclerViewAdapter(notes, this)
        recView.adapter = rvAdapter
        recView.layoutManager = LinearLayoutManager(this)

        getData()

        addBu.setOnClickListener {
            var note = addNoteEt.text.toString()
            databaseHelper.saveData(note)
            Toast.makeText(this, "Added successfully", Toast.LENGTH_LONG).show()
            getData()


        }


        }

    private fun getData(){
        notes = databaseHelper.readData()
        rvAdapter.update(notes)


    }

    private fun updateNote(notePk: Int,noteText: String) {
        databaseHelper.updateData(Notes(notePk,noteText))
        rvAdapter.notifyDataSetChanged()
    }
    private fun deleteNote (notePk: Int){
        databaseHelper.deleteData(Notes(notePk,""))
        Toast.makeText(this, "Deleted", Toast.LENGTH_LONG).show()
        rvAdapter.update(notes)
        rvAdapter.notifyDataSetChanged()
    }

    fun dilog(updateOrDelete :String,selectedNote:String,pk:Int) {

        if (updateOrDelete.equals("update")){
            val builder = AlertDialog.Builder(this)
            //  set title for alert dialog
            builder.setTitle("Update Note")

          var  input = EditText(this)
           input.setText(selectedNote)
            input.inputType = InputType.TYPE_CLASS_TEXT
            builder.setView(input)


            //performing positive action
            builder.setPositiveButton("update") { dialogInterface, which ->

                userInput = input.text.toString()
                updateNote(pk,userInput)


            }
            builder.setNegativeButton("CANCEL"){dialogInterface, which ->}
            // Create the AlertDialog
            val alertDialog: AlertDialog = builder.create()
            // Set other dialog properties
            // alertDialog.setCancelable(false)
            alertDialog.show()
        }

        else {
            val builder = AlertDialog.Builder(this)
            //  set title for alert dialog
            builder.setTitle("Are you sure to delete note  ")
            var  input = EditText(this)
            input.setText(selectedNote)
            input.inputType = InputType.TYPE_CLASS_TEXT
            builder.setView(input)
            //performing positive action
            builder.setPositiveButton("Delete") { dialogInterface, which ->
                userInput = input.text.toString()
              deleteNote(pk)

            }
            builder.setNegativeButton("CANCEL"){dialogInterface, which ->}
            // Create the AlertDialog
            val alertDialog: AlertDialog = builder.create()
            // Set other dialog properties
            alertDialog.setCancelable(true)
            alertDialog.show()
        }
    }
        }



