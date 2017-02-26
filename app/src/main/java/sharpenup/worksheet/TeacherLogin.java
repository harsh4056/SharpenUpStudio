package sharpenup.worksheet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import sharpenup.customgridview.R;

public class TeacherLogin extends Activity {

    EditText et;
    String user;

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
        setContentView(R.layout.teacherlogin);


        et = (EditText) findViewById(R.id.editText1);

    }


    public void enter(View v) {


        Intent i = new Intent(TeacherLogin.this, TeacherSelect.class);
        i.putExtra("teacher", et.getText().toString());
        startActivity(i);
    }
}
