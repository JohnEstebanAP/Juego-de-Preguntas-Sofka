package com.johnestebanap.juegodepreguntassofka.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.johnestebanap.juegodepreguntassofka.R;

public class RecordFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View recordTitle = inflater.inflate(R.layout.fragment_record, container, false);
        TextView textViewTitle = recordTitle.findViewById(R.id.txtTitleRecord);

        View txtViewRecord = inflater.inflate(R.layout.fragment_record, container, false);
        TextView textViewRecord = txtViewRecord.findViewById(R.id.txtViewRecord);








        return recordTitle;
    }
}