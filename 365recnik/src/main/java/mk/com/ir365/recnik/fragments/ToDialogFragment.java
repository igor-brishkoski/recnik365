package mk.com.ir365.recnik.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import mk.com.ir365.recnik.fund.RecnikConstant;

public class ToDialogFragment extends DialogFragment {
    String jazikOd;
    private PrevediVoInterface mListener;

    public ToDialogFragment(String jazikOd) {
        this.jazikOd = jazikOd;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        switch (jazikOd) {
            case "Македонски": {
                builder.setItems(RecnikConstant.drugi_jazici, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.prevediVoJazik(RecnikConstant.drugi_jazici[which]);
                    }
                });
                break;
            }
            default: {
                builder.setItems(RecnikConstant.makednoski, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.prevediVoJazik(RecnikConstant.makednoski[0]);
                    }
                });
            }
        }

        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (PrevediVoInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnTranslatingActionPerformed");
        }
    }

    public interface PrevediVoInterface {
        public void prevediVoJazik(String jazik);
    }
}
