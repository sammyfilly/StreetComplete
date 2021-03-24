package de.westnordost.streetcomplete.data.osmnotes.edits

import de.westnordost.osmapi.map.data.LatLon
import de.westnordost.streetcomplete.data.edithistory.Edit

/** Contains all necessary information to create/comment on an OSM note. */
data class NoteEdit(
    /** (row) id of the edit. 0 if not inserted into DB yet */
    var id: Long,

    /** note id this edit refers to. New notes get assigned negative ids. It may be 0 if a new
     * note is created and it hasn't been inserted into DB yet */
    var noteId: Long,

    /** position of the note */
    override val position: LatLon,

    /** The action to perform */
    val action: NoteEditAction,

    /** note comment text */
    val text: String?,

    /** attached photos */
    val imagePaths: List<String>,

    /** timestamp when this edit was made. Used to order the (unsynced) edits in a queue */
    override val createdTimestamp: Long,

    /** whether this edit has been uploaded already */
    val isSynced: Boolean,

    /** Whether the images attached still need activation. Already true if imagePaths is empty */
    val imagesNeedActivation: Boolean
): Edit {
    override val isUndoable: Boolean get() = !isSynced
}

enum class NoteEditAction {
    CREATE, COMMENT
}
