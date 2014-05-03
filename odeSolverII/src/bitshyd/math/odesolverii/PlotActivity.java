package bitshyd.math.odesolverii;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;
import com.jjoe64.graphview.GraphViewStyle.GridStyle;
import com.jjoe64.graphview.LineGraphView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PlotActivity extends Activity {

Handler handler,handler1;
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.plot);
		handler=new Handler();
		handler1=new Handler();

		makePlot();
	}
	public void onSave(View view){
		final ProgressDialog SaveDialog=ProgressDialog.show(this, "Saving", "saving data and graph");
		Thread th1=new Thread(){
				public void run(){
					handler1.post(
							new Runnable(){
								public void run(){
									

									LinearLayout graph=(LinearLayout)findViewById(R.id.plotLayout);
									graph.setDrawingCacheEnabled(true);
									Bitmap bitmap=graph.getDrawingCache();
									
									//Save to file with today's date
									String name=new SimpleDateFormat("yyyyMMdd_hhmmss").format(new Date());
									
									try {
										File filePlot,fplot=null;
										if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) 
									    {  
									     filePlot =new File(android.os.Environment.getExternalStorageDirectory(),"ODEsolver"+File.separator+"Results"+File.separator+"Plots");
									         if(!filePlot.exists())
									        {
									          filePlot.mkdirs();

									         } 
									         
									         fplot=new File(filePlot.getAbsolutePath()+File.separator+ "Plot_"+name+".png");
									    }
										
										//make graph file
										ByteArrayOutputStream bytes = new ByteArrayOutputStream();
										  bitmap.compress(Bitmap.CompressFormat.PNG, 40, bytes);
										  fplot.createNewFile();
										  FileOutputStream fo = new FileOutputStream(fplot);
										  fo.write(bytes.toByteArray()); 
										  fo.close();
										  										Toast
										 
										.makeText(PlotActivity.this, "The contents are saved under folder ODESolver/Results/Plots.", Toast.LENGTH_LONG)
										 
										.show();
										 
										}
										 
										catch (Throwable t) {
										 
										Toast
										 
										.makeText(PlotActivity.this, "Exception: "+t.toString(), Toast.LENGTH_LONG)
										 
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
	private void makePlot(){
		//Progress Bar
		final ProgressDialog PlotDialog=ProgressDialog.show(this, "Plotting", "Loading Output");
		Thread myth=new Thread(){
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
									String MyMethod=intent1.getStringExtra("MyMethod");

									String f_x_y=et1;
									Integer precision=Integer.parseInt(et6);
									
									BigDecimal x0=new BigDecimal(et2);
									BigDecimal y0=new BigDecimal(et3);
									BigDecimal xfinal=new BigDecimal(et4);
									BigDecimal stepsize=new BigDecimal(et5);
									
									int numOfIntervals=(xfinal.subtract(x0)).divide(stepsize,new MathContext(4)).intValue();
									EvaluatorEngine e1=new EvaluatorEngine("Simple Euler",f_x_y,x0,y0,stepsize,numOfIntervals,precision);//f(x,y)(=dy/dx),x0,y0,stepsize(=h),.,.
									
									EvaluatorEngine e2=new EvaluatorEngine("Modified Euler",f_x_y,x0,y0,stepsize,numOfIntervals,precision);
									EvaluatorEngine e3=new EvaluatorEngine("Improved Euler",f_x_y,x0,y0,stepsize,numOfIntervals,precision);
									EvaluatorEngine e4=new EvaluatorEngine("Runge Kutta order 4",f_x_y,x0,y0,stepsize,numOfIntervals,precision);
									e1.callMe();
									e2.callMe();
									e3.callMe();
									e4.callMe();
									
									
									
									//TextView tv=(TextView)findViewById(R.id.new_output);
									//tv.setText(e1.output);
									//tv.setText(e1.xArray[2]+" "+e1.yArray[2]);
									GraphViewData []gvd_simple=new GraphViewData[numOfIntervals+1];
									GraphViewData []gvd_modified=new GraphViewData[numOfIntervals+1];
									GraphViewData []gvd_improved=new GraphViewData[numOfIntervals+1];
									GraphViewData []gvd_rk=new GraphViewData[numOfIntervals+1];
									for(int i=0;i<numOfIntervals+1;i++){
										//tv.append(e1.xArray[i].toString()+" "+e1.yArray[i].toString()+"\n");
										gvd_simple[i] = new GraphViewData(e1.xArray[i],e1.yArray[i]);
										gvd_modified[i] = new GraphViewData(e2.xArray[i],e2.yArray[i]);
										gvd_improved[i] = new GraphViewData(e3.xArray[i],e3.yArray[i]);
										gvd_rk[i] = new GraphViewData(e4.xArray[i],e4.yArray[i]);
									}
									makeGraph(gvd_simple,gvd_modified,gvd_improved,gvd_rk);
									//tv.setText(et1+" "+et2+" "+et3+" "+et4+" "+et5+" "+et6+" "+MyMethod);
				//Dismiss dialog
				PlotDialog.dismiss();
				
				//Add toast(notification)
				Toast.makeText(PlotActivity.this, "Plotted", Toast.LENGTH_SHORT).show();
								}
							}
						);
				}
		};
		myth.start();
	
	
	}
	public void makeGraph(GraphViewData[] gvd_simple,GraphViewData[] gvd_modified,GraphViewData[] gvd_improved,GraphViewData[] gvd_rk){
		// init example series data
		GraphViewData []data=new GraphViewData[] {
		      new GraphViewData(1, 2.0d)
		      ,new GraphViewData(new BigDecimal(1.4),new BigDecimal(1.4535353))

		      , new GraphViewData(2, 1.5d)
		      , new GraphViewData(3, 2.5d)
		      , new GraphViewData(4, 1.0d)
		};
		GraphViewSeries exampleSeries = new GraphViewSeries("First",new GraphViewSeriesStyle(Color.rgb(200, 50, 00),1),data);
		
		 GraphViewSeries Series_simple = new GraphViewSeries("Simple Euler",new GraphViewSeriesStyle(Color.GREEN,2),gvd_simple);
		 GraphViewSeries Series_modified = new GraphViewSeries("Modified Euler",new GraphViewSeriesStyle(Color.RED,2),gvd_modified);
		 GraphViewSeries Series_improved = new GraphViewSeries("Improved Euler",new GraphViewSeriesStyle(Color.BLUE,2),gvd_improved);
		 GraphViewSeries Series_rk = new GraphViewSeries("RK 4th",new GraphViewSeriesStyle(Color.MAGENTA,2),gvd_rk);
		 //GraphViewSeries seriesSin = new GraphViewSeries("Sinus curve", Color.rgb(200, 50, 00), data);
		GraphView graphView = new LineGraphView(
		      this // context
		      , "Graph" // heading
		);
		graphView.addSeries(Series_simple); // data
		graphView.addSeries(Series_modified);
		graphView.addSeries(Series_improved);
		graphView.addSeries(Series_rk);
		//graphView.addSeries(exampleSeries); // data
		((LineGraphView) graphView).setDrawDataPoints(true);
		((LineGraphView) graphView).setDataPointsRadius(5f);
		graphView.getGraphViewStyle().setGridStyle(GridStyle.BOTH);
		graphView.setScalable(true);
		 graphView.setScrollable(true);
		 graphView.setShowLegend(true);
		LinearLayout layout = (LinearLayout) findViewById(R.id.plotLayout);
		// RelativeLayout layout = (RelativeLayout) findViewById(R.id.plotLayout);
		 layout.addView(graphView);

		}
		
}
