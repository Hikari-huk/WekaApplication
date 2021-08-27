package jp.aoyagi.application.wekaapplication;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    TextView right_view;
    EditText predicted_view1;
    EditText predicted_view2;
    EditText predicted_view3;
    EditText predicted_view4;

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        predicted_view1=findViewById(R.id.edit_predicted1);
        predicted_view2=findViewById(R.id.edit_predicted2);
        predicted_view3=findViewById(R.id.edit_predicted3);
        predicted_view4=findViewById(R.id.edit_predicted4);
        right_view=findViewById(R.id.right_view);
        Button button = findViewById(R.id.button);
        context=getApplicationContext();

    }


    ClassificationModel clfModel;

    public void click(View v){
        if(v.getId()==R.id.button){
            right_view.setText("Right");
            String predicted_text1=predicted_view1.getText().toString();
            String predicted_text2=predicted_view2.getText().toString();
            String predicted_text3=predicted_view3.getText().toString();
            String predicted_text4=predicted_view4.getText().toString();



            float[] features = new float[4];
            features[0]=Float.parseFloat(predicted_text1);
            features[1]=Float.parseFloat(predicted_text2);
            features[2]=Float.parseFloat(predicted_text3);
            features[3]=Float.parseFloat(predicted_text4);
//            Log.d("f","result:"+features[0]);
//            Log.d("f1","result:"+features[1]);
//            Log.d("f2","result:"+features[2]);


//            モデル用意
//            clfModel = new ClassificationModel("test_weka.model",context);
            clfModel = new ClassificationModel("4features.model",context);

//            分類
            String predictedResult = clfModel.predict(features);
            Log.d("result", "result: " + predictedResult);

            right_view.setText(predictedResult);

        }
    }

}