package sharpenup.androidbegin.viewpagertutorial;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

import sharpenup.customgridview.R;

public class Main extends Activity {
    // Declare Variables
    ViewPager viewPager;
    PagerAdapter adapter;
    ArrayList<String> rank;
    ArrayList<String> country;
    ArrayList<String> population;
    ArrayList<String> day;
    ArrayList<String> topic;
    int[] flag;

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
        setContentView(R.layout.viewpager_main);

        rank = new ArrayList<String>();
        // arrayList.add(SUBJECT);

        country = new ArrayList<String>();
        //arrayList1.add(SUBJECT);

        population = new ArrayList<String>();

        day = new ArrayList<String>();

        topic = new ArrayList<String>();
        //arrayList2.add(SUBJECT);


        Bundle b = this.getIntent().getExtras();
        rank = b.getStringArrayList("date");

        // rank =b.getStringArray("date");
        country = b.getStringArrayList("faculty");
        population = b.getStringArrayList("subject");

        day = b.getStringArrayList("day");

        topic = b.getStringArrayList("topic");
        
		
		
	/*	// Generate sample data
        rank = new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };

		country = new String[] { "China", "India", "United States",
				"Indonesia", "Brazil", "Pakistan", "Nigeria", "Bangladesh",
				"Russia", "Japan" };

		population = new String[] { "1,354,040,000", "1,210,193,422",
				"315,761,000", "237,641,326", "193,946,886", "182,912,000",
				"170,901,000", "152,518,015", "143,369,806", "127,360,000" };
*/
		/*flag = new int[] { R.drawable.china, R.drawable.india,
				R.drawable.unitedstates, R.drawable.indonesia,
				R.drawable.brazil, R.drawable.pakistan, R.drawable.nigeria,
				R.drawable.bangladesh, R.drawable.russia, R.drawable.japan };
*/
        // Locate the ViewPager in viewpager_main.xml
        viewPager = (ViewPager) findViewById(R.id.pager);
        // Pass results to ViewPagerAdapter Class
        adapter = new ViewPagerAdapter(Main.this, rank, country, population, day, topic);
        // Binds the Adapter to the ViewPager
        viewPager.setAdapter(adapter);

    }
}