package edu.uncc.hw04;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

import edu.uncc.hw04.databinding.ActivityMainBinding;
import edu.uncc.hw04.fragments.AddDrinkFragment;
import edu.uncc.hw04.fragments.BACFragment;
import edu.uncc.hw04.fragments.SetProfileFragment;
import edu.uncc.hw04.fragments.ViewDrinksFragment;
import edu.uncc.hw04.models.Drink;
import edu.uncc.hw04.models.Profile;

public class MainActivity extends AppCompatActivity implements BACFragment.BAC_interface, SetProfileFragment.sendWeightGenderInterface,
        ViewDrinksFragment.ViewDrinksInterface, AddDrinkFragment.AddDrinkInterface {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction()
                .add(R.id.containerView, new BACFragment())
                .commit();
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void gotoSetProfile() {
        setTitle("Set Weigh/Gender");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView,new SetProfileFragment(),"ProfileFragment").addToBackStack(null)
                .commit();

    }

    @Override
    public void gotoAddDrink() {
        setTitle("Add Drink");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView,new AddDrinkFragment(),"AddDrinkFragment").addToBackStack(null)
                .commit();
    }



    Profile user;
    ArrayList<Drink> drinksList = new ArrayList<>();

    @Override
    public void setWeighGender(Profile profile) {

        user = profile;

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, BACFragment.newInstance(user,drinksList),"BACFragment")
                .commit();
        //getSupportFragmentManager().popBackStack();

    }

    @Override
    public void setDrink(Drink drink) {
        drinksList.add(drink);
        //BACCalculator bacFragment1 = (BACCalculator) getSupportFragmentManager().findFragmentByTag("BACFragment");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, BACFragment.newInstance(user, drinksList),"BACFragment")
                .commit();
        //getSupportFragmentManager().popBackStack();

    }




    @Override
    public void gotoViewDrink(ArrayList<Drink> drinkData) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, ViewDrinksFragment.newInstance(drinksList),"ViewDrinkFragment")
                .commit();
    }




    @Override
    public void deletedDrink(Drink drinkDeleted) {
        drinksList.remove(drinkDeleted);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, BACFragment.newInstance(user,drinksList),"BACFragment")
                .commit();


    }

    @Override
    public void updatedDrinklists(ArrayList<Drink> updatedDrinksList) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, BACFragment.newInstance(user, updatedDrinksList),"BACFragment")
                .commit();
    }


    @Override
    public void reset() {
        user = new Profile(0.0,"");
        drinksList.clear();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, BACFragment.newInstance(user, drinksList),"BACFragment")
                .commit();

    }


}