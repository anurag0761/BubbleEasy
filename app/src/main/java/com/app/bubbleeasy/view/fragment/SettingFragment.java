package com.app.bubbleeasy.view.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import com.app.bubbleeasy.R;
import com.app.bubbleeasy.Utills.Constant;
import com.app.bubbleeasy.databinding.FragmentSettingLayoutBinding;


public class SettingFragment extends Fragment {


    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    SharedPreferences sharedpreferences;
    FragmentSettingLayoutBinding binding;
    SharedPreferences.Editor editor;
    String[] rows = {"10", "20", "30", "40"};
    String[] columns = {"8", "16", "24", "32"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.fragment_setting_layout, container, false);
        sharedpreferences = getActivity().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        if (sharedpreferences.getString(Constant.Vibrate, "").equals("1")) {
            binding.switchVibrate.setChecked(true);
        } else {
            binding.switchVibrate.setChecked(false);
        }

        if (sharedpreferences.getString(Constant.Sound, "").equals("1")) {
            binding.switchSound.setChecked(true);
        } else {
            binding.switchSound.setChecked(false);
        }

        binding.switchVibrate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editor.putString(Constant.Vibrate, "1");
                    editor.commit();
                } else {
                    editor.putString(Constant.Vibrate, "0");
                    editor.commit();
                }
            }
        });

        binding.switchSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editor.putString(Constant.Sound, "1");
                    editor.commit();
                } else {
                    editor.putString(Constant.Sound, "0");
                    editor.commit();
                }
            }
        });


        ArrayAdapter rowsAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, rows);
        rowsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerNumberOfRows.setAdapter(rowsAdapter);
        if (!sharedpreferences.getString(Constant.rowsPosition, "").equals("")) {
            binding.spinnerNumberOfRows.setSelection(Integer.parseInt(sharedpreferences.getString(Constant.rowsPosition, "")));
        }
        binding.spinnerNumberOfRows.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editor.putString(Constant.numberOfRows, rows[position]);
                editor.putString(Constant.rowsPosition, "" + position);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter columnsAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, columns);
        columnsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerNumberOfColoumns.setAdapter(columnsAdapter);
        if (!sharedpreferences.getString(Constant.colsPosition, "").equals("")) {
            binding.spinnerNumberOfColoumns.setSelection(Integer.parseInt(sharedpreferences.getString(Constant.colsPosition, "")));
        }
        binding.spinnerNumberOfColoumns.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editor.putString(Constant.numberOfColumns, columns[position]);
                editor.putString(Constant.colsPosition, "" + position);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return binding.getRoot();
    }


}
