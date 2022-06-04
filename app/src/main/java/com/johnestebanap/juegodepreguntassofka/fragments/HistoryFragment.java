package com.johnestebanap.juegodepreguntassofka.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.johnestebanap.juegodepreguntassofka.R;
import com.johnestebanap.juegodepreguntassofka.db.DbHelper;
import com.johnestebanap.juegodepreguntassofka.db.DbHelper2;
import com.johnestebanap.juegodepreguntassofka.db.HistoryUser;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {

    private ArrayList<HistoryUser> historyUsers;

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

        for (HistoryUser history : historyUsers) {
            String user = "Usuario : ".concat(history.getNameUser());
            txtHistory.setText(user);
            lyRecord.addView(txtHistory);
        }
        return view;
    }

    public void dataBaseInit() {
        DbHelper2 dbHelper2 = new DbHelper2(getContext());
        historyUsers = dbHelper2.getUsersAndScore();
    }
}