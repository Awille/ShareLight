package com.example.will.sharelight.main.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.will.sharelight.R;

public class WarnningDialog {
    private Dialog dialog;
    private Context context;
    TextView title;
    TextView content;
    Button certain;
    Button cancel;



    public interface ActionListener {
        void onCertain();
        void onCancel();
    }

    public static WarnningDialog build(Context context, String titleStr, String contentStr, ActionListener actionListener) {
        return new WarnningDialog(context, titleStr, contentStr, actionListener);
    }

    public WarnningDialog(Context context, String titleStr, String contentStr,ActionListener actionListener) {
        this.context = context;
        initDilog();
        title.setText(titleStr);
        content.setText(contentStr);
        certain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionListener.onCertain();
                dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionListener.onCancel();
                dismiss();
            }
        });
    }

    public void show() {
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        double width = ((Activity)context).getWindowManager().getDefaultDisplay().getWidth() * 0.72;
        lp.width = (int) width;
        dialog.getWindow().setAttributes(lp);
        //dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.show();
    }

    public void dismiss() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }


    private void initDilog() {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_warning, null);
        dialog.setContentView(view);
        initView(view);
    }

    private void initView(View view) {
        title = view.findViewById(R.id.title_txt);
        content = view.findViewById(R.id.content_txt);
        certain = view.findViewById(R.id.certain);
        cancel = view.findViewById(R.id.cancel);
    }


}
