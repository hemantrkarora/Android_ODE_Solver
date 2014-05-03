package bitshyd.math.odesolverii;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import bitshyd.math.odesolverii.EvaluatorEngineForSystem;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;
import com.jjoe64.graphview.LineGraphView;
import com.jjoe64.graphview.GraphViewStyle.GridStyle;

public class OutputActivitySystem extends Activity {

	Handler handler,handler1;
	String given="";
	private TextView txtView1,txtView2;

	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.output_activity_system);
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
									txtView1=(TextView)findViewById(R.id.finalAnswerSystem);
									txtView2=(TextView)findViewById(R.id.new_outputSystem);
									
									given=given.concat("\n*****************************\n");
									given=given.concat(txtView1.getText().toString());
									given=given.concat("\n*****************************\n\n");
									String output=given.concat(txtView2.getText().toString());

									LinearLayout graph=(LinearLayout)findViewById(R.id.graph1System);
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
										 
										.makeText(OutputActivitySystem.this, "The contents are saved under folder ODESolver/Results.", Toast.LENGTH_LONG)
										 
										.show();
										 
										}
										 
										catch (Throwable t) {
										 
										Toast
										 
										.makeText(OutputActivitySystem.this, "Exception: "+t.toString(), Toast.LENGTH_LONG)
										 
										.show();
										 
										}

											SaveDialog.dismiss();
				
								}
							}
						);
				}
		};
		th1.start();
		
		Intent intent=getIntent();
		intent.putExtra("notes",1);
		intent.putExtra("counter", 2);
		this.setResult(RESULT_OK,intent);
		finish();
		
	}
	public void onCancel(View view){
		//Intent intent=new Intent(this,MainActivity.class);
		//startActivity(intent);
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
									
									Intent intent1=getIntent();
									String et1=intent1.getStringExtra("et1");
									String et2=intent1.getStringExtra("et2");
									String et3=intent1.getStringExtra("et3");
									String et4=intent1.getStringExtra("et4");
									String et5=intent1.getStringExtra("et5");
									String et6=intent1.getStringExtra("et6");
									String et7=intent1.getStringExtra("et7");
									String et8=intent1.getStringExtra("et8");

									
									//String MyMethod=intent1.getStringExtra("MyMethod");

									
									/*
									String et2="-1";//x0
									String et3="1";//y0
									String et5="0.2";//stepsize
									String et6="5";//precision
									String MyMethod="Runge Kutta order 4";

									//dx/dt=f_t_x_y dy/dt=g_t_x_y 
									//x0,y0,t0
									String f_t_x_y="sin(t)+cos(x)+sin(y)";//"y-t";
									String g_t_x_y="cos(t)+sin(y)";//"x+t";
									String et7="0";//t0
									String et8="1.8";//tfinal
									
									*/
									String f_t_x_y=et1;
									String g_t_x_y=et2;
									
									BigDecimal t0=new BigDecimal(et3);
									BigDecimal x0=new BigDecimal(et4);
									BigDecimal y0=new BigDecimal(et5);
									BigDecimal tfinal=new BigDecimal(et6);
							
									BigDecimal stepsize=new BigDecimal(et7);
									Integer precision=Integer.parseInt(et8);
									
									int numOfIntervals=(tfinal.subtract(t0)).divide(stepsize,new MathContext(4)).intValue();
									String MyMethod="RK for System of eqns";
									given=given.concat("dx/dt = "+f_t_x_y+"\ndy/dt = "+g_t_x_y+"\nt0 = "+t0+"\nx0 = "+x0+"\ny0 = "+y0+"\ntfinal = "+tfinal+"\nStepsize = "+stepsize+"\nPrecision = "+precision+"\n\nUsing : "+MyMethod+"  method \n\n");
									

									EvaluatorEngineForSystem e1=new EvaluatorEngineForSystem(MyMethod,f_t_x_y,g_t_x_y,x0,y0,t0,stepsize,numOfIntervals,precision);
									e1.callMe();
									//d2y/dx2 --- z=dy/dx dz/dx ;f=z-dy/dx g=dz/dx
									
									
									TextView tv1=(TextView)findViewById(R.id.finalAnswerSystem);
									TextView tv2=(TextView)findViewById(R.id.new_outputSystem);
									tv1.append("x( "+e1.tArray[numOfIntervals]+" ) = "+e1.xArray[numOfIntervals]+"\ny( "+e1.tArray[numOfIntervals]+" ) = "+e1.yArray[numOfIntervals]+"\n");
									//tv1.setText("Answer : y("+e1.xArray[numOfIntervals]+") = "+e1.yArray[numOfIntervals]+"\n\n");
									
									tv2.setText(e1.output);
									//tv.setText(e1.xArray[2]+" "+e1.yArray[2]);
									GraphViewData []gvd1=new GraphViewData[numOfIntervals+1];
									GraphViewData []gvd2=new GraphViewData[numOfIntervals+1];
									for(int i=0;i<numOfIntervals+1;i++){
										//tv.append(e1.xArray[i].toString()+" "+e1.yArray[i].toString()+"\n");
										gvd1[i] = new GraphViewData(e1.tArray[i],e1.xArray[i]);
										gvd2[i]= new GraphViewData(e1.tArray[i],e1.yArray[i]);
									}
									makeGraph(gvd1,gvd2);
									//tv.setText(et1+" "+et2+" "+et3+" "+et4+" "+et5+" "+et6+" "+MyMethod);
				//Dismiss dialog
				SolveDialog.dismiss();
				
				//Add toast(notification)
				Toast.makeText(OutputActivitySystem.this, "Solved", Toast.LENGTH_SHORT).show();
								}
							}
						);
				}
		};
		th.start();
	
	
	}
	public void makeGraph(GraphViewData[] gvd1,GraphViewData[] gvd2){
	// init example series data
	/*GraphViewSeries exampleSeries = new GraphViewSeries(new GraphViewData[] {
	      new GraphViewData(1, 2.0d)
	      ,new GraphViewData(new BigDecimal(1.4),new BigDecimal(1.4535353))

	      , new GraphViewData(2, 1.5d)
	      , new GraphViewData(3, 2.5d)
	      , new GraphViewData(4, 1.0d)
	});
	*/
	GraphViewSeries Series1= new GraphViewSeries("x-t",new GraphViewSeriesStyle(Color.BLUE,2),gvd1);
	 GraphViewSeries Series2 = new GraphViewSeries("y-t",new GraphViewSeriesStyle(Color.RED,2),gvd2);
	 //GraphViewSeries test=new GraphViewSeries("pt1",new GraphViewSeriesStyle(Color.RED,2) ,new GraphViewData[]{gvd[2],new GraphViewData(gvd[2].getX(),0)});//new GraphViewData(0, gvd[2].getY()),
	GraphView graphView = new LineGraphView(
	      this // context
	      , "Graph" // heading
	);
	graphView.addSeries(Series1); // data
	graphView.addSeries(Series2);
	//graphView.addSeries(test);
	((LineGraphView) graphView).setDrawDataPoints(true);
	((LineGraphView) graphView).setDataPointsRadius(5f);
	graphView.getGraphViewStyle().setGridStyle(GridStyle.BOTH);
	 graphView.setScalable(true);
	 graphView.setScrollable(true);
	 graphView.setShowLegend(true);

	 //graphView.setDrawData
	// graphView.getGraphViewStyle().setGridStyle(GridStyle.BOTH);
	 //graphView.getGraphViewStyle().setNumHorizontalLabels(n+1);
	LinearLayout layout = (LinearLayout) findViewById(R.id.graph1System);
	layout.addView(graphView);

	}
	
}

