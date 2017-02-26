package nonactivity.javafunctionality.classes;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import sharpenup.customgridview.JSONParser;
import sharpenup.customgridview.PrefManager;
import sharpenup.customgridview.R;
import sharpenup.customgridview.UniversalTableLayout;

@SuppressWarnings("deprecation")
@SuppressLint("NewApi")
public class NameDialougeForMarks {
    Dialog marksDialouge;
    Context context;
    HashMap<String, String> hm;
    ArrayList<String> names;
    LinearLayout linearLayout;
    Integer id;
    Button b1;
    String topic;
    Boolean textChecker = false;
    EditText paymaentPlace, cutOff;
    String parts[];
    String marksKiLambiSiString = "";
    HashMap<String, String> rollMarks;
    boolean forHashMap;

    JSONParser jparser = new JSONParser();
    PrefManager pref;

    public NameDialougeForMarks(final Context context, final List<String> names, String parts[], HashMap<String, String> rollMarks, boolean forHashMap, String outOf, boolean forViewMarksTableForm, String cuttoff) {
        id = 0;
        this.rollMarks = rollMarks;
        this.forHashMap = forHashMap;
        pref = new PrefManager(context);
        this.parts = parts;

        hm = new HashMap<String, String>();
        this.names = (ArrayList<String>) names;
        this.context = context;
        marksDialouge = new Dialog(context);
        marksDialouge.requestWindowFeature(Window.FEATURE_NO_TITLE);
        marksDialouge.setContentView(R.layout.addhisaab);
        paymaentPlace = (EditText) marksDialouge.findViewById(R.id.editText1);
        cutOff = (EditText) marksDialouge.findViewById(R.id.editText2);
        linearLayout = (LinearLayout) marksDialouge.findViewById(R.id.forNameSet);

	/*	Used HashmAp and boolean value to check if admin or not and later used split to enter names and marks. 
		If marks are not available the functions revert back to original behavior i.e. entering marks from scratch. :)*/
        if (forHashMap) {
            this.names.clear();
            paymaentPlace.setText(outOf);
            cutOff.setText(cuttoff.trim());
            for (Entry<String, String> m : rollMarks.entrySet()) {
                this.names.add(m.getValue());

            }

        }

        if (!forViewMarksTableForm) {
            setTextViews(this.names, linearLayout);
            marksDialouge.show();
            b1 = (Button) marksDialouge.findViewById(R.id.addRow);
            b1.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {


                    if (hm.size() == names.size()) {

                        if (!paymaentPlace.getText().toString().equals("") && !cutOff.getText().toString().equals("")) {
                            runSubmitMarks();
                            marksDialouge.dismiss();
                        } else
                            Toast.makeText(context, "Enter all fields!!!", Toast.LENGTH_SHORT).show();

                    } else
                        Toast.makeText(context, "Here Enter is Store. So please press enter on all the fields!!", Toast.LENGTH_LONG).show();

                }
            });

        } else {
            List<String[]> dataContainer = new ArrayList<String[]>();
            if (forHashMap) {
                List<String> name = new ArrayList<String>();
                List<String> marks = new ArrayList<String>();

                for (Entry<String, String> m : rollMarks.entrySet()) {


                    String splitter[] = m.getValue().split("#");
                    if (m.getValue().contains("#")) {
                        name.add(splitter[0]);
                        marks.add(splitter[1]);
                    }
                }

                dataContainer.add(name.toArray(new String[name.size()]));
                dataContainer.add(marks.toArray(new String[marks.size()]));
                new UniversalTableLayout(dataContainer, context, false);
            } else {
                Toast.makeText(context, "No Marks entered yet", Toast.LENGTH_LONG).show();
            }

        }

    }


    protected void runSubmitMarks() {
        for (Entry<String, String> m : hm.entrySet()) {

            marksKiLambiSiString += ", Roll Number =" + m.getKey() + " Marks = " + m.getValue() + " out of " + paymaentPlace.getText().toString();

            new studentsview().execute();
        }

    }


    protected void setTextViews(ArrayList<String> names, LinearLayout linearLayout) {

        String Names = "";
        String Marks = "";
        for (int i = 0; i < names.size(); i++) {
            if (this.forHashMap) {
                String parts[] = names.get(i).split("#");
                Names = parts[0];
                Marks = parts[1];
            } else {
                Names = names.get(i);
            }

            LinearLayout llEt = new LinearLayout(linearLayout.getContext());
            LinearLayout lltv = new LinearLayout(linearLayout.getContext());
            llEt.setOrientation(LinearLayout.HORIZONTAL);
            llEt.setGravity(Gravity.CENTER);
            LayoutParams params1 = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 0.5f);

            final EditText marksAttained = new EditText(llEt.getContext());
            marksAttained.setId(id++);
            marksAttained.setText(Marks);
            marksAttained.setHint("Marks");
            marksAttained.setInputType(paymaentPlace.getInputType());

            marksAttained.setImeOptions(EditorInfo.IME_ACTION_NEXT);
            marksAttained.setSingleLine();
            marksAttained.setLayoutParams(params1);
            marksAttained.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            marksAttained.setTextColor(Color.BLACK);
            marksAttained.setPadding(0, 0, 0, 0);
            marksAttained.setGravity(Gravity.CENTER);

            llEt.addView(marksAttained);


            final TextView tvName = new TextView(linearLayout.getContext());
            tvName.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            tvName.setText(Names);
            tvName.setId(id++);
            tvName.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            tvName.setTextColor(Color.BLACK);
            tvName.setPadding(0, 0, 0, 0);
            tvName.setGravity(Gravity.CENTER);
            setEnterKey(marksAttained, tvName);
            lltv.addView(tvName);
            linearLayout.addView(lltv);
            linearLayout.addView(llEt);

        }
    }

    private void setEnterKey(final EditText etName, final TextView tvName) {
        etName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                hm.remove(tvName.getText().toString().split("-")[0]);
                etName.setFocusableInTouchMode(true);
                etName.setFocusable(true);


                return false;
            }
        });


        etName.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView tv, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    //	Toast.makeText(context, et.getText().toString(), Toast.LENGTH_SHORT).show();
                    String splitted[] = tvName.getText().toString().split("-");
                    if (!etName.getText().toString().equals("")) {
                        hm.put(splitted[0], etName.getText().toString());
                        etName.setFocusable(false);

                    }

                    return true;
                }
                return false;
            }
        });


    }

    public class studentsview extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... urls) {
            String topic = "";
            for (int i = 23; i < parts.length; i++) {
                topic = topic + parts[i] + " ";

            }
            topic = topic.split("\r\n")[0];

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            //params.add(new BasicNameValuePair("datelow", date_pend_down.getText().toString()));
            params.add(new BasicNameValuePair("date", parts[2]));
            params.add(new BasicNameValuePair("subject", parts[20]));
            params.add(new BasicNameValuePair("topic", topic.trim()));
            params.add(new BasicNameValuePair("class", parts[11]));
            params.add(new BasicNameValuePair("batch", parts[15]));
            params.add(new BasicNameValuePair("center", parts[7]));
            params.add(new BasicNameValuePair("cutoff", cutOff.getText().toString()));
            if (pref.getLogin().equals("teacher"))
                params.add(new BasicNameValuePair("faculty", pref.getLogin()));
            params.add(new BasicNameValuePair("marks", marksKiLambiSiString + " }"));

            String url = "http://176.32.230.250/anshuli.com/sharpenup2/marksfeed.php";


            jparser.makeHttpRequest(url, "POST", params);


            return null;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {


            ((Activity) context).finish();


        }


    }
}
