package edu.uncc.hw04.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.uncc.hw04.R;
import edu.uncc.hw04.databinding.FragmentBacBinding;
import edu.uncc.hw04.models.Drink;
import edu.uncc.hw04.models.Profile;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BACFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BACFragment extends Fragment {
    FragmentBacBinding binding;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PROFILE = "param_profile";
    private static final String ARG_DRINK = "param_drink";
    //private static final String ARG_PARAM3 = "param3";

    // TODO: Rename and change types of parameters
    private String weightOut;
    private Integer numDrinks;
    private double bacOut;

    private Profile profile = new Profile(0.0,"");
    private ArrayList<Drink> drinksList = new ArrayList<Drink>();


    public BACFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static BACFragment newInstance(Profile profile, ArrayList<Drink> drinksList) {
        BACFragment fragment = new BACFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PROFILE, profile);
        args.putParcelableArrayList(ARG_DRINK, drinksList);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            profile = (Profile) getArguments().getSerializable(ARG_PROFILE);
            drinksList = getArguments().getParcelableArrayList(ARG_DRINK);
        }
    }

    TextView bacTxt, status, numDrinksTxt, userWeight;
    Button addDrinkBt, viewDrinkBt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBacBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view1, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view1, savedInstanceState);


        userWeight = view1.findViewById(R.id.initial_weight);
        numDrinksTxt = view1.findViewById(R.id.num_drink);
        bacTxt = view1.findViewById(R.id.bac_level);
        addDrinkBt = view1.findViewById(R.id.add_drink);
        viewDrinkBt = view1.findViewById(R.id.view_drink);

        //Click Set button
        view1.findViewById(R.id.set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bac.gotoSetProfile();
            }
        });

        //Click Add Drink button
        addDrinkBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bac.gotoAddDrink();
            }
        });


        //Display user input weight and gender
        weightOut = profile.getWeight()+" lbs("+profile.getGender()+")";
        userWeight.setText(weightOut);


        //Disable Add Drink and View Drinks button when there's no weight and gender output
        if(profile.getWeight()==0 && profile.getGender().isEmpty()){
            addDrinkBt.setEnabled(false);
            viewDrinkBt.setEnabled(false);
        }


        //Display number of added drinks
        numDrinks = drinksList.size();
        numDrinksTxt.setText(String.valueOf(this.numDrinks));


        //BAC calculation
        double bacNum = 0.0;
        double consumed = 0.0;


        for (int i = 0; i < drinksList.size(); i++) {
            consumed += drinksList.get(i).getAlcoholPercentage()*drinksList.get(i).getDrinkSize();
            System.out.println(consumed);
        }

        if (profile.getGender()=="Male") {
            bacNum = (consumed * 5.14)/(profile.getWeight() * 0.73);
            System.out.println(bacNum);
        } else {
            bacNum = (consumed * 5.14)/(profile.getWeight() * 0.66);

            System.out.println(profile.getWeight());
            System.out.println(bacNum);

        }


        //Changing the color of the status bar
        status = view1.findViewById(R.id.status);
        status.setBackgroundResource(R.drawable.roundedcorner);
        GradientDrawable drawable = (GradientDrawable) status.getBackground();

        if (bacNum >= 0.25) {
            System.out.println(bacNum);
            //disable add drink
            addDrinkBt.setEnabled(false);
            view1.findViewById(R.id.add_drink).setEnabled(false);
            status.setText("Over the limit");
            drawable.setColor(Color.RED);


            Toast overLimit = Toast.makeText(getActivity(), "No more drinks for you!", Toast.LENGTH_SHORT);
            overLimit.setGravity(Gravity.CENTER, 0, 0);
            overLimit.show();
        }

        else if (bacNum > 0.2) {
            status.setText("Over the limit");
            drawable.setColor(Color.RED);

        } else if (bacNum > 0.08) {
            status.setText("Be Careful!");
            drawable.setColor(Color.YELLOW);
        } else {
            status.setText("You're Safe");
            drawable.setColor(Color.GREEN);
        }


        //Outputting the bac
        bacTxt.setText(String.format("%.3f", bacNum));






        //Click View Drink
        viewDrinkBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drinksList.size()==0){
                    Toast overLimit = Toast.makeText(getActivity(), "There is no drinks!", Toast.LENGTH_SHORT);
                    overLimit.setGravity(Gravity.CENTER, 0, 0);
                    overLimit.show();
                }
                bac.gotoViewDrink(drinksList);
            }
        });

        //Click Reset button
        view1.findViewById(R.id.reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    bac.reset();
                    addDrinkBt.setEnabled(false);
                    viewDrinkBt.setEnabled(false);
                    status.setBackgroundResource(R.drawable.roundedcorner);
                    GradientDrawable drawable = (GradientDrawable) status.getBackground();
                    drawable.setColor(Color.GREEN);


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
    }

    //BAC interface
    BAC_interface bac;
    public interface BAC_interface{
        void gotoSetProfile();
        void gotoAddDrink();
        void gotoViewDrink(ArrayList<Drink> drinkArrayList);
        void reset();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof BAC_interface) {
            bac = (BAC_interface) context;
        } else {
            throw new RuntimeException(context.toString() + "must implement BAC_interface");
        }
    }
}