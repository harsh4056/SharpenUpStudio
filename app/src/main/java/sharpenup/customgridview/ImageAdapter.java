package sharpenup.customgridview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageAdapter extends BaseAdapter {
    private final Drawable[] mThumbIds;
    private final String[] mTextIds;
    private Context mContext;


    public ImageAdapter(Context c, String[] s, Drawable[] d) {
        mContext = c;
        mThumbIds = d;
        mTextIds = s;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    //create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.gridview_item_layout, parent, false);
        } else {
            v = convertView;
        }
        TextView text = (TextView) v.findViewById(R.id.grid_item_text);
        text.setText(mTextIds[position]);
        ImageView image = (ImageView) v.findViewById(R.id.grid_item_image);
        image.setImageDrawable(mThumbIds[position]);
        return v;
    }


}