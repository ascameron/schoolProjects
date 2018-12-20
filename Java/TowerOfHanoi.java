package HW7;

import java.util.Scanner;

public class TowerOfHanoi {

	//recursive Tower of Hanoi
	
	static void recursiveTowerOfHanoi(int x, char start, char aux, char end){
		if (x == 1) {
	           System.out.println("move disk " + x + " from " + start + " -> " + end);
	       } else {
	           recursiveTowerOfHanoi(x - 1, start, end, aux);
	           System.out.println("move disk " + x + " from " +start + " -> " + end);
	           recursiveTowerOfHanoi(x - 1, aux, start, end);
	       }
	}
	
	
	
	
	//non recursive Tower of Hanoi
	

	
	
	
	static void nonRecursiveTowerOfHanoi(int x, Stack start, Stack aux, Stack end){
			//TODO
			 int i;
			 int total_moves;
		     char s = 'S', e = 'E', a = 'A';
		     //s = start, e = end, a = auxiliary
		      
		        // If number of disks is even, then interchange
		        // destination pole and auxiliary pole
		        if (x % 2 == 0)
		        {
		            char temp = e;
		            e = a;
		            a  = temp;
		        }
		        total_moves = (int) (Math.pow(2, x) - 1);
		      
		        // Larger disks pushed first
		        for (i = x; i >= 1; i--)
		            push(start, i);
		      
		        for (i = 1; i <= total_moves; i++)
		        {
		            if (i % 3 == 1)
		              moveDisksBetweenTwoPoles(start, end, s, e);
		      
		            else if (i % 3 == 2)
		              moveDisksBetweenTwoPoles(start, aux, s, a);
		      
		            else if (i % 3 == 0)
		              moveDisksBetweenTwoPoles(aux, end, a, e);
		        }
			
			
			
			
		}

    // A structure to represent a stack
    class Stack
    {
        int capacity;
        int top;
        int array[];
    }
     
    // function to create a stack of given capacity.
    Stack createStack(int capacity)
    {
        Stack stack=new Stack();
        stack.capacity = capacity;
        stack.top = -1;
        stack.array = new int[capacity];
        return stack;
    }
     
    // Stack is full when top is equal to the last index
    static boolean isFull(Stack stack)
    {
        return (stack.top == stack.capacity - 1);
    }
     
    // Stack is empty when top is equal to -1
    static boolean isEmpty(Stack stack)
    {
        return (stack.top == -1);
    }
     
    // Function to add an item to stack.  It increases
    // top by 1
    static void push(Stack stack,int item)
    {
        if(isFull(stack))
            return;
        stack.array[++stack.top] = item;
    }
     
    // Function to remove an item from stack.  It
    // decreases top by 1
    static int pop(Stack stack)
    {
        if(isEmpty(stack))
            return Integer.MIN_VALUE;
        return stack.array[stack.top--];
    }
	
	static void moveDisksBetweenTwoPoles(Stack start, Stack end, char s, char e) {
			// TODO Auto-generated method stub
		 int pole1Top = pop(start);
	     int pole2Top = pop(end);

	     // When pole 1 is empty
	     if (pole1Top == Integer.MIN_VALUE) 
	     {
	         push(start, pole2Top);
	         System.out.println("move disk " + pole2Top + " from " + e + " -> " + s);
	     }
	     // When pole2 pole is empty
	     else if (pole2Top == Integer.MIN_VALUE) 
	     {
	         push(end, pole1Top);
	         System.out.println("move disk " + pole1Top + " from " + s + " -> " + e);
	     }
	     // When top disk of pole1 > top disk of pole2
	     else if (pole1Top > pole2Top) 
	     {
	         push(start, pole1Top);
	         push(start, pole2Top);
	         System.out.println("move disk " + pole2Top + " from " + e + " -> " + s);
	     }
	     // When top disk of pole1 < top disk of pole2
	     else
	     {
	         push(end, pole2Top);
	         push(end, pole1Top);
	         System.out.println("move disk " + pole1Top + " from " + s + " -> " + e);
	     }
		}
	
	

	public static void main(String[] args){
		
		//get # of disks
		System.out.print("Enter number of discs: ");
	       Scanner input = new Scanner(System.in);
	       int num_disks = input.nextInt();
	       	input.close();
	       	
	      //Recursive
		System.out.println("\nRecursive");
		recursiveTowerOfHanoi(num_disks, 'S', 'A', 'E');
		
		  //non Recursive
		System.out.println("\nnon Recursive");
		TowerOfHanoi ob = new TowerOfHanoi();
     	Stack start, end, aux;
		// Create three stacks 
		// to hold the disks
		start = ob.createStack(num_disks);
		end = ob.createStack(num_disks);
		aux = ob.createStack(num_disks);
     
		TowerOfHanoi.nonRecursiveTowerOfHanoi(num_disks, start, aux, end);
		
}




}
