package com.example.test_evo_reborn.lockbox;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by test-evo-REBORN on 3/1/2018.
 */

public class LockFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)   {
        View view = inflater.inflate(R.layout.lock_fragment, container, false);
        return view;
    }

    public static LockFragment newInstance(int i){
        LockFragment fragment = new LockFragment();
        return fragment;


    }
}
