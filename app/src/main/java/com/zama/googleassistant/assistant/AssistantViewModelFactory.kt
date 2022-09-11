package com.zama.googleassistant.assistant

import android.app.Application
import androidx.lifecycle.ViewModel
import com.zama.googleassistant.data.AssistantDao
import java.lang.IllegalArgumentException


class AssistantViewModelFactory(
    private val dataSource : AssistantDao,
    private val application: Application)

{
    @Suppress("UNCHECKED_CAST")
    fun <T : ViewModel?> create(modelClass: Class<T>): T
    {
        if (modelClass.isAssignableFrom(AssistantViewModel::class.java)){
            return AssistantViewModel(dataSource,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

