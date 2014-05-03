package bitshyd.math.odesolverii;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {
public static final int TIME_ENTRY_REQUEST_CODE=1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	public void onSingleEquationClicked(View v){
		Intent intent =new Intent(this,SingleType.class);
		startActivityForResult(intent,TIME_ENTRY_REQUEST_CODE);
	}
	public void onSystemEquationClicked(View v){
		Intent intent =new Intent(this,InputsFormSystem.class);
		startActivityForResult(intent,TIME_ENTRY_REQUEST_CODE);
	}
	/*public void onActivityResult(int requestCode,int resultCode,Intent data){
		
	}*/
}