package bitshyd.math.odesolverii;
import java.math.BigDecimal;

import com.jjoe64.graphview.GraphViewDataInterface;
public class GraphViewData implements GraphViewDataInterface{
private double x,y;
GraphViewData(double x,double y){
	this.x=x;
	this.y=y;
}
GraphViewData(BigDecimal x,BigDecimal y){
	this.x=x.doubleValue();
	this.y=y.doubleValue();
}
	public double getX() {
		// TODO Auto-generated method stub
		return this.x;
	}

	public double getY() {
		// TODO Auto-generated method stub
		return this.y;
	}

}
