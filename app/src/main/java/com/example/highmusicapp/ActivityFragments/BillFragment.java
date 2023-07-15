package com.example.highmusicapp.ActivityFragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.highmusicapp.ActivityController.BillActivity;
import com.example.highmusicapp.ActivityController.ViewBillDetailActivity;
import com.example.highmusicapp.R;

public class BillFragment extends Fragment {

    Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        activity = getActivity();
        return inflater.inflate(R.layout.fragment_bill, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Intent intent = new Intent(activity, BillActivity.class);
        startActivity(intent);
    }
}