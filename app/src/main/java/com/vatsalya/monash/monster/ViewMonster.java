package com.vatsalya.monash.monster;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.vatsalya.monash.monster.models.Monster;

public class ViewMonster extends AppCompatActivity {

    private Monster monster;

    private TextView nameTextView;
    private TextView ageTextView;
    private TextView speciesTextView;
    private TextView attackPowerTextView;
    private TextView healthTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_monster);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        monster = intent.getParcelableExtra(getString(R.string.monster_parcel_id));

        nameTextView = (TextView) findViewById(R.id.nameTextView);
        ageTextView = (TextView) findViewById(R.id.ageTextView);
        speciesTextView = (TextView) findViewById(R.id.speciesTextView);
        attackPowerTextView = (TextView) findViewById(R.id.attackPowerTextView);
        healthTextView = (TextView) findViewById(R.id.healthTextView);

        nameTextView.setText(monster.getName());
        ageTextView.setText(Integer.toString(monster.getAge()));
        speciesTextView.setText(monster.getSpecies());
        attackPowerTextView.setText(Integer.toString(monster.getAttackPower()));
        healthTextView.setText(Integer.toString(monster.getHealth()));

    }

}
