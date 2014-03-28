package mk.com.ir365.recnik.listeners;

import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import mk.com.ir365.recnik.fund.RecnikApp;


public class SugestiiTextChangedListener implements TextWatcher {

    AutoCompleteTextView autoCompleteTextView;
    String type;

    public SugestiiTextChangedListener(AutoCompleteTextView autoCompleteTextView, String type) {
        this.autoCompleteTextView = autoCompleteTextView;
        this.type = type;
    }

    @Override
    public void onTextChanged(CharSequence word, int arg1, int arg2,
                              int arg3) {
        if (word.length() > 2) {

            type = "angmkd";
            new Sugestii().execute(type, word.toString());
        }
    }

    @Override
    public void beforeTextChanged(CharSequence arg0, int arg1,
                                  int arg2, int arg3) {
    }

    @Override
    public void afterTextChanged(Editable arg0) {
    }

    /**
     * AsyncTask Class worker responsible for getting suggestion
     */
    private class Sugestii extends AsyncTask<String, Void, ArrayList<String>> {
        ArrayList<String> sugestii;

        @Override
        protected ArrayList<String> doInBackground(String... params) {
            DefaultHttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(
                    "http://365.com.mk/recnik/upiti/pronajdi.php");
            ArrayList<BasicNameValuePair> parametars = new ArrayList<>();
            parametars.add(new BasicNameValuePair("jazik", params[0]));
            parametars.add(new BasicNameValuePair("zbor", params[1]));

            try {
                post.setEntity(new UrlEncodedFormEntity(parametars, HTTP.UTF_8));
                HttpResponse response = client.execute(post);
                HttpEntity entity = response.getEntity();
                String sug = EntityUtils.toString(entity);


                Document doc = Jsoup.parse(sug);
                sugestii = new ArrayList<>();
                Elements divs = doc.getElementsByTag("div");
                int i = 0;
                for (Element e : divs) {
                    if (i % 2 == 0)
                        sugestii.add(e.getElementsByClass("word").text()
                                .substring(1));
                    i++;
                }

            } catch (UnsupportedEncodingException e) {

                e.printStackTrace();
            } catch (ClientProtocolException e) {

                e.printStackTrace();
            } catch (IOException e) {

                e.printStackTrace();
            }
            return sugestii;
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    RecnikApp.getContext(),
                    android.R.layout.select_dialog_item, result);
            autoCompleteTextView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            super.onPostExecute(result);
        }

    }

}
