package esadrcanfer.us.diversityapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click1(View view) {
            TextView text = (TextView) findViewById(R.id.textView4);
            text.setText("Se ha pulsado el botón 1");
    }


    public void click2(View view) {
        TextView text = (TextView) findViewById(R.id.textView4);
        text.setText("Se ha pulsado el botón 2");
    }


    public void click3(View view) {
        TextView text = (TextView) findViewById(R.id.textView4);
        text.setText("Se ha pulsado el botón 3");
    }

    public void click4(View view) {
        TextView text = (TextView) findViewById(R.id.textView4);
        text.setText("Se ha pulsado el botón 4");
    }

    public void click5(View view) {
        Intent intent = new Intent(view.getContext(), Main2Activity.class);
        startActivityForResult(intent, 0);
        //TextView text = (TextView) findViewById(R.id.textView4);
        //text.setText("Se ha pulsado el botón 5");
    }

    public void click6(View view) {
        TextView text = (TextView) findViewById(R.id.textView4);
        text.setText("Se ha pulsado el botón 6");
    }

    public void click7(View view) {
        TextView text = (TextView) findViewById(R.id.textView4);
        text.setText("Se ha pulsado el botón 7");
    }

    public void click8(View view) {
        TextView text = (TextView) findViewById(R.id.textView4);
        text.setText("Se ha pulsado el botón 8");
    }

    public void click9(View view) {
        TextView text = (TextView) findViewById(R.id.textView4);
        text.setText("Se ha pulsado el botón 9");
    }

    public void click10(View view) {
        TextView text = (TextView) findViewById(R.id.textView4);
        text.setText("Se ha pulsado el botón 10");
    }

    public void click11(View view) {
        TextView text = (TextView) findViewById(R.id.textView4);
        text.setText("Se ha pulsado el botón 11");
    }

    public void click12(View view) {
        TextView text = (TextView) findViewById(R.id.textView4);
        text.setText("Se ha pulsado el botón 12");
    }
}
