package com.johnestebanap.juegodepreguntassofka.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.johnestebanap.juegodepreguntassofka.R;

public class HistoryFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record, container, false);
        TextView textViewTitle = view.findViewById(R.id.txtTitleRecord);

        LinearLayout lyRecord = view.findViewById(R.id.lyRecord);

        TextView txtHistory = new TextView(getContext());
        txtHistory.setText("Usuario: 0000000");

        lyRecord.addView(txtHistory);
        
        return view;
    }
}