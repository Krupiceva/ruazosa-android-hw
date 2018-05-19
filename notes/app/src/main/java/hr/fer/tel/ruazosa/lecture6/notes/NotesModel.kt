package hr.fer.tel.ruazosa.lecture6.notes

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import java.util.*

/**
 * Created by dejannovak on 09/04/2018.
 */
object NotesModel {

    @DatabaseTable(tableName = "table")
    data class Note (
            @DatabaseField(columnName = "ID",generatedId = true)
            var ID: Int? = null,
            @DatabaseField(columnName = "noteTitle")
            var noteTitle: String? = null,
            @DatabaseField(columnName = "noteDescription")
            var noteDescription: String? = null,
            @DatabaseField(columnName = "noteTime")
            var noteTime: Date? = null
    )

    var notesList: MutableList<Note> = mutableListOf()


}