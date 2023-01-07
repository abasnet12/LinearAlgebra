import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

public class abasnet_P3 
{

	public static void main(String[] args) throws IOException
	{
		
		String addition ="AD";
		String subtraction ="SU";
		String dotProduct ="DO";
		String scaling ="SC";
		String orthoProjection ="PR";
		String calcMode = " ";
		
		int numOfLineToInput = 8;
		Random randomNum = new Random();
		RandomNum generateRanNum = new RandomNum();
		FileWriter inputFile = new FileWriter("abasnet_P3_input.txt");
		int i = 0;
		
		FileWriter inputFile2 = new FileWriter("class_P3_output.txt");
		
		
		
		while( i < numOfLineToInput)
		{
			int selectCalcMode = randomNum.nextInt(5);
			
			if(selectCalcMode == 0)
			{
				calcMode = addition;
			}
			else if(selectCalcMode == 1)
			{
				calcMode = subtraction;
			}
			else if(selectCalcMode == 2)
			{
				calcMode = scaling;
			}
			else if(selectCalcMode == 3)
			{
				calcMode = dotProduct;
			}
			else
			{
				calcMode = orthoProjection;
			}
			
			int num1 = generateRanNum.getIntRandom();
			int num2 = generateRanNum.getIntRandom();
			int num3 = generateRanNum.getIntRandom();
			int num4 = generateRanNum.getIntRandom();
			
			inputFile.write(calcMode + " " +  num1 + " " + num2 + " " + num3 + " " + num4);
			inputFile.write("\n");
			
			i++;
			
		}// end of while loop
		
		inputFile.close();
		
		//read the input and to produce output for part 3
		//ReadInput produceOutput = new ReadInput();
		File inputFileName = new File("abasnet_P3_input.txt");
		Scanner readFile = new Scanner(inputFileName);
		
		FileWriter writeTopart3 = new FileWriter("abasnet_P3_output.txt");
		
		while(readFile.hasNext())
		{
			float[] numForVector = new float[4];
			String calcMode1 = readFile.next();
			int counter = 4;
			Vector<Float> resulVec = new Vector<Float>();
			// read each line to get the coordinates for vector
			for (int j = 0 ; j < counter ; j++)
			{
				if(readFile.hasNextInt())
				{
					int intNum = readFile.nextInt();
					numForVector[j] = intNum;
				}
				else if(readFile.hasNextFloat())
				{
					float floatNum = readFile.nextFloat();
					numForVector[j] = floatNum;
				}// storing float number
			}// for loop
			CreatingVectorAndCalcMode vectorAndCalcMode = new CreatingVectorAndCalcMode( calcMode1  );
			resulVec = vectorAndCalcMode.calcVector(calcMode1, numForVector[0], numForVector[1], numForVector[2], numForVector[3]);
			System.out.println(resulVec);
			
			//writeTopart3.write(resulVec.elements());
			
			
		}// while loop
		
		FileWriter writeTopart3_1 = new FileWriter("class_p3_output.txt");
		
		File inputFileName1 = new File("class_p3_input.txt");
		Scanner readFile1 = new Scanner(inputFileName1);
		while(readFile.hasNext())
		{
			float[] numForVector = new float[4];
			String calcMode1 = readFile.next();
			int counter = 4;
			Vector<Float> resulVec = new Vector<Float>();
			// read each line to get the coordinates for vector
			for (int j = 0 ; j < counter ; j++)
			{
				if(readFile1.hasNextInt())
				{
					int intNum = readFile.nextInt();
					numForVector[j] = intNum;
				}
				else if(readFile1.hasNextFloat())
				{
					float floatNum = readFile.nextFloat();
					numForVector[j] = floatNum;
				}// storing float number
			}// for loop
			CreatingVectorAndCalcMode vectorAndCalcMode = new CreatingVectorAndCalcMode( calcMode1  );
			resulVec = vectorAndCalcMode.calcVector(calcMode1, numForVector[0], numForVector[1], numForVector[2], numForVector[3]);
			System.out.println(resulVec);
			
			//writeTopart3_1.write(resulVec.elementAt(0) + " " + resulVec.elementAt(1));
			
			
		}// while loop
		
		
		writeTopart3.close();
		readFile.close();
		readFile1.close();
		
		writeTopart3_1.close();
		inputFile2.close();
	}// end of main

}// end of class

class CreatingVectorAndCalcMode
{
	private String calcMode;
	
	public  CreatingVectorAndCalcMode(String calcMode)
	{
		this.calcMode = calcMode;	
	}
	
	public Vector<Float> calcVector(String calcMode ,float num1 , float num2 , float num3 , float num4 ) 
	{
		
		
		CreateVector vec = new CreateVector();
		Vector<Float> vector1 = vec.createVector(num1 , num2);
		Vector<Float> vector2 = vec.createVector(num3 , num4);
		Vector<Float> resultantVector =  new Vector<Float>();
		
		if(calcMode.equals("AD"))
		{
			float numA = vector1.elementAt(0) + vector2.elementAt(0);
			float numB = vector1.elementAt(1) + vector2.elementAt(1);
			
			resultantVector.add(numA);
			resultantVector.add(numB);
			
			return resultantVector;
			
			
		}
		
		if(calcMode.equals("SU"))
		{
			float numA = vector1.elementAt(0) - vector2.elementAt(0);
			float numB = vector1.elementAt(1) - vector2.elementAt(1);
			
			resultantVector.add(numA);
			resultantVector.add(numB);
			
			return resultantVector;
		}
	
		else if(calcMode.equals("DO"))
		{
			System.out.print(" DO " );
		}
		
		else if(calcMode.equals("SC"))
		{
			System.out.print(" SC " );
		}
		
		else
		{
			System.out.print(" PR "  );
		}
		
		//return resultantVector;
		return resultantVector;
		
	}
	
	
}// end of class ReadtheINput

class CreateVector
{
	public Vector<Float> createVector(float num1 , float num2)
	{
		Vector<Float> vector = new Vector<Float>();
		vector.add(num1);
		vector.add(num2);
		
		return vector;
	}
}

class RandomNum
{
	 public float roundUp(float num)
	 {
		 String changeToString = String.format("%1,1f", num);
		 Float roundUpNum = Float.valueOf(changeToString);
		 return roundUpNum;
	 }// roundup method
	 
	 public int getIntRandom()
	 {
		 Random randomNum = new Random();
	
		 int randomInt = randomNum.nextInt(12+10)-10;
			
		 return randomInt;
	 } //get IntRandom method
	
 }// end of class RandomNum
 
 