package fr.clerc.myapplication.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import fr.clerc.myapplication.R;
import fr.clerc.myapplication.RecyclerViewAdapter;


public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerAdapter;
    private HomeViewModel homeViewModel;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getChildFragmentManager());
        ViewPager viewPager = view.findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabs = view.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        return view;


    }

}