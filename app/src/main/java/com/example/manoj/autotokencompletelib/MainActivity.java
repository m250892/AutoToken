package com.example.manoj.autotokencompletelib;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import com.example.manoj.autotokencompletelib.customtext.FilteredArrayAdapter;
import com.example.manoj.autotokencompletelib.customtext.TokenCompleteTextView;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements TokenCompleteTextView.TokenListener<Person> {

    ContactsCompletionView completionView;
    Person[] people;
    ArrayAdapter<Person> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, COUNTRIES);
        MultiAutoCompleteTextView textView = (MultiAutoCompleteTextView) findViewById(R.id.edit_text);
        textView.setAdapter(adapter);
        textView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());


        setupTokenView(savedInstanceState);
    }


    private void setupTokenView(Bundle savedInstanceState) {
        people = new Person[]{
                new Person("Marshall Weir", "marshall@example.com"),
                new Person("Margaret Smith", "margaret@example.com"),
                new Person("Max Jordan", "max@example.com"),
                new Person("Meg Peterson", "meg@example.com"),
                new Person("Amanda Johnson", "amanda@example.com"),
                new Person("Terry Anderson", "terry@example.com")
        };

        adapter = new FilteredArrayAdapter<Person>(this, R.layout.person_layout, people) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {

                    LayoutInflater l = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                    convertView = l.inflate(R.layout.person_layout, parent, false);
                }

                Person p = getItem(position);
                ((TextView) convertView.findViewById(R.id.name)).setText(p.getName());
                ((TextView) convertView.findViewById(R.id.email)).setText(p.getEmail());

                return convertView;
            }

            @Override
            protected boolean keepObject(Person person, String mask) {
                mask = mask.toLowerCase();
                return person.getName().toLowerCase().startsWith(mask) || person.getEmail().toLowerCase().startsWith(mask);
            }
        };

        completionView = (ContactsCompletionView) findViewById(R.id.searchView);
        completionView.setAdapter(adapter);
        completionView.setTokenListener(this);
        completionView.setTokenClickStyle(TokenCompleteTextView.TokenClickStyle.Select);

        if (savedInstanceState == null) {
            completionView.setPrefix("To: ");
            completionView.addObject(people[0]);
            completionView.addObject(people[1]);
        }

        Button removeButton = (Button) findViewById(R.id.removeButton);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Person> people = completionView.getObjects();
                if (people.size() > 0) {
                    completionView.removeObject(people.get(people.size() - 1));
                }
            }
        });

        Button addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random rand = new Random();
                completionView.addObject(people[rand.nextInt(people.length)]);
            }
        });


    }

    private void updateTokenConfirmation() {
        StringBuilder sb = new StringBuilder("Current tokens:\n");
        for (Object token : completionView.getObjects()) {
            sb.append(token.toString());
            sb.append("\n");
        }
    }


    @Override
    public void onTokenAdded(Person token) {
//        ((TextView) findViewById(R.id.lastEvent)).setText("Added: " + token);
        updateTokenConfirmation();
    }

    @Override
    public void onTokenRemoved(Person token) {
        //      ((TextView) findViewById(R.id.lastEvent)).setText("Removed: " + token);
        updateTokenConfirmation();
    }

    private static final String[] COUNTRIES = new String[]{
            "Belgium", "France", "Italy", "Germany", "Spain"
    };


}
