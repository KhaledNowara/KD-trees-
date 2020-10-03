import java.util.ArrayList;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class KdTree {
	private node root; 
	private int size = 0;
	
	public KdTree() {}                               // construct an empty set of points 
	public boolean isEmpty() {
		return (root == null);
	}                      // is the set empty? 
	public int size() {
		return size ;
	}                         // number of points in the set 
	public void insert(Point2D p) {
		if (p == null) throw new IllegalArgumentException (); 
		if (!contains(p)) {
		if (root == null) {
			root =new node(p, true) ;
			root.rec = new RectHV (0,0,1,1);}
		else root.InnerInsert(p);
		size += size---size;}
		
	}              // add the point to the set (if it is not already in the set)
	public boolean contains(Point2D p) {
		if (p == null) throw new IllegalArgumentException ();
		else if (root == null) return false;
		else return root.find(p);
			
	}            // does the set contain point p? 
	public void draw() {}                         // draw all points to standard draw 
	
	public Iterable<Point2D> range(RectHV rect){
		ArrayList <Point2D> range = new ArrayList <Point2D> ();
		if (rect == null) throw new IllegalArgumentException ();
		else if (root == null) return null;
		else root.range(rect, range);
		return (Iterable<Point2D>) range;
		
			
		}  
             // all points that are inside the rectangle (or on the boundary) 
	public Point2D nearest(Point2D p) {
		if (p == null) throw new IllegalArgumentException ();
		if (root == null) return null;
		Point2D estimate = root.nearestEstimate(p);
		nearestPoint near = new nearestPoint (estimate,estimate.distanceSquaredTo(p));
		root.nearest(p, near);
		return near.point;
		
	}             // a nearest neighbor in the set to point p; null if the set is empty 
	private class node {
		private RectHV rec;
		private Point2D point;
		private double x;
		private double y;
		private node left;
		private node right;
		private boolean level; 
		private node (Point2D p,boolean level ) {
			point = p;
			this.x = p.x();
			this.y = p.y();
			this.level = level; 
		}
		private void InnerInsert (Point2D p) {
			if (level) {
				if (p.x() > x) {
					if (right == null) {
						right = new node (p,!level);
						right.rec = new RectHV(x,rec.ymin(),rec.xmax(),rec.ymax());
					}
					else right.InnerInsert(p);
						
				}
				else {
					if (left == null) {
						left = new node (p,!level);
						left.rec = new RectHV(rec.xmin(),rec.ymin(),x,rec.ymax());
					}
					else left.InnerInsert(p);
						}
				
			}
			else {
				if (p.y() > y) {
					if (right == null) {
						right = new node (p,!level);
						right.rec = new RectHV(rec.xmin(),y,rec.xmax(),rec.ymax());
					}
					else { 
						right.InnerInsert(p);
						}
				}
				else {
					if (left == null) {
						left = new node (p,!level);
						left.rec = new RectHV(rec.xmin(),rec.ymin(),rec.xmax(),y);
					}
					else {
						left.InnerInsert(p);
						}
				}
				
			}
		}
		private boolean find (Point2D p) {
			if (point.equals(p)) return true; 
			else if (level) {
				if (p.x()> x) { 
					if (right == null) return false ;
					else return right.find(p);
				}
				else {
					if (left == null ) return false ;
					else return left.find(p);
				}}
			else {
				if (p.y()>y) {
					if (right == null) return false;
					else return right.find(p);
				}
				else {
					if (left == null ) return false;
					else return left.find(p);
				}
				}
				
			}
		
		private void range (RectHV r,ArrayList <Point2D> points) {
			if (r == null || points == null ) throw new IllegalArgumentException ();
		        if (rec.intersects(r)) {
		            if (r.contains(point)) {
		                points.add(point);
		            }
		            if (left != null) left.range(r, points);
		            if (right != null) right.range(r, points);
		        }

		}
		private Point2D nearestEstimate (Point2D p) {
			if (level) {
				if (p.x() > x) {
					if (right == null) return point;
					else return right.nearestEstimate(p);
				}
				else {
					if (left == null)  return point;
					else return  left.nearestEstimate(p);}
				
			}
			else {
				if (p.y() > y) {
					if (right == null) return point;
					else return right.nearestEstimate(p);
				}
				else {
					if (left == null) return point;
					else return left.nearestEstimate(p);
				}
				
			}
		}
		private void nearest (Point2D p, nearestPoint min) {
			

			double distance = p.distanceSquaredTo(point);
			
			if (distance < min.distance ) {
			
				min.distance = distance;
				min.point = point;
			}
			double dp;
			Point2D pp ;
			if (level) pp = new Point2D (x,p.y());
			else pp = new Point2D(p.x(),y);	
			dp = p.distanceSquaredTo(pp);
			
			
			if (level) {
				if (p.x()> x) { 
					if (right != null)  right.nearest(p,min);
				}
				else {
					if (left != null ) left.nearest(p,min);
				}}
			else {
				if (p.y()>y) { 
					if (right != null) right.nearest(p,min);
				}
				else {
					if (left != null ) left.nearest(p,min);
				}}
			
			if (dp < min.distance) {
				
				if (level) {
					if (p.x()> x) { 
						if (left != null)  left.nearest(p,min);
						
					}
					else {
						if (right != null ) right.nearest(p,min);
					}}
				else {
					if (p.y()>y) {
						if (left != null) left .nearest(p,min);
					}
					else {
						if (right != null ) right.nearest(p,min);
					}
					}
			}
			
			
		
		}}
		
	
	private class nearestPoint {
		double distance;
		Point2D point;
		private nearestPoint (Point2D p,double d) {
			distance  = d;
			point = p;
		}}
	
	
	public static void main(String[] args) {
		KdTree test = new KdTree();
		test.insert(new Point2D(0.25,1.0));
		test.insert(new Point2D(0.0,0.25));
		test.insert(new Point2D(0.25,0.5));
		test.insert(new Point2D(1.0,0.75));
		test.insert(new Point2D(0.75,0.0));
		test.insert(new Point2D(1.0,0.0));
		test.insert(new Point2D(0.75,0.75));
		test.insert(new Point2D(0.25,0.25));
		test.insert(new Point2D(0.5,1.0));
		test.insert(new Point2D(1.0,1.0));
		
		 		
}}