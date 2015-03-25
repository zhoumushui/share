package com.zms.share;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class Main extends Activity {
    private ListView listView;

    enum IntentType {
        TEXT_PLAIN, IMAGE_BMP, AUDIO_WMA;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        listView = (ListView) findViewById(R.id.list);
        final List<String> adapterData = new ArrayList<String>();

        // MIME Type
        adapterData.add("Choice Intent Below to Share"); // 0
        adapterData.add("text/plain"); // 1
        adapterData.add("image/bmp"); // 2
        adapterData.add("audio/x-ms-wma"); // 3

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, adapterData);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1:
                        StartShare(IntentType.TEXT_PLAIN);
                        break;
                    case 2:
                        StartShare(IntentType.IMAGE_BMP);
                        break;
                    case 3:
                        StartShare(IntentType.AUDIO_WMA);
                        break;
                    default:
                        Toast.makeText(Main.this, "", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void StartShare(IntentType intentType) {
        String title = "title";
        String subject = "subject";
        String content = "content";

        Intent intent = new Intent(Intent.ACTION_SEND);
        switch (intentType) {
            case TEXT_PLAIN:
                intent.setType("text/plain");
                break;
            case IMAGE_BMP:
                intent.setType("image/bmp");
                break;
            case AUDIO_WMA:
                intent.setType("audio/x-ms-wma");
                break;
        }

        intent.putExtra(Intent.EXTRA_TITLE, title);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, content);

        Intent chooserIntent = Intent.createChooser(intent, "Select app to share");
        if (chooserIntent == null) {
            return;
        }
        try {
            startActivity(chooserIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(Main.this, "Can't find share component to share",
                    Toast.LENGTH_SHORT).show();
        }
    }

}
