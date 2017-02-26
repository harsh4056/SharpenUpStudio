package sharpenup.previous.sharpenup;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

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
import java.util.HashMap;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import sharpenup.customgridview.R;


@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
@SuppressLint("NewApi")
public class delete extends Activity {

    private static final int SWIPE_DURATION = 250;
    private static final int MOVE_DURATION = 150;
    private static PrefManager pref;
    String cndValue;
    Dialog dialogReg;
    Spinner spinner_cnd;
    String v;
    long itemId;
    int position;
    String HANDLED;
    String MODE;
    String NATURE;
    String DETAIL;
    String ACTION_TAKEN;
    String PERSON_RESPONSIBLE;
    String complete_action;
    String cnd_code;
    String cnd_des;
    String itemValue;
    Button handle;
    Button mode;
    Button nature;
    Button common_details;
    Button atb;
    Button centre;
    Button person;
    Button search;
    ListView lv1;
    Dialog dialogStudName;
    List<String> pendings;
    EditText stud_edit;
    JSONParser jParser1 = new JSONParser();
    StableArrayAdapter mAdapter;
    ListView mListView;
    BackgroundContainer mBackgroundContainer;
    boolean mSwiping = false;
    boolean mItemPressed = false;
    HashMap<Long, Integer> mItemIdTopMap = new HashMap<Long, Integer>();
    Button cnd;

    JSONParserNew jParser = new JSONParserNew();
    @SuppressLint("NewApi")
    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {

        float mDownX;
        private int mSwipeSlop = -1;
        private int mSwipe = -1;


        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @SuppressLint("NewApi")
        @Override
        public boolean onTouch(final View v, MotionEvent event) {
            if (mSwipeSlop < 0) {
                mSwipeSlop = ViewConfiguration.get(delete.this).
                        getScaledTouchSlop();


            }


            switch (event.getAction()) {


                case MotionEvent.ACTION_DOWN:
            /*int itemPosition     = position;
        	cndValue    = (String) mListView.getItemAtPosition(position);

             String[] parts = itemValue.split(" ");

             String number = parts[3];
             cndDialog(cndValue);
            // ListView Clicked item value
*/
                    if (mItemPressed) {
                        // ListView Clicked item index
                        return false;
                    }
                    mItemPressed = true;
                    mDownX = event.getX();
                    break;
                case MotionEvent.ACTION_CANCEL:
                    v.setAlpha(1);
                    v.setTranslationX(0);
                    mItemPressed = false;
                    break;
                case MotionEvent.ACTION_MOVE: {
                    float x = event.getX() + v.getTranslationX();
                    float deltaX = x - mDownX;
                    float deltaXAbs = Math.abs(deltaX);
                    if (!mSwiping) {


                        if (deltaXAbs > mSwipeSlop) {
                            mSwiping = true;
                            mListView.requestDisallowInterceptTouchEvent(true);
                            mBackgroundContainer.showBackground(v.getTop(), v.getHeight());


                        }

                    }
                    if (mSwiping) {


                        v.setTranslationX((x - mDownX));
                        v.setAlpha(1 - deltaXAbs / v.getWidth());
                    }
                }
                break;
                case MotionEvent.ACTION_UP: {
                    // User let go - figure out whether to animate the view out, or back into place
                    if (mSwiping) {
                        float x = event.getX() + v.getTranslationX();
                        float deltaX = x - mDownX;
                        float deltaXAbs = Math.abs(deltaX);
                        float fractionCovered;
                        float endX;
                        float endAlpha;
                        final boolean remove;
                        if (deltaXAbs > v.getWidth() / 4) {
                            // Greater than a quarter of the width - animate it out
                            fractionCovered = deltaXAbs / v.getWidth();
                            endX = deltaX < 0 ? -v.getWidth() : v.getWidth();
                            endAlpha = 0;
                            remove = true;
                        } else {
                            // Not far enough - animate it back
                            fractionCovered = 1 - (deltaXAbs / v.getWidth());
                            endX = 0;
                            endAlpha = 1;
                            remove = false;
                        }
                        // Animate position and alpha of swiped item
                        // NOTE: This is a simplified version of swipe behavior, for the
                        // purposes of this demo about animation. A real version should use
                        // velocity (via the VelocityTracker class) to send the item off or
                        // back at an appropriate speed.
                        long duration = (int) ((1 - fractionCovered) * SWIPE_DURATION);
                        mListView.setEnabled(false);
                        v.animate().setDuration(duration).
                                alpha(endAlpha).translationX(endX).
                                withEndAction(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Restore animated values
                                        v.setAlpha(1);
                                        v.setTranslationX(0);
                                        if (remove) {
                                            animateRemoval(mListView, v);
                                        } else {
                                            mBackgroundContainer.hideBackground();
                                            mSwiping = false;
                                            mListView.setEnabled(true);
                                        }
                                    }
                                });
                    }
                }
                mItemPressed = false;


                break;
                default:
                    return false;
            }


            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_deletion);
        cnd = (Button) findViewById(R.id.cnd);
        cnd.setVisibility(View.INVISIBLE);
        getActionBar().hide();

        pref = new PrefManager(getApplicationContext());

        Intent intent = getIntent();

        itemValue = intent.getStringExtra("key");


        if (itemValue.equals("action_to_action") || itemValue.equals("nature"))
            cnd.setVisibility(Button.VISIBLE);


        //handle = (Button) findViewById(R.id.handle_me);


        pendings = new ArrayList<String>();

        new searching().execute();


        cnd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                cndDialog(itemValue);

            }
        });

        Button add = (Button) findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                newDialog(itemValue);

            }
        });


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

    private void animateRemoval(final ListView listview, View viewToRemove) {
        int firstVisiblePosition = listview.getFirstVisiblePosition();

        for (int i = 0; i < listview.getChildCount(); ++i) {
            View child = listview.getChildAt(i);
            if (child != viewToRemove) {
                int position = firstVisiblePosition + i;

                itemId = mAdapter.getItemId(position);


                mItemIdTopMap.put(itemId, child.getTop());
            }
        }
        // Delete the item from the adapter

        position = mListView.getPositionForView(viewToRemove);
        v = pendings.get(position);
        new deleteValue().execute();
        mAdapter.remove(mAdapter.getItem(position));


        final ViewTreeObserver observer = listview.getViewTreeObserver();
        observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                observer.removeOnPreDrawListener(this);
                boolean firstAnimation = true;
                int firstVisiblePosition = listview.getFirstVisiblePosition();
                for (int i = 0; i < listview.getChildCount(); ++i) {
                    final View child = listview.getChildAt(i);
                    int position = firstVisiblePosition + i;
                    long itemId = mAdapter.getItemId(position);
                    Integer startTop = mItemIdTopMap.get(itemId);
                    int top = child.getTop();
                    if (startTop != null) {
                        if (startTop != top) {
                            int delta = startTop - top;
                            child.setTranslationY(delta);
                            child.animate().setDuration(MOVE_DURATION).translationY(0);
                            if (firstAnimation) {
                                child.animate().withEndAction(new Runnable() {
                                    public void run() {
                                        mBackgroundContainer.hideBackground();
                                        mSwiping = false;
                                        mListView.setEnabled(true);
                                    }
                                });
                                firstAnimation = false;
                            }
                        }
                    } else {
                        // Animate new views along with the others. The catch is that they did not
                        // exist in the start state, so we must calculate their starting position
                        // based on neighboring views.
                        int childHeight = child.getHeight() + listview.getDividerHeight();
                        startTop = top + (i > 0 ? childHeight : -childHeight);
                        int delta = startTop - top;
                        child.setTranslationY(delta);
                        child.animate().setDuration(MOVE_DURATION).translationY(0);
                        if (firstAnimation) {
                            child.animate().withEndAction(new Runnable() {
                                public void run() {
                                    mBackgroundContainer.hideBackground();
                                    mSwiping = false;
                                    mListView.setEnabled(true);
                                }
                            });
                            firstAnimation = false;
                        }
                    }
                }
                mItemIdTopMap.clear();
                return true;
            }
        });
    }

    protected void execute() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(delete.this, admin.class);
        startActivity(intent);
        finish();
    }

    protected void cndDialog(final String itemValue) {
        // TODO Auto-generated method stub

        dialogReg = new Dialog(delete.this);
        dialogReg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogReg.setContentView(R.layout.cnd);

        Button register = (Button) dialogReg.findViewById(R.id.cnd_sub);

        spinner_cnd = (Spinner) dialogReg.findViewById(R.id.spinner_cnd);

        // Spinner click listener
        //  spinner.setOnItemSelectedListener((OnItemSelectedListener) this);

        // Spinner Drop down elements

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter20 = new ArrayAdapter<String>(delete.this, android.R.layout.simple_spinner_item, pendings);

        // Drop down layout style - list view with radio button
        dataAdapter20.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner_cnd.setAdapter(dataAdapter20);

        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                cnd_des = ((EditText) dialogReg.findViewById(R.id.cnd_des)).getText().toString();
                cnd_code = ((EditText) dialogReg.findViewById(R.id.cnd_code)).getText().toString();


                new cndNew().execute();


                dialogReg.dismiss();

        	/*Intent refresh = new Intent(Pendings.this,Pendings.class);
        startActivity(refresh);
        finish();*/

            }
        });

        dialogReg.show();
    }

    protected void newDialog(final String itemValue) {
        // TODO Auto-generated method stub

        dialogReg = new Dialog(delete.this);
        dialogReg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogReg.setContentView(R.layout.add_mem);

        Button register = (Button) dialogReg.findViewById(R.id.add_sub);


        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                complete_action = ((EditText) dialogReg.findViewById(R.id.add_edit)).getText().toString();


                new addNew().execute();


                dialogReg.dismiss();

        	/*Intent refresh = new Intent(Pendings.this,Pendings.class);
        startActivity(refresh);
        finish();*/

            }
        });

        dialogReg.show();
    }

    private class addNew extends AsyncTask<String, Void, String> {

        final SweetAlertDialog pDialog = new SweetAlertDialog(delete.this, SweetAlertDialog.PROGRESS_TYPE)
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
            params.add(new BasicNameValuePair("itemvaluenew", itemValue));
            params.add(new BasicNameValuePair("addNew", complete_action));
            String url = "http://176.32.230.250/anshuli.com/sharpenup/add_new.php";


            JSONObject json = jParser.makeHttpRequest(url, "POST", params);


            JSONArray jsonArray;


            return null;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            pDialog.dismiss();
        }

    }

    private class cndNew extends AsyncTask<String, Void, String> {

        final SweetAlertDialog pDialog = new SweetAlertDialog(delete.this, SweetAlertDialog.PROGRESS_TYPE)
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
            params.add(new BasicNameValuePair("cnd_des", cnd_des));
            params.add(new BasicNameValuePair("cnd_code", cnd_code));
            params.add(new BasicNameValuePair("cndValue", spinner_cnd.getSelectedItem().toString()));


            String url = "http://176.32.230.250/anshuli.com/sharpenup/cnd_new.php";


            JSONObject json = jParser.makeHttpRequest(url, "POST", params);


            JSONArray jsonArray;


            return null;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            pDialog.dismiss();
        }


    }

    private class searching extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("itemvaluenew", itemValue));

            String url = "http://176.32.230.250/anshuli.com/sharpenup/listview.php";


            JSONObject json = jParser.makeHttpRequest(url, "POST", params);


            JSONArray jsonArray;

            try {
                // Checking for SUCCESS TAG


                //Toast.makeText(MainActivity.this, (CharSequence) json, 1).show();

                JSONArray account = json.getJSONArray("list");
                for (int i = 0; i < account.length(); i++) {
                    json = account.getJSONObject(i);


		        	 	 /*String S_NO= json.getString("S_No");
		        	    String NAME= json.getString("NAME");
		        	 	String PARENT= json.getString("PARENT");
		        	 	String PHONE= json.getString("PHONE");*/
                    if (itemValue.equals("handle_by"))
                        HANDLED = json.getString("handle_by");

                    if (itemValue.equals("mode"))
                        MODE = json.getString("mode");

                    if (itemValue.equals("nature"))
                        NATURE = json.getString("nature");
                    // String code_nature= json.getString("code_nature");
                    // String description_nature= json.getString("description_nature");
                    // String remarks_nature= json.getString("remarks_nature");
                    if (itemValue.equals("common_detail"))
                        DETAIL = json.getString("common_detail");
                    // String REMARKS= json.getString("REMARKS");
                    // String ACTION_TAKEN_FLAG= json.getString("ACTION_TAKEN_FLAG");
                    if (itemValue.equals("action_to_action"))
                        ACTION_TAKEN = json.getString("action_to_action");

                    // String ACTION_TAKEN_CODE= json.getString("ACTION_TAKEN_CODE");
                    // String ACTION_TAKEN_DESCRIPTION= json.getString("ACTION_TAKEN_DESCRIPTION");
                    // String ACTION_TAKEN_REMARKS= json.getString("ACTION_TAKEN_REMARKS");
                    // String DATE= json.getString("DATE");
                    if (itemValue.equals("person_res"))
                        PERSON_RESPONSIBLE = json.getString("person_res");
                    // String Time= json.getString("Time");
                    //String Date_LOG= json.getString("Date_LOG");*/


                    if (HANDLED != null && !HANDLED.equals(""))
                        pendings.add(HANDLED);

                    if (MODE != null && !MODE.equals(""))
                        pendings.add(MODE);

                    if (NATURE != null && !NATURE.equals(""))
                        pendings.add(NATURE);

                    if (DETAIL != null && !DETAIL.equals(""))
                        pendings.add(DETAIL);

                    if (ACTION_TAKEN != null && !ACTION_TAKEN.equals(""))
                        pendings.add(ACTION_TAKEN);

                    if (PERSON_RESPONSIBLE != null && !PERSON_RESPONSIBLE.equals(""))
                        pendings.add(PERSON_RESPONSIBLE);




		        	 	/*categories_handle.add(handled_1);
		        	 	categories_mode.add(mode_1);
		        	 	categories_nature.add(nature_1);
		        	 	categories_details.add(detail_1);
		        	 	categories_person_res.add(person_res_1);
		        	 	categories_action_taken.add(action_taken_1);*/
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
        	/* ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(admin.this, R.layout.atom_pay_list_item, pendings);
         	  lv1.setAdapter(dataAdapter); */
            mBackgroundContainer = (BackgroundContainer) findViewById(R.id.listViewBackground);
            mListView = (ListView) findViewById(R.id.listview);
            android.util.Log.d("Debug", "d=" + mListView.getDivider());

            mAdapter = new StableArrayAdapter(delete.this, R.layout.opaque_text_view, pendings,
                    mTouchListener);
            mListView.setAdapter(mAdapter);


        }

    }

    private class deleteValue extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();


            // String deletethis = pendings.get((int) itemId);


            params.add(new BasicNameValuePair("itemId", v));
            params.add(new BasicNameValuePair("itemvaluenew", itemValue));

            String url = "http://176.32.230.250/anshuli.com/sharpenup/deleteValue.php";


            JSONObject json = jParser.makeHttpRequest(url, "POST", params);


            JSONArray jsonArray;

            return null;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {


        }

    }


}

  
