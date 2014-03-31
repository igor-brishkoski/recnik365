package mk.com.ir365.recnik.fund;

import android.os.AsyncTask;
import android.util.Log;

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
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mk.com.ir365.recnik.R;
import mk.com.ir365.recnik.adapters.WordsAdapter;
import mk.com.ir365.recnik.models.Zbor;

public class InvokeWebService extends AsyncTask<String, Void, String> {

    private static final String TAG = InvokeWebService.class.getSimpleName();
    ArrayList<Zbor> zborovi;
    WordsAdapter adapter;


    public InvokeWebService(ArrayList<Zbor> zbors, WordsAdapter adapter) {
        this.zborovi = zbors;
        this.adapter = adapter;

    }

    @Override
    protected String doInBackground(String... params) {
        String html = "";
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(
                "http://365.com.mk/recnik/upiti/prikazi.php");
        ArrayList<NameValuePair> postParams;
        post.setHeader("Content-Type",
                "application/x-www-form-urlencoded;charset=UTF-8");

        postParams = new ArrayList<>();
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

        if (!divs.get(0).toString().contains("НЕ Е ПРОНАЈДЕН ТАКОВ")) {
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
                        if(j>19)
                            break;
                    }
                }
                zborovi.add(zbor);
            }
        }
        // ///////////////////////////////////////////////////////////
        if (zborovi.size() != 0) {
            adapter.refresh(zborovi);
        } else {
            Zbor z = new Zbor();
            z.setPrevod(RecnikApp.getContext().getResources().getString(R.string.ne_postoi_zborot));
            z.setDesc("");
            zborovi.add(z);
            adapter.refresh(zborovi);
        }
    }
}