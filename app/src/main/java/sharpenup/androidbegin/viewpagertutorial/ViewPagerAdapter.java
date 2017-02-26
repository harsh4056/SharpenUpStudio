package sharpenup.androidbegin.viewpagertutorial;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import sharpenup.customgridview.R;

public class ViewPagerAdapter extends PagerAdapter {


    // Declare Variables
    Context context;
    ArrayList<String> rank;
    ArrayList<String> country;
    ArrayList<String> population;
    int[] flag;
    LayoutInflater inflater;
    private ArrayList<String> myday;
    private ArrayList<String> topic;

    public ViewPagerAdapter(Context context, ArrayList<String> rank, ArrayList<String> country,
                            ArrayList<String> population, ArrayList<String> myday, ArrayList<String> topic) {
        this.context = context;
        this.rank = rank;
        this.country = country;
        this.population = population;
        this.myday = myday;
        this.topic = topic;
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
        TextView txtrank;
        TextView txtcountry;
        TextView txtpopulation;
        TextView day;
        TextView topic1;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.viewpager_item, container,
                false);

        // Locate the TextViews in viewpager_item.xml
        txtrank = (TextView) itemView.findViewById(R.id.rank);
        txtcountry = (TextView) itemView.findViewById(R.id.country);
        txtpopulation = (TextView) itemView.findViewById(R.id.population);
        day = (TextView) itemView.findViewById(R.id.day);
        topic1 = (TextView) itemView.findViewById(R.id.t_tt);


        // Capture position and set to the TextViews
        txtrank.setText(rank.get(position));
        txtcountry.setText(country.get(position));
        txtpopulation.setText(population.get(position));
        day.setText(myday.get(position));
        topic1.setText(topic.get(position));

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
}
