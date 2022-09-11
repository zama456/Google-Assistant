package com.zama.googleassistant.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AssistantDao {
    @Insert
    fun Insert(assistant: Assistant)
    @Update
    fun Update(assistant: Assistant)
    @Query("DELETE FROM  assistant_message_table")
    fun clear()
    @Query("Select * FROM assistant_message_table ORDER BY assistantId DESC")
    fun getAllMessages():LiveData<List<Assistant>>
    @Query("SELECT *FROM assistant_message_table WHERE assistantId=:key")
    fun get(key:Long):Assistant?
    @Query("SELECT *FROM assistant_message_table ORDER BY assistantId DESC  LIMIT 1")
    fun getCurrentMessage():Assistant?
}