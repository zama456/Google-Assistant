package com.zama.googleassistant.utils;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.zama.googleassistant.R;


public class utils {
    public static final String[] Commands = {"Check my mail","What can i do","Can we date","How to use Google Assistant","Hey ", "for example : search assistant","explore","how to use google assistant","hi","hello","thanks","welcome","clear","date","time","dial","send SMS", "send sms", "joke", "tell me a joke"
            ,"Ask me fun questions", "Open Whatsapp" , "Open Facebook" , "Open Gmail", "Open Youtube" , "Open  GoogleMaps" , "Open Google",
            "Turn on Bluetooth" , "For example : Call mum or call papa ",  "Dial" , "Turn off Bluetooth" , "Turn on Flash" , "Turn off Flash","capture photo" , "any thoughts",
            "play ringtone","stop ringtone","are you married","haha","read me","read my last sms","share file","share a text message that your message",
            "Get bluetooth devices","Copy to clipboard","Read last clipboard","Open google lens","explore","What is your name" , "Play some music",
            "Stop music"};
    //log for text to speech
    public static final String logTTS = "Text To Speech";
    //to see speech recognitions
    public static final String logSR = "SR";
    // to see the availability of text to speech
    public static final String logKeeper = "Keeper";
    public static final String tableName="assistant_message_table";
    public static void setCustomActionBar(ActionBar supportActionBar, Context context) {
        supportActionBar.setDisplayShowHomeEnabled(true);
        supportActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(context);
        @SuppressLint("InflateParams") View mCustomView = mInflater.inflate(R.layout.customtoolbar, null);
        supportActionBar.setCustomView(mCustomView);
        supportActionBar.setDisplayShowCustomEnabled(true);

    }
//
//    public static void setCustomActionBar(Fragment fragment, Context context) {
//        setCustomActionBar( ((AppCompatActivity) fragment.requireActivity()).getSupportActionBar(),context);
//    }


}
