import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.view.LayoutInflater
import com.example.test.R  // Import your R class with the correct package name

class LoadingDialog(private val activity: Activity) {
    private var dialog: AlertDialog? = null

    @SuppressLint("InflateParams")
    fun startLoadingDialog() {
        // Create an AlertDialog.Builder instance
        val builder = AlertDialog.Builder(activity)

        // Use the activity's LayoutInflater to inflate the custom_dialog.xml layout
        val inflater = LayoutInflater.from(activity)
        val dialogView = inflater.inflate(R.layout.custom_dialog, null)

        // Set the inflated view as the dialog's content view
        builder.setView(dialogView)

        // Make the dialog non-cancelable (users can't dismiss it by tapping outside)
        builder.setCancelable(false)

        // Create the AlertDialog
        dialog = builder.create()

        // Show the dialog
        dialog?.show()
    }

    fun dismissDialog() {
        // Dismiss the dialog if it's not null
        dialog?.dismiss()
    }
}
