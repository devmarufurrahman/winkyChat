package com.example.mrnchatbd.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mrnchatbd.R;


public class ChatFragment extends Fragment {
    RecyclerView recyclerView;

    public ChatFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        recyclerView = view.findViewById(R.id.chatFragmentRecycler);
        setupRecyclerView();
        return view;
    }

    private void setupRecyclerView() {
    }
}