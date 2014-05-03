package bitshyd.math.odesolverii;
import android.app.*;
import android.content.Intent;
import android.os.*;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class InputsFormSystem extends Activity {
	Handler handler;
	public static final int TIME_ENTRY_REQUEST_CODE=1;
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inputs_form_system);
				
		handler=new Handler();
		}
public void solve(View v){
	
		
		Intent intent=new Intent(this,OutputActivitySystem.class);
		
		EditText et1=(EditText) findViewById(R.id.editText_dx_by_dt);
		EditText et2=(EditText) findViewById(R.id.editText_dy_by_dt);
		EditText et3=(EditText) findViewById(R.id.editText_t0);
		EditText et4=(EditText) findViewById(R.id.editText_x0);
		EditText et5=(EditText) findViewById(R.id.editText_y0);
		EditText et6=(EditText) findViewById(R.id.editText_tfinal);
		EditText et7=(EditText) findViewById(R.id.editText_stepsize);
		EditText et8=(EditText) findViewById(R.id.editText_precision);
		//Spinner spinner=(Spinner)findViewById(R.id.spinnerForMethods);
		intent.putExtra("et1", et1.getText().toString());
		intent.putExtra("et2", et2.getText().toString());
		intent.putExtra("et3", et3.getText().toString());
		intent.putExtra("et4", et4.getText().toString());
		intent.putExtra("et5", et5.getText().toString());
		intent.putExtra("et6", et6.getText().toString());
		intent.putExtra("et7", et7.getText().toString());
		intent.putExtra("et8", et8.getText().toString());
		//intent.putExtra("MyMethod", spinner.getSelectedItem().toString());
		
		startActivityForResult(intent,TIME_ENTRY_REQUEST_CODE);
	}
	
public void onActivityResult(int requestCode,int resultCode,Intent data){
	if(requestCode==TIME_ENTRY_REQUEST_CODE){
		if(resultCode==RESULT_OK){
			Integer integer1=data.getIntExtra("notes",-1);
			Integer integer2=data.getIntExtra("counter", -1);

		}
	}
}

public void onBtn_9Clicked(View v){
	EditText a=(EditText)getCurrentFocus();
	int start=a.getSelectionStart();
	a.getText().insert(start,"9");
}
public void onBtn_8Clicked(View v){
	EditText a=(EditText)getCurrentFocus();
	int start=a.getSelectionStart();
	a.getText().insert(start,"8");
}

public void onBtn_7Clicked(View v){
	EditText a=(EditText)getCurrentFocus();
	int start=a.getSelectionStart();
	a.getText().insert(start,"7");

}
public void onBtn_6Clicked(View v){
	EditText a=(EditText)getCurrentFocus();
	int start=a.getSelectionStart();
	a.getText().insert(start,"6");
}
public void onBtn_5Clicked(View v){
	EditText a=(EditText)getCurrentFocus();
	int start=a.getSelectionStart();
	a.getText().insert(start,"5");
}
public void onBtn_4Clicked(View v){
	EditText a=(EditText)getCurrentFocus();
	int start=a.getSelectionStart();
	a.getText().insert(start,"4");
}
public void onBtn_3Clicked(View v){
	EditText a=(EditText)getCurrentFocus();
	int start=a.getSelectionStart();
	a.getText().insert(start,"3");
}
public void onBtn_2Clicked(View v){
	EditText a=(EditText)getCurrentFocus();
	int start=a.getSelectionStart();
	a.getText().insert(start,"2");
}
public void onBtn_1Clicked(View v){
	EditText a=(EditText)getCurrentFocus();
	int start=a.getSelectionStart();
	a.getText().insert(start,"1");
}
public void onBtn_0Clicked(View v){
	EditText a=(EditText)getCurrentFocus();
	int start=a.getSelectionStart();
	a.getText().insert(start,"0");
}
public void onBtn_XClicked(View v){
	EditText a=(EditText)getCurrentFocus();
	int start=a.getSelectionStart();
	a.getText().insert(start,"x");
}
public void onBtn_YClicked(View v){
	EditText a=(EditText)getCurrentFocus();
	int start=a.getSelectionStart();
	a.getText().insert(start,"y");
}
public void onBtn_dotClicked(View v){
	EditText a=(EditText)getCurrentFocus();
	int start=a.getSelectionStart();
	a.getText().insert(start,".");
}
public void onBtn_mulClicked(View v){
	EditText a=(EditText)getCurrentFocus();
	int start=a.getSelectionStart();
	a.getText().insert(start,"*");
}
public void onBtn_divClicked(View v){
	EditText a=(EditText)getCurrentFocus();
	int start=a.getSelectionStart();
	a.getText().insert(start,"/");
}
public void onBtn_plusClicked(View v){
	EditText a=(EditText)getCurrentFocus();
	int start=a.getSelectionStart();
	a.getText().insert(start,"+");
}
public void onBtn_minusClicked(View v){
	EditText a=(EditText)getCurrentFocus();
	int start=a.getSelectionStart();
	a.getText().insert(start,"-");
}
public void onBtn_openClicked(View v){
	EditText a=(EditText)getCurrentFocus();
	int start=a.getSelectionStart();
	a.getText().insert(start,"(");
}
public void onBtn_closedClicked(View v){
	EditText a=(EditText)getCurrentFocus();
	int start=a.getSelectionStart();
	a.getText().insert(start,")");
}

public void onBtn_powClicked(View v){
	//EditText et=(EditText)findViewById(R.id.editText_dy_by_dx);
	//Spannable str=et.getText();
	//str.setSpan(new SuperscriptSpan(),str.length()-1,str.length(),Spannable.SPAN_INCLUSIVE_INCLUSIVE);
	EditText a=(EditText)getCurrentFocus();
	int start=a.getSelectionStart();
	a.getText().insert(start,"^");
}
public void onBtn_deleteClicked(View v){
	EditText a=(EditText)getCurrentFocus();
	int start=a.getSelectionStart();
		if(start!=0){
			a.getText().delete(start-1,start);
		}
}
public void onBtn_ACClicked(View v){
		EditText a=(EditText)getCurrentFocus();
		a.setText("");
}
/*public void onPlot(View v){
	Intent intent=new Intent(this,PlotActivity.class);
	EditText et1=(EditText) findViewById(R.id.editText_dy_by_dx);
	EditText et2=(EditText) findViewById(R.id.editText_x0);
	EditText et3=(EditText) findViewById(R.id.editText_y0);
	EditText et4=(EditText) findViewById(R.id.editText_xfinal);
	EditText et5=(EditText) findViewById(R.id.editText_stepsize);
	EditText et6=(EditText) findViewById(R.id.editText_precision);
	Spinner spinner=(Spinner)findViewById(R.id.spinnerForMethods);
	intent.putExtra("et1", et1.getText().toString());
	intent.putExtra("et2", et2.getText().toString());
	intent.putExtra("et3", et3.getText().toString());
	intent.putExtra("et4", et4.getText().toString());
	intent.putExtra("et5", et5.getText().toString());
	intent.putExtra("et6", et6.getText().toString());
	intent.putExtra("MyMethod", spinner.getSelectedItem().toString());
	startActivityForResult(intent,TIME_ENTRY_REQUEST_CODE);
}
*/
}
