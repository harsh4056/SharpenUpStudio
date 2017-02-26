package sharpenup.androidbegin.viewpagertutorial;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import sharpenup.customgridview.JSONParser;
import sharpenup.customgridview.R;

@SuppressWarnings("deprecation")
@SuppressLint("Instantiatable")
public class ViewPagerAdapter3 extends PagerAdapter {
    // Declare Variables
    Context context;
    ArrayList<String> rank;
    ArrayList<String> country;
    ArrayList<String> population;
    int[] flag;
    JSONParser jparser = new JSONParser();

    LayoutInflater inflater;
    TextView txtrank;
    TextView txtcountry;
    TextView txtpopulation;
    TextView day;
    ImageView imgflag;
    EditText marksOb;
    EditText outOf;
    String mycls;
    String mybatch;
    String mycenter;
    String myrank;
    String mycountry;
    String mypopulation;
    String marks;
    private ArrayList<String> myday;
    private String name_roll;
    private ArrayList<String> cls;
    private ArrayList<String> batch;
    private ArrayList<String> center;

    public ViewPagerAdapter3(Context context, ArrayList<String> rank, ArrayList<String> country,
                             ArrayList<String> population, ArrayList<String> myday, String name_roll,
                             ArrayList<String> cls, ArrayList<String> batch, ArrayList<String> center) {
        super();
        this.context = context;
        this.rank = rank;
        this.country = country;
        this.population = population;
        this.myday = myday;
        this.name_roll = name_roll;
        this.cls = cls;
        this.batch = batch;
        this.center = center;
        //this.flag = flag;
    }

    @Override
    public int getCount() {
        return rank.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        // Declare Variables

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.viewpager_item3, container,
                false);

        // Locate the TextViews in viewpager_item.xml
        txtrank = (TextView) itemView.findViewById(R.id.rank3);
        txtcountry = (TextView) itemView.findViewById(R.id.country3);
        txtpopulation = (TextView) itemView.findViewById(R.id.population3);
        day = (TextView) itemView.findViewById(R.id.day3);
        marksOb = (EditText) itemView.findViewById(R.id.marksOb);
        outOf = (EditText) itemView.findViewById(R.id.outOf);

        marks = marksOb.getTag(position).toString();

        mycls = cls.get(position);
        mybatch = batch.get(position);
        mycenter = center.get(position);

        myrank = rank.get(position);
        mycountry = country.get(position);
        mypopulation = population.get(position);
        // Capture position and set to the TextViews
        txtrank.setText(rank.get(position));
        txtcountry.setText(country.get(position));
        txtpopulation.setText(population.get(position));
        day.setText(myday.get(position));

        Button submit = (Button) itemView.findViewById(R.id.submit_marksOb);
        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new studentsview().execute();

            }
        });
        // Locate the ImageView in viewpager_item.xml
        //imgflag = (ImageView) itemView.findViewById(R.id.flag);
        // Capture position and set to the ImageView
        //imgflag.setImageResource(flag[position]);

        // Add viewpager_item.xml to ViewPager
        container.addView(itemView);

        return itemView;


    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        container.removeView((LinearLayout) object);

    }

    private class studentsview extends AsyncTask<String, Void, String> {
        JSONObject json;


        @Override
        protected String doInBackground(String... urls) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            //params.add(new BasicNameValuePair("datelow", date_pend_down.getText().toString()));
            params.add(new BasicNameValuePair("date", myrank));
            params.add(new BasicNameValuePair("subject", mycountry));
            params.add(new BasicNameValuePair("topic", mypopulation));
            params.add(new BasicNameValuePair("class", mycls));
            params.add(new BasicNameValuePair("batch", mybatch));
            params.add(new BasicNameValuePair("center", mycenter));
            params.add(new BasicNameValuePair("marks", "Roll Number =" + name_roll + " Marks = " + marks + " out of " + outOf.getText().toString()));

            String url = "http://176.32.230.250/anshuli.com/sharpenup2/marksfeed.php";


            json = jparser.makeHttpRequest(url, "POST", params);


            return null;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            //pDialog.dismiss();


        }

    }


}
