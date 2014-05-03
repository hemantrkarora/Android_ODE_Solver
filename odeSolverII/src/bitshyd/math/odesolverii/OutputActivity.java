package bitshyd.math.odesolverii;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LineGraphView;
import com.jjoe64.graphview.GraphViewStyle.GridStyle;

public class OutputActivity extends Activity {
Handler handler,handler1;
String given="";
//int i=0;
//private final static String STORETEXT="storetext.txt";
private TextView txtView1,txtView2;


	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.output_activity);
	handler=new Handler();
	handler1=new Handler();
	doSolver();
	}
	public void onSave(View view){
		final ProgressDialog SaveDialog=ProgressDialog.show(this, "Saving", "saving data and graph");
		Thread th1=new Thread(){
				public void run(){
					handler1.post(
							new Runnable(){
								public void run(){
									txtView1=(TextView)findViewById(R.id.finalAnswer);
									txtView2=(TextView)findViewById(R.id.new_output);
									
									given=given.concat("\n*****************************\n");
									given=given.concat(txtView1.getText().toString());
									given=given.concat("\n*****************************\n\n");
									String output=given.concat(txtView2.getText().toString());

									LinearLayout graph=(LinearLayout)findViewById(R.id.graph1);
									graph.setDrawingCacheEnabled(true);
									Bitmap bitmap=graph.getDrawingCache();
									
									//Save to file with today's date
									String name=new SimpleDateFormat("yyyyMMdd_hhmmss").format(new Date());
									
									try {
										File fileData,fileGraph,fdata = null,fgraph=null;
										if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) 
									    {  
									         fileData =new File(android.os.Environment.getExternalStorageDirectory(),"ODEsolver"+File.separator+"Results"+File.separator+"Data");
									         if(!fileData.exists())
									        {
									          fileData.mkdirs();

									         } 
									         fileGraph =new File(android.os.Environment.getExternalStorageDirectory(),"ODEsolver"+File.separator+"Results"+File.separator+"Graphs");
									         if(!fileGraph.exists())
									        {
									          fileGraph.mkdirs();

									         } 
									         fdata = new File(fileData.getAbsolutePath()+File.separator+ "Data_"+name+".txt");
									         fgraph=new File(fileGraph.getAbsolutePath()+File.separator+ "Graph_"+name+".png");
									    }
										
										//make graph file
										ByteArrayOutputStream bytes = new ByteArrayOutputStream();
										  bitmap.compress(Bitmap.CompressFormat.PNG, 40, bytes);
										  fgraph.createNewFile();
										  FileOutputStream fo = new FileOutputStream(fgraph);
										  fo.write(bytes.toByteArray()); 
										  fo.close();
										  
									//	make data file
										 
										FileOutputStream ostream_data=new FileOutputStream(fdata);
										PrintStream p=new PrintStream(ostream_data);
										p.print(output);
										p.close();
										

										
										
										 
										Toast
										 
										.makeText(OutputActivity.this, "The contents are saved under folder ODESolver/Results.", Toast.LENGTH_LONG)
										 
										.show();
										 
										}
										 
										catch (Throwable t) {
										 
										Toast
										 
										.makeText(OutputActivity.this, "Exception: "+t.toString(), Toast.LENGTH_LONG)
										 
										.show();
										 
										}

											SaveDialog.dismiss();
				
								}
							}
						);
				}
		};
		th1.start();
	
	
		
				
		/*
		try {
		RelativeLayout content=(RelativeLayout)findViewById(R.id.relativeLayout_graph);
		content.setDrawingCacheEnabled(true);
		Bitmap bitmap=content.getDrawingCache();
		File file,f = null;
		if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) 
	    {  
	         file =new File(android.os.Environment.getExternalStorageDirectory(),"ODEsolver_Results");
	         if(!file.exists())
	        {
	          file.mkdirs();

	         } 
	         f = new File(file.getAbsolutePath()+File.separator+ "Result_"+i+".png");
	    }

*/
	/*	ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		  bitmap.compress(Bitmap.CompressFormat.PNG, 40, bytes);
		  f.createNewFile();
		  FileOutputStream fo = new FileOutputStream(f);
		  fo.write(bytes.toByteArray()); 
		  fo.close();
		*/
	/*	FileOutputStream ostream=new FileOutputStream(f);
		bitmap.compress(CompressFormat.PNG, 10, ostream);
		  ostream.close();

	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}                                   
	  */
		
		
		
		Intent intent=getIntent();
		intent.putExtra("notes",1);
		intent.putExtra("counter", 2);
		this.setResult(RESULT_OK,intent);
		finish();
		
	}
	public void onCancel(View view){

		finish();
	}

	private void doSolver(){
		//Progress Bar
		final ProgressDialog SolveDialog=ProgressDialog.show(this, "Solving", "Loading Output");
		Thread th=new Thread(){
				public void run(){
					handler.post(
							new Runnable(){
								public void run(){
									
									/*//Code
									EditText et1=(EditText) findViewById(R.id.editText_dy_by_dx);
									EditText et2=(EditText) findViewById(R.id.editText_x0);
									EditText et3=(EditText) findViewById(R.id.editText_y0);
									EditText et4=(EditText) findViewById(R.id.editText_xfinal);
									EditText et5=(EditText) findViewById(R.id.editText_stepsize);
									EditText et6=(EditText) findViewById(R.id.editText_precision);
									
									CharSequence s=et1.getText();
									TextView tv=(TextView)findViewById(R.id.new_output);
									System.out.println(s);
									
									Spinner spinner=(Spinner)findViewById(R.id.spinnerForMethods);
											
									String MyMethod=spinner.getSelectedItem().toString();
									String f_x_y=et1;
									Double x0=Double.valueOf(et2);
									Double y0=Double.valueOf(et3);
									Double xfinal=Double.valueOf(et4);
									Double stepsize=Double.valueOf(et5);
									Integer precision=Integer.parseInt(et6);

									int numOfIntervals=(int)((xfinal-x0)/stepsize);
									
									EulerMethods e1=new EulerMethods(MyMethod,f_x_y,x0,y0,stepsize,numOfIntervals,precision);//f(x,y)(=dy/dx),x0,y0,stepsize(=h),.,.
									e1.callMe();
									tv.setText(e1.output);
									*/
									Intent intent1=getIntent();
									String et1=intent1.getStringExtra("et1");//dy/dx
									String et2=intent1.getStringExtra("et2");//x0
									String et3=intent1.getStringExtra("et3");//y0
									String et4=intent1.getStringExtra("et4");//xfinal
									String et5=intent1.getStringExtra("et5");//step
									String et6=intent1.getStringExtra("et6");//precision
									String MyMethod=intent1.getStringExtra("MyMethod");
									
									
									
									String f_x_y=et1;
									//Double x0=Double.parseDouble(et2);
									//Double y0=Double.parseDouble(et3);
									//Double xfinal=Double.parseDouble(et4);
									//Double stepsize=Double.parseDouble(et5);
									Integer precision=Integer.parseInt(et6);

									BigDecimal x0=new BigDecimal(et2);
									BigDecimal y0=new BigDecimal(et3);
									BigDecimal xfinal=new BigDecimal(et4);
									BigDecimal stepsize=new BigDecimal(et5);
									
									int numOfIntervals=(xfinal.subtract(x0)).divide(stepsize,new MathContext(4)).intValue();
									//System.out.println("numOfintervals = "+numOfIntervals);
									
									given=given.concat("dy/dx = "+f_x_y+"\nx0 = "+x0+"\ny0 = "+y0+"\nxfinal = "+xfinal+"\nStepsize = "+stepsize+"\nPrecision = "+precision+"\n\nUsing : "+MyMethod+"  method \n\n");
									//EulerMethods e1=new EulerMethods(MyMethod,f_x_y,x0,y0,stepsize,numOfIntervals,precision);//f(x,y)(=dy/dx),x0,y0,stepsize(=h),.,.
									EvaluatorEngine e1=new EvaluatorEngine(MyMethod,f_x_y,x0,y0,stepsize,numOfIntervals,precision);//f(x,y)(=dy/dx),x0,y0,stepsize(=h),.,.
									e1.callMe();
									
									TextView tv1=(TextView)findViewById(R.id.finalAnswer);
									TextView tv2=(TextView)findViewById(R.id.new_output);
									tv1.append("  y("+e1.xArray[numOfIntervals]+") = "+e1.yArray[numOfIntervals]+"\n");
									//tv1.setText("Answer : y("+e1.xArray[numOfIntervals]+") = "+e1.yArray[numOfIntervals]+"\n\n");
									
									tv2.setText(e1.output);
									//tv.setText(e1.xArray[2]+" "+e1.yArray[2]);
									GraphViewData []gvd=new GraphViewData[numOfIntervals+1];
									for(int i=0;i<numOfIntervals+1;i++){
										//tv.append(e1.xArray[i].toString()+" "+e1.yArray[i].toString()+"\n");
										gvd[i] = new GraphViewData(e1.xArray[i],e1.yArray[i]);
									}
									makeGraph(gvd);
									//tv.setText(et1+" "+et2+" "+et3+" "+et4+" "+et5+" "+et6+" "+MyMethod);
				//Dismiss dialog
				SolveDialog.dismiss();
				
				//Add toast(notification)
				Toast.makeText(OutputActivity.this, "Solved", Toast.LENGTH_SHORT).show();
								}
							}
						);
				}
		};
		th.start();
	
	
	}
	public void makeGraph(GraphViewData[] gvd){
	// init example series data
	/*GraphViewSeries exampleSeries = new GraphViewSeries(new GraphViewData[] {
	      new GraphViewData(1, 2.0d)
	      ,new GraphViewData(new BigDecimal(1.4),new BigDecimal(1.4535353))

	      , new GraphViewData(2, 1.5d)
	      , new GraphViewData(3, 2.5d)
	      , new GraphViewData(4, 1.0d)
	});
	*/
	 GraphViewSeries Series2 = new GraphViewSeries(gvd);
	 //GraphViewSeries test=new GraphViewSeries("pt1",new GraphViewSeriesStyle(Color.RED,2) ,new GraphViewData[]{gvd[2],new GraphViewData(gvd[2].getX(),0)});//new GraphViewData(0, gvd[2].getY()),
	GraphView graphView = new LineGraphView(
	      this // context
	      , "Graph" // heading
	);
	graphView.addSeries(Series2); // data
	//graphView.addSeries(test);
	((LineGraphView) graphView).setDrawDataPoints(true);
	((LineGraphView) graphView).setDataPointsRadius(5f);
	graphView.getGraphViewStyle().setGridStyle(GridStyle.BOTH);
	 graphView.setScalable(true);
	 graphView.setScrollable(true);
	 //graphView.setDrawData
	// graphView.getGraphViewStyle().setGridStyle(GridStyle.BOTH);
	 //graphView.getGraphViewStyle().setNumHorizontalLabels(n+1);
	LinearLayout layout = (LinearLayout) findViewById(R.id.graph1);
	layout.addView(graphView);

	}
	
}