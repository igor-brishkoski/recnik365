package mk.com.ir365.recnik.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import mk.com.ir365.recnik.R;
import mk.com.ir365.recnik.fund.RecnikApp;
import mk.com.ir365.recnik.models.Zbor;

public class WordsAdapter extends BaseAdapter{

    ArrayList<Zbor> zborovi;

    public WordsAdapter(ArrayList<Zbor> zborovi) {
        this.zborovi = zborovi;
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

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) RecnikApp.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listitem,null);
        }

        TextView t = (TextView) convertView;
        t.setText(zborovi.get(position).getPrevod());
        return t;
    }

    public void refresh(ArrayList<Zbor> noviZborovi){
        zborovi.clear();
        zborovi.addAll(noviZborovi);
        notifyDataSetChanged();
    }
}
