package com.abualrub.assignmenttwoindividualv2.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.abualrub.assignmenttwoindividualv2.R;
import com.abualrub.assignmenttwoindividualv2.domain.Gender;
import com.abualrub.assignmenttwoindividualv2.domain.User;
import com.abualrub.assignmenttwoindividualv2.util.Tags;
import com.abualrub.assignmenttwoindividualv2.util.Utils;
import com.google.gson.Gson;

public class PersonalInformation extends Fragment {

    private EditText editTextFirstName;
    private EditText editTextLastName;
    private RadioGroup radioGroupGender;
    private EditText editTextEmail;
    private Spinner spinner;
    private Button saveButton;

    private static final String[] DYNAMIC_VALUES =
            {"Food", "Programming", "Osid Abu-Alrub", "Homework", "Work","School","Mechanical Keyboards"};
    private static boolean isCreated;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private View fragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_personal_information,container,false);
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

    private void setupViews(){
        editTextFirstName = fragment.findViewById(R.id.editTextFirstName);
        editTextLastName = fragment.findViewById(R.id.editTextLastName);
        editTextEmail = fragment.findViewById(R.id.editTextEmail);
        radioGroupGender =fragment.findViewById(R.id.radioGroupGender);
        saveButton = fragment.findViewById(R.id.buttonSave);
        initSaveButton();
        spinner = fragment.findViewById(R.id.spinner);
        initSpinner();
    }

    private void initSaveButton(){
        saveButton.setOnClickListener(view ->{
            try{
                String firstName = editTextFirstName.getText().toString();
                String lastName = editTextLastName.getText().toString();
                String email = editTextEmail.getText().toString();
                int genderId = radioGroupGender.getCheckedRadioButtonId();
                String whatMakesHappy = DYNAMIC_VALUES[spinner.getSelectedItemPosition()];

                if(!Utils.isValidString(firstName)) throw new IllegalArgumentException("Enter First Name");
                if(!Utils.isValidString(lastName)) throw new IllegalArgumentException("Enter Last Name");
                if(!Utils.isValidString(email)) throw new IllegalArgumentException("Enter Email");
                if(genderId == -1) throw new IllegalArgumentException("Choose Gender");

                String gender = (genderId == R.id.radioButtonMale) ? Gender.MALE.CONSTANT : Gender.FEMALE.CONSTANT;
                User user = new User();
                if(isCreated)
                    user = new Gson().fromJson
                            (Utils.isPropertyPreviouslyFilled
                                    (getContext(), Tags.USER.CONSTANT),User.class);
                isCreated = true;
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setEmail(email);
                user.setGender(gender);
                user.setWhatMakesHappy(whatMakesHappy);
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

    private void initSpinner() {
        ArrayAdapter<String> adp = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_list_item_1, DYNAMIC_VALUES);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adp);
    }

    private void setupValues(){
        Gson gson = new Gson();
        String json = prefs.getString(Tags.USER.CONSTANT,null);
        if(json == null){isCreated = false; return;};
        User user = gson.fromJson(json,User.class);

        editTextFirstName.setText(user.getFirstName());
        editTextLastName.setText(user.getLastName());
        editTextEmail.setText(user.getEmail());
        if(user.getGender().equals(Gender.MALE.CONSTANT)) radioGroupGender.check(R.id.radioButtonMale);
        else radioGroupGender.check(R.id.radioButtonFemale);
        for (int i = 0 ; i < DYNAMIC_VALUES.length ; i++){
            if(!DYNAMIC_VALUES[i].equals(user.getWhatMakesHappy())) continue;
            spinner.setSelection(i);
            break;
        }
    }
}
