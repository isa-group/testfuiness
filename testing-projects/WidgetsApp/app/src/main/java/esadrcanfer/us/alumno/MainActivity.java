package esadrcanfer.us.alumno;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void send(View view) {
        CheckBox checkBox = findViewById(R.id.checkBox);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        TextView textView = findViewById(R.id.text2);

        if(checkBox.isChecked() && radioGroup.getCheckedRadioButtonId() != -1){
            throw new IllegalArgumentException();
        } else if(checkBox.isChecked()){
            textView.setText("Ha marcado el checkbox y pulsado el botón");
        } else if(radioGroup.getCheckedRadioButtonId() != -1){
            textView.setText("Ha marcado un radio button y pulsado el botón");
        }
    }



    public void print1(View view) {
        TextView textView = findViewById(R.id.text2);
        textView.setText("Seleccionado Radio Button 1");
    }

    public void print2(View view) {
        TextView textView = findViewById(R.id.text2);
        textView.setText("Seleccionado Radio Button 2");
    }

    public void print3(View view) {
        TextView textView = findViewById(R.id.text2);
        textView.setText("Seleccionado Radio Button 3");
    }
}
