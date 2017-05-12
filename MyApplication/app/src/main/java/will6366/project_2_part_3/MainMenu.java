package will6366.project_2_part_3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        findViewById(R.id.create_account_button).setOnClickListener(this);
        findViewById(R.id.place_hold_button).setOnClickListener(this);
        findViewById(R.id.cancel_hold_button).setOnClickListener(this);
        findViewById(R.id.manage_system_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        Class nextActivity = null;

        switch (view.getId()) {
            case R.id.create_account_button:
                //Log.d("click","Create Account");
                nextActivity = CreateAccount.class;
                break;
            case R.id.place_hold_button:
                Log.d("click","Place Hold");
                nextActivity = PlaceHold.class;
                break;
            case R.id.cancel_hold_button:
                //Log.d("click","Cancel Hold");
                nextActivity = CancelHold.class;
                break;
            case R.id.manage_system_button:
                //Log.d("click","Manage Hold");

                break;
            default:
                //Log.d("click","Default");
                break;
        }

        //get intent
        Intent intent = new Intent(this,nextActivity);

        //start the new activity
        startActivity(intent);
    }
}
