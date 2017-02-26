package sharpenup.androidbegin.viewpagertutorial;

import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import cn.pedant.SweetAlert.SweetAlertDialog;
import nonactivity.javafunctionality.classes.NameDialougeForMarks;
import sharpenup.customgridview.JSONParser;
import sharpenup.customgridview.R;

@SuppressWarnings("deprecation")
public class marksfill extends Activity {
    public String cls;
    public String batch;
    public String center;
    public String date;
    public String topic;
    public String subject;
    public String new_sub;
    public String new_date;
    public String sub;
    public String cutoff = "";
    public String marksKiLambiSiString;
    // Declare Variables
    ViewPager viewPager;
    PagerAdapter adapter;
    String rank;
    String country;
    String population;
    String day;
    ArrayList<String> list, listWithMarks;
    int[] flag;
    String name_roll;
    ListView lv1;
    Dialog dialogReg;
    String total_marks, marks_obt;
    String outOf;
    HashMap<String, String> rollMarks;
    boolean forHashMap = false;
    boolean forViewMarksTableForm;
    JSONParser jparser = new JSONParser();
    List<String> categories_code;
    String parts[];
    SearchView sv;

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        overridePendingTransition(R.anim.open_main, R.anim.close_next);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from viewpager_main.xml
        setContentView(R.layout.list);
        rollMarks = new HashMap<String, String>();
        lv1 = (ListView) findViewById(R.id.listtest);
        sv = (SearchView) findViewById(R.id.searchView);
        categories_code = new ArrayList<String>();
        getActionBar().hide();


        list = new ArrayList<String>();
        Bundle b = this.getIntent().getExtras();
        //date=b.getString("date");
        forViewMarksTableForm = b.getBoolean("viewMarks");
        // rank =b.getStringArray("date");
        topic = b.getString("faculty");
        subject = b.getString("subject");

        day = b.getString("day");

        cls = b.getString("cls");
        batch = b.getString("batch");
        center = b.getString("center");
        list = b.getStringArrayList("list");
        name_roll = b.getString("name_roll");
        listWithMarks = b.getStringArrayList("listWithMarks");
        view();

    }


    private void view() {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(marksfill.this, android.R.layout.simple_list_item_1, list);
        lv1.setAdapter(dataAdapter);

        // ListView Item Click Listener
        lv1.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                HashMap<String, String> hm = new HashMap<String, String>();

                // ListView Clicked item value
                if (listWithMarks.get(position).split("@").length > 1) {
                    forHashMap = true;
                    String parts[] = listWithMarks.get(position).split("@");
                    String parts1[] = parts[1].split(" ");
                    outOf = parts1[9].split(",")[0];
                    for (int i = 3; i < parts1.length - 1; i += 9) {

                        hm.put(parts1[i].replace("=", ""), parts1[i + 3]);

                    }
                    rollMarks = hm;


                } else {
                    rollMarks.clear();
                    forHashMap = false;

                }

                String itemValue = (String) lv1.getItemAtPosition(position);

                parts = itemValue.split(" ");

                cls = parts[11];
                batch = parts[15];
                center = parts[7];
                String[] splitted = itemValue.split("\r\n");
                cutoff = splitted[6].split(":")[1];


                new GettingStudents().execute();

            }

        });
        lv1.setTextFilterEnabled(true);
        sv.setSubmitButtonEnabled(true);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

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


    protected void completeActionDialog() {


        dialogReg = new Dialog(marksfill.this);
        dialogReg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogReg.setContentView(R.layout.action_taken_dialogue);

        Button register = (Button) dialogReg.findViewById(R.id.button1);


        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                total_marks = ((EditText) dialogReg.findViewById(R.id.editText1)).getText().toString();
                marks_obt = ((EditText) dialogReg.findViewById(R.id.act_taken_by)).getText().toString();


                dialogReg.dismiss();


            }
        });

        dialogReg.show();
    }

    public void setlist() {
        new NameDialougeForMarks(marksfill.this, categories_code, parts, rollMarks, forHashMap, outOf, forViewMarksTableForm, cutoff);


    }

    private class GettingStudents extends AsyncTask<String, Void, String> {
        final SweetAlertDialog pDialog = new SweetAlertDialog(marksfill.this, SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText("Miles To go.....");
        JSONObject json;


        /** progress dialog to show user that the backup is processing. */

        /**
         * application context.
         */

        protected void onPreExecute() {
            pDialog.show();
            pDialog.setCancelable(false);
        }


        @Override
        protected String doInBackground(String... urls) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            //params.add(new BasicNameValuePair("datelow", date_pend_down.getText().toString()));
            params.add(new BasicNameValuePair("batch", batch));
            params.add(new BasicNameValuePair("center", center));
            params.add(new BasicNameValuePair("class", cls));

            String url = "http://176.32.230.250/anshuli.com/sharpenup2/studentsview.php";


            json = jparser.makeHttpRequest(url, "POST", params);


            return null;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            pDialog.dismiss();

            try {
                // Checking for SUCCESS TAG

                categories_code.clear();


                //Toast.makeText(MainActivity.this, (CharSequence) json, 1).show();

                JSONArray account = json.getJSONArray("pendings");
                for (int i = 0; i < account.length(); i++) {
                    json = account.getJSONObject(i);

                    String NAME = json.getString("NAME");
                    String Roll = json.getString("ROLL");
                    if (forHashMap) {
                        for (Entry<String, String> m : rollMarks.entrySet()) {
                            if (m.getKey().equals(Roll)) {
                                rollMarks.put(m.getKey(), Roll + "-" + NAME + "#" + m.getValue());

                            }

                        }
                    }

                    categories_code.add(Roll + "-" + NAME);
                    //categories_description.add(description);


                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            setlist();

        }

    }

}