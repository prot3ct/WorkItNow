package prot3ct.workit.utils

import android.app.ProgressDialog
import android.content.Context

class WorkItProgressDialog(context: Context?) {
    var dialog: ProgressDialog
    fun showProgress(text: String?) {
        dialog.setMessage(text)
        dialog.show()
    }

    fun dismissProgress() {
        dialog.dismiss()
    }

    init {
        dialog = ProgressDialog(context)
    }
}