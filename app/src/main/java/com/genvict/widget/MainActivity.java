package com.genvict.widget;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.genvict.widgets.JMenuButton;
import com.genvict.widgets.JMenuList;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private JMenuButton testMenuButton;
    private JMenuList versoin;
    private JMenuList setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testMenuButton = findViewById(R.id.testMenuBtn);
        testMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "You click the menu item!", Toast.LENGTH_SHORT).show();
            }
        });
        testMenuButton.setTitle("这是一个菜单按钮")
                .setTitleMarginTop(30)
                .setIconClickEffect(false);

        versoin = findViewById(R.id.versoin);
        versoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "You click the menu list!", Toast.LENGTH_SHORT).show();
                versoin.setShowUnreadIcon(false);
            }
        });

        setting = findViewById(R.id.setting);
        setting.setItemPressedColor(getResources().getColor(R.color.colorAccent)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "You click the menu list!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
