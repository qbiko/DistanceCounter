package chodorowski.jakub.myapplication.controllers;

import androidx.fragment.app.Fragment;

public class DistanceCounterActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new DistanceCounterFragment();
    }
}
