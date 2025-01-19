package com.example.seminar_4_dam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

public class MasiniAdapter extends BaseAdapter {
    private List<Masina> masini = null;
    private Context context;
    private int resursaLayout;

    public MasiniAdapter(List<Masina> masini, Context context, int resursaLayout) {
        this.masini = masini;
        this.context = context;
        this.resursaLayout = resursaLayout;
    }

    @Override
    public int getCount() {
        return masini.size();
    }

    @Override
    public Object getItem(int position) {
        return masini.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(resursaLayout, parent, false);

        TextView denumireTV = view.findViewById(R.id.modelTV);
        TextView anTV = view.findViewById(R.id.anTV);
        TextView brandTV = view.findViewById(R.id.brandTV);
        CheckBox electricaCB = view.findViewById(R.id.electricaCB);
        TextView caiPutereTV = view.findViewById(R.id.caiPutereTV);


        Masina masina = (Masina) getItem(position);

        denumireTV.setText(masina.getModel());
        anTV.setText(String.valueOf(masina.getAn()));
        brandTV.setText(masina.getBrand());
        electricaCB.setChecked(masina.isEsteElectrica());
        caiPutereTV.setText(String.valueOf(masina.getCaiPutere()));

        return view;
    }
}
