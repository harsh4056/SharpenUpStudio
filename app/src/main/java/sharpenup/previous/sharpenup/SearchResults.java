package sharpenup.previous.sharpenup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

import java.util.List;

import sharpenup.customgridview.R;

public class SearchResults extends Activity {
    List<String> data;
    ArrayAdapter<String> dataAdapter;
    ListView lv1;
    SearchView sv;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.searchresults);
        getActionBar().hide();
        lv1 = (ListView) findViewById(R.id.listViewsearch);
        sv = (SearchView) findViewById(R.id.searchView1);
        lv1.setAdapter(null);
        Intent i = getIntent();
        data = i.getStringArrayListExtra("data");

        dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
        lv1.setAdapter(dataAdapter);
        lv1.setTextFilterEnabled(true);
        sv.setSubmitButtonEnabled(true);
        sv.setOnQueryTextListener(new OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO Auto-generated method stub
                if (TextUtils.isEmpty(newText)) {
                    lv1.clearTextFilter();
                } else {
                    lv1.setFilterText(newText.toString());
                }
                return true;
            }
        });

    }
}
