
package sharpenup.worksheet;

import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class complainparser extends AsyncTask<String, String, String> {

    private static final String TAG_CITY = "app_cntr";
    // url to get all products list
    private static String url = "http://176.32.230.250/anshuli.com/sharpenup2/stud_details.php";    // JSON Node names, 2 for counter, add data
    JSONParser jParser = new JSONParser();

    //String TAG_NAME = "name";

    // products JSONArray
    JSONArray user = null;
    String output;
    int i = 0;
    String name;
    String parents;
    String school;
    String comment;
    String Prev_res;
    String van;
    String admts;
    String batch;
    String cls;
    String centre;
    String date_view;
    private Context parent;

    public complainparser(Context parent, String name,
                          String parents, String school,
                          String comment,
                          String Prev_res,
                          String van,
                          String admts,
                          String batch,
                          String cls,
                          String centre,
                          String date_view
    ) {

        this.name = name;
        this.parents = parents;
        this.school = school;
        this.comment = comment;
        this.Prev_res = Prev_res;
        this.van = van;
        this.admts = admts;
        this.batch = batch;
        this.cls = cls;
        this.centre = centre;
        this.date_view = date_view;


    }


    /**
     * Before starting background thread Show Progress Dialog
     */
    @Override
    protected void onPreExecute() {

    }

    /**
     * getting All products from url
     */
    protected String doInBackground(String... args) {
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair(" name", this.name));
        params.add(new BasicNameValuePair("parents", this.parents));
        params.add(new BasicNameValuePair("school", this.school));
        params.add(new BasicNameValuePair("comment", this.comment));
        params.add(new BasicNameValuePair("Prev_res", this.Prev_res));
        params.add(new BasicNameValuePair("van", this.van));
        params.add(new BasicNameValuePair("admts", this.admts));
        params.add(new BasicNameValuePair("batch", this.batch));
        params.add(new BasicNameValuePair("cls", this.cls));
        params.add(new BasicNameValuePair("centre", this.centre));
        params.add(new BasicNameValuePair("date_view", this.date_view));


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


    }

    //	public HashMap<String, String> getUserInfo1() {
    //	// TODO Auto-generated method stub
    //		while(i==0);
    //	return userInfo1;
    //	}


}
