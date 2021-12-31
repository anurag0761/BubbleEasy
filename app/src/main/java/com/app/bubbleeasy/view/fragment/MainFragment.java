package com.app.bubbleeasy.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import com.app.bubbleeasy.R;
import com.app.bubbleeasy.databinding.FragmentMainLayoutBinding;
import com.app.bubbleeasy.view.activity.BubbleActivity;


public class MainFragment extends Fragment {

    FragmentMainLayoutBinding binding;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.fragment_main_layout, container, false);

        binding.btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), BubbleActivity.class);
                startActivity(i);


            }
        });

        return binding.getRoot();
    }


}
