package mk.com.ir365.recnik.adapters;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import mk.com.ir365.recnik.R;
import mk.com.ir365.recnik.customtextview.TypeFacedTextView;
import mk.com.ir365.recnik.fragments.TranslateFragment;
import mk.com.ir365.recnik.fund.RecnikApp;
import mk.com.ir365.recnik.models.Zbor;
import android.view.ViewGroup.LayoutParams;

public class WordsAdapter extends BaseAdapter{

    private static final String TAG = WordsAdapter.class.getSimpleName();
    ArrayList<Zbor> zborovi;
    TranslateFragment fragment;

    ViewGroup.LayoutParams lp1;
    ViewGroup.LayoutParams lp2;
    LinearLayout.LayoutParams imglp;

    LinearLayout mainLayout;

    public WordsAdapter(ArrayList<Zbor> zborovi, TranslateFragment fragment) {
        this.zborovi = zborovi;
        lp1 = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        lp2 = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        imglp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
        imglp.setMargins(30, 5, 30, 5);

        this.fragment = fragment;
    }

    @Override
    public int getCount() {
        return zborovi.size();
    }

    @Override
    public Object getItem(int position) {
        return zborovi.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Zbor zbor = zborovi.get(position);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) RecnikApp.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.mainlayout,null);
        }



        TypeFacedTextView prevod = (TypeFacedTextView) convertView.findViewById(R.id.tv_prevod);
        if(((LinearLayout)convertView).getChildAt(2)!=null)
            ((LinearLayout)convertView).removeViewAt(2);
        prevod.setText(zbor.getPrevod());

        addTextView(zbor,(LinearLayout)convertView);

        TypeFacedTextView descr = (TypeFacedTextView) convertView.findViewById(R.id.zborovi_so_sl_znacenjce);
        descr.setVisibility(View.VISIBLE);

        if(zbor.getPrevod().contains("НЕ Е ПРОНАЈДЕН ТАКОВ")){
            descr.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }

    public void refresh(ArrayList<Zbor> noviZborovi){
        zborovi = new ArrayList<>();
        zborovi.addAll(noviZborovi);
        notifyDataSetChanged();
        notifyDataSetInvalidated();
    }

    public void addTextView(Zbor z,LinearLayout mainLayout) {
        LinearLayout ll = null;
        if (z.getDesc().length() != 0) {
            // ////////////LINEAR LAYOUT///////////////
            if(ll!=null){
                for(int i=0;i<ll.getChildCount();i++)
                    ll.removeViewAt(i);
            }
            ll = new LinearLayout(RecnikApp.getContext());
            ll.setLayoutParams(lp2);
            ll.setOrientation(LinearLayout.VERTICAL);
            // /////////////////////////////////////////////
            LinearLayout ll1 = new LinearLayout(RecnikApp.getContext());
            ll1.setLayoutParams(lp2);
            ll1.setOrientation(LinearLayout.HORIZONTAL);
            int counter = 0;
            for (final String s : z.getSimilarWords()) {
                final TextView w = new TextView(RecnikApp.getContext());
                w.setText(s + "; ");
                w.setTextColor(Color.parseColor("#00adee"));
                w.setLayoutParams(lp2);
                w.setPadding(10, 0, 0, 10);
                w.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        fragment.getTranslations();
                        //pb.setVisibility(View.VISIBLE);
                    }
                });
                counter++;
                ll1.addView(w);
                if (counter % 4 == 0) {
                    ll.addView(ll1);
                    ll1 = new LinearLayout(RecnikApp.getContext());
                    ll1.setLayoutParams(lp2);
                    ll1.setOrientation(LinearLayout.HORIZONTAL);

                }
                if(counter>19)
                    break;
            }

            mainLayout.addView(ll);
        }
    }
}
