package sharpenup.worksheet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import sharpenup.customgridview.R;

public class TeacherSelect extends Activity {
    String teacher;

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        overridePendingTransition(R.anim.open_main, R.anim.close_next);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacherselect);
        getActionBar().hide();
        Intent i = getIntent();
        teacher = i.getStringExtra("teacher");
    }


    public void submit(View v) {
        Intent i = new Intent(TeacherSelect.this, TeacherSubmission.class);
        i.putExtra("teacher", teacher);
        startActivity(i);

    }

    public void complete(View v) {
        Intent i = new Intent(TeacherSelect.this, TeacherCheck.class);
        i.putExtra("teacher", teacher);
        startActivity(i);
    }

}
