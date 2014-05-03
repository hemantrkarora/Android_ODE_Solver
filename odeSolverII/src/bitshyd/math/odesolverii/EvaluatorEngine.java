package bitshyd.math.odesolverii;

import java.math.*;//BigDecimal and MathContext
import java.util.ArrayList;
import bitshyd.math.odesolverii.MathEval;

//import bitshyd.math.odesolver.ScientificCalculator;

public class EvaluatorEngine {
	//Instance Variables
			String f_x_y,MyMethod;
			public StringBuffer output;
			BigDecimal x0,y0,stepsize,ynew;
			int numOfIntervals,precision;
			BigDecimal xArray[];
			BigDecimal yArray[];
			
			BigDecimal[] xMilne;
			BigDecimal[] yMilne;
			public StringBuffer outputMilne;

			
			int i=0;
		//Constructor
			public EvaluatorEngine(String MyMethod,String f_x_y,BigDecimal x0,BigDecimal y0,BigDecimal stepsize,int numOfIntervals,int precision){
				this.MyMethod=MyMethod;
				this.f_x_y=f_x_y;
				this.x0=x0;//BigDecimal.valueOf(x0);
				this.y0=y0;//BigDecimal.valueOf(y0);
				this.stepsize=stepsize;//BigDecimal.valueOf(stepsize);
				this.numOfIntervals=numOfIntervals;
				this.precision=precision;
				this.output=new StringBuffer("Output : \n");
				
				this.outputMilne=new StringBuffer("Output : \n");

				//this.computeEuler("Modified");
				this.xArray=new BigDecimal[numOfIntervals+1];
				this.yArray=new BigDecimal[numOfIntervals+1];
			}
		//Method
			void method(String s){
				if(s.equals("Simple Euler")){
					this.ynew=this.SimpleEuler();
				}
				else if (s.equals("Modified Euler")){
					this.ynew=this.ImprovedEuler();
				}
				else if (s.equals("Improved Euler")){
					this.ynew=this.ModifiedEuler();
				}
				else if (s.equals("Runge Kutta order 4")){
					this.ynew=this.RungeKutta();
				}
			}
		//Driver method
			public void computeEuler(String s){
				while((this.numOfIntervals--)>0){
					this.method(s);
					this.xArray[i]=this.x0.add(this.stepsize);
					this.yArray[i]=this.ynew;
					i++;
					    this.output.append("y("+this.x0.add(this.stepsize)+") = "+this.ynew);
					    this.output.append("\n");
						this.x0=this.x0.add(this.stepsize);
						this.y0=this.ynew;
				}
			}
		//Simple Euler method
			BigDecimal SimpleEuler(){
				return this.y0.add(this.stepsize.multiply(computeFunction(this.f_x_y,this.x0,this.y0))).round(new MathContext(precision));	
				//return y(x0+h)=y0+h*f(x0,y0) where dy/dx=f(x,y) , y0=y(x0) , h=stepsize
			}
		//Improved Euler method
			BigDecimal ImprovedEuler(){
				return y0.add((stepsize.multiply(computeFunction(f_x_y,x0,y0).add(computeFunction(f_x_y,x0.add(stepsize),y0.add(stepsize.multiply(computeFunction(f_x_y,x0,y0))))))).divide(BigDecimal.valueOf(2)).round(new MathContext(precision)));
			}
		//Modified Euler method
			BigDecimal ModifiedEuler(){
				return y0.add(stepsize.
						multiply(computeFunction(f_x_y,x0.add(stepsize.divide(BigDecimal.valueOf(2))),
								y0.add(computeFunction(f_x_y,x0,y0).multiply(stepsize.divide(BigDecimal.valueOf(2))))))).round(new MathContext(precision));
			}
			
		//Runge Kutta Order 4
			BigDecimal RungeKutta(){
				BigDecimal two=new BigDecimal(2);
				MathContext mc=new MathContext(this.precision);
				BigDecimal K1=this.stepsize.multiply(computeFunction(f_x_y,x0,y0));
				BigDecimal K2=this.stepsize.multiply(computeFunction(f_x_y,x0.add(stepsize.divide(two, mc)),y0.add(K1.divide(two,mc))));
				BigDecimal K3=this.stepsize.multiply(computeFunction(f_x_y,x0.add(stepsize.divide(two,mc)),y0.add(K2.divide(two,mc))));
				BigDecimal K4=this.stepsize.multiply(computeFunction(f_x_y,x0.add(this.stepsize),y0.add(K3)));
				
				return y0.add((K1.add(K2.multiply(two)).add(K3.multiply(two)).add(K4)).divide(new BigDecimal(6),mc));
			}
			
			
			//Milne Simpson method
			void MilneSimpson(BigDecimal startPoint,BigDecimal endPoint,BigDecimal maxError){
				
				int i=0;

				BigDecimal r=(endPoint.subtract(startPoint)).divide(this.stepsize,new MathContext(this.precision));
				System.out.println(startPoint+" "+endPoint+" "+this.stepsize+" "+r);
				String s=r.toString();
				//BigInteger rr=new BigInteger(s);
				Integer ri=Integer.parseInt(s);
				this.xMilne=new BigDecimal[ri+1];
				this.yMilne=new BigDecimal[ri+1];
				
				//max index of xArray and yArray is=numOfIntervals
				//hence m=numOfIntervals for milne simpson
				int m=3;//=this.numOfIntervals;
				System.out.println("m = "+m);
				int count;
				//predictor ; predict Y_(m+1) as Yp
				BigDecimal y_m3=this.yArray[m-3],y_m2=this.yArray[m-2],y_m1=this.yArray[m-1],y_m= this.yArray[m];
				BigDecimal x_m2=this.xArray[m-2],x_m1=this.xArray[m-1],x_m=this.xArray[m];
				BigDecimal k=this.xArray[m].add(this.stepsize);
				BigDecimal F0=y_m3;
				BigDecimal F1=computeFunction(f_x_y, x_m2, y_m2);
				BigDecimal F2=computeFunction(f_x_y, x_m1, y_m1);
				BigDecimal F3=computeFunction(f_x_y, x_m,y_m);
				BigDecimal F4=y_m1;

				BigDecimal y_m_plus_1;
				
				for(i=0;i<=m;i++){
				this.xMilne[i]=xArray[i];
				this.yMilne[i]=yArray[i];
				}
				
			
				while(k.compareTo(endPoint)<=0){
					y_m_plus_1=computeMilne(F0,F1,F2,F3,F4,k,maxError);
					
					y_m3=y_m2;
					y_m2=y_m1;
					y_m1=y_m;
					y_m=y_m_plus_1;
					
					x_m2=x_m1;
					x_m1=x_m;
					x_m=k;
					
					count=i++;
					this.xMilne[count]=k;
					this.yMilne[count]=y_m_plus_1;
					
					
					
					k=x_m.add(this.stepsize);
					
					
					F0=y_m3;
					F1=computeFunction(f_x_y, x_m2, y_m2);
					F2=computeFunction(f_x_y, x_m1, y_m1);
					F3=computeFunction(f_x_y, x_m,y_m);
					F4=y_m1;
					
					
				}
			for(count=0;count<=ri;count++){
				this.outputMilne.append("y("+this.xMilne[count]+") = "+this.yMilne[count]+"\n");
				//System.out.println("\n"+xMilne[count]+"    "+yMilne[count]);
			}
			 
			}

			public BigDecimal computeMilne(BigDecimal F0,BigDecimal F1,BigDecimal F2,BigDecimal F3,BigDecimal F4,BigDecimal k,BigDecimal maxError){
				BigDecimal two=BigDecimal.valueOf(2);
				BigDecimal three=BigDecimal.valueOf(3);
				BigDecimal four=BigDecimal.valueOf(4);
				MathContext mc=new MathContext(this.precision);
				
				
				BigDecimal Yp=F0.add(this.stepsize.multiply(two.multiply(F1).subtract(F2).add(two.multiply(F3))).multiply(four).divide(three,mc));
				
				//System.out.println("For y("+k+") : ");
				//System.out.println("Predicted Value : "+Yp);
				
				//Corrector ; Yc is corrected value
				BigDecimal Yc_old = Yp;
				BigDecimal F5= computeFunction(f_x_y, k, Yc_old);
				
				BigDecimal constantVal=F4.add(this.stepsize.multiply((F2).add(four.multiply(F3))).divide(three,mc));
				BigDecimal Yc_new=constantVal.add((F5).multiply(this.stepsize).divide(three,mc));
				
				//check for convergence
				BigDecimal error=Yc_old.subtract(Yc_new).divide(Yc_old,mc).multiply(BigDecimal.valueOf(100));
				//System.out.println("\nCorrected value in iteration "+(++i)+" =  "+Yc_new+"\t(Error : "+error+" %)");
				
				
				while(error.compareTo(maxError)>0){
					error=Yc_old.subtract(Yc_new).divide(Yc_old,mc).multiply(BigDecimal.valueOf(100));
					Yc_old=Yc_new;
					Yc_new=constantVal.add(computeFunction(f_x_y,k, Yc_old).multiply(this.stepsize).divide(three,mc));
					//System.out.println("\n"+Yc_new);
					
					//System.out.println("\nCorrected value in iteration "+(++i)+" =  "+Yc_new+"\t(Error : "+error+" %)");
					
				}
				System.out.println("y("+k+") =  "+Yc_new+"\t(Error : "+error+" %)");
				return Yc_new;
				
			}

			
			
			
			
			
			
		//Value of a function at particular point
			BigDecimal computeFunction(String f_x_y,BigDecimal x0,BigDecimal y0){
		return new BigDecimal(Eval(f_x_y,x0.toString(),y0.toString()));
			}
			public String Eval(String f_x_y,String str_x0,String str_y0){
				return new BigDecimal(new MathEval().evaluate(f_x_y.replaceAll("x", str_x0).replaceAll("y",str_y0))).toString();
				
			}

		/*	public static String EvalEngine(String f_x_y,String str_x0,String str_y0){
				ArrayList<String> asPostfix ;
				String parseMe=f_x_y.replaceAll("x",str_x0).replaceAll("y", str_y0) ;
				asPostfix =  rp.infixToPostfix(parseMe);
				String asPostfix_str = asPostfix.toString().replaceAll(",", " " ) ;
				asPostfix_str = asPostfix_str.substring(1, asPostfix_str.length()-1 ) ;
				rp.parseRPN( asPostfix_str ) ;

					return Double.valueOf(rp.getResult()).toString();
				
			}
*/
	public void callMe(){

				//MathEval me =new MathEval();
				this.xArray[i]=this.x0;
				this.yArray[i]=this.y0;
				i++;
				this.output.append("y("+this.x0+") = "+this.y0+"\n");
				this.computeEuler(this.MyMethod);
	}

}
