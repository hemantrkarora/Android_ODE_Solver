package bitshyd.math.odesolverii;

import java.math.BigDecimal;
import java.math.MathContext;

public class EvaluatorEngineForSystem {
	//Instance Variables
	String f_t_x_y,g_t_x_y,MyMethod;
	public StringBuffer output;
	BigDecimal x0,y0,t0,stepsize,xnew,ynew;
	int numOfIntervals,precision;
	BigDecimal tArray[];
	BigDecimal xArray[];
	BigDecimal yArray[];
	int i=0;
	
	//Constructor
	public EvaluatorEngineForSystem(String MyMethod, String f_t_x_y,
			String g_t_x_y, BigDecimal x0, BigDecimal y0,
			BigDecimal t0, BigDecimal stepsize, int numOfIntervals,
			Integer precision) {
		// TODO Auto-generated constructor stub
		this.MyMethod=MyMethod;
		this.f_t_x_y=f_t_x_y;
		this.g_t_x_y=g_t_x_y;
		this.t0=t0;
		this.x0=x0;//BigDecimal.valueOf(x0);
		this.y0=y0;//BigDecimal.valueOf(y0);
		this.stepsize=stepsize;//BigDecimal.valueOf(stepsize);
		this.numOfIntervals=numOfIntervals;
		this.precision=precision;
		this.output=new StringBuffer("Output : \n");
		
		this.tArray=new BigDecimal[numOfIntervals+1];//this.computeEuler("Modified");
		this.xArray=new BigDecimal[numOfIntervals+1];
		this.yArray=new BigDecimal[numOfIntervals+1];

	}





//Method
void method(String s){
	if(s.equals("RK for System of eqns")){
		BigDecimal a[]=this.RK_For_System();
		this.xnew=a[0];
		this.ynew=a[1];
		//this.ynew=this.RK_For_System();
		
	}
}
//Driver method
public void computeEuler(String s){
	while((this.numOfIntervals--)>0){
		this.method(s);
		this.tArray[i]=this.t0.add(this.stepsize);
		this.xArray[i]=this.xnew;
		this.yArray[i]=this.ynew;
		i++;
		    this.output.append("x("+this.t0.add(this.stepsize)+") = "+this.xnew+"\n");
		    this.output.append("y("+this.t0.add(this.stepsize)+") = "+this.ynew+"\n\n");
		    this.t0=this.t0.add(this.stepsize);
			this.x0=this.xnew;
			this.y0=this.ynew;
	}
}
/*
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
*/
BigDecimal[] RK_For_System(){
	BigDecimal two=new BigDecimal(2);
	BigDecimal six=new BigDecimal(6);
	MathContext mc=new MathContext(this.precision);
	
	BigDecimal K1=this.stepsize.multiply(computeFunction(f_t_x_y,t0,x0,y0));
	BigDecimal L1=this.stepsize.multiply(computeFunction(g_t_x_y, t0, x0, y0));
	
	BigDecimal K2=this.stepsize.multiply(computeFunction(f_t_x_y, t0.add(stepsize.divide(two,mc)), x0.add(K1.divide(two,mc)), y0.add(L1.divide(two,mc))));
	BigDecimal L2=this.stepsize.multiply(computeFunction(g_t_x_y, t0.add(stepsize.divide(two,mc)), x0.add(K1.divide(two,mc)), y0.add(L1.divide(two,mc))));
	
	BigDecimal K3=this.stepsize.multiply(computeFunction(f_t_x_y,t0.add(stepsize.divide(two,mc)),x0.add(K2.divide(two,mc)),y0.add(L2.divide(two,mc))));
	BigDecimal L3=this.stepsize.multiply(computeFunction(g_t_x_y,t0.add(stepsize.divide(two,mc)),x0.add(K2.divide(two,mc)),y0.add(L2.divide(two,mc))));
	
	BigDecimal K4=this.stepsize.multiply(computeFunction(f_t_x_y,t0.add(stepsize),x0.add(K3), y0.add(L3)));
	BigDecimal L4=this.stepsize.multiply(computeFunction(g_t_x_y, t0.add(stepsize),x0.add(K3),y0.add(L3)));
	
	BigDecimal x_= x0.add((K1.add(K2.multiply(two)).add(K3.multiply(two)).add(K4)).divide(six,mc));
    BigDecimal y_=y0.add((L1.add(L2.multiply(two)).add(L3.multiply(two)).add(L4)).divide(six,mc));
	BigDecimal a[]={x_,y_};
	return a;
}
//Value of a function at particular point
BigDecimal computeFunction(String f_t_x_y,BigDecimal t0,BigDecimal x0,BigDecimal y0){
return new BigDecimal(Eval(f_t_x_y,t0.toString(),x0.toString(),y0.toString()));
}
public String Eval(String f_t_x_y,String str_t0,String str_x0,String str_y0){
	return new BigDecimal(new MathEval().evaluate(f_t_x_y.replaceAll("t", str_t0).replaceAll("x", str_x0).replaceAll("y",str_y0))).toString();
	
}

public void callMe(){

	//MathEval me =new MathEval();
	this.tArray[i]=this.t0;
	this.xArray[i]=this.x0;
	this.yArray[i]=this.y0;
	i++;
	this.output.append("x("+this.t0+") = "+this.x0+"\n");
	this.output.append("y("+this.t0+") = "+this.y0+"\n\n");
	this.computeEuler(this.MyMethod);
	System.out.println(this.output);
}

}
