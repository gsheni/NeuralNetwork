# NeuralNetwork

Recall that a perceptron – a single-neuron – can used to represent the logical functions and, or, nor, and nand.  How can you logically combine these function to create xor?  
![alt text](http://vlm1.uta.edu/~athitsos/courses/cse4308_fall2011/exams/exam3/answer12.png)
A multilayer neural network with two inputs and one hidden layer that uses the backpropagation algorithm to represent the logical exclusive-or function.

The network has two nodes in the hidden layer and one node in the output layer.  (The input layer isn't have to be a true layer – it can just pass the input to the hidden layer.) 

The program initializes the weights to random values.  

The program runs until the sum of squares of the errors is less than 0.0001.  

The number of layers, nodes and input into the program for this particular problem are hard-coded. 

