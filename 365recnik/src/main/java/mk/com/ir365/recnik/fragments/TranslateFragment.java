package mk.com.ir365.recnik.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

import mk.com.ir365.recnik.R;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link mk.com.ir365.recnik.fragments.TranslateFragment.OnTranslatingActionPerformed} interface
 * to handle interaction events.
 * Use the {@link TranslateFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class TranslateFragment extends Fragment {

    //instance of parent activity
    private OnTranslatingActionPerformed mListener;

    AutoCompleteTextView word;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public static TranslateFragment newInstance() {

        return  new TranslateFragment();
    }
    public TranslateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_translate, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String jazik, String zbor) {
        if (mListener != null) {
            mListener.getTranslation(jazik,zbor);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnTranslatingActionPerformed) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnTranslatingActionPerformed");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnTranslatingActionPerformed {

        public void getTranslation(String jazik, String zbor);
    }

}
