package com.genvict.widget;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.genvict.jsimplestep.JSimpleStepView;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class SimpleStepActivity extends AppCompatActivity {
    private JSimpleStepView jSimpleStepView1;
    private JSimpleStepView jSimpleStepView2;

    private Button btnTestStepView;

    private List<String> stepContentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_step);

        stepContentList = new ArrayList<>(5);
        stepContentList.add("步骤1");
        stepContentList.add("步骤2");
        stepContentList.add("步骤3");
        stepContentList.add("步骤4");
        stepContentList.add("步骤5");

        jSimpleStepView1 = findViewById(R.id.JSimpleStepView1);
        jSimpleStepView1.setStepContentList(stepContentList).setCurrentStepNum(2);

        jSimpleStepView2 = findViewById(R.id.JSimpleStepView2);
        jSimpleStepView2.setStepContentList(stepContentList)
                .setCurrentStepNum(3);

        btnTestStepView = findViewById(R.id.btn_test);
        btnTestStepView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int step = jSimpleStepView2.getCurrentStepNum() + 1;
                if (step > stepContentList.size()) {
                    step = 1;
                }
                jSimpleStepView2.setCurrentStepNum(step);
            }
        });
    }
}
