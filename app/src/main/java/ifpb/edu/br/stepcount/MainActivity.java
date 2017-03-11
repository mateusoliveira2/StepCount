package ifpb.edu.br.stepcount;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements SensorEventListener {
    private long steps = 0;
    private TextView tv_steps;
    SensorManager sManager;
    Sensor stepSensor;
    boolean run;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_steps = (TextView) findViewById(R.id.tv_steps);
        sManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
       /* Sensor sensor = event.sensor;
        float[] values = event.values;
        int value = -1;

        if (values.length > 0) {
            value = (int) values[0];
        }
        if (sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
            steps++;
        }*/
        tv_steps.setText(String.valueOf(event.values[0]));
    }

    //function to determine the distance run in kilometers using average step length for men and number of steps
    public float getDistanceRun(long steps){
        float distance = (float)(steps*78)/(float)100000;
        return distance;
    }

    @Override
    protected void onResume() {
        super.onResume();

        run = true;
        stepSensor = sManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

        if (stepSensor != null){
            sManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_FASTEST);
        }else{
            Toast.makeText(this,"sensor nao ta pegando, trouxa", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    @Override
    protected void onStop() {
        super.onStop();
        sManager.unregisterListener(this, stepSensor);
    }

}
