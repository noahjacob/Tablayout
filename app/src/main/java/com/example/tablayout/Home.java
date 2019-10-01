package com.example.tablayout;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {

   private ArrayList<Items> Items;

    public Home() {
        // Required empty public constructor
    }
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    AdapterList AdapterList;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);


        linearlist();
        View rootView =  inflater.inflate(R.layout.fragment_home,container,false);
        recyclerView = rootView.findViewById(R.id.item_recycler);
        recyclerView.hasFixedSize();
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        AdapterList = new AdapterList(getActivity(),this.Items);
        recyclerView.setAdapter(AdapterList);
        FloatingActionButton fab = rootView.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getContext(),AddingItem.class);
                startActivity(intent);
            }
        });
        return rootView;



    }
    private void linearlist(){
        Items = new ArrayList<>();
        Items.add(new Items("PETA India","Dadar",22));
        Items.add(new Items("Wild World","JBnagar",22));
    }


}
