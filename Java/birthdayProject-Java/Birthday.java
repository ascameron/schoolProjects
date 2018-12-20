/*
ascameron
 CPS2232 Data Structures
 Project : Birthday
 */

package Project1_Birthday;
import java.io.*;
import java.util.*;

//***student object
class Student implements Comparable<Student>{
		//*** create data fields for Student class
		private String first = "";
		private String last = "";
		private String name = "";
		private String month = "";
		private int day;
		private int year;
		private int age;
	
		//*** constructor
		public Student(String first, String last, String month, int day, int year){
			this.first = first;
			this.last = last;
			this.name = first + " " + last;
			this.month = month;
			this.day = day;
			this.year = year;
			this.age = day+convertMonth();
		}
	
		//*** accessors
		public String getfirst() { return first; }
		public String getlast() { return last; }
		public String getName() { return name; }
		public String getMonth() { return month; }
		public int getDay() { return day; }
		public int getYear() { return year; }
		public int getAge() { return age; }
	
		//*** setter
		public void setAge(int newAge) {
			age = newAge;
		}
		
		//***convert month to # of days
		public int convertMonth(){
			String x = this.month;
			int y=0;
				switch(x){
				case "JANUARY":
					y = 0;
					break;
				case "FEBRUARY":
					y = 31;
					break;
				case "MARCH":
					y = 59;
					break;
				case "APRIL":
					y = 90;
					break;
				case "MAY":
					y = 120;
					break;
				case "JUNE":
					y = 151;
					break;
				case "JULY":
					y = 181;
					break;
				case "AUGUST":
					y = 212;
					break;
				case "SEPTEMBER":
					y = 243;
					break;
				case "OCTOBER":
					y = 273;
					break;
				case "NOVEMBER":
					y = 304;
					break;
				case "DECEMBER":
					y = 334;
					break;
				default:
					System.out.println("error");	
				}
			return y;
		}

		
		//*** override collections compareTo
		@Override
		public int compareTo(Student compareStudent) {		
			int number = 0;
			int compareAge = ((Student) compareStudent).getAge();
			String compareLast = ((Student) compareStudent).getlast();
			String compareFirst = ((Student) compareStudent).getfirst();
			
			//sort by age
			if((this.age - compareAge) != 0 ){
				number = this.age - compareAge;
			}
			//if age is the same break tie and sort by last name
			else if ((this.age - compareAge) == 0){
				number = (this.last).compareTo(compareLast);
				
				//if last name is the same break tie and sort by first name
				if((this.last).compareTo(compareLast) == 0){
					number = (this.first).compareTo(compareFirst);
				}
			}
			
			return number;
		}
	
}



public class Birthday {
	
	//create Student ArrayList to store class entries
	static ArrayList<Student> list = new ArrayList<Student>();
	
	//Evaluate file
	public static void evaluateFile(String y){


		//Attempt to read input from file
		try{
			  //*** Open the file 
			  FileInputStream fstream = new FileInputStream(y);
			  //Get the object of DataInputStream
			  DataInputStream in = new DataInputStream(fstream);
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
			  String strLine;
			  
			 //***read first line for # of classes and queries
			  int classCount = Integer.parseInt(br.readLine())/2;

			  //***Read File Line By Line
			  //*** iterates for how many classes there are
			  for(int i = 0; i < classCount; i++){						
				 System.out.println("\nClass #" + (i+1) + ":");
				 strLine = br.readLine();
					  
				 int  studentCount = Integer.parseInt(strLine);
					  
				 	  //*** iterates for how many students there are
					  for(int j = 0; j < studentCount; j++){
						  String line = br.readLine();
						  Student s1 = createStudent(line);
						  
						  //*** ADD student to arrayList of objects
						  list.add(j, s1);
						  
					  }
					  //sort students by age, using selection sort
					  Collections.sort(list);
					  
					  //check if list contains student with birthday feb 29th
					  check(list);
					  
					  //how many students to query
					  int queryCount = Integer.parseInt(br.readLine());

					//*** iterates for how many students to be queried
					  for(int k = 0; k < queryCount; k++){
						  String name = br.readLine();
						  //find where in the ArrayList the student is
						  int location = findIndex(name, list);
						  //perform the query for each student
						  query(location, list);
					  }
					//clear list after query to start next class
					  list.clear();	  
				  }
			  //Close the input stream
			  in.close();
			    }catch (Exception e){//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
			  }
	}
	
	//***create student object from String
	public static Student createStudent(String line){
		//split line and create object variables
		String[] splited = line.split("\\s+");
		  String a = splited[0];
		  String b = splited[1];
		  String c = splited[2];
		  int d = Integer.parseInt(splited[3]);
		  int e = Integer.parseInt(splited[4]);
		  
		  //*** create student object with data
		  Student s = new Student(a, b, c, d, e);
		//return the student object
		return s;
		
	}

	//check if any student objects have a birthday of Feb 29th
	//if so, make adjustment to rest of ArrayList
	public static void check(ArrayList<Student> a){
		
		for(int i = 0; i < a.size(); i++){
			//if student has bday feb 29th, make adjustments to rest of list
			if((a.get(i)).getMonth() == "FEBRUARY" && (a.get(i)).getDay() == 29){
				for(int j = i+1; j < a.size()-1; j++){
					int x = a.get(j).getAge();
					if((a.get(j)).getMonth() != "FEBRUARY" && (a.get(j)).getDay() != 29){
						a.get(j).setAge(x + 1);
					}
				}
				//break when first student with feb 29th birthday is found
				//and the rest of the list has been adjusted
				break;
			}
		}
	}
	
	//***find index in arrayList for queried student
	public static int findIndex(String name, ArrayList<Student> list){
		Student check = null;
		int index = 0;
		for (Student c : list) {
	        if (name.equals(c.getName())) {
	            check = c;
	            index = list.indexOf(check);
	            break;
	        }
		}
		return index;
	}
	
	//*** find closest birthday
	public static void query(int location, ArrayList<Student> list){		
		int z = location;
		int y = 0;
		Student target = list.get(z);

		//compare when query is first in the list
		if(z == 0){
			y = 0;
			Student test1 = list.get(list.size()-1);
			Student test2 = list.get((z+1));
			
			report(target, compareStudents(target, test1, test2, y));
		}
		//compare when query is last in the list
		else if(z == (list.size()-1)){
			y = 1;
			Student test1 = list.get((z-1));
			Student test2 = list.get(0);
			report(target, compareStudents(target, test1, test2, y));
		}
		//compare all other query locations
		else {
			y = 2;
			Student test1 = list.get((z-1));
			Student test2 = list.get((z+1));
			report(target, compareStudents(target, test1, test2, y));
		}
	
}

	//***select closest student to the queried student
	public static Student compareStudents(Student target, Student test1, Student test2, int y){
		Student closest = null;
		int a = 0;
		int b = 0;
		//students are not equal distance from the query
		if(test1.getAge() != test2.getAge()){
		
			if (y==0){	//query is first in list							
				a =((target.getAge() +365) - test1.getAge());
				b =(test2.getAge() - target.getAge());
			}
		
			else if(y==1){	//query is last in list							
				a = (target.getAge() - test1.getAge());
				b = ((test2.getAge()+365) - target.getAge());
			}
		
			else if(y==2){	//all other query positions in list
				a =(target.getAge() - test1.getAge());
				b =(test2.getAge() - target.getAge());
			}
		
			if (a < b){
				closest = test1;
			}
			else if(a > b){
				closest = test2;
			}
			else if(a == b){
				closest = test2;
			}
		}
		//students are equal distance away
		else {
			closest = test2;
		}
	
		return closest;
	}
	
	//print the result for each query
 	public static void report(Student target, Student test){
		System.out.println(test.getName() 
				+ " has the closest birthday to " 
				+ target.getName());;
	}

 	//get input from user
 	public static String getFile(){
 		
		Scanner input = new Scanner(System.in);

		System.out.println("Enter location of file to be tested:  ");
		String y = input.next();
		
		input.close();
		
		return y;
		
 		
 	}
 	
	public static void main(String[] args) {
		String x = getFile();
		
		evaluateFile(x);

	}

}

