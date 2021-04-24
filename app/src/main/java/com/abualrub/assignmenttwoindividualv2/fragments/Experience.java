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

public class Experience extends Fragment {

    private CheckBox checkBoxDoesHaveWorkExperience;
    private EditText editTextWorkPlace;
    private EditText editTextFrom;
    private EditText editTextTo;
    private EditText editTextRole;
    private ListView listview;
    private Button buttonSave;
    private Button buttonAdd;
    private LinearLayoutCompat linearLayoutWorkExperience;

    private static boolean isCreated;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private View fragment;
    private ArrayList<String> listViewItems = new ArrayList<String>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_experience,container,false);
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
        checkBoxDoesHaveWorkExperience = fragment.findViewById(R.id.checkBoxDoesHaveWorkExperience);
        initCheckBoxDoesHaveWorkExperience();
        linearLayoutWorkExperience = fragment.findViewById(R.id.linearLayoutWorkExperience);
        editTextWorkPlace = fragment.findViewById(R.id.editTextWorkPlace);
        editTextFrom = fragment.findViewById(R.id.editTextFrom);
        editTextTo = fragment.findViewById(R.id.editTextTo);
        editTextRole =fragment.findViewById(R.id.editTextRole);
        listview = fragment.findViewById(R.id.listview);
        buttonSave = fragment.findViewById(R.id.buttonSave);
        initSaveButton();
        buttonAdd = fragment.findViewById(R.id.buttonAdd);
        initAddButton();
    }

    private void setupValues() {
        Gson gson = new Gson();
        String json = prefs.getString(Tags.USER.CONSTANT,null);
        if(json == null){isCreated = false; return;};
        User user = gson.fromJson(json,User.class);

        if(!user.doesHaveWorkEx()) return;
        if(user.getWorkExperience() == null) return;
        listViewItems = user.getWorkExperience();
        ArrayAdapter<String> adpt = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,listViewItems);
        listview.setAdapter(adpt);
        checkBoxDoesHaveWorkExperience.setChecked(true);
    }

    private void initCheckBoxDoesHaveWorkExperience(){
        checkBoxDoesHaveWorkExperience.setOnCheckedChangeListener((v,e) ->{
            if(v.isChecked()) {
                linearLayoutWorkExperience.setVisibility(View.VISIBLE);
                listview.setVisibility(View.VISIBLE);
            }
            else {
                linearLayoutWorkExperience.setVisibility(View.GONE);
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
                user.setWorkExperience(listViewItems);
                user.setDoesHaveWorkEx(checkBoxDoesHaveWorkExperience.isChecked());
                String json = new Gson().toJson(user);

                editor.putString(Tags.USER.CONSTANT,json);
                editor.putBoolean(Tags.IS_CREATED.CONSTANT,isCreated);
                editor.commit();
                Toast.makeText(getActivity(), "Saved Successfully", Toast.LENGTH_SHORT).show();
            }
            catch(IllegalArgumentException e){
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            catch(Exception e){
                Log.d("ERROR_CATCH",e.getMessage());
            }
        });
    }

    private void initAddButton(){
        buttonAdd.setOnClickListener(view ->{
            try{
                String workPlace = editTextWorkPlace.getText().toString();
                String from = editTextFrom.getText().toString();
                String to = editTextTo.getText().toString();
                String role = editTextRole.getText().toString();
                String s = new User().workExperienceToString(workPlace,from,to,role);

                if(!Utils.isValidString(workPlace)) throw new IllegalArgumentException("Enter Work Place");
                if(!Utils.isValidString(from)) throw new IllegalArgumentException("Enter Start Year");
                if(!Utils.isValidString(to)) throw new IllegalArgumentException("Enter End Year");
                if(!Utils.isValidString(role)) throw new IllegalArgumentException("Enter Role");

                listViewItems.add(s);
                ArrayAdapter<String> adpt = new ArrayAdapter<>
                        (getActivity(), android.R.layout.simple_list_item_1,listViewItems);
                listview.setAdapter(adpt);

                editTextWorkPlace.setText("");
                editTextRole.setText("");
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
