package chodorowski.jakub.myapplication.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import chodorowski.jakub.myapplication.R;

public class DistanceCounterFragment extends Fragment {

    @BindView(R.id.firstLatEditText)
    EditText mFirstLatEditText;
    @BindView(R.id.firstLongEditText)
    EditText mFirstLongEditText;
    @BindView(R.id.secondLatEditText)
    EditText mSecondLatEditText;
    @BindView(R.id.secondLongEditText)
    EditText mSecondLongEditText;
    @BindView(R.id.firstLatTextField)
    TextInputLayout mFirstLatTextField;
    @BindView(R.id.firstLongTextField)
    TextInputLayout mFirstLongTextField;
    @BindView(R.id.secondLatTextField)
    TextInputLayout mSecondLatTextField;
    @BindView(R.id.secondLongTextField)
    TextInputLayout mSecondLongTextField;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_distance_counter, container, false);
        ButterKnife.bind(this, v);

        if(savedInstanceState != null) {
            String firstLat = savedInstanceState.getString("firstLat");
            String firstLong = savedInstanceState.getString("firstLong");
            String secondLat = savedInstanceState.getString("secondLat");
            String secondLong = savedInstanceState.getString("secondLong");

            mFirstLatEditText.setText(firstLat);
            mFirstLongEditText.setText(firstLong);
            mSecondLatEditText.setText(secondLat);
            mSecondLongEditText.setText(secondLong);
        }

        return v;
    }

    @OnClick(R.id.countButton)
    void onCountButtonClick() {
        if(validate()) {
            double firstLat = Double.valueOf(mFirstLatEditText.getText().toString());
            double firstLong = Double.valueOf(mFirstLongEditText.getText().toString());

            double secondLat = Double.valueOf(mSecondLatEditText.getText().toString());
            double secondLong = Double.valueOf(mSecondLongEditText.getText().toString());

            Intent intent = new Intent(getActivity(), MapActivity.class);
            intent.putExtra("firstLat", firstLat);
            intent.putExtra("firstLong", firstLong);
            intent.putExtra("secondLat", secondLat);
            intent.putExtra("secondLong", secondLong);
            startActivity(intent);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("firstLat", mFirstLatEditText.getText().toString());
        outState.putString("firstLong", mFirstLongEditText.getText().toString());
        outState.putString("secondLat", mSecondLatEditText.getText().toString());
        outState.putString("secondLong", mSecondLongEditText.getText().toString());
    }

    private void validateLat(TextInputLayout view, String text) {
        if(validateLat(text)) {
            view.setError(null);
        }
        else {
            view.setError(getActivity().getString(R.string.error_lat_long));
        }
    }

    private void validateLong(TextInputLayout view, String text) {
        if(validateLong(text)) {
            view.setError(null);
        }
        else {
            view.setError(getActivity().getString(R.string.error_lat_long));
        }
    }

    private boolean validateLat(String text) {
        try {
            double lat = Double.parseDouble(text);
            return lat < -90 || lat > 90;
        }
        catch(NumberFormatException ex) {
            return false;
        }
    }

    private boolean validateLong(String text) {
        try {
            double lng = Double.parseDouble(text);
            return lng < -180 || lng > 180;
        }
        catch(NumberFormatException ex) {
            return false;
        }
    }

    private boolean validate() {
        validateLat(mFirstLatTextField, mFirstLatEditText.getText().toString());
        validateLong(mFirstLongTextField, mFirstLongEditText.getText().toString());

        validateLat(mSecondLatTextField, mSecondLatEditText.getText().toString());
        validateLong(mSecondLongTextField, mSecondLongEditText.getText().toString());

        return mFirstLatTextField.getError() == null && mFirstLongTextField.getError() == null && mSecondLatTextField.getError() == null && mSecondLongTextField.getError() == null;
    }

    @OnTextChanged(R.id.firstLatEditText)
    void onFirstLatEditTextChange() {
        validateLat(mFirstLatTextField, mFirstLatEditText.getText().toString());
    }

    @OnTextChanged(R.id.firstLongEditText)
    void onFirstLongEditTextChange() {
        validateLong(mFirstLongTextField, mFirstLongEditText.getText().toString());
    }

    @OnTextChanged(R.id.secondLatEditText)
    void onSecondLatEditTextChange() {
        validateLat(mSecondLatTextField, mSecondLatEditText.getText().toString());
    }

    @OnTextChanged(R.id.secondLongEditText)
    void onSecondLongEditTextChange() {
        validateLong(mSecondLongTextField, mSecondLongEditText.getText().toString());
    }
}
