package sharpenup.customgridview;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

public class UniversalTableLayout {
    final float gravity = 1.0f;
    Integer id = 0;
    List<String[]> data;
    Context context;
    Dialog dialogReg;
    TableLayout tableLayout;
    Button btn;
    boolean Rotation;

    public UniversalTableLayout(List<String[]> data, final Context context) {
        this.data = data;
        this.context = context;

        dialogReg = new Dialog(this.context);
        dialogReg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        ((Activity) this.context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        dialogReg.setContentView(R.layout.tabledialog);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialogReg.getWindow();
        lp.copyFrom(window.getAttributes());
        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);


        tableLayout = (TableLayout) dialogReg.findViewById(R.id.UniversalTable);

        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        tableDisplay();
        dialogReg.show();
        dialogReg.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                changeOrientation();

            }
        });


    }

    public UniversalTableLayout(List<String[]> data, final Context context, boolean Rotation) {
        this.data = data;
        this.context = context;
        this.Rotation = Rotation;
        dialogReg = new Dialog(this.context);
        dialogReg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Rotation)
            ((Activity) this.context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        dialogReg.setContentView(R.layout.tabledialog);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialogReg.getWindow();
        lp.copyFrom(window.getAttributes());
        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);


        tableLayout = (TableLayout) dialogReg.findViewById(R.id.UniversalTable);

        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        tableDisplay();
        dialogReg.show();
        dialogReg.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                changeOrientation();

            }
        });


    }

    void changeOrientation() {
        ((Activity) context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    private void tableDisplay() {

        int rowSize = 0;
        int columnSize = 0;
        rowSize = (data.get(0)).length;
        columnSize = data.size();
        float dynaGravity = gravity / columnSize;

        rowSetter(tableLayout, columnSize, rowSize, dynaGravity);

    }

    protected void rowSetter(TableLayout tableLayout, int columnSize, int rowSize, float dynaGravity) {


        LayoutParams params = new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, dynaGravity);
        for (int j = 0; j < rowSize; j++) {
            TableRow rowLoc = new TableRow(tableLayout.getContext());
            rowLoc.setGravity(Gravity.CENTER);

            rowLoc.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            for (int i = 0; i < columnSize; i++) {

                String Text = (data.get(i))[j];
                TextView tv = new TextView(rowLoc.getContext());
                tv.setId(id++);
                tv.setHorizontalScrollBarEnabled(true);
                tv.setGravity(Gravity.CENTER);
                tv.setLayoutParams(params);
                tv.setText(" " + Text + " ");
                tv.setBackgroundResource(R.drawable.cell_shape);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, (15));
                tv.setTextColor(Color.BLACK);
                tv.setPadding(0, 0, 0, 0);
                rowLoc.addView(tv);
            }
            tableLayout.addView(rowLoc);
        }


    }


}
