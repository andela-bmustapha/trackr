package com.bmustapha.trackr.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.bmustapha.trackr.R;

/**
 * Created by tunde on 10/21/15.
 */
public class HelpDialog {

    public static String newline = System.getProperty("line.separator");

    public void showDialog(Context context, View view) {
        String helpText = "Trackr is a productivity management application that helps you keep track of your most active" +
                " locations and time spent in such locations without getting in the way." + newline +
                 newline + "To start tracking, click the Start Tracking button and the application takes care of the rest. When you are " +
                "done tracking, click the Stop Tracking button and the application stops." + newline +
                newline + "You can view tracking history by clicking the view History button. History is sorted by date and location.";
        TextView textView = (TextView) view.findViewById(R.id.help_text);
        textView.setText(helpText);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context)
                .setTitle("How it works")
                .setView(view)
                .setCancelable(false)
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // cancel dialog
                    }
                });

        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
