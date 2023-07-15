package com.example.highmusicapp.ActivityFragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.highmusicapp.ActivityController.LoginActivity;
import com.example.highmusicapp.ActivityController.LogoutActivity;
import com.example.highmusicapp.R;

public class LogoutFragment extends Fragment {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        activity = getActivity();
        preferences = activity.getSharedPreferences("MIA", Context.MODE_PRIVATE);
        editor = preferences.edit();
        return inflater.inflate(R.layout.fragment_logout, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        editor.clear();
        editor.commit();
        Intent intent = new Intent(activity, LoginActivity.class);
        startActivity(intent);
    }
}