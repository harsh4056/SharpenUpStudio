package sharpenup.previous.sharpenup;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class completeParse extends AsyncTask<String, String, String> {

    private static final String TAG_CITY = "app_cntr";
    // url to get all products list
    private static String url = "http://176.32.230.250/anshuli.com/sharpenup/complete_action.php";    // JSON Node names, 2 for counter, add data
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
    private String complete_action;
    private String number;
    private String act_taken_by;

    public completeParse(Context parent, String complete_action,
                         String number, String act_taken_by

    ) {

        this.parent = parent;
        this.complete_action = complete_action;
        this.number = number;
        this.act_taken_by = act_taken_by;
    }

			
			

	/*private ProgressDialog dialog = new ProgressDialog(parent);

    *//** progress dialog to show user that the backup is processing. *//*
    */

    /**
     * application context.
     *//*
*/
    protected void onPreExecute() {
        /*this.dialog.setMessage("Miles to go...");
        this.dialog.show();*/
    }

    /**
     * getting All products from url
     */
    protected String doInBackground(String... args) {
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("complete_action", this.complete_action));
        params.add(new BasicNameValuePair("number", this.number));
        params.add(new BasicNameValuePair("act_taken_by", this.act_taken_by));

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
        Toast.makeText(parent, "Action has been taken", 1).show();

    }

    //	public HashMap<String, String> getUserInfo1() {
    //	// TODO Auto-generated method stub
    //		while(i==0);
    //	return userInfo1;
    //	}


}
