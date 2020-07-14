package com.genvict.widget;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.genvict.jsimplestep.JSimpleStepView;
import com.genvict.widgets.jmb.JMenuButton;
import com.genvict.widgets.jmi.JMenuItem;
import com.genvict.widgets.jtb.JTopBar;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试Demo
 * @author jayz
 */
public class MainActivity extends Activity {
    private JMenuButton testMenuButton;
    private JMenuItem versoin;
    private JMenuItem setting;

    private JTopBar topBar;

    private JSimpleStepView stepView;
    private List<String> stepList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testMenuButton = findViewById(R.id.testMenuBtn);
        testMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "You click the menu button!", Toast.LENGTH_SHORT).show();
                int step = stepView.getCurrentStepNum() + 1;
                if (step > stepList.size()) {
                    step = 1;
                }
                stepView.setCurrentStepNum(step);
            }
        });
        testMenuButton.setTitle("这是一个菜单按钮")
                .setTitleMarginTop(30)
                .setIconClickEffect(false);

        versoin = findViewById(R.id.versoin);
        versoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "You click the menu item!", Toast.LENGTH_SHORT).show();
                versoin.setShowUnreadIcon(false);
            }
        });

        setting = findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "You click the menu item!", Toast.LENGTH_SHORT).show();
            }
        });

        topBar = findViewById(R.id.topbar);
        topBar.setLeftIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "You click left icon button!", Toast.LENGTH_SHORT).show();
                finish();

            }
        }).setRightIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "You click right icon button!", Toast.LENGTH_SHORT).show();
            }
        });

        stepView = findViewById(R.id.simpleStep);
        stepView.setStepItemSize(5);
        //步骤内容列表
        stepList = new ArrayList<>(6);
        stepList.add("步骤1");
        stepList.add("步骤2");
        stepList.add("这是步骤3");
        stepList.add("步骤4");
        stepView.setStepContentList(stepList);
    }
}
