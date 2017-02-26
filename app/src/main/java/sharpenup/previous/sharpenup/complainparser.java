package sharpenup.previous.sharpenup;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("deprecation")
public class complainparser extends AsyncTask<String, String, String> {

    private static final String TAG_CITY = "app_cntr";
    // url to get all products list
    private static String url = "http://176.32.230.250/anshuli.com/sharpenup/sharpenup.php";    // JSON Node names, 2 for counter, add data
    public ArrayAdapter<String> dataAdapter;

    //String TAG_NAME = "name";

    // products JSONArray
    public HashMap<String, String> userInfo;
    public HashMap<String, String> userInfo1;
    public HashMap<String, String> userInfo3;
    public HashMap<String, String> userInfo4;
    JSONParser jParser = new JSONParser();
    JSONArray user = null;
    String output;
    int i = 0;
    private Context parent;
    private TextView username, credits;
    private ProgressDialog pDialog;
    private String name;
    private String parents;
    private String remarks;
    private String action_taken;
    private String action_taken_flag;

    private String spinner_handle;
    private String spinner_mode;
    private String spinner_nature;
    private String spinner_details;
    private String spinner_person_res;
    private String date_view;
    private String spinner_code;
    private String spinner_description;
    private String nature_remarks;
    private String spinner_code_action;
    private String spinner_description_action;
    private String action_remarks;
    private String center;
    private String roll;
    private String batch;
    private String cls;

    public complainparser(
            Context parent,
            String roll,
            String cls,
            String batch,
            String name,
            String parents,
            String center,
            String remarks,
            String action_taken,
            String action_taken_flag,
            String spinner_handle,
            String spinner_mode,
            String spinner_nature,
            String spinner_details,
            String spinner_person_res,
            String spinner_code,
            String spinner_description,
            String nature_remarks,
            String spinner_code_action,
            String spinner_description_action,
            String action_remarks,
            String date_view
    ) {
        this.roll = roll;
        this.cls = cls;
        this.batch = batch;
        this.parent = parent;
        this.name = name;
        this.parents = parents;
        this.center = center;
        this.remarks = remarks;
        this.action_taken = action_taken;
        this.action_taken_flag = action_taken_flag;

        this.spinner_handle = spinner_handle;
        this.spinner_mode = spinner_mode;
        this.spinner_nature = spinner_nature;
        this.spinner_details = spinner_details;
        this.spinner_person_res = spinner_person_res;
        this.spinner_code = spinner_code;
        this.spinner_description = spinner_description;
        this.nature_remarks = nature_remarks;
        this.spinner_code_action = spinner_code_action;
        this.spinner_description_action = spinner_description_action;
        this.action_remarks = action_remarks;
        this.date_view = date_view;
    }

			
			

	/*private ProgressDialog dialog = new ProgressDialog(parent);

    *//** progress dialog to show user that the backup is processing. *//*
    */

    /**
     * application context.
     */

    protected void onPreExecute() {
       /* this.dialog.setMessage("Please wait..");
        this.dialog.show();*/
    }

    /**
     * getting All products from url
     */
    protected String doInBackground(String... args) {
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("name", this.name));
        params.add(new BasicNameValuePair("parents", this.parents));
        params.add(new BasicNameValuePair("remarks", this.remarks));
        params.add(new BasicNameValuePair("center", this.center));
        params.add(new BasicNameValuePair("roll", this.roll));
        params.add(new BasicNameValuePair("batch", this.cls));
        params.add(new BasicNameValuePair("class", this.batch));

        params.add(new BasicNameValuePair("action_taken", this.action_taken));
        params.add(new BasicNameValuePair("action_taken_flag", this.action_taken_flag));

        params.add(new BasicNameValuePair("spinner_handle", this.spinner_handle));
        params.add(new BasicNameValuePair("spinner_mode", this.spinner_mode));
        params.add(new BasicNameValuePair("spinner_nature", this.spinner_nature));
        params.add(new BasicNameValuePair("spinner_details", this.spinner_details));
        params.add(new BasicNameValuePair("spinner_person_res", this.spinner_person_res));
        params.add(new BasicNameValuePair("date_view", this.date_view));
        params.add(new BasicNameValuePair("spinner_code", this.spinner_code));
        params.add(new BasicNameValuePair("spinner_description", this.spinner_description));
        params.add(new BasicNameValuePair("nature_remarks", this.nature_remarks));
        params.add(new BasicNameValuePair("spinner_code_action", this.spinner_code_action));
        params.add(new BasicNameValuePair("spinner_description_action", this.spinner_description_action));
        params.add(new BasicNameValuePair("action_remarks", this.action_remarks));


        JSONObject json = jParser.makeHttpRequest(url, "POST", params);


        return null;
    }

			
		/*	public HashMap<String,String> getUserInfo()
			{
				while(i==0);
				return userInfo;
				
			}
		*/
		/*	public HashMap<String,String> getUserInfo3()
			{
				while(i==0);
				return userInfo3;
				
			}
			public HashMap<String,String> getUserInfo4()
			{
				while(i==0);
				return userInfo4;
				
			}
			*/

    /**
     * After completing background task Dismiss the progress dialog
     **/


    protected void onPostExecute(String file_url) {

        //	pDialog.dismiss();


    }

    //	public HashMap<String, String> getUserInfo1() {
    //	// TODO Auto-generated method stub
    //		while(i==0);
    //	return userInfo1;
    //	}


}
