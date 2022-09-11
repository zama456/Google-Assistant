package com.zama.googleassistant.assistant

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.zama.googleassistant.data.Assistant
import com.zama.googleassistant.data.AssistantDao
import kotlinx.coroutines.*

class AssistantViewModel(private val database : AssistantDao, application: Application):AndroidViewModel(application) {
    private var viewModejob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModejob.cancel()

    }

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModejob)
    private var currentMessage = MutableLiveData<Assistant?>()
    val message = database.getAllMessages()

    init {
        intalizeCurrentMessage()
    }

    private fun intalizeCurrentMessage() {
        uiScope.launch {
            currentMessage.value = getcurrentMessagefromDatabase()
        }
    }

    private suspend fun getcurrentMessagefromDatabase(): Assistant? {
        return withContext(Dispatchers.IO) {
            var message = database.getCurrentMessage()
            if (message?.assistant_message == "DEFAULT_MESSAGE" || message?.human_message == "DEFAULT_MESSAGE") {
                message = null
            }
            message

        }
    }

    private suspend fun insert(message: Assistant) {

        withContext(Dispatchers.IO) {
            database.Insert(message)
        }
    }

    fun onClear() {
        uiScope.launch {
            clear()
            currentMessage.value=null
        }
    }
    private suspend fun clear(){
        withContext(Dispatchers.IO){
            database.clear()
        }
    }
fun sendMessagetoDatabase(assistantMessage:String,humanMessage : String)
{
    uiScope.launch {
        val newAssistant = Assistant()
        newAssistant.assistant_message=assistantMessage
        newAssistant.human_message=humanMessage
        insert(newAssistant)
        currentMessage.value=getcurrentMessagefromDatabase()

    }
}
}