package ca.uottawa.finalproject.ticketmaster.fragments;

import android.app.AlertDialog;
import android.content.Context;

import ca.uottawa.finalproject.R;

public class HelpDialog {

    public static void show(Context context, int messageId ) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.help_title)
                .setMessage(messageId)
                .setNeutralButton(R.string.dismiss, (click, b) -> { })
                .create().show();
    }
}
