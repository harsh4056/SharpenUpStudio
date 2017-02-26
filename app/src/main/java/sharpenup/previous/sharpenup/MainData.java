package sharpenup.previous.sharpenup;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import sharpenup.customgridview.R;


@SuppressWarnings("deprecation")
public class MainData extends Activity {


    EditText name;
    EditText parents;
    EditText remarks;
    EditText action_taken;
    EditText action_taken_flag;
    EditText phone;
    EditText nature_remarks;
    EditText action_remarks;

    Button handled;
    Button mode;
    Button nature;
    Button detail;
    Button person_res;
    Button date;
    Button submit;

    String handled_1;
    String mode_1;
    String nature_1;
    String detail_1;
    String person_res_1;
    String date_1;
    String code;
    String description;
    String action_taken_1;

    Spinner spinner_parent;
    Spinner spinner_center;
    Spinner spinner_batch;
    Spinner spinner_cls;
    Spinner spinner_name;
    Spinner spinner_handle;
    Spinner spinner_mode;
    Spinner spinner_nature;
    Spinner spinner_details;
    Spinner spinner_person_res;
    Spinner spinner_code;
    Spinner spinner_phone;
    Spinner spinner_code_action;
    Spinner spinner_description;
    Spinner spinner_description_action;
    Spinner spinner_action_taken;
    Dialog dialogLog;

    String id_response;
    String date_view;
    String itemValue;
    String itemValuenew;
    List<String> bachoKNaam = new ArrayList<String>();
    List<String> cls_ = new ArrayList<String>();
    List<String> categories_phone;
    List<String> categories_center;
    List<String> categories_handle;
    List<String> categories_parent;
    List<String> categories_mode;
    List<String> categories_nature;
    List<String> categories_details;
    List<String> categories_person_res;
    List<String> categories_code;
    List<String> categories_code_action;
    List<String> categories_description;
    List<String> categories_description_action;
    List<String> categories_action_taken;
    JSONParserNew jparser = new JSONParserNew();
    Context ctx = MainData.this;
    String my_url = "http://176.32.230.250/anshuli.com/sharpenup/action_to_taken.php";
    String my_url2 = "http://176.32.230.250/anshuli.com/sharpenup/action_to_taken_new.php";
    JSONParser jParser1 = new JSONParser();
    JSONParserNew jParser2 = new JSONParserNew();
    Dialog dialogReg;
    Dialog dialogRegnew;
    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;
    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            showDate(arg1, arg2 + 1, arg3);
        }
    };
    private OnItemSelectedListener itemSelectedListener1 = new OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
                                   long arg3) {


            // ListView Clicked item index


            int initialposition = spinner_code.getSelectedItemPosition();
            spinner_description.setSelection(initialposition, false);

            //  spinner_description.getSelectedItemPosition();

            // Show Alert
                /*S*/


        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub

        }
    };
    private OnItemSelectedListener itemSelectedListener2 = new OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
                                   long arg3) {


            // ListView Clicked item index


            int initialposition = spinner_description.getSelectedItemPosition();
            spinner_code.setSelection(initialposition, false);

            //  spinner_description.getSelectedItemPosition();

            // Show Alert
	            /*S*/


        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub

        }
    };
    private OnItemSelectedListener itemSelectedListener = new OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
                                   long arg3) {


            // ListView Clicked item index

            // ListView Clicked item value
            itemValue = (String) spinner_nature.getItemAtPosition(position);


            spinnerDialog(itemValue);


            // Show Alert
            /*S*/


        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub

        }
    };
    private OnItemSelectedListener itemSelectedListener4 = new OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
                                   long arg3) {


            // ListView Clicked item index


            int initialposition = spinner_code_action.getSelectedItemPosition();
            spinner_description_action.setSelection(initialposition, false);

            //  spinner_description.getSelectedItemPosition();

            // Show Alert
	            /*S*/


        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub

        }
    };
    private OnItemSelectedListener itemSelectedListener5 = new OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
                                   long arg3) {


            // ListView Clicked item index


            int initialposition = spinner_description_action.getSelectedItemPosition();
            spinner_code_action.setSelection(initialposition, false);

            //  spinner_description.getSelectedItemPosition();

            // Show Alert
	            /*S*/


        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub

        }
    };
    private OnItemSelectedListener itemSelectedListener3 = new OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
                                   long arg3) {


            // ListView Clicked item index


            // ListView Clicked item value
            itemValuenew = (String) spinner_action_taken.getItemAtPosition(position);


            spinnerDialognew(itemValuenew);


            // Show Alert
            /*S*/


        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mai);


        getActionBar().hide();

        String url = "http://176.32.230.250/anshuli.com/sharpenup/data_schema.php";
        new handle().execute(url);

        spinner_name = (Spinner) findViewById(R.id.studentName);

        spinner_name.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    new getStudentsNameValuePair().execute();


                }
                return false;
            }
        });

        spinner_cls = (Spinner) findViewById(R.id.cls);
        // phone = (EditText)findViewById(R.id.b);

        // parents = (EditText)findViewById(R.id.parent);

        remarks = (EditText) findViewById(R.id.remarks);

        // action_taken = (EditText)findViewById(R.id.action_taken);

        action_taken_flag = (EditText) findViewById(R.id.action_taken_flag);


       /* handled = (Button)findViewById(R.id.handle);

        mode = (Button)findViewById(R.id.mode);

        nature = (Button)findViewById(R.id.nature);

        detail = (Button)findViewById(R.id.detail);

        person_res = (Button)findViewById(R.id.person_res);
        */
        date = (Button) findViewById(R.id.date);

        submit = (Button) findViewById(R.id.submit);


        cls_.add("Junior");
        cls_.add("6th");
        cls_.add("7th");
        cls_.add("8th");
        cls_.add("9th");
        cls_.add("10th");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterclass = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cls_);

        // Drop down layout style - list view with radio button
        dataAdapterclass.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner_cls.setAdapter(dataAdapterclass);

        //----------------------------------------
        spinner_phone = (Spinner) findViewById(R.id.batchreg);

        // Spinner click listener
        //  spinner.setOnItemSelectedListener((OnItemSelectedListener) this);

        // Spinner Drop down elements
        categories_phone = new ArrayList<String>();
        categories_phone.add("SELECT BATCH");
        categories_phone.add("A");
        categories_phone.add("B");
        categories_phone.add("C");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter50 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories_phone);

        // Drop down layout style - list view with radio button
        dataAdapter50.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner_phone.setAdapter(dataAdapter50);

        // Spinner element for handle-
        spinner_parent = (Spinner) findViewById(R.id.parent);

        // Spinner click listener
        //  spinner.setOnItemSelectedListener((OnItemSelectedListener) this);

        // Spinner Drop down elements
        categories_parent = new ArrayList<String>();
        categories_parent.add("SELECT PARENT");
        categories_parent.add("Mother");
        categories_parent.add("Father");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter21 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories_parent);

        // Drop down layout style - list view with radio button
        dataAdapter21.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner_parent.setAdapter(dataAdapter21);


        spinner_center = (Spinner) findViewById(R.id.centre);

        // Spinner click listener
        //  spinner.setOnItemSelectedListener((OnItemSelectedListener) this);

        // Spinner Drop down elements
        categories_center = new ArrayList<String>();
        categories_center.add("SELECT CENTER");
        categories_center.add("CC");
        categories_center.add("PB");
        categories_center.add("DDN");
        categories_center.add("RT");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter22 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories_center);

        // Drop down layout style - list view with radio button
        dataAdapter22.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner_center.setAdapter(dataAdapter22);


        // Spinner element for handle-
        spinner_handle = (Spinner) findViewById(R.id.handle);

        // Spinner click listener
        //  spinner.setOnItemSelectedListener((OnItemSelectedListener) this);

        // Spinner Drop down elements
        categories_handle = new ArrayList<String>();
        categories_handle.add("SELECT HANDLER");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories_handle);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner_handle.setAdapter(dataAdapter);


        //----------------------------------------

        // Spinner element for mode-
        spinner_mode = (Spinner) findViewById(R.id.mode);

        // Spinner click listener
        //  spinner.setOnItemSelectedListener((OnItemSelectedListener) this);

        // Spinner Drop down elements
        categories_mode = new ArrayList<String>();
        categories_mode.add("SELECT MODE");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories_mode);

        // Drop down layout style - list view with radio button
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner_mode.setAdapter(dataAdapter1);


//----------------------------------------

        // Spinner element for nature-
        spinner_nature = (Spinner) findViewById(R.id.nature);

        // Spinner click listener
        //  spinner.setOnItemSelectedListener((OnItemSelectedListener) this);

        // Spinner Drop down elements
        categories_nature = new ArrayList<String>();
        categories_nature.add("SELECT NATURE");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories_nature);

        // Drop down layout style - list view with radio button
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner

        spinner_nature.setAdapter(dataAdapter2);
        int position = 0;
        spinner_nature.setSelection(position, false);
        spinner_nature.setOnItemSelectedListener(itemSelectedListener);

       /* spinner_nature.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
               int position, long id) {

             // ListView Clicked item index
             int itemPosition     = position;

             // ListView Clicked item value
              itemValue    = (String) spinner_nature.getItemAtPosition(position);


             spinnerDialog();


              // Show Alert
              Toast.makeText(getApplicationContext(),
                "Position :"+itemPosition+"  ListItem : " +parts[3] , Toast.LENGTH_SHORT)
                .show();

            }

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}

       });
        */


        // Spinner element for nature-
        spinner_action_taken = (Spinner) findViewById(R.id.spinner_action_taken);

        // Spinner click listener
        //  spinner.setOnItemSelectedListener((OnItemSelectedListener) this);

        // Spinner Drop down elements
        categories_action_taken = new ArrayList<String>();
        categories_action_taken.add("Select action to be taken");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter9 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories_action_taken);

        // Drop down layout style - list view with radio button
        dataAdapter9.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner

        spinner_action_taken.setAdapter(dataAdapter9);
        int position1 = 0;
        spinner_action_taken.setSelection(position1, false);
        spinner_action_taken.setOnItemSelectedListener(itemSelectedListener3);


//----------------------------------------

        // Spinner element for nature-
        spinner_details = (Spinner) findViewById(R.id.common_details);

        // Spinner click listener
        //  spinner.setOnItemSelectedListener((OnItemSelectedListener) this);

        // Spinner Drop down elements
        categories_details = new ArrayList<String>();
        categories_details.add("SELECT COMMON DETAILS");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories_details);

        // Drop down layout style - list view with radio button
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner_details.setAdapter(dataAdapter3);


//----------------------------------------

        // Spinner element for nature-
        spinner_person_res = (Spinner) findViewById(R.id.person_res);

        // Spinner click listener
        //  spinner.setOnItemSelectedListener((OnItemSelectedListener) this);

        // Spinner Drop down elements
        categories_person_res = new ArrayList<String>();
        categories_person_res.add("SELECT PERSON RESPONSIBLE");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories_person_res);

        // Drop down layout style - list view with radio button
        dataAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner_person_res.setAdapter(dataAdapter4);

        //-------dATE pICKER---------------

        dateView = (TextView) findViewById(R.id.date_1);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1, day);


        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (
                        spinner_parent.getSelectedItem().toString().equals("SELECT PARENT") ||
                                spinner_center.getSelectedItem().toString().equals("SELECT CENTER") ||
                                remarks.getText().toString().equals("") ||
                                spinner_action_taken.getSelectedItem().toString().equals("Select action to be take") ||
                                spinner_phone.getSelectedItem().toString().equals("SELECT BATCH") ||
                                //phone.getText().toString().equals("")||
                                spinner_handle.getSelectedItem().toString().equals("SELECT HANDLER") ||
                                spinner_mode.getSelectedItem().toString().equals("SELECT MODE") ||
                                spinner_nature.getSelectedItem().toString().equals("SELECT NATURE") ||
                                spinner_details.getSelectedItem().toString().equals("SELECT COMMON DETAILS") ||
                                spinner_person_res.getSelectedItem().toString().equals("SELECT PERSON RESPONSIBLE") ||
                                spinner_code.getSelectedItem().toString().equals("SELECT CODE") ||
                                spinner_description.getSelectedItem().toString().equals("SELECT Description") ||

                                spinner_code_action.getSelectedItem().toString().equals("SELECT CODE") ||
                                spinner_description_action.getSelectedItem().toString().equals("SELECT Description")
                        )

                {


                    Toast.makeText(getApplicationContext(), "Please enter all the details",
                            Toast.LENGTH_LONG).show();

                } else {

                    try {

                        String parts[] = spinner_name.getSelectedItem().toString().split("-");

                        complainparser userinfo = new complainparser(getBaseContext(),
                                parts[0],
                                spinner_cls.getSelectedItem().toString(),
                                spinner_phone.getSelectedItem().toString(),
                                parts[1],
                                spinner_parent.getSelectedItem().toString(),
                                spinner_center.getSelectedItem().toString(),
                                remarks.getText().toString(),
                                spinner_action_taken.getSelectedItem().toString(),
                                action_taken_flag.getText().toString(),
                                spinner_handle.getSelectedItem().toString(),
                                spinner_mode.getSelectedItem().toString(),
                                spinner_nature.getSelectedItem().toString(),
                                spinner_details.getSelectedItem().toString(),
                                spinner_person_res.getSelectedItem().toString(),
                                spinner_code.getSelectedItem().toString(),
                                spinner_description.getSelectedItem().toString(),
                                nature_remarks.getText().toString(),
                                spinner_code_action.getSelectedItem().toString(),
                                spinner_description_action.getSelectedItem().toString(),
                                action_remarks.getText().toString(),
                                date_view);


                        userinfo.execute();

                        Toast.makeText(getApplicationContext(), "Your complain has been sucessfully lodged",
                                Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), e.getMessage(),
                                Toast.LENGTH_LONG).show();

                    }


                }

            }
        });


    }

    public void setDate(View view) {
        showDialog(999);
        // Toast.makeText(getApplicationContext(), "ca", Toast.LENG .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(year).append("-")
                .append(month).append("-").append(day));
        date_view = "" + String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(day);
        // Toast.makeText(this, "date_view:" + date_view, 1).show();
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    protected void spinnerDialog(String itemValue) {
        // TODO Auto-generated method stub

        dialogReg = new Dialog(MainData.this);
        dialogReg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogReg.setContentView(R.layout.spinnerdialog);


        new action_to_taken().execute(my_url);


        nature_remarks = (EditText) dialogReg.findViewById(R.id.nature_remarks);

        Button submit_nature = (Button) dialogReg.findViewById(R.id.submit_nature);


        // Spinner element for mode-
        spinner_code = (Spinner) dialogReg.findViewById(R.id.code);

        // Spinner click listener
        //  spinner.setOnItemSelectedListener((OnItemSelectedListener) this);

        // Spinner Drop down elements
        categories_code = new ArrayList<String>();
        categories_code.add("SELECT CODE");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter5 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories_code);

        // Drop down layout style - list view with radio button
        dataAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner_code.setAdapter(dataAdapter5);


        spinner_code.setOnItemSelectedListener(itemSelectedListener1);


        // Spinner element for mode-
        spinner_description = (Spinner) dialogReg.findViewById(R.id.description);

        // Spinner click listener
        //  spinner.setOnItemSelectedListener((OnItemSelectedListener) this);

        // Spinner Drop down elements
        categories_description = new ArrayList<String>();
        categories_description.add("SELECT Description");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter6 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories_description);

        // Drop down layout style - list view with radio button
        dataAdapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner_description.setAdapter(dataAdapter6);


        spinner_description.setOnItemSelectedListener(itemSelectedListener2);


        submit_nature.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {



					/*if(spinner_code.getSelectedItem().toString().equals("others")&&
						spinner_description.getSelectedItem().toString().equals("others") && nature_remarks.getText().toString().equals(null))


					{

						Toast.makeText(getApplicationContext(), "Please fill the remarks",
			        			   Toast.LENGTH_LONG).show();


					}else
					{
						dialogReg.dismiss();

					}*/


                dialogReg.dismiss();

            }
        });


        dialogReg.show();

    }

    protected void spinnerDialognew(String itemValue) {
        // TODO Auto-generated method stub

        dialogRegnew = new Dialog(MainData.this);
        dialogRegnew.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogRegnew.setContentView(R.layout.spinnerdialognew);

        my_url2 = "http://176.32.230.250/anshuli.com/sharpenup/action_to_taken_new.php";

        new action_to_taken2().execute(my_url2);


        action_remarks = (EditText) dialogRegnew.findViewById(R.id.action_remarks);

        Button submit_action = (Button) dialogRegnew.findViewById(R.id.submit_action);


        // Spinner element for mode-
        spinner_code_action = (Spinner) dialogRegnew.findViewById(R.id.codenew);

        // Spinner click listener
        //  spinner.setOnItemSelectedListener((OnItemSelectedListener) this);

        // Spinner Drop down elements
        categories_code_action = new ArrayList<String>();
        categories_code_action.add("SELECT CODE");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter10 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories_code_action);

        // Drop down layout style - list view with radio button
        dataAdapter10.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner_code_action.setAdapter(dataAdapter10);


        spinner_code_action.setOnItemSelectedListener(itemSelectedListener4);


        // Spinner element for mode-
        spinner_description_action = (Spinner) dialogRegnew.findViewById(R.id.descriptionnew);

        // Spinner click listener
        //  spinner.setOnItemSelectedListener((OnItemSelectedListener) this);

        // Spinner Drop down elements
        categories_description_action = new ArrayList<String>();
        categories_description_action.add("SELECT Description");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter11 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories_description_action);

        // Drop down layout style - list view with radio button
        dataAdapter11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner_description_action.setAdapter(dataAdapter11);


        spinner_description_action.setOnItemSelectedListener(itemSelectedListener5);


        submit_action.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


					/*if(spinner_code_action.getSelectedItem().toString().equals("others")||
							spinner_description_action.getSelectedItem().toString().equals("others")||
							action_remarks.getText().toString() != null
							)
						{


							Toast.makeText(getApplicationContext(), "Plesae fill the remarks",
				        			   Toast.LENGTH_LONG).show();


						}else
						{
							dialogRegnew.dismiss();

						}*/


                dialogRegnew.dismiss();

            }
        });


        dialogRegnew.show();

    }

    public String GET(String url) {
        InputStream inputStream = null;
        String result = "";
        try {

            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
            HttpEntity httpEntity = httpResponse.getEntity();
            inputStream = httpResponse.getEntity().getContent();
            if (inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
            //  Toast.makeText(MainActivity, e.getLocalizedMessage(), 1000).show();
        }
        return result;
    }

    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;
        inputStream.close();
        return result;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MainData.this, buttons.class);
        startActivity(intent);
        finish();
    }

    public void listViewSetting(List<String> list) {
        ArrayAdapter<String> bache = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);

        // Drop down layout style - list view with radio button
        bache.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner_name.setAdapter(bache);

    }

    private class handle extends AsyncTask<String, Void, String> {

        final SweetAlertDialog pDialog = new SweetAlertDialog(MainData.this, SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText("Miles To go.....");


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
            //visible
            return GET(urls[0]);


        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {  //gone
            // //System.out.println("RESULT : " + result);

            JSONObject json;
            try {
                // Checking for SUCCESS TAG


                json = new JSONObject(result);
                JSONArray account = json.getJSONArray("schedule");
                for (int i = 0; i < account.length(); i++) {
                    json = account.getJSONObject(i);


                    handled_1 = json.getString("handle_by");
                    mode_1 = json.getString("mode");
                    nature_1 = json.getString("nature");
                    detail_1 = json.getString("common_detail");
                    person_res_1 = json.getString("person_res");
                    action_taken_1 = json.getString("action_to_action");
					        		/* time= time + json.getString("Time");
					        		 others= others + json.getString("Others");
					        		 */
                    if (!handled_1.equals(""))
                        categories_handle.add(handled_1);

                    if (!mode_1.equals(""))
                        categories_mode.add(mode_1);

                    if (!nature_1.equals(""))
                        categories_nature.add(nature_1);

                    if (!detail_1.equals(""))
                        categories_details.add(detail_1);

                    if (!person_res_1.equals(""))
                        categories_person_res.add(person_res_1);

                    if (!action_taken_1.equals(""))
                        categories_action_taken.add(action_taken_1);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


            pDialog.dismiss();
        }

    }

    private class action_to_taken extends AsyncTask<String, Void, String> {

        final SweetAlertDialog pDialog = new SweetAlertDialog(MainData.this, SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText("Miles To go.....");


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
            params.add(new BasicNameValuePair("itemvalue", itemValue));


            JSONObject json = jParser1.makeHttpRequest(my_url, "POST", params);


            try {
                // Checking for SUCCESS TAG


                //Toast.makeText(MainActivity.this, (CharSequence) json, 1).show();

                JSONArray account = json.getJSONArray("pendings");
                for (int i = 0; i < account.length(); i++) {
                    json = account.getJSONObject(i);

                    code = json.getString("code");
                    description = json.getString("description");


                    categories_code.add(code);
                    categories_description.add(description);


                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            //visible
            return null;


        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {  //gone
            // //System.out.println("RESULT : " + result);


            pDialog.dismiss();


        }


    }

    private class action_to_taken2 extends AsyncTask<String, Void, String> {
        final SweetAlertDialog pDialog = new SweetAlertDialog(MainData.this, SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText("Miles To go.....");


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
            params.add(new BasicNameValuePair("itemvaluenew", itemValuenew));


            JSONObject json = jParser2.makeHttpRequest(my_url2, "POST", params);


            try {
                // Checking for SUCCESS TAG


                //Toast.makeText(MainActivity.this, (CharSequence) json, 1).show();

                JSONArray account = json.getJSONArray("pendings");
                for (int i = 0; i < account.length(); i++) {
                    json = account.getJSONObject(i);

                    String code_action = json.getString("code");
                    String description_action = json.getString("description");


                    categories_code_action.add(code_action);
                    categories_description_action.add(description_action);


                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            //visible
            return null;


        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {  //gone
            // //System.out.println("RESULT : " + result);


            pDialog.dismiss();


        }
    }

    private class getStudentsNameValuePair extends AsyncTask<String, Void, String> {
        final SweetAlertDialog pDialog = new SweetAlertDialog(MainData.this, SweetAlertDialog.PROGRESS_TYPE)
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
            params.add(new BasicNameValuePair("batch", spinner_phone.getSelectedItem().toString()));
            params.add(new BasicNameValuePair("center", spinner_center.getSelectedItem().toString()));
            params.add(new BasicNameValuePair("class", spinner_cls.getSelectedItem().toString()));

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

                bachoKNaam.clear();


                //Toast.makeText(MainActivity.this, (CharSequence) json, 1).show();

                JSONArray account = json.getJSONArray("pendings");
                for (int i = 0; i < account.length(); i++) {
                    json = account.getJSONObject(i);

                    String NAME = json.getString("NAME");
                    String Roll = json.getString("ROLL");

                    bachoKNaam.add(Roll + "-" + NAME);
                    //categories_description.add(description);


                }
                listViewSetting(bachoKNaam);


            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(ctx, e.getMessage(), Toast.LENGTH_LONG).show();
            }


        }
    }


}
