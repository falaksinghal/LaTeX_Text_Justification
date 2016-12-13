package neatlyPrintPackage;

/**
 * @author Falak Singhal_fxs161530@utdallas.edu
 * Date November 28, 2016
 * 
 * Problem  15-4 Consider  the problem of neatly printing a paragraph on a printer. The input
 * text  is a  sequence of  n words  of lengths l1;l2;...;ln, measured in characters. We want
 * to  print this  paragraph neatly  on a number of lines that hold a maximum of M characters
 * each.  Our criterion of “neatness” is as follows. If a given line contains words i through
 * j,  where i  < j,  and we leave exactly one space between words, the number of extra space
 * characters  at the  end of  the line  is M - j+ i-SUMMATION k from i to j (lk), which must
 * be  nonnegative so  that the  words fit on the line. We wish to minimize the sum, over all
 * lines  except the  last, of the cubes of the numbers of extra space characters at the ends
 * of lines. Give a dynamic-programming algorithm to print a paragraph of n words neatly on a
 * printer.  Analyze the  running time  and space  requirements of  your algorithm.
 *
 * Input    Instructions :    Can be    provided   either   through   console   or  via  run
 * configuration.  If provided  in command  line, the  input file  path is  of the  form, eg,
 * D://eclipse//workspace//file.txt and then the Line Width. If Run Configuration is used for
 * command line input use eg, D://eclipse//workspace//file.txt 132
 *  
 * Running Time : O(nM)
 */



import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

public class PrintNeatly {
	
	public static void main(String[] args) throws FileNotFoundException  {

		final double MAX=Double.POSITIVE_INFINITY;// represents INFINITY

		//size of the line, default to 80
		int M=80;
		
		//read file and create one single string
		
		
		Scanner sc;

		StringBuilder allText=new StringBuilder();

		if (args.length > 0) 
		{
			File inputFile= new File(args[0]);
			
			
			sc=new Scanner(inputFile); // to close the scanner would mean to close the System.in stream in later stage which is not desirable
								 
			
			while(sc.hasNextLine())		
				allText.append(sc.nextLine()+" ");
			if (args.length > 1) 
				M = Integer.parseInt(args[1]);
		
		}
		else{

			System.out.println("Enter File Path");
			sc=new Scanner(System.in);
			System.out.flush();
			String Filename=sc.nextLine();
			File input=new File(Filename);
			
			sc=new Scanner(input);sc.close();
			while(sc.hasNextLine())
				allText.append(sc.nextLine()+" ");
		
			
			System.out.println("Enter Line width");
			sc=new Scanner(System.in);
			M=sc.nextInt();
			
			
		}

		
		//convert into string tokens after replacing any double spaces 
		String [] tokens=allText.toString().replaceAll("( )+", " ").split(" ");


		//calculate the length of each word
		int wordLength[]=new int[tokens.length+1];
		for(int i=1;i<=tokens.length;i++){
			wordLength[i]=tokens[i-1].length();
		}



		//penalty matrix
		double[][] penalty= new double[tokens.length+1][tokens.length+1];

		// spaces matrix stores the spaces required to put words i to j in 1 line
		int [][] spaces=new int [tokens.length+1][tokens.length+1];

		// calculate spaces required and penalty to fit words in a line 

		for(int i=1;i<=tokens.length;i++){
			for(int j=i;j<=tokens.length;j++){
				if(i==j) // putting only i in 1 line of size M
					spaces[i][i]=M-wordLength[i];
				else{
					//calculate the spaces required to fit words from i to j -> depends only on  i to j-1 => optimal sub-structure
					spaces[i][j]=spaces[i][j-1]-wordLength[j]-1; //length of space in till j-1 word - length of jth word - 1 for space b/w j-1 &j
				}

				//calculate the penalty matrix from cube(space). if spaces = negative
				// means that it is not possible, set penalty to infinity

				if(spaces[i][j]<0) // means ith till jth word can't be fit into M character line
					penalty[i][j]=MAX; //  INFINITY

				else if (j==tokens.length && spaces[i][j]>0) // if we are considering the cost of words in last line, by design it is 0
					penalty[i][j]=0;

				else
					penalty[i][j]=spaces[i][j] * spaces[i][j]* spaces[i][j];	
			}

		}

		//Cost Matrix Cost[] indicating cost of printing words 1 to j in a single line = 
		double cost[]=new double[tokens.length+1];	

		// to keep track of min index of cost matrix		
		int solution[]=new int[tokens.length+1];

		
			


		//Let C[j] be the optimal cost of printing the words from 1 to j, such that word j ends a line
		cost[0]=0; // j=0 means no words that end the line, which is 0

		double temp; // holds temporary results

		//calculate the cost matrix which tells the optimized cost
		for(int j=1;j<=tokens.length;j++){
			cost[j]=MAX;
			// improving the computation;  else can do i =1 to i<=j
			for(int i = 1 > (j+1-(M+1)/2)?1:(j+1-(M+1)/2);i<=j;i++){
				//for(int i = 1;i<=j;i++){
				temp = cost[i-1]+penalty[i][j];	
				if(temp<cost[j]){
					cost[j]=temp;
					solution[j]=i;
				}
			}
		}

		// printing the lines : 
		int  k = tokens.length;

		/* assign k = total number of words
		 * while k>0 
		 * 	print solution[k] till =k
		 * 	k = solution[k]-1
		 * */

		Stack<String> pretty=new Stack<String>(); // since the solution is constructed backwords, using a stack to print the text in right order
		while(k>0)
		{
			//constructing back the optimized line
			StringBuilder sb=new StringBuilder();
			for (int i = solution[k];i<=k;i++){
				sb.append(tokens[i-1]);			// word ith is i-1 for tokens since tokens is still 0 index based and rest is not
				if(i<k) sb.append(' '); 		// append the requisite single space space only till before last word					
			}

			//-----------------------------------------------------------------------------------------------------			

			// constructing spaced out string out of extra-spaces and tokens above
			int extraSpaces=M-sb.length(); // represents the extra spaces available for optimized line
			Boolean skip=true;			   // to create a alternate pattern of spaces
			String[] LineWords = sb.toString().split(" ");  // splitting the optimized line again to insert spaces
			StringBuilder sb2=new StringBuilder();

			//reconstruct the line again
			for(int m=0;m<LineWords.length;m++){

				if(skip==true) // creating a alternating pattern of extra spaces
					skip=false; 
				else skip=true; 

				sb2.append(LineWords[m]);

				if(m<LineWords.length-1) sb2.append(" "); // append the requisite single space space only till before last word

				if(extraSpaces>0&&skip==false){ 		  // append extra space alternatively till extraspaces available
					sb2.append(" ");
					extraSpaces--;
				}
			}

			pretty.push(sb2.toString()); // push final string in stack
			k=solution[k]-1;
		}

		//final printing 
		int finalCost=(int) cost[tokens.length];
		System.out.println(finalCost); // minimum cost
		System.out.println(); 					// blank line
		while(!(pretty.isEmpty())){				// print paragraph
			System.out.println(pretty.pop());
		}
		
	}
}
