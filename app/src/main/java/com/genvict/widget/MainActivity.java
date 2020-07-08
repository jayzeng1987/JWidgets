package com.genvict.widget;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.genvict.widgets.jmb.JMenuButton;
import com.genvict.widgets.jmi.JMenuItem;
import com.genvict.widgets.jtb.JTopBar;

public class MainActivity extends Activity {
    private JMenuButton testMenuButton;
    private JMenuItem versoin;
    private JMenuItem setting;

    private JTopBar topBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testMenuButton = findViewById(R.id.testMenuBtn);
        testMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "You click the menu button!", Toast.LENGTH_SHORT).show();
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
        topBar.setLeftIconClickListener(new JTopBar.OnIconClick() {
            @Override
            public void onClick() {
                Toast.makeText(MainActivity.this, "You click left icon button!", Toast.LENGTH_SHORT).show();
            }
        }).setRightIconClickListener(new JTopBar.OnIconClick() {
            @Override
            public void onClick() {
                Toast.makeText(MainActivity.this, "You click right icon button!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
