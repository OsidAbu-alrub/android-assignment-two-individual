package com.abualrub.assignmenttwoindividualv2.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;

import com.abualrub.assignmenttwoindividualv2.R;
import com.abualrub.assignmenttwoindividualv2.domain.User;
import com.abualrub.assignmenttwoindividualv2.util.Tags;
import com.abualrub.assignmenttwoindividualv2.util.Utils;
import com.google.gson.Gson;

import java.util.ArrayList;

public class Education extends Fragment {

    private CheckBox checkBoxDoesHaveEducation;
    private EditText editTextSchoolName;
    private EditText editTextMajor;
    private EditText editTextFrom;
    private EditText editTextTo;
    private Spinner spinner;
    private ListView listview;
    private Button buttonSave;
    private Button buttonAdd;
    private LinearLayoutCompat linearLayoutEducation;

        private static final String[] DYNAMIC_VALUES =
            {"High School","BA","MS","PhD"};
    private static boolean isCreated;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private View fragment;
    private ArrayList<String> listViewItems = new ArrayList<String>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_education,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        fragment = view;
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = prefs.edit();
        setupViews();
        isCreated = prefs.getBoolean(Tags.IS_CREATED.CONSTANT,false);
        if(isCreated) setupValues();
    }

    private void setupViews() {
        checkBoxDoesHaveEducation = fragment.findViewById(R.id.checkBoxDoesHaveEducation);
        initCheckBoxDoesHaveEducation();

        linearLayoutEducation = fragment.findViewById(R.id.linearLayoutEducation);
        editTextSchoolName = fragment.findViewById(R.id.editTextSchoolName);
        editTextMajor = fragment.findViewById(R.id.editTextMajor);
        editTextFrom = fragment.findViewById(R.id.editTextFrom);
        editTextTo = fragment.findViewById(R.id.editTextTo);

        spinner =fragment.findViewById(R.id.spinner);
        initSpinner();

        listview = fragment.findViewById(R.id.listview);
        buttonSave = fragment.findViewById(R.id.buttonSave);
        initSaveButton();

        buttonAdd = fragment.findViewById(R.id.buttonAdd);
        initAddButton();
    }
    private void initSpinner() {
        ArrayAdapter<String> adp = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_list_item_1, DYNAMIC_VALUES);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adp);
    }


    private void setupValues() {
        Gson gson = new Gson();
        String json = prefs.getString(Tags.USER.CONSTANT,null);
        if(json == null){isCreated = false; return;};
        User user = gson.fromJson(json,User.class);

        if(!user.doesHaveEdu()) return;
        if(user.getEducation() == null) return;
        listViewItems = user.getEducation();
        ArrayAdapter<String> adpt = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,listViewItems);
        listview.setAdapter(adpt);
        checkBoxDoesHaveEducation.setChecked(true);
    }

    private void initCheckBoxDoesHaveEducation(){
        checkBoxDoesHaveEducation.setOnCheckedChangeListener((v,e) ->{
            if(v.isChecked()) {
                linearLayoutEducation.setVisibility(View.VISIBLE);
                listview.setVisibility(View.VISIBLE);
            }
            else {
                linearLayoutEducation.setVisibility(View.GONE);
                listview.setVisibility(View.GONE);
            }
        });
    }

    private void initSaveButton()
    {
        buttonSave.setOnClickListener(view ->{
            try{
                User user = new User();
                if(isCreated)
                    user = new Gson().fromJson
                            (Utils.isPropertyPreviouslyFilled
                                    (getContext(), Tags.USER.CONSTANT),User.class);

                isCreated = true;
                user.setEducation(listViewItems);
                user.setDoesHaveEdu(checkBoxDoesHaveEducation.isChecked());
                String json = new Gson().toJson(user);

                editor.putString(Tags.USER.CONSTANT,json);
                editor.putBoolean(Tags.IS_CREATED.CONSTANT,isCreated);
                editor.commit();
                Toast.makeText(getActivity(), "Saved Successfully", Toast.LENGTH_SHORT).show();
            }
            catch(IllegalArgumentException e){
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
            catch(Exception e){
                Log.d("ERROR_CATCH",e.getMessage());
            }
        });
    }

    private void initAddButton(){
        buttonAdd.setOnClickListener(view ->{
            try{
                String schoolName = editTextSchoolName.getText().toString();
                String major = editTextMajor.getText().toString();
                String from = editTextFrom.getText().toString();
                String to = editTextTo.getText().toString();
                String level = spinner.getSelectedItem().toString();
                String s = new User().educationToString(schoolName,major,from,to,level);

                if(!Utils.isValidString(schoolName)) throw new IllegalArgumentException("Enter School Name");
                if(!Utils.isValidString(major)) throw new IllegalArgumentException("Enter Major");
                if(!Utils.isValidString(from)) throw new IllegalArgumentException("Enter Start Year");
                if(!Utils.isValidString(to)) throw new IllegalArgumentException("Enter End Year");
                if(!Utils.isValidString(level)) throw new IllegalArgumentException("Choose level");

                listViewItems.add(s);
                ArrayAdapter<String> adpt = new ArrayAdapter<>
                        (getActivity(), android.R.layout.simple_list_item_1,listViewItems);
                listview.setAdapter(adpt);

                editTextSchoolName.setText("");
                editTextMajor.setText("");
                editTextFrom.setText("");
                editTextTo.setText("");
                Toast.makeText(getActivity(), "Added Successfully", Toast.LENGTH_SHORT).show();
            }
            catch(IllegalArgumentException e){
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            catch(Exception e){
                Log.d("ERROR_CATCH",e.getMessage());
            }
        });
    }
}
