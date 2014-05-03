package bitshyd.math.odesolverii;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

public class SingleType extends Activity{
	public static final int TIME_ENTRY_REQUEST_CODE=1;

	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single_type);

	}
	public void onSingleStepClicked(View v){
		Intent intent =new Intent(this,InputsFormSingle.class);
		startActivityForResult(intent,TIME_ENTRY_REQUEST_CODE);
	}
	public void onIterativeClicked(View v){
		Intent intent =new Intent(this,InputsFormMilne.class);
		startActivityForResult(intent,TIME_ENTRY_REQUEST_CODE);
	}
}
