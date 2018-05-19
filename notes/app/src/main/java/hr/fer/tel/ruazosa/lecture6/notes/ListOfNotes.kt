package hr.fer.tel.ruazosa.lecture6.notes

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.text.SimpleDateFormat
import java.util.*

class ListOfNotes : AppCompatActivity() {

    private var fab: FloatingActionButton? = null
    private var listView: ListView? = null
    private var adapter: NotesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_notes)
        fab = findViewById(R.id.fab)

        fab?.setOnClickListener({
            val startEnterNewNoteIntent = Intent(this, EnterNewNote::class.java)
            startActivity(startEnterNewNoteIntent)
        })

        listView = findViewById(R.id.list_view)
        adapter = NotesAdapter(this)
        listView?.adapter = adapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        adapter?.notifyDataSetChanged()
    }

    inner class NotesAdapter : BaseAdapter {

        private var notesList = NotesModel.notesList
        private var context: Context? = null


        constructor(context: Context) : super() {
            this.context = context
            val tableDao = DatabaseHelper(this@ListOfNotes).getDao(NotesModel.Note::class.java)
            NotesModel.notesList.clear()
            NotesModel.notesList.addAll(tableDao.queryForAll())
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {

            val view: View?
            val vh: ViewHolder

            if (convertView == null) {
                view = layoutInflater.inflate(R.layout.note_in_list, parent, false)
                vh = ViewHolder(view)
                view.tag = vh
            } else {
                view = convertView
                vh = view.tag as ViewHolder
            }
            val dateFormatter = SimpleDateFormat("dd'.'MM'.'yyyy ',' HH:mm");


            val dateAsString = dateFormatter.format(notesList[position].noteTime)
            vh.noteTitle.text = notesList[position].noteTitle
            vh.noteTime.text = dateAsString

            vh.deleteButton.setOnClickListener({
                val tableDao = DatabaseHelper(this@ListOfNotes).getDao(NotesModel.Note::class.java)
                val deleteBuilder = tableDao.deleteBuilder()
                deleteBuilder.where().eq("ID", notesList[position].ID)
                deleteBuilder.delete()
                NotesModel.notesList.clear()
                NotesModel.notesList.addAll(tableDao.queryForAll())
                this.notifyDataSetChanged()
            })

            vh.editButton.setOnClickListener({
                val startEnterNewNoteIntent = Intent(context, EnterNewNote::class.java)
                startEnterNewNoteIntent.putExtra("ID", notesList[position].ID)
                startActivityForResult(startEnterNewNoteIntent, 0)
                this.notifyDataSetChanged()
            })

            return view
        }

        override fun getItem(position: Int): Any {
            return notesList[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return notesList.size
        }
    }

    private class ViewHolder(view: View?) {
        val noteTime: TextView
        val noteTitle: TextView
        val deleteButton: ImageView
        val editButton: ImageView

        init {
            this.noteTime = view?.findViewById<TextView>(R.id.note_time) as TextView
            this.noteTitle = view?.findViewById<TextView>(R.id.note_title) as TextView
            this.deleteButton = view?.findViewById<ImageView>(R.id.ivDelete) as ImageView
            this.editButton = view?.findViewById<ImageView>(R.id.ivEdit) as ImageView
        }

        //  if you target API 26, you should change to:
//        init {
//            this.tvTitle = view?.findViewById<TextView>(R.id.tvTitle) as TextView
//            this.tvContent = view?.findViewById<TextView>(R.id.tvContent) as TextView
//        }
    }
}
