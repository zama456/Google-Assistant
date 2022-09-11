package com.zama.googleassistant.assistant

import android.content.ClipboardManager
import android.content.Intent
import android.content.res.Resources
import android.hardware.camera2.CameraManager
import android.media.Ringtone
import android.media.RingtoneManager
import android.media.RingtoneManager.*
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewTreeObserver
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.zama.googleassistant.R
import com.zama.googleassistant.data.AssistantDatabase
import com.zama.googleassistant.databinding.ActivityAssistantBinding
import com.zama.googleassistant.databinding.ActivityMainBinding
import com.zama.googleassistant.utils.utils
import com.zama.googleassistant.utils.utils.*
import java.util.*
import androidx.lifecycle.ViewModelProvider as ViewModelProvider1

class AssistantActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAssistantBinding
    private lateinit var assistantViewModel: AssistantViewModel
    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var textToSpeech: TextToSpeech
    private lateinit var recognizerIntent: Intent
    private lateinit var keeper: String
    private lateinit var cameraManager: CameraManager
    private lateinit var clipboardManager: ClipboardManager
    private lateinit var cameraID: String
    private lateinit var ringtone: Ringtone
    private lateinit var imageUri: Uri

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        utils.setCustomActionBar(supportActionBar, this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_assistant)
        if (Settings.System.canWrite(this)) {
            ringtone = getRingtone(applicationContext, RingtoneManager.getDefaultUri(TYPE_RINGTONE))

        } else {
            val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
            intent.data = Uri.parse("package:" + this.packageName)
            startActivity(intent)
        }
        val application = requireNotNull(this).application
        val dataSource = AssistantDatabase.getInstance(this).assistantDao
        val viewModelFactory = AssistantViewModelFactory(dataSource, application)
//        assistantViewModel = ViewModelProvider1(this, viewModelFactory).get(AssistantViewModel::class.java)
        val adapter = AssistantAdapter()
        binding.recylerView.adapter = adapter
        assistantViewModel.message.observe(this, {
            it?.let {
                adapter.data
            }
        })

        binding.lifecycleOwner = this
        if (savedInstanceState == null) {
            binding.assistantConstraintLayout.visibility = View.INVISIBLE
            //Q&A so like a tree for ques and ans like a conversation with help if tree
            //chat like applications

            val viewTreeObserver: ViewTreeObserver =
                binding.assistantConstraintLayout.viewTreeObserver
            if (viewTreeObserver.isAlive) {
                viewTreeObserver.addOnGlobalLayoutListener(object :
                    ViewTreeObserver.OnGlobalLayoutListener {

                    override fun onGlobalLayout() {
                        circularRevealActivity()
                        binding.assistantConstraintLayout.viewTreeObserver
                            .removeOnGlobalLayoutListener(this)
                    }
                })
            }
            cameraManager = getSystemService(CAMERA_SERVICE) as CameraManager
            try {
                cameraID = cameraManager.cameraIdList[0]
                //0 for back camera
                // 1 for front camera
            } catch (e: java.lang.Exception) {
                e.printStackTrace()

            }
            clipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            textToSpeech = TextToSpeech(this) { status ->
                if (status == TextToSpeech.SUCCESS) {
                    val result: Int = textToSpeech.setLanguage(Locale.ENGLISH)
                    if (result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA) {
                        Log.i(logTTS, "Language Not Supported")

                    } else {
                        Log.i(logTTS, "Language Supported")
                    }
                } else {
                    Log.i(logTTS, "Initialization of Text To Speech Failed")
                }

            }
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
            recognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,Locale.getDefault())
            speechRecognizer.setRecognitionListener(object : RecognitionListener{
                override fun onReadyForSpeech(p0: Bundle?) {
                     //leave empty no use
                }

                override fun onBeginningOfSpeech() {
                    Log.d(logSR, "started")
                }

                override fun onRmsChanged(p0: Float) {
                    TODO("Not yet implemented")
                }

                override fun onBufferReceived(p0: ByteArray?) {
                    TODO("Not yet implemented")
                }

                override fun onEndOfSpeech() {
                    Log.d(logSR, "ended")

                }

                override fun onError(error: Int) {
                    Log.i(logSR, error.toString())
                }

                override fun onResults(bundle: Bundle?) {
                    val data = bundle!!.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    if (data!=null){
                            keeper = data[0]
                            Log.d(logKeeper,keeper)
                            when{

                            }

                        }
                }


                override fun onPartialResults(p0: Bundle?) {
                    TODO("Not yet implemented")
                }

                override fun onEvent(p0: Int, p1: Bundle?) {
                    TODO("Not yet implemented")
                }
            })
        }

    }

    private fun circularRevealActivity() {
        val cx: Int = binding.assistantConstraintLayout.right-getDips(44)
        val cy: Int = binding.assistantConstraintLayout.bottom -getDips(44)
        val finalRadius:Int=Math.max(
            binding.assistantConstraintLayout.width,
            binding.assistantConstraintLayout.height
        )
        val circularReveal = ViewAnimationUtils.createCircularReveal(
            binding.assistantConstraintLayout,
            cx,
            cy,
            0f,
            finalRadius.toFloat()
        )
        val AnimationTime=100
        circularReveal.duration=AnimationTime.toLong()
        binding.assistantConstraintLayout.visibility=View.VISIBLE
        circularReveal.start()
    }

    private fun getDips(i: Int): Int {
        val resources:Resources = resources
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            i.toFloat(),
            resources.displayMetrics
        ).toInt()

    }
}