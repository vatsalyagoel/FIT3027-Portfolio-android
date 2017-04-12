package com.vatsalya.monash.monster;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;

import com.vatsalya.monash.monster.models.Monster;
import com.vatsalya.monash.monster.utils.DatabaseHelper;

import java.util.ArrayList;

public class SearchMonsters extends AppCompatActivity {

    private ListView listView;
    private MonsterAdapter monsterAdapter;
    private ArrayList<Monster> mainMonsterList;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_monsters);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();

        databaseHelper = new DatabaseHelper(this);
        mainMonsterList = new ArrayList<>(databaseHelper.getAllMonsters());
        monsterAdapter = new MonsterAdapter(this, mainMonsterList);
        listView = (ListView) findViewById(R.id.list_monster);
        listView.setAdapter(monsterAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Monster monster= (Monster) parent.getAdapter().getItem(position);
                Intent intent = new Intent(SearchMonsters.this, ViewMonster.class);
                intent.putExtra(getString(R.string.monster_parcel_id), monster);
                startActivity(intent);
            }
        });

        updateListCount();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();

        searchView.setSearchableInfo(searchManager.
                getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true; // handled
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                monsterAdapter.getFilter().filter(newText);

                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.create_monster:
                Intent intent = new Intent(SearchMonsters.this, CreateMonster.class);
                intent.putExtra(getString(R.string.sender_class_name), this.getClass().getSimpleName());
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateListCount() {
        // Get total size of person list & set title
        int numPeople = mainMonsterList.size();
        setTitle("Number of monsters: " + numPeople);
    }

    private class MonsterAdapter extends BaseAdapter implements Filterable {

        private Context currentContext;
        private ArrayList<Monster> monsterList;

        private MonsterFilter filter;

        public MonsterAdapter(Context con, ArrayList<Monster> monsters) {
            currentContext = con;
            monsterList = monsters;
        }

        public ArrayList<Monster> getMonsterList() {
            return monsterList;
        }

        public void setMonsterList(ArrayList<Monster> monsterList) {
            this.monsterList = monsterList;
        }

        @Override
        public int getCount() { return monsterList.size(); }
        @Override
        public Object getItem(int i) { return monsterList.get(i); }
        @Override
        public long getItemId(int i) { return i; }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if(view == null) {
                LayoutInflater inflater = (LayoutInflater)
                        currentContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                // Create a list item based off layout definition
                view = inflater.inflate(R.layout.list_monster_item, null);
            }

            TextView nameView = (TextView) view.findViewById(R.id.monsterNameList);
            TextView speciesView = (TextView) view.findViewById(R.id.monsterSpeciesList);

            nameView.setText(monsterList.get(i).getName());
            speciesView.setText(monsterList.get(i).getSpecies());

            return view;
        }

        @Override
        public Filter getFilter() {
            if(filter == null)
                filter = new MonsterFilter();
            return filter;
        }

        private class MonsterFilter extends Filter {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                // Create a FilterResults object
                FilterResults results = new FilterResults();

                // If the constraint (search string/pattern) is null
                // or its length is 0, i.e., its empty then
                // we just set the `values` property to the
                // original contacts list which contains all of them
                if (constraint == null || constraint.length() == 0) {
                    results.values = mainMonsterList;
                    results.count = mainMonsterList.size();
                }
                else {
                    // Some search copnstraint has been passed
                    // so let's filter accordingly
                    ArrayList<Monster> filteredContacts = new ArrayList<Monster>();

                    // We'll go through all the contacts and see
                    // if they contain the supplied string
                    for (Monster c : mainMonsterList) {
                        if (c.getName().toUpperCase().contains( constraint.toString().toUpperCase() )) {
                            // if `contains` == true then add it
                            // to our filtered list
                            filteredContacts.add(c);
                        }
                    }

                    // Finally set the filtered values and size/count
                    results.values = filteredContacts;
                    results.count = filteredContacts.size();
                }

                // Return our FilterResults object
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                monsterList = (ArrayList<Monster>) results.values;
                notifyDataSetChanged();
            }
        }
    }
}
