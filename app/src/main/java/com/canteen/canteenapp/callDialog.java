package com.canteen.canteenapp;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class callDialog {

    boolean cr;

    public boolean showDialog(Activity activity){

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.prompt_ask_payment_dialog);

        Button proc = (Button) dialog.findViewById(R.id.proceed);
        Button canceled = (Button) dialog.findViewById(R.id.cancel);

        canceled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        proc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cr = true;
                dialog.dismiss();


            }
        });

        dialog.show();

        return cr;

    }






}
