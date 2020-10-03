import java.util.ArrayList;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
public class PointSET {
	private SET <Point2D> points ;
	public PointSET() {
		points  = new SET <Point2D> ();
	}                             
	public boolean isEmpty() {
		return points.isEmpty();
	}                      // is the set empty? 
	public int size() {
		return points.size();
	}                        // number of points in the set 
	public void insert(Point2D p) {
		if( p == null ) throw new IllegalArgumentException ();
		points.add(p);
	}             // add the point to the set (if it is not already in the set)
	public boolean contains(Point2D p) {
		if( p == null ) throw new IllegalArgumentException ();
		return points.contains(p);
	}            // does the set contain point p? 
	public void draw() {
		for (Point2D i : points) {
			i.draw();
		}
	}                         
	
	public Iterable<Point2D> range(RectHV rect){
		if( rect == null ) throw new IllegalArgumentException ();
		ArrayList <Point2D> range = new ArrayList <Point2D>();
		for (Point2D i : points) {
			if (i.x() >= rect.xmin() && i.x() <= rect.xmax() && i.y() >= rect.ymin() && i.y() <= rect.ymax()) {
				range.add(i);
			}} 
			return ( (Iterable<Point2D>) range);
		
}             // all points that are inside the rectangle (or on the boundary) 
	
	public Point2D nearest(Point2D p) {
		if( p == null ) throw new IllegalArgumentException ();
		if (points.size() == 0) return null;
		Point2D nearestPoint = null; 
		double nearestDistance = Double.POSITIVE_INFINITY;
		double distance; 
		for (Point2D i : points) {
				distance = Math.sqrt(Math.pow(i.x()-p.x(),2) + Math.pow(i.y()-p.y(),2));
				if (distance == 0) {
					nearestDistance = distance;
					nearestPoint = i;
					break;
				}
				if (distance < nearestDistance) {
					nearestDistance = distance;
					nearestPoint = i;
				
				}
		}
		return nearestPoint ;
	}
	// a nearest neighbor in the set to point p; null if the set is empty 

	public static void main(String[] args) {}                  // unit testing of the methods (optional) 
}