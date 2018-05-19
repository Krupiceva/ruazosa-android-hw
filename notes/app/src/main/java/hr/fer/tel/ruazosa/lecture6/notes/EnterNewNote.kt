package hr.fer.tel.ruazosa.lecture6.notes

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class EnterNewNote : AppCompatActivity() {

    private var noteTitle: EditText? = null
    private var noteDescription: EditText? = null
    private var storeButton: Button? = null
    private var cancelButton: Button? = null
    private var note : NotesModel.Note? = null
    private var isEdit: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_new_note)

        noteTitle = findViewById(R.id.note_title)
        noteDescription = findViewById(R.id.note_description)

        val tableDao = DatabaseHelper(this).getDao(NotesModel.Note::class.java)

        if(this.intent.extras != null) {
            if (this.intent.extras.containsKey("ID")) {
                isEdit = true
                val id = this.intent.getIntExtra("ID", 0)
                val queryBuilder = tableDao.queryBuilder()
                queryBuilder.where().eq("ID", id)
                note = queryBuilder.queryForFirst()
                noteTitle?.setText(note?.noteTitle)
                noteDescription?.setText(note?.noteDescription)
            }
        }

        storeButton = findViewById(R.id.store_button)
        storeButton?.setOnClickListener({

            if(noteTitle?.text.toString() == "" || noteDescription?.text.toString() == ""){
                var message = "Mandatory fields:\n"
                if(noteTitle?.text.toString() == ""){
                    message = message + "Title\n"
                }
                if(noteDescription?.text.toString() == ""){
                    message = message + "Description\n"
                }
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }
            else {
                if (isEdit) {
                    val updateBuilder = tableDao.updateBuilder()
                    updateBuilder.where().eq("ID", note?.ID)
                    updateBuilder.updateColumnValue("noteTitle" , noteTitle?.text.toString())
                    updateBuilder.updateColumnValue("noteDescription" , noteDescription?.text.toString())
                    updateBuilder.updateColumnValue("noteTime", Date())
                    updateBuilder.update()
                } else {
                    val note = NotesModel.Note(noteTitle = noteTitle?.text.toString(),
                            noteDescription = noteDescription?.text.toString(), noteTime = Date())
                    //NotesModel.notesList.add(note)
                    val tableDao = DatabaseHelper(this).getDao(NotesModel.Note::class.java)
                    tableDao.create(note)
                }
                NotesModel.notesList.clear()
                NotesModel.notesList.addAll(tableDao.queryForAll())
                finish()
            }
        })
        cancelButton = findViewById(R.id.cancel_button)

        cancelButton?.setOnClickListener({
            finish()
        })
    }
}
