package com.example.yunjingliu.tutorial.navigation;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.yunjingliu.tutorial.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends Fragment implements AdapterView.OnItemClickListener{
    public final String[] list = {"Profile","Posted Session","Received Feedback"};

    public MeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_me, container, false);
        ListView meList = (ListView) view.findViewById(R.id.lvMeList);
        ListAdapter adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, list);
        meList.setAdapter(adapter);
        meList.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(i==0) {

        }
        if(i==1){
            SessionListFragment sessionListFragment = new SessionListFragment();
            FragmentManager manager = getFragmentManager();
            manager.beginTransaction().add(R.id.flContent, sessionListFragment, "sessionList").addToBackStack("meFragment").commit();

        }
        else{
            TutorHistoryFragment tutorHistoryFragment = new TutorHistoryFragment();
            FragmentManager manager = getFragmentManager();
            manager.beginTransaction().add(R.id.flContent, tutorHistoryFragment, "Profile").addToBackStack("listpage").commit();

        }
    }
}
