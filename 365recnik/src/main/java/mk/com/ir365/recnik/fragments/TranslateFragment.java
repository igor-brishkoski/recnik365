package mk.com.ir365.recnik.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import mk.com.ir365.recnik.R;
import mk.com.ir365.recnik.customtextview.TypeFacedTextView;
import mk.com.ir365.recnik.fund.RecnikApp;
import mk.com.ir365.recnik.fund.RecnikConstant;
import mk.com.ir365.recnik.listeners.SugestiiTextChangedListener;
import mk.com.ir365.recnik.models.Zbor;

public class TranslateFragment extends Fragment {

    private static final String TAG = TranslateFragment.class.getSimpleName();
    TypeFacedTextView tvPrevediOd;
    TypeFacedTextView tvPrevediVo;
    ImageView ivSearch;
    AutoCompleteTextView word;
    ArrayList<Zbor> zborovi;
    //instance of parent activity
    private OnTranslatingActionPerformed mListener;

    public TranslateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public static TranslateFragment newInstance() {

        return new TranslateFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_translate, null);
        word = (AutoCompleteTextView) view.findViewById(R.id.actv_word_for_translating);
        word.addTextChangedListener(new SugestiiTextChangedListener(word, ""));
        word.setTypeface(Typeface.createFromAsset(RecnikApp.getContext().getAssets(), RecnikConstant.ROBOTO_THIN));

        tvPrevediOd = (TypeFacedTextView) view.findViewById(R.id.tftv_language_from);
        tvPrevediOd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FromDialogFragment().show(getActivity().getSupportFragmentManager(), "FROM");
            }
        });

        tvPrevediVo = (TypeFacedTextView) view.findViewById(R.id.tftv_language_to);
        tvPrevediVo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ToDialogFragment(tvPrevediOd.getText().toString()).show(getActivity().getSupportFragmentManager(), "TO");
            }
        });

        ivSearch = (ImageView) view.findViewById(R.id.iv_search);
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new InvokeWebService().execute(getLanguageType(),word.getText().toString());
                Log.d(TAG,word.getText().toString()+" "+getLanguageType());
                InputMethodManager inputManager = (InputMethodManager)
                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });

        return view;
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

    private String getLanguageType() {
        String type = "";
        switch (tvPrevediOd.getText().toString()) {
            case "Македонски": {
                switch (tvPrevediVo.getText().toString()) {
                    case "English":
                        type = RecnikConstant.types[0];
                        break;
                    case "Deutchs":
                        type = RecnikConstant.types[1];
                        break;
                    case "Français":
                        type = RecnikConstant.types[2];
                        break;
                    case "Shqip":
                        type = RecnikConstant.types[3];
                        break;
                    case "Eλληνικά":
                        type = RecnikConstant.types[4];
                        break;
                    case "Slovenščina":
                        type = RecnikConstant.types[5];
                        break;
                    case "Pусский":
                        type = RecnikConstant.types[6];
                        break;
                    case "Српски":
                        type = RecnikConstant.types[7];
                        break;
                    case "Türkçe":
                        type = RecnikConstant.types[8];
                        break;
                }
                break;
            }//Makedonski END
            case "English":
                type = RecnikConstant.types[9];
                break;
            case "Deutchs":
                type = RecnikConstant.types[10];
                break;
            case "Français":
                type = RecnikConstant.types[11];
                break;
            case "Shqip":
                type = RecnikConstant.types[12];
                break;
            case "Eλληνικά":
                type = RecnikConstant.types[13];
                break;
            case "Slovenščina":
                type = RecnikConstant.types[14];
                break;
            case "Pусский":
                type = RecnikConstant.types[15];
                break;
            case "Српски":
                type = RecnikConstant.types[16];
                break;
            case "Türkçe":
                type = RecnikConstant.types[17];
                break;
            case "Лексикон на изрази и зборови":
                type = RecnikConstant.types[18];
                break;
            case "Правопис":
                type = RecnikConstant.types[19];
                break;
            case "ИТ поимник (Eng->Мак)":
                type = RecnikConstant.types[20];
                break;
            default:
                type = RecnikConstant.types[0];
        }
        Log.d(TAG, type);
        return type;
    }

    public interface OnTranslatingActionPerformed {
        public void getTranslation(String jazik, String zbor);
    }

    private class InvokeWebService extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String html = "";
            DefaultHttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(
                    "http://365.com.mk/recnik/upiti/prikazi.php");
            ArrayList<NameValuePair> postParams;
            post.setHeader("Content-Type",
                    "application/x-www-form-urlencoded;charset=UTF-8");

            postParams = new ArrayList<NameValuePair>();

            postParams.add(new BasicNameValuePair("jazik", params[0]));
            postParams.add(new BasicNameValuePair("zbor", params[1]));

            try {

                UrlEncodedFormEntity en = new UrlEncodedFormEntity(postParams,
                        HTTP.UTF_8);
                post.setEntity(en);

                HttpResponse response = client.execute(post);

                HttpEntity entity = response.getEntity();

                html = EntityUtils.toString(entity, HTTP.UTF_8);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return html;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Document doc = Jsoup.parse(result);

            Elements divs = doc.getElementsByTag("div");

            zborovi = new ArrayList<Zbor>();

            for (int i = 0; i < divs.size(); i++) {
                Zbor zbor = new Zbor();

                zbor.setPrevod(divs.get(i).getElementsByClass("prevod").text());
                zbor.setDesc(divs.get(i).getElementsByClass("desc").text());

                if (zbor.getDesc().length() != 0) {
                    String simWords = divs.get(i).getElementsByClass("word")
                            .text();
                    String[] simw = simWords.split(" ");
                    for (int j = 0; j < simw.length; j++) {
                        zbor.getSimilarWords().add(simw[j]);
                    }

                }
                zborovi.add(zbor);
            }

            // ///////////////////////////////////////////////////////////
            if (zborovi.get(0).getPrevod().length() != 0) {
                //TODO after we get all words
                for (Zbor zbor : zborovi)
                    Log.d(TAG, zbor.toString());

            } else {/*
                TextView prevod = new TextView(getActivity());
                prevod.setText("НЕ Е ПРОНАЈДЕН ТАКОВ ЗБОР ВО БАЗАТА");
                prevod.setTextSize(25);
                prevod.setTextColor(Color.parseColor("#0a0007"));
                // prevod.setId(5);
                lp1 = new LayoutParams(LayoutParams.MATCH_PARENT,
                        LayoutParams.WRAP_CONTENT);
                prevod.setLayoutParams(lp1);
                prevod.setPadding(10, 10, 0, 10);
                mainLayout.addView(prevod);*/
            }


        }

    }

}
