package sharpenup.worksheet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import sharpenup.customgridview.R;

public class AdminWorksheet extends Activity {
    Button b;

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        overridePendingTransition(R.anim.open_main, R.anim.close_next);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_work);


    }


    public void addTopic(View v) {

        Intent i = new Intent(AdminWorksheet.this, AddTopics.class);
        startActivity(i);
        overridePendingTransition(R.anim.open_next, R.anim.close_main);
    }

    public void percent_subs(View v) {

        Intent i = new Intent(AdminWorksheet.this, Percent_submissions.class);
        startActivity(i);
        overridePendingTransition(R.anim.open_next, R.anim.close_main);
    }

    public void percent_comps(View v) {

        Intent i = new Intent(AdminWorksheet.this, Percent_completions.class);
        startActivity(i);
        overridePendingTransition(R.anim.open_next, R.anim.close_main);
    }


}
