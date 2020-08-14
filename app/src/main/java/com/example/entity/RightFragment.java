package com.example.entity;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.pplication.MainShopActivity;
import com.example.pplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class RightFragment extends ListFragment {
    private List<Food> foodList = new ArrayList<>();

    public RightFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFood();
        FoodAdapter adapter = new FoodAdapter(getContext(),R.layout.food_item,foodList);
        this.setListAdapter(adapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_right, container, false);
    }
    private void initFood(){
        Food food1 = new Food("名字1",12,R.drawable.icon);
        foodList.add(food1);
        foodList.add(new Food("名字2",13,R.drawable.icon));
        foodList.add(new Food("名字3",13,R.drawable.icon));
        foodList.add(new Food("名字4",13,R.drawable.icon));
        foodList.add(new Food("名字5",13,R.drawable.icon));
        foodList.add(new Food("名字6",13,R.drawable.icon));
        foodList.add(new Food("名字7",13,R.drawable.icon));

    }

}
