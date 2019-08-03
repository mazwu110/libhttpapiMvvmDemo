package com.qlib.components;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.qlib.R;

public class TipDialog extends AlertDialog {
    public Dialog mDialog;
    public Button btn_confirm;

    public TipDialog(Context context, String message) {
        super(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_layout, null);

        mDialog = new Dialog(context, R.style.dialog);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(true);

        btn_confirm = view.findViewById(R.id.btn_confirm);
        TextView dialog_message =  view.findViewById(R.id.dialog_message);
        dialog_message.setText(message);
        view.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });
        mDialog.show();
    }
}
