package com.vatsalya.monash.monster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.vatsalya.monash.monster.models.Monster;

public class MainActivity extends AppCompatActivity {

    private Button createMonsterButton;
    private Button searchMonstersButton;
    private Button viewMonsterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createMonsterButton = (Button) findViewById(R.id.createMonsterButton);
        searchMonstersButton = (Button) findViewById(R.id.searchMonstersButton);
        viewMonsterButton = (Button) findViewById(R.id.viewMonsterButton);

        createMonsterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateMonster.class);
                startActivity(intent);
            }
        });

        searchMonstersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchMonsters.class);
                startActivity(intent);
            }
        });

        viewMonsterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewMonster.class);
                Monster monster = new Monster();
                intent.putExtra(getString(R.string.monster_parcel_id), monster);
                startActivity(intent);
            }
        });
    }
}
