package sharpenup.androidbegin.viewpagertutorial;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import sharpenup.customgridview.R;

@SuppressLint("Instantiatable")
public class ViewPagerAdapter4 extends PagerAdapter {
    // Declare Variables
    Context context;
    ArrayList<String> rank;
    ArrayList<String> country;
    ArrayList<String> population;
    int[] flag;
    LayoutInflater inflater;
    ArrayList<String> myday;
    ArrayList<String> present;
    ArrayList<String> absent;
    ArrayList<String> noclass;
    ArrayList<String> day;
    ArrayList<String> leave;
    ArrayList<String> date;
    ArrayList<String> name;
    ArrayList<String> subject;

    public ViewPagerAdapter4(Context context, ArrayList<String> present,
                             ArrayList<String> day,
                             ArrayList<String> leave,
                             ArrayList<String> date, ArrayList<String> name,
                             ArrayList<String> subject, ArrayList<String> absent) {
        super();
        this.context = context;
        this.present = present;
        this.absent = absent;
        this.day = day;
        this.leave = leave;
        this.date = date;
        this.name = name;
        this.subject = subject;
        //this.flag = flag;
    }

    @Override
    public int getCount() {
        return present.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        // Declare Variables
        TextView dayttext;
        TextView datetext;
        TextView subjecttext;
        TextView presenttext;

        TextView leavetext;

        TextView cutofftext;


        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.view_stud_scores, container,
                false);

        LinearLayout root = (LinearLayout) itemView.findViewById(R.id.ll1);


        // Locate the TextViews in viewpager_item.xml
        dayttext = (TextView) itemView.findViewById(R.id.dayat);
        datetext = (TextView) itemView.findViewById(R.id.dat);
        subjecttext = (TextView) itemView.findViewById(R.id.subj);
        presenttext = (TextView) itemView.findViewById(R.id.pres);
        leavetext = (TextView) itemView.findViewById(R.id.leav);
        cutofftext = (TextView) itemView.findViewById(R.id.cutoff);

		
		/*if(present.get(position).equals("P"))
		{
			
			root.setBackgroundColor(Color.parseColor("#1db018"));
				
		}
		
		if(absent.get(position).equals("A"))
		{
			
			root.setBackgroundColor(Color.parseColor("#F6190D"));
				
		}
		
		if(leave.get(position).equals("L"))
		{
			
			root.setBackgroundColor(Color.parseColor("#FFFF00"));
				
		}*/
		
		
		/*absent = (TextView) itemView.findViewById(R.id.absent);
		leave = (TextView) itemView.findViewById(R.id.leave);
*/
        // Capture position and set to the TextViews
        dayttext.setText(day.get(position));
        datetext.setText(date.get(position));
        subjecttext.setText(subject.get(position));


        presenttext.setText(present.get(position));

        leavetext.setText(leave.get(position));
        cutofftext.setText(absent.get(position));
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
