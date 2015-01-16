
public class Node {

	    // private member variables.
	    private double weight1;
	    private double weight2;
	    private double threshold;
	        
	    // public functions, mutator and accessory functions
	    // one constructor
	    public Node(double w, double w2, double th) {
	    	weight1 = w;
	    	weight2 = w2;
	    	threshold = th;
	    }
	    // constructor function with no arguments
	    public Node() {
	    	weight1 = 0;
	    	weight2 = 0;
	    	threshold = 0;
	    }

	    // set the weight
	    public void setweight1(double newValue) {
	    	weight1 = newValue;
	    }
	    
	    // set the weight	        
	    public void setweight2(double nw) {
	    	weight2 = nw;
	    }
	    // set the threshold 
	    public void setthreshold(double th) {
	    	threshold = th;
	    }
	    // get the weight
	    public double getweight1() {
	        return weight1;
	    }
	    // get the weight
	    public double getweight2() {
	        return weight2;
	    }
	    // get the threshold
	    public double getthreshold() {
	        return threshold;
	    }
}
