package com.example.bes12.tipcalc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    private TipCalculator tipCalc;
    public NumberFormat money = NumberFormat.getCurrencyInstance();
    private EditText billEditText;
    private EditText tipEditText;
    private RadioGroup radioGroup;
    private RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tipCalc = new TipCalculator(0.17f, 100.0f);
        setContentView(R.layout.activity_main);

        billEditText = findViewById(R.id.amount_bill);
        tipEditText = findViewById(R.id.amount_tip_percent);

        TextChangeHandler tch = new TextChangeHandler();
        billEditText.addTextChangedListener(tch);
        tipEditText.addTextChangedListener(tch);

    }

    public Integer onRadioButtonClicked(View v, RadioGroup group){
        Integer tipS = 0;
        boolean checked = ((RadioButton) v).isChecked();

        switch(v.getId()) {
            case R.id.percent_small:
                if (checked)
                    tipS = 15;
            case R.id.percent_large:
                if (checked)
                    Toast.makeText(this, "random ", Toast.LENGTH_SHORT).show();
                    tipS = 20;
            case R.id.percent_custom:
                if (checked)

                    break;
        }
        return tipS;
    }

    /* Called when the user clicks on the Calculate button */
    public void calculate(){
        String billString = billEditText.getText().toString();
        String tipString = tipEditText.getText().toString();

        TextView tipTextView =
                (TextView) findViewById(R.id.amount_tip);
        TextView totalTextView =
                (TextView) findViewById(R.id.amount_total);

        try {
            // convert billString and tipString to floats
            float billAmount = Float.parseFloat(billString);
            int tipPercent = Integer.parseInt(tipString);
            // update the Model
            tipCalc.setBill(billAmount);
            tipCalc.setTip(.01f*tipPercent);
            // ask Model to calculate tip and total amounts
            float tip = tipCalc.tipAmount();
            float total = tipCalc.totalAmount();
            // update the View with formatted tip and total amounts
            tipTextView.setText(money.format(tip));
            totalTextView.setText(money.format(total));
        } catch(NumberFormatException nfe){
            // pop up an alert view here
        }
    }

    private class TextChangeHandler implements TextWatcher{
        public void afterTextChanged(Editable e){
            calculate();
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after){

        }

        public void onTextChanged(CharSequence s, int start, int before, int after){

        }
    }
}
