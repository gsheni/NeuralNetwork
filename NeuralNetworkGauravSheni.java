//Gaurav Sheni
//November 25, 2014

import java.util.Random; // for random generator
import java.util.Scanner; // for debugging and getting input
/*None of the optimizations helped. 
 * The only thing that helped was changing the range of the weights, the range of the thresholds and the learning rate.
 * Not even the hyperbolic tangent function helped. I changed the a and b values for that for a while, no value was optimal and in some situations, it made it worse.
 * I also tried to randomize the inputs. The error would decrease for a bit and then stop decreasing. It would jump back and forth.
 */


public class NeuralNetworkGauravSheni {
	public static void main(String[] args){
		
		boolean PRINT = false;
			
		//Create an object of type trainer from Dr.Burg's notes
		TD[] trainer = new TD[4];
		//Initalize the trainer object 
		for (int i=0;i<trainer.length;i++){
		    trainer[i] = new TD();
		}
		//File in the training inputs
		trainer[0].first = 0.0; trainer[0].second = 0.0;
		trainer[1].first = 0.0; trainer[1].second = 1.0;
		trainer[2].first = 1.0; trainer[2].second = 0.0;
		trainer[3].first = 1.0; trainer[3].second = 1.0;
		 
		//Answers to the XOR, this is the answer to the inputs above
		double[] answer = new double[4];
		//As per the book, the answer of 0 should be 0.1, 1 should be 0.9
		//this is because the function will never get to 0, it will approach it
		//the same goes for 1, it will never get to 1, it will approach it.
		//it is an asymptote. 
		answer[0] = 0.1;
		answer[1] = 0.9;
		answer[2] = 0.9;
		answer[3] = 0.1;

		//create a new random object to generate numbers
		Random rand = new Random();
		//I changed the initial weight range from -0.5 to 0.5 
		//to 2.4/n, which n is this case is 2, for two inputs
		//this is one way which improved the neural network
		double mininitialweight = -1.2;
		double maxmininitialweight = 1.2;

		double minthreshold = 0.8;
		double maxthreshold = 1.0;
        System.out.println("The range of the weight is from " + minthreshold + " to " + maxthreshold );

		//this is really unnecessary, just for understanding what the variable meant
		//initalize each of the weights with a range
		double firstweight = (mininitialweight) + ((maxmininitialweight) - (mininitialweight)) * rand.nextDouble();
		double secondweight = (mininitialweight) + ((maxmininitialweight) - (mininitialweight)) * rand.nextDouble();
		//initalize the threshold to a random number between 0 and 1
		double threshold = (minthreshold) + ((maxthreshold) - (minthreshold)) * rand.nextDouble();
		//create this node with the weights and threshold numbers already made
		Node OR = new Node(firstweight, secondweight, threshold);
        System.out.println("The OR node is created with weight 1 with " + firstweight + " and weight 2 with " + secondweight + " and threshold of "  + threshold );
		//do same initialization below
		firstweight = (mininitialweight) + (maxmininitialweight - (mininitialweight)) * rand.nextDouble();
		secondweight = (mininitialweight) + (maxmininitialweight - (mininitialweight)) * rand.nextDouble();
		threshold = (minthreshold) + ((maxthreshold) - (minthreshold)) * rand.nextDouble();
		//create same way as above
		Node NAND = new Node(firstweight, secondweight, threshold);
        System.out.println("The NAND node is created with weight with " + firstweight + " and weight 2 with " + secondweight + " and threshold of "  + threshold );
		//do same initialization below
		firstweight = (mininitialweight) + (maxmininitialweight - (mininitialweight)) * rand.nextDouble();
		secondweight = (mininitialweight) + (maxmininitialweight - (mininitialweight)) * rand.nextDouble();
		threshold = (minthreshold) + ((maxthreshold) - (minthreshold)) * rand.nextDouble();
		Node AND = new Node(firstweight, secondweight, threshold);
        System.out.println("The AND node is created with weight with " + firstweight + " and weight 2 with " + secondweight + " and threshold of "  + threshold );
        	
		//create doubles to for the values outputed by the function
		double outputOR;
		double outputNAND;
		double outputAND;
		//set the learning rate
		double a = 0.8;
        System.out.println("The learning rate is " + a);
		//create doubles for the error gradient values for each node
		double errorgradoutput;
		double errorgradOR;
		double errorgradNAND;
		//boolean to know if sum or error^2 is low enough to finish
		boolean finished = false;
		//double for total error calculation
		double totalerror;
		//temp double for the current error
		double currenterror;
		//to know which iteration we are on
		int epoch = 0;
		//doubles to hold the current weights of the inputs of the final
		//these are used for the error gradient calculation of the OR and NAND node
		double ANDweight1;
		double ANDweight2;
		//temporary doubles to hold the new OR and NAND weights
		double tempweight1;
		double tempweight2;

		Scanner input = new Scanner( System.in );
		//System.out.println("Please enter 1 to continue");
		//int dontcare = input.nextInt(); 
		while(!finished){
			//set the total error to 0 before each run
			totalerror = 0;
			
			//This was to randomize the inputs.	
			/*
			for (int i=0; i<4; i++) {
			    int randomPosition = rand.nextInt(4);
			    double temp = trainer[i].first;
			    double temp2 = trainer[i].second;
			    double temp3 = answer[i];
			    trainer[i].first = trainer[randomPosition].first;
			    trainer[i].second = trainer[randomPosition].second;
			    answer[i] = answer[randomPosition];
			    trainer[randomPosition].first = temp;
			    trainer[randomPosition].second = temp2;
			    answer[randomPosition] = temp3;

			}
			*/
			
			
			for(int i = 0; i < 4 ; i++){
				///RANDOMIZE THE INPUTS
				//get the outputs of each node, given inputs from training data
				outputOR = neural(OR,trainer[i].first,trainer[i].second);
				//output other node, the NAND
				outputNAND = neural(NAND,trainer[i].first,trainer[i].second);
				//for this output, make the input the output of the OR and NAND node
				outputAND = neural(AND,outputOR,outputNAND);
		        if(PRINT){
		        	System.out.println("The output of the final node is " + outputAND);
		        }
				//get weights of AND node so that it can be used for error gradient calculation
				ANDweight1 = AND.getweight1();
				ANDweight2 = AND.getweight1();
				//find the current error by subtracting desired - currentoutput
				currenterror = answer[i] - outputAND;
					
				//get the error gradient of the output
				errorgradoutput = outputAND * ( 1 - outputAND ) * currenterror;
							
				//get the error gradient of the OR and NAND nodes
				errorgradOR = outputOR *  ( 1 - outputOR ) * ANDweight1 * errorgradoutput;			
				errorgradNAND = outputNAND *  ( 1 - outputNAND ) * ANDweight2 * errorgradoutput; 
				
		        if(PRINT){
		        	System.out.println("The current error is " + currenterror);
		        	System.out.println("The error gradient for the output node is " + errorgradoutput);
		        	System.out.println("The error gradient for the hidden node of OR is " + errorgradOR);
		        	System.out.println("The error gradient for the hidden node of NAND is " + errorgradNAND);
		        }
				
				//find error each run, add to total to see if close to less than 0.001
				totalerror = totalerror + (currenterror*currenterror);
				//if(totalerror >= 0.001){
					//set the new weights of the nodes with inputs, learning rate, current weight, and error gradient
					//could have not used tempweight variable and directly set weights
					tempweight1 = OR.getweight1() + ( a * trainer[i].first * errorgradOR);
					OR.setweight1(tempweight1);	
					tempweight2 = OR.getweight2() + ( a * trainer[i].second * errorgradOR);
					OR.setweight2(tempweight2);
					
			        if(PRINT){
			        	System.out.println("The new weight 1 for the OR node is " + tempweight1);
			        	System.out.println("The new weight 2 for the OR node is " + tempweight2);
			        }
					
					tempweight1 = NAND.getweight1() + ( a * trainer[i].first * errorgradNAND);
					NAND.setweight1(tempweight1);			
			        tempweight2 = NAND.getweight2() + ( a * trainer[i].second * errorgradNAND);
					NAND.setweight2(tempweight2);
					
			        if(PRINT){
			        	System.out.println("The new weight 1 for the NAND node is " + tempweight1);
			        	System.out.println("The new weight 2 for the NAND node is " + tempweight2);
			        }
					
					//this calculation uses the output of the OR and NAND nodes to recalculate the weights
					tempweight1 = AND.getweight1() + ( a * outputOR * errorgradoutput );
					AND.setweight1(tempweight1);				
					tempweight2 = AND.getweight2() + ( a * outputNAND * errorgradoutput );
					AND.setweight1(tempweight2);
					
			        if(PRINT){
			        	System.out.println("The new weight 1 for the AND node is " + tempweight1);
			        	System.out.println("The new weight 2 for the AND node is " + tempweight2);
			        }
				//}
			}
			//print error and iteration
            System.out.println("The Total Error is " + totalerror);
            System.out.println("\n");  
            //add to the iteration number
            epoch++;
            System.out.println("Currently at iteration " + epoch);
            //check if the total error is less than 0.001
            if( totalerror < 0.001){
            	//break out of loop by changing boolean
                finished = true; 
                System.out.println("Breaking out of loop, sum of total error squared is at an appropriate value.");
                for(int i = 0; i < 4 ;i++){
                	System.out.println(trainer[i].first + trainer[i].second + answer[i] );
                	System.out.println("OR weights" + OR.getweight1() + " " + OR.getweight2() );
                	System.out.println("OR threshold" + OR.getthreshold() + " " + OR.getthreshold() );

                	System.out.println("NAND weights" + NAND.getweight1() + " " + NAND.getweight2() );
                	System.out.println("NAND threshold" + OR.getthreshold() + " " + OR.getthreshold() );
                	
                	System.out.println("AND weights" + AND.getweight1() + " " + AND.getweight2() );
                	System.out.println("AND threshold" + OR.getthreshold() + " " + OR.getthreshold() );         
                	
                    System.out.println("The Total Error is " + totalerror);
                }
            }
		}
	}
static double neural(Node n, double input1, double input2){        
	double temp1 = n.getweight1();
	double temp2 = n.getweight2();
	double thres = n.getthreshold();
	//this calculation follows instructions in the book, thres is threshold
	//so this is getting Xj
    double X = (((input1 * temp1))+ ((input2 * temp2)) - thres);
    //this calculation follows the book, so this is Yj
    double output = 1 / ( 1 + Math.exp(X*(-1)) ); 
    //the hyperbolic tangent function is below
    // double a = 1.25; 
    //double b = 0.10;
    //double output = ((2*a)/(1+Math.exp(-b * X))) - a;
    return output; 
	}

//class from Dr.Burg's notes for holding training inputs
static class TD{
    double first ;
    double second ;
    //for initializing
    TD(){
    	first = 0.0;
    	second = 0.0;
    	}
	}
}
