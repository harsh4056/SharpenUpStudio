
package sharpenup.customgridview;

import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

@SuppressWarnings("deprecation")
public class complainparser extends AsyncTask<String, String, String> {

    // url to get all products list
    private static String url = "http://176.32.230.250/anshuli.com/sharpenup2/stud_details.php";    // JSON Node names, 2 for counter, add data
    JSONParser jParser = new JSONParser();

    //String TAG_NAME = "name";

    // products JSONArray
    JSONArray user = null;
    JSONObject json;
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
    String pwd1;
    private Context parent;
    private String username;

    public complainparser(Context parent, String name,
                          String username,
                          String parents, String school,
                          String comment,
                          String Prev_res,
                          String van,
                          String admts,
                          String batch,
                          String cls,
                          String centre,
                          String date_view, String pwd
    ) {
        this.parent = parent;
        this.name = name;
        this.username = username;
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
        this.pwd1 = pwd;

    }


    @Override
    protected void onPreExecute() {

    }

    /**
     * getting All products from url
     */
    protected String doInBackground(String... args) {
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("name", this.name));
        params.add(new BasicNameValuePair("username", this.username));
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
        params.add(new BasicNameValuePair("pwd", this.pwd1));


        json = jParser.makeHttpRequest(url, "POST", params);


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


        try {
            String account = json.getString("pendings");

            if (account.equals("SUCCESS"))
                new SweetAlertDialog(this.parent, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Done!")
                        .setContentText("Student is registered")
                        .show();
            else {

                new SweetAlertDialog(this.parent, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Username already exits..")
                        .show();
            }
        } catch (Exception e) {

            e.printStackTrace();
            new SweetAlertDialog(this.parent, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("Something went wrong!")
                    .show();
        }

    }

    //	public HashMap<String, String> getUserInfo1() {
    //	// TODO Auto-generated method stub
    //		while(i==0);
    //	return userInfo1;
    //	}


}
