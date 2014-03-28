package mk.com.ir365.recnik.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import mk.com.ir365.recnik.R;
import mk.com.ir365.recnik.fund.RecnikConstant;


public class FromDialogFragment extends DialogFragment {

    private PrevediOdInterface mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.prevedi_od));
        builder.setItems(RecnikConstant.siteopcii, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.prevediOdJazik(RecnikConstant.siteopcii[which]);
            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (PrevediOdInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnTranslatingActionPerformed");
        }
    }

    public interface PrevediOdInterface {
        public void prevediOdJazik(String jazik);
    }
}
