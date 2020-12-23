package edu.mluby.gameapp;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.TextView;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

public class Game extends AppCompatActivity implements SensorEventListener {

    String Name;
    TextView tvScore, tvRound;
    Button btnNorth, btnWest, btnEast, btnSouth;
    int[] sequence = new int[120];
    int number = 0, randomSequence = -1, score = 0, increase = 2, roundNo = 0;

    private final double NORTH_MOVE_FORWARD = 8;
    private final double NORTH_MOVE_BACKWARD = 5;

    private final double SOUTH_MOVE_FORWARD =  1;
    private final double SOUTH_MOVE_BACKWARD = 4;

    private final double WEST_MOVE_FORWARD = -1;
    private final double WEST_MOVE_BACKWARD = 0;

    private final double EAST_MOVE_FORWARD = 1;
    private final double EAST_MOVE_BACKWARD = 0;

    boolean highestNorth = false;
    boolean highestSouth = false;
    boolean highestEast = false;
    boolean highestWest = false;

    int counterNorth = 0;
    int counterSouth = 0;
    int counterEast = 0;
    int counterWest = 0;

    TextView tvx, tvy, tvz, tvNorth, tvSouth, tvWest, tvEast;
    private SensorManager mSensorManager;
    private Sensor mSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        btnEast = findViewById(R.id.btnEast);
        btnWest = findViewById(R.id.btnWest);
        btnNorth = findViewById(R.id.btnNorth);
        btnSouth = findViewById(R.id.btnSouth);

        tvx = findViewById(R.id.tvX3);
        tvy = findViewById(R.id.tvY3);
        tvz = findViewById(R.id.tvZ3);


        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


        sequence = getIntent().getIntArrayExtra("sequence");

        roundNo = getIntent().getIntExtra("round", 1);
        score = getIntent().getIntExtra("score", 0);
        increase = getIntent().getIntExtra("increase", 0);

        tvScore = findViewById(R.id.tvScore);
        tvRound = findViewById(R.id.tvRound);

        tvRound.setText(String.valueOf(roundNo));
        tvScore.setText(String.valueOf(score));
    }

    protected void onResume() {
        super.onResume();

        mSensorManager.registerListener(this, mSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        tvx.setText(String.valueOf(x));
        tvy.setText(String.valueOf(y));
        tvz.setText(String.valueOf(z));

        if ((x > NORTH_MOVE_FORWARD && z > 0) && (highestNorth == false)) {
            highLimitNorth = true;
        }
        if ((x < NORTH_MOVE_BACKWARD && z > 0) && (highestNorth == true)) {
            counterNorth++;
            tvNorth.setText(String.valueOf(counterNorth));
            highestNorth = false;

            Handler handler = new Handler();
            Runnable r = new Runnable() {
                public void run() {

                    btnNorth.setPressed(true);
                    btnNorth.invalidate();
                    btnNorth.performClick();
                    Handler handler1 = new Handler();
                    Runnable r1 = new Runnable() {
                        public void run() {
                            btnNorth.setPressed(false);
                            btnNorth.invalidate();
                        }
                    };
                    handler1.postDelayed(r1, 600);

                }
            };
            handler.postDelayed(r, 600);


        }

        if ((x < SOUTH_MOVE_FORWARD && z < 0) && (highestSouth == false)) {
            highestSouth = true;
        }
        if ((x > SOUTH_MOVE_BACKWARD && z < 0) && (highestSouth == true)) {
            counterSouth++;
            tvSouth.setText(String.valueOf(counterSouth));
            highestSouth = false;


            Handler handler = new Handler();
            Runnable r = new Runnable() {
                public void run() {

                    btnSouth.setPressed(true);
                    btnSouth.invalidate();
                    btnSouth.performClick();
                    Handler handler1 = new Handler();
                    Runnable r1 = new Runnable() {
                        public void run() {
                            btnSouth.setPressed(false);
                            btnSouth.invalidate();
                        }
                    };
                    handler1.postDelayed(r1, 600);

                }
            };
            handler.postDelayed(r, 600);


        }

        if (y > EAST_MOVE_FORWARD && highestEast == false) {
            highestEast = true;
        }
        if (y < EAST_MOVE_BACKWARD && highestEast == true) {
            counterEast++;
            tvEast.setText(String.valueOf(counterEast));
            highestEast = false;


            Handler handler = new Handler();
            Runnable r = new Runnable() {
                public void run() {

                    btnEast.setPressed(true);
                    btnEast.invalidate();
                    btnEast.performClick();
                    Handler handler1 = new Handler();
                    Runnable r1 = new Runnable() {
                        public void run() {
                            btnEast.setPressed(false);
                            btnEast.invalidate();
                        }
                    };
                    handler1.postDelayed(r1, 600);

                }
            };
            handler.postDelayed(r, 600);


        }

        if (y < WEST_MOVE_FORWARD && highestWest == false) {
            highestWest = true;
        }
        if (y > WEST_MOVE_BACKWARD && highestWest == true) {

            counterWest++;
            tvWest.setText(String.valueOf(counterWest));
            highestWest = false;


            Handler handler = new Handler();
            Runnable r = new Runnable() {
                public void run() {

                    btnWest.setPressed(true);
                    btnWest.invalidate();
                    btnWest.performClick();
                    Handler handler1 = new Handler();
                    Runnable r1 = new Runnable() {
                        public void run() {
                            btnWest.setPressed(false);
                            btnWest.invalidate();
                        }
                    };
                    handler1.postDelayed(r1, 600);

                }
            };
            handler.postDelayed(r, 600);


        }

    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public static double roundNo(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }




    public void doClick(View view){

        randomSequence++;

        switch (view.getId())
        {
            case(R.id.btnNorth) :
                number = 1;
                Name ="North";
                break;
            case(R.id.btnWest) :
                number = 2;
                Name ="West";
                break;
            case(R.id.btnSouth) :
                number = 3;
                Name ="South";
                break;
            case(R.id.btnEast) :
                number = 4;
                Name ="East";
                break;
        }


        for(int i : sequence)
        {
            if(number == sequence[randomSequence])
            {
                score++;
                tvScore.setText(String.valueOf(score));

                if(randomSequence > increase)
                {
                    increase = increase + 2;
                    roundNo++;
                    Intent returnToMain = new Intent(GameActivity.this, MainActivity.class);
                    returnToMain.putExtra("score", score);
                    returnToMain.putExtra("round", roundNo);
                    returnToMain.putExtra("increase", increase);
                    startActivity(returnToMain);
                }
                return;
            }
            else if(number != sequence[randomSequence])
            {
                Intent intent = new Intent(view.getContext(), GameOver.class);

                intent.putExtra("score", score);
                intent.putExtra("round", roundNo);

                startActivity(intent);

                return;
            }
        }
    }
}