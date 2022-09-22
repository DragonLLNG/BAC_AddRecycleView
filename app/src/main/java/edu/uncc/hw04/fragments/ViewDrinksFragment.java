package edu.uncc.hw04.fragments;

import static java.lang.Math.round;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import edu.uncc.hw04.R;
import edu.uncc.hw04.databinding.FragmentViewDrinksBinding;
import edu.uncc.hw04.models.Drink;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewDrinksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewDrinksFragment extends Fragment {
    FragmentViewDrinksBinding binding;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    ViewAdapter adapter;
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_DRINKLIST = "param_drinkList";

    // TODO: Rename and change types of parameters
    private ArrayList<Drink> drinkList = new ArrayList<>();
    private Drink drinkRemove;
    int orderType = -1;

    public ViewDrinksFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ViewDrinksFragment newInstance(ArrayList<Drink> list) {
        ViewDrinksFragment fragment = new ViewDrinksFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_DRINKLIST, list);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            drinkList = getArguments().getParcelableArrayList(ARG_DRINKLIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentViewDrinksBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);



        //Sorting by Alcohol percentage
        view.findViewById(R.id.atoz_Alcohol).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(drinkList, new Comparator<Drink>() {
                    @Override
                    public int compare(Drink d1, Drink d2) {
                        return Double.compare(d1.getAlcoholPercentage(),d2.getAlcoholPercentage());
                    }
                });
                adapter.notifyDataSetChanged();
            }
        });


        view.findViewById(R.id.ztoa_Alcohol).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(drinkList, new Comparator<Drink>() {
                    @Override
                    public int compare(Drink d1, Drink d2) {
                        return orderType * Double.compare(d1.getAlcoholPercentage(),d2.getAlcoholPercentage());
                    }
                });
                adapter.notifyDataSetChanged();
            }
        });


        //Sorting by Date
        view.findViewById(R.id.atoz_Date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(drinkList, new Comparator<Drink>() {
                    @Override
                    public int compare(Drink d1, Drink d2) {

                        return d1.getAddedOn().compareTo(d2.getAddedOn());
                    }
                });
                adapter.notifyDataSetChanged();
            }
        });



        view.findViewById(R.id.ztoa_Date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(drinkList, new Comparator<Drink>() {
                    @Override
                    public int compare(Drink d1, Drink d2) {
                        return orderType * d1.getAddedOn().compareTo(d2.getAddedOn());
                    }
                });
                adapter.notifyDataSetChanged();
            }
        });

        adapter = new ViewAdapter(drinkList);
        recyclerView.setAdapter(adapter);


        //Click close
        view.findViewById(R.id.closeView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vd.updatedDrinkslist(drinkList);
            }
        });

    }

    public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.DrinksViewHolder>{
        public ViewAdapter(ArrayList<Drink> data){
            drinkList = data;
        }

        @NonNull
        @Override
        public DrinksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drinkdetails, parent,false);

            DrinksViewHolder drinksViewHolder = new DrinksViewHolder(view);

            return drinksViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull DrinksViewHolder holder, @SuppressLint("RecyclerView") int position) {

            Drink drink = drinkList.get(position);

            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm aa");
            Date date = drink.getAddedOn();
            holder.dateAdded.setText("Added " + dateFormat.format(date));


            holder.alcohol_pc.setText(String.valueOf(drink.getAlcoholPercentage()));
            holder.oz.setText(String.format("%.0f oz", drink.getDrinkSize()));

            holder.position = position;
            holder.drinkRemove = drinkRemove;

        }

        @Override
        public int getItemCount() {
            return drinkList.size();
        }

        public class DrinksViewHolder extends RecyclerView.ViewHolder{
            TextView alcohol_pc;
            TextView oz;
            TextView dateAdded;
            ImageView trash;
            int position;
            Drink drinkRemove;

            public DrinksViewHolder(@NonNull View itemView) {
                super(itemView);
                alcohol_pc = itemView.findViewById(R.id.alcohol_pc);
                oz = itemView.findViewById(R.id.oz);
                dateAdded = itemView.findViewById(R.id.dateView);
                trash = itemView.findViewById(R.id.trash);

                trash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        drinkRemove = drinkList.get(position);
                        drinkList.remove(position);
                        adapter.notifyDataSetChanged();
                        vd.deletedDrink(drinkRemove);

                    }
                });

            }
        }
    }


    ViewDrinksInterface vd;
    public interface ViewDrinksInterface {
        void deletedDrink(Drink drinkDeleted);
        void updatedDrinkslist(ArrayList<Drink> updatedDrinksList);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ViewDrinksInterface) {
            vd = (ViewDrinksInterface) context;
        } else {
            throw new RuntimeException(context.toString() + "must implement ViewDrinksInterface");
        }
    }
}
