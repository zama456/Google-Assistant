package com.zama.googleassistant.functions

import android.view.animation.Animation

class AssistantFunctions {
    companion object {
        var Dips = 44
        var Animation_Time = 10
        var REQUEST_CALL = 1
        var SEN_DSMS = 2
        var READ_SMS = 3
        var SHARE_FILE = 5
        var SHARE_TEXT_FILE = 5
        var READ_CONTACTS = 6
        var CAPTURE_PHOTO = 7
        var image_Index: Int = 0
        var REQUEST_CODE_SELECT_DOC: Int = 100
        var REQUEST_ENABLE_BT = 1000
        var questions: List<String> = listOf<String>(
            " What would you name your boat if you had one? ",
            "What's the closest thing to real magic?",
            "Who is the messiest person you know?",
            " What will finally break the internet? ",
            " What's the most useless talent you have?",
            " Where is the worst smelling place you've been?"
        )
    }
}