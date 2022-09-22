package edu.uncc.hw04.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import edu.uncc.hw04.R;
import edu.uncc.hw04.databinding.FragmentSetProfileBinding;
import edu.uncc.hw04.models.Profile;


public class SetProfileFragment extends Fragment {



    private Profile profile;
    FragmentSetProfileBinding binding;

    public SetProfileFragment() {
        // Required empty public constructor
    }


    double num_weight;
    EditText weight_input;
    RadioGroup gender;
    String genderReturn;
    Button setw, cancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSetProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view1, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view1, savedInstanceState);
        setw = view1.findViewById(R.id.set2);
        setw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    weight_input = view1.findViewById(R.id.weight_input);
                    num_weight = Double.parseDouble(weight_input.getText().toString());
                } catch(NumberFormatException exception){
                    System.out.println("Weight is not format corrected");
                }

                gender = view1.findViewById(R.id.gender_group);
                int checked = gender.getCheckedRadioButtonId();
                if(checked==R.id.female){
                    genderReturn="Female";
                }
                else if (checked==R.id.male){
                    genderReturn="Male";
                }
                if(weight_input.getText().length()==0 || gender.getCheckedRadioButtonId() == -1){
                    Toast toast = Toast.makeText(getActivity(), "Set weight and gender first.", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                else{
                    profile = new Profile(num_weight,genderReturn);
                    wg.setWeighGender(profile);
                }
            }
        });

        //Click Cancel button
        cancel = view1.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    sendWeightGenderInterface wg;
    public interface sendWeightGenderInterface{
        void setWeighGender(Profile profile);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof sendWeightGenderInterface) {
            wg = (sendWeightGenderInterface) context;
        } else {
            throw new RuntimeException(context.toString() + "must implement sendWeightGenderInterface");
        }
    }

}