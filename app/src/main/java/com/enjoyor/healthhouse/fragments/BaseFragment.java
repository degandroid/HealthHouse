package com.enjoyor.healthhouse.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;

import com.enjoyor.healthhouse.R;

/**
 * Created by YuanYuan on 2016/4/25.
 */
public class BaseFragment extends Fragment {
    protected Dialog mDialog;

    protected ProgressDialog progress() {
        return progress("", "", null, false);
    }

    protected ProgressDialog progress(DialogInterface.OnCancelListener listener) {
        return progress("", "", listener, true);
    }

    protected ProgressDialog progress(String pTitle, String pMessage,
                                      DialogInterface.OnCancelListener pCancelClickListener, boolean outsideCancel) {
        if (getActivity().isFinishing())
            return null;
        ProgressDialog mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.show();
        mProgressDialog.setCanceledOnTouchOutside(outsideCancel);
        mProgressDialog.setCancelable(outsideCancel);
        mProgressDialog.setContentView(View.inflate(getActivity(),
                R.layout.dialog_process, null));
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable());
        mProgressDialog.setOnCancelListener(pCancelClickListener);
        mProgressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            public boolean onKey(DialogInterface dialog,
                                 int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialog.dismiss();
                    return true;
                } else {
                    return false;
                }
            }
        });
        mDialog = mProgressDialog;
        return mProgressDialog;
    }


    protected void cancel() {
        if (mDialog != null)
            mDialog.cancel();
    }


}
