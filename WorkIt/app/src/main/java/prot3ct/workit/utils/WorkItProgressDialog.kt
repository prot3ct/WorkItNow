package prot3ct.workit.utils;


import android.app.ProgressDialog;
import android.content.Context;

public class WorkItProgressDialog {
    ProgressDialog dialog;

    public WorkItProgressDialog(Context context) {
        this.dialog = new ProgressDialog(context);
    }

    public void showProgress(String text) {
        dialog.setMessage(text);
        dialog.show();
    }

    public void dismissProgress() {
        dialog.dismiss();
    }
}
