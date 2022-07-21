package esadrcanfer.us.alumno.textinputapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void send(View view) {
        EditText edit = (EditText)findViewById(R.id.editText1);
        if(edit.getText().length() == 0){
            TextView text = (TextView) findViewById(R.id.text2);
            text.setText("Se ha pulsado el botón");
        } else {
            throw new IllegalArgumentException();
        }

    }
}
