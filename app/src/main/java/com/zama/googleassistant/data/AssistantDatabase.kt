package com.zama.googleassistant.data

import android.content.Context
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zama.googleassistant.data.Assistant
import com.zama.googleassistant.data.AssistantDao


@Database(entities = [Assistant::class], version = 1, exportSchema = false)
 abstract class AssistantDatabase: RoomDatabase(){
    abstract val assistantDao: AssistantDao

    companion object {
        @Volatile
        private var INSTANTCE: AssistantDatabase? = null
        fun getInstance(context: Context): AssistantDatabase {
            synchronized(this) {
                var instance = INSTANTCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AssistantDatabase::class.java,
                        "assistant_message_database"
                    ).allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANTCE =instance

                }
                return instance
            }

        }

    }

}