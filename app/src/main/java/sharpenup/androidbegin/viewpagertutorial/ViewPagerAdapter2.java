package sharpenup.androidbegin.viewpagertutorial;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import sharpenup.customgridview.R;

public class ViewPagerAdapter2 extends PagerAdapter {
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

    public ViewPagerAdapter2(Context context, ArrayList<String> present,
                             ArrayList<String> absent,
                             ArrayList<String> noclass, ArrayList<String> day,
                             ArrayList<String> leave,
                             ArrayList<String> date, ArrayList<String> name,
                             ArrayList<String> subject) {
        this.context = context;
        this.present = present;
        this.absent = absent;
        this.noclass = noclass;
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
        TextView absenttext;
        TextView leavetext;
        TextView noclasstext;


        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.view_atten, container,
                false);

        LinearLayout root = (LinearLayout) itemView.findViewById(R.id.l1);


        // Locate the TextViews in viewpager_item.xml
        dayttext = (TextView) itemView.findViewById(R.id.dayatten);
        datetext = (TextView) itemView.findViewById(R.id.date);
        subjecttext = (TextView) itemView.findViewById(R.id.subject);
        presenttext = (TextView) itemView.findViewById(R.id.present);
        absenttext = (TextView) itemView.findViewById(R.id.absent);
        noclasstext = (TextView) itemView.findViewById(R.id.noclass);
        leavetext = (TextView) itemView.findViewById(R.id.leave);


        if (present.get(position).equals("PRESENT")) {

            root.setBackgroundColor(Color.parseColor("#99ff99"));

        }

        if (absent.get(position).equals("ABSENT")) {

            root.setBackgroundColor(Color.parseColor("#ff9999"));

        }

        if (leave.get(position).equals("LEAVE")) {

            root.setBackgroundColor(Color.parseColor("#ffff99"));

        }

		
		/*absent = (TextView) itemView.findViewById(R.id.absent);
		leave = (TextView) itemView.findViewById(R.id.leave);
*/
        // Capture position and set to the TextViews
        dayttext.setText(day.get(position));
        datetext.setText(date.get(position));
        subjecttext.setText(subject.get(position));


        presenttext.setText(present.get(position));

        absenttext.setText(absent.get(position));
        noclasstext.setText(noclass.get(position));
        leavetext.setText(leave.get(position));
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
