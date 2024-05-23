package tr.igb.todoapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="task_table")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0L,
    @ColumnInfo(name="task-title")
    val title: String = "",
    @ColumnInfo(name="task-desc")
    val description: String = "",
    @ColumnInfo(name="task-priority")
    val priority: Int = 0,
    @ColumnInfo(name="task-is-completed")
    val isCompleted: Boolean = false
)