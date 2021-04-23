package com.genvict.widget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.genvict.widgets.jmb.JMenuButton;
import com.genvict.widgets.jmi.JMenuItem;
import com.genvict.widgets.jtb.JTopBar;

/**
 * 测试Demo
 * @author jayz
 */
public class MainActivity extends Activity {
    private JMenuButton testMenuButton;
    private JMenuItem versoin;
    private JMenuItem setting;
    private JMenuItem me;

    private JTopBar topBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testMenuButton = findViewById(R.id.testMenuBtn);
        testMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SimpleStepActivity.class);
                startActivity(intent);
            }
        });
        testMenuButton.setTitle("这是一个菜单按钮")
                .setTitleMarginTop(30)
                .setIconClickEffect(true);

        versoin = findViewById(R.id.versoin);
        versoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "You click the menu item!", Toast.LENGTH_SHORT).show();
                versoin.setShowUnreadIcon(false);

                Intent intent = new Intent(MainActivity.this, WaveViewActivity.class);
                startActivity(intent);
            }
        });

        setting = findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "You click the menu item!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, WaveLoadingActivity.class);
                startActivity(intent);
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

        me = findViewById(R.id.me);
        me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TrafficLightActivity.class);
                startActivity(intent);
            }
        });
    }
}
