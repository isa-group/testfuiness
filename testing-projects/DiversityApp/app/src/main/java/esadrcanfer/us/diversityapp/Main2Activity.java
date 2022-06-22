package esadrcanfer.us.diversityapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void print1(View view) {
        TextView textView = findViewById(R.id.textView4);
        textView.setText("Seleccionado Radio Button 1");
    }

    public void print2(View view) {
        TextView textView = findViewById(R.id.textView4);
        textView.setText("Seleccionado Radio Button 2");
    }

    public void print3(View view) {
        TextView textView = findViewById(R.id.textView4);
        textView.setText("Seleccionado Radio Button 3");
    }

    public void send(View view) {
        EditText editText = findViewById(R.id.editText);

        if(editText.getText().length() != 0){
            Intent intent = new Intent(view.getContext(), Main3Activity.class);
            startActivityForResult(intent, 0);
        }
    }
}
