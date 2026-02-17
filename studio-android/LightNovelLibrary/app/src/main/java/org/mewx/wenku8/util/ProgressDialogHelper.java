package org.mewx.wenku8.util;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import org.mewx.wenku8.R;

/**
 * A helper to create Material 3 dialogs with an embedded progress indicator.
 * Replaces the old MaterialDialog.Builder.progress() pattern.
 * 
 * @author Claude Opus 4.6
 */
public class ProgressDialogHelper {

    private final AlertDialog dialog;
    private final LinearProgressIndicator progressBar;
    private final TextView messageView;

    private ProgressDialogHelper(@NonNull AlertDialog dialog,
                                 @NonNull LinearProgressIndicator progressBar,
                                 @NonNull TextView messageView) {
        this.dialog = dialog;
        this.progressBar = progressBar;
        this.messageView = messageView;
    }

    /**
     * Create and show a progress dialog.
     *
     * @param context        the context
     * @param message        the message to display
     * @param indeterminate  true for indeterminate, false for determinate
     * @param cancelable     whether the dialog can be cancelled
     * @param cancelListener optional cancel listener
     * @return the ProgressDialogHelper instance
     */
    public static ProgressDialogHelper show(@NonNull Context context,
                                            @NonNull CharSequence message,
                                            boolean indeterminate,
                                            boolean cancelable,
                                            @Nullable DialogInterface.OnCancelListener cancelListener) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_progress, null);
        TextView messageView = view.findViewById(R.id.progress_message);
        LinearProgressIndicator progressBar = view.findViewById(R.id.progress_bar);

        messageView.setText(message);

        if (indeterminate) {
            progressBar.setIndeterminate(true);
        } else {
            progressBar.setIndeterminate(false);
            progressBar.setMax(1);
            progressBar.setProgress(0);
        }

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context, R.style.CustomMaterialAlertDialog)
                .setView(view)
                .setCancelable(cancelable);

        if (cancelListener != null) {
            builder.setOnCancelListener(cancelListener);
        }

        AlertDialog dialog = builder.create();
        dialog.show();

        return new ProgressDialogHelper(dialog, progressBar, messageView);
    }

    /**
     * Convenience overload that accepts a string resource for the message.
     */
    public static ProgressDialogHelper show(@NonNull Context context,
                                            @StringRes int messageResId,
                                            boolean indeterminate,
                                            boolean cancelable,
                                            @Nullable DialogInterface.OnCancelListener cancelListener) {
        return show(context, context.getString(messageResId), indeterminate, cancelable, cancelListener);
    }

    public void setProgress(int progress) {
        progressBar.setProgress(progress, true);
    }

    public void setMaxProgress(int max) {
        progressBar.setIndeterminate(false);
        progressBar.setMax(max);
    }

    public void dismiss() {
        try {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (Exception e) {
            // Ignore exceptions from dismissed dialogs or detached windows.
        }
    }

    public boolean isShowing() {
        return dialog.isShowing();
    }

    public void setTitle(@StringRes int titleId) {
        dialog.setTitle(titleId);
    }

    public void setTitle(CharSequence title) {
        dialog.setTitle(title);
    }

    public void setMessage(CharSequence message) {
        messageView.setText(message);
    }
}
