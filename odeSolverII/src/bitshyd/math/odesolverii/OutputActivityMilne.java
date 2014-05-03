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
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class OutputActivityMilne extends Activity{
	Handler handler,handler1;
	String given="";
	private TextView txtView1,txtView2;


		protected void onCreate(Bundle savedInstanceState){
			super.onCreate(savedInstanceState);
			setContentView(R.layout.output_activity_milne);
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
										txtView1=(TextView)findViewById(R.id.finalAnswerMilne);
										txtView2=(TextView)findViewById(R.id.new_outputMilne);
										
										given=given.concat("\n*****************************\n");
										given=given.concat(txtView1.getText().toString());
										given=given.concat("\n*****************************\n\n");
										String output=given.concat(txtView2.getText().toString());

										
										//Save to file with today's date
										String name=new SimpleDateFormat("yyyyMMdd_hhmmss").format(new Date());
										
										try {
											File fileData,fdata = null;
											if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) 
										    {  
										         fileData =new File(android.os.Environment.getExternalStorageDirectory(),"ODEsolver"+File.separator+"Results"+File.separator+"Data");
										         if(!fileData.exists())
										        {
										          fileData.mkdirs();

										         } 
										        										         
										         fdata = new File(fileData.getAbsolutePath()+File.separator+ "Data_"+name+".txt");
										    }
										    
											
											  
										//	make data file
											 
											FileOutputStream ostream_data=new FileOutputStream(fdata);
											PrintStream p=new PrintStream(ostream_data);
											p.print(output);
											p.close();
											

											
											
											 
											Toast
											 
											.makeText(OutputActivityMilne.this, "The contents are saved under folder ODESolver/Results.", Toast.LENGTH_LONG)
											 
											.show();
											 
											}
											 
											catch (Throwable t) {
											 
											Toast
											 
											.makeText(OutputActivityMilne.this, "Exception: "+t.toString(), Toast.LENGTH_LONG)
											 
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
										String et1=intent1.getStringExtra("et1");//dy/dx
										String et2=intent1.getStringExtra("et2");//x0
										String et3=intent1.getStringExtra("et3");//y0
										String et4=intent1.getStringExtra("et4");//xfinal
										String et5=intent1.getStringExtra("et5");//step
										String et6=intent1.getStringExtra("et6");//precision
										String StarterMethod=intent1.getStringExtra("MyMethod");
										String et7=intent1.getStringExtra("et7");//maxError
										
										
										String f_x_y=et1;
										Integer precision=Integer.parseInt(et6);

										BigDecimal x0=new BigDecimal(et2);
										BigDecimal y0=new BigDecimal(et3);
										BigDecimal xfinal=new BigDecimal(et4);
										BigDecimal stepsize=new BigDecimal(et5);
										BigDecimal maxError=new BigDecimal(et7);
										/*
										int numOfIntervals=(xfinal.subtract(x0)).divide(stepsize,new MathContext(4)).intValue();
										
										
										String f_x_y="x^2+y^2";//"30-5*y";//"1/(x^2)-y/x";
										String StarterMethod="Runge Kutta order 4";
										//String MyMethod="MilneSimpson";
										
										String str_x0="0";//1
										String str_y0="1";//1
										
										String str_stepsize="0.2";//.1
										//String str_xfinal="0.4";//1.3
										
										String str_startPoint=str_x0;
										String str_endPoint="1";
										
										
										BigDecimal x0=new BigDecimal(str_x0);
										BigDecimal y0=new BigDecimal(str_y0);
										BigDecimal stepsize=new BigDecimal(str_stepsize);
										BigDecimal xfinal=(new BigDecimal(str_startPoint)).add(stepsize.multiply(BigDecimal.valueOf(3)));
										int precision=4;
										double maxError=0.001;
										*/
								
										int numOfIntervals=(xfinal.subtract(x0)).divide(stepsize).intValue();//3
										
										System.out.println("numOfintervals = "+numOfIntervals);
										String MyMethod="Milne-Simpson Predictor-Corrector";
										
										given=given.concat("dy/dx = "+f_x_y+"\nx0 = "+x0+"\ny0 = "+y0+"\nxfinal = "+xfinal+"\nStepsize = "+stepsize+"\nPrecision = "+precision+"\n\nUsing :  "+MyMethod+" method \nwith Starter-method as : "+StarterMethod+"  method \n\n");
										//EulerMethods e1=new EulerMethods(MyMethod,f_x_y,x0,y0,stepsize,numOfIntervals,precision);//f(x,y)(=dy/dx),x0,y0,stepsize(=h),.,.
										EvaluatorEngine e1=new EvaluatorEngine(StarterMethod,f_x_y,x0,y0,stepsize,3,precision);//f(x,y)(=dy/dx),x0,y0,stepsize(=h),.,.
										e1.callMe();
										e1.MilneSimpson(x0,xfinal,maxError);

										
										TextView tv1=(TextView)findViewById(R.id.finalAnswerMilne);
										TextView tv2=(TextView)findViewById(R.id.new_outputMilne);
										tv1.append("  y("+e1.xMilne[numOfIntervals]+") = "+e1.yMilne[numOfIntervals]+"\n");
										//tv1.setText("Answer : y("+e1.xArray[numOfIntervals]+") = "+e1.yArray[numOfIntervals]+"\n\n");
										
										tv2.setText(e1.outputMilne);
										//tv.setText(e1.xArray[2]+" "+e1.yArray[2]);
					//Dismiss dialog
					SolveDialog.dismiss();
					
					//Add toast(notification)
					Toast.makeText(OutputActivityMilne.this, "Solved", Toast.LENGTH_SHORT).show();
									}
								}
							);
					}
			};
			th.start();
		
		
		}

}
