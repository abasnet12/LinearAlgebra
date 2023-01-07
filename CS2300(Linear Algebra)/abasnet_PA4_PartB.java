import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Vector;

public class abasnet_PA4_PartB 
{
	static int outputFileNumber = 1;
	public static void main(String[] args) throws IOException 
	{

		String input_FileName1 = "abasnet_PA4_input_1.txt";
		readFile(input_FileName1 );
		
		String input_FileName2 = "abasnet_PA4_input_2.txt";
		readFile(input_FileName2 );
		
		String input_FileName3 = "abasnet_PA4_input_3.txt";
		readFile(input_FileName3 );
		
		String input_FileName4 = "input_1.txt";
		readFile(input_FileName4 );
		
		String input_FileName5 = "input_2.txt";
		readFile(input_FileName5 );
		
		String input_FileName6 = "input_3.txt";
		readFile(input_FileName6 );
		
		String input_FileName7 = "input_4.txt";
		readFile(input_FileName7 );
		
		String input_FileName8 = "input_5.txt";
		readFile(input_FileName8 );

	}// end of main

	private static void readFile(String fileName) throws IOException 
	{
		String outputFile = outputFile();
		
		Vector<Float> eyeLocation = new Vector<Float>();
		Vector<Float> lightSource = new Vector<Float>();
		int count = 0 ;

		File inputFileName = new File(fileName);
		Scanner readFile = new Scanner(inputFileName); 
		
		//read the eye location coordinates
		while(count < 3)
		{
			eyeLocation.add(readFile.nextFloat());
			count++;
		}// while loop
		
		//read the light source coordinates
		count = 0;
		while(count < 3)
		{
			lightSource.add(readFile.nextFloat());
			count++;
		}// while loop
		
		// skip rest of the numbers on first line
		readFile.nextLine();
		
		List<Triangle> listOfTriangles = new ArrayList<>();
		Queue<Float> storeCoordinates = new LinkedList<>();
		
		while(readFile.hasNextFloat())
		{
			Vector<Float> vect_P = new Vector<>();
			Vector<Float> vect_Q = new Vector<>();
			Vector<Float> vect_R = new Vector<>();
			
			//store coordinates in queue
			storeCoordinates.add(readFile.nextFloat());
			
			// store coordinates for P, Q , R
			if(storeCoordinates.size() == 9)
			{
			
				// add coordinates to vector P
				int i = 0;
				while( i < 3 )
				{
					vect_P.add(storeCoordinates.remove());
					i++;
				}// while loop
				
				// add coordinates to vector Q
				i = 0;
				while( i < 3 )
				{
					vect_Q.add(storeCoordinates.remove());
					i++;
				}// while loop
				
				// add coordinates to vector R
				i = 0;
				while( i < 3 )
				{
					vect_R.add(storeCoordinates.remove());
					i++;
				}// while loop
				
				// add triangle objects to the list
				listOfTriangles.add(new Triangle(vect_P , vect_Q , vect_R ));

			}// if statement
		}// while loop
		
		// front or back facing method
		frontOrBackFacing(eyeLocation  , lightSource,listOfTriangles , outputFile);
		
		readFile.close();
	} // readFile
	
	// front or back facing method
	private static void frontOrBackFacing(Vector<Float> eyeLocation, Vector<Float> lightSource,List<Triangle> 
	listOfTriangles, String outputFile) throws IOException 
	{
		FileWriter output = new FileWriter(outputFile);
		int booleanValue = 0;
		
		for(int i = 0 ; i < listOfTriangles.size() ; i++)
		{
			
			Vector<Float> p = listOfTriangles.get(i).getP();
			Vector<Float> q = listOfTriangles.get(i).getQ();
			Vector<Float> r = listOfTriangles.get(i).getR();
			booleanValue = boolValue(eyeLocation,listOfTriangles,p , q ,r);
			
			 output.write(booleanValue + " "); 
		}// for loop
		
		output.write("\n"); 
		
		// calculate light intensity
		lightIntensity(lightSource , output , listOfTriangles);
		
		output.write("\n");
		
		//if front facing from eye's perspective, compute the lighting intensity
		frontFacing_LightIntensity(listOfTriangles , output  , eyeLocation);
		
		output.close();
		
	}// frontOrBackFacing
	

	private static int boolValue(Vector<Float> eyeLocation ,List<Triangle> listOfTriangles, Vector<Float> p, 
			Vector<Float> q, Vector<Float> r) 
	{
		int boolValue;
		Vector<Float> normal_N= findNormal_N(p , q , r);
		
		// vector formed by centroid of a triangle and eye's position
		Vector<Float> centroidFromEye = findVector_CentroidAndEye(eyeLocation ,p , q , r);
		
		// Dot product n * v
		float n1 = normal_N.get(0);
		float n2 = normal_N.get(1);
		float n3 = normal_N.get(2);
		
		float v1 = centroidFromEye.get(0);
		float v2 = centroidFromEye.get(1);
		float v3 = centroidFromEye.get(2);

		float dotProd = ( n1 * v1 ) + ( n2 * v2 ) + ( n3 * v3 );

		// Back Facing
		if( dotProd < 1 )
		{
			boolValue = 0;
		}
		
		// Front facing
		else
		{
			boolValue = 0;
		}
		 
		return boolValue;
	}//boolValue

	// normal vector to triangular facet
	private static Vector<Float> findNormal_N(Vector<Float> p, Vector<Float> q, Vector<Float> r) 
	{
		// normalized n vector
		Vector<Float> norm_N = new Vector<>();
			
		// vect_v = q - p
		Vector<Float> vect_V = new Vector<>();
		vect_V.add(q.get(0) - p.get(0));
		vect_V.add(q.get(1) - p.get(1));
		vect_V.add(q.get(2) - p.get(2));
			
		// vect_W = r - p
		Vector<Float> vect_W = new Vector<>();
		vect_W.add(r.get(0) - p.get(0));
		vect_W.add(r.get(1) - p.get(1));
		vect_W.add(r.get(2) - p.get(2));
			
		//cross product
		float n1 = (vect_V.get(1) * vect_W.get(2) ) - ( vect_W.get(1) * vect_V.get(2) );
		float n2 = (vect_V.get(2) * vect_W.get(0) ) - ( vect_W.get(2) * vect_V.get(0) );
		float n3 = (vect_V.get(0) * vect_W.get(1) ) - ( vect_W.get(0) * vect_V.get(1) );
			
		float mag_n = (float) Math.sqrt( (Math.pow(n1, 2)) + (Math.pow(n2, 2)) + (Math.pow(n3, 2)));
			
		// normalized n = cross product of v and w / ||n||
		float norm_n1 = n1 / mag_n;
		float norm_n2 = n2 / mag_n;
		float norm_n3 = n3 / mag_n;
			
		// add normalized components to vector norm_N
		norm_N.add(norm_n1);
		norm_N.add(norm_n2);
		norm_N.add(norm_n3);
			
		return norm_N;
	}// findNormal_N method

	//if front facing from eye's perspective, compute the lighting intensity
	private static void frontFacing_LightIntensity( List<Triangle> listOfTriangles, FileWriter output,
			Vector<Float> eyeLocation) throws IOException 
	{
		for(int i = 0 ; i < listOfTriangles.size() ; i++)
		{
			
				Vector<Float> p = listOfTriangles.get(i).getP();
				Vector<Float> q = listOfTriangles.get(i).getQ();
				Vector<Float> r = listOfTriangles.get(i).getR();
				
				int facing = boolValue(eyeLocation,listOfTriangles,p , q ,r);
				
				// back facing
				if(facing == 0)
				{
					output.write(0 + " ");
				}// if
				
				// front facing
				else
				{
					int booleanValue = computeLightIntensity(p , q , r , eyeLocation);
					output.write(booleanValue + " ");
				}
		}// for loop
		
	}// frontFacing_LightIntensity

	// calculate light intensity
	private static void lightIntensity(Vector<Float> source, FileWriter output, List<Triangle> listOfTriangles) 
			throws IOException 
	{
		for(int i = 0 ; i < listOfTriangles.size() ; i++)
		{
			Vector<Float> p = listOfTriangles.get(i).getP();
			Vector<Float> q = listOfTriangles.get(i).getQ();
			Vector<Float> r = listOfTriangles.get(i).getR();
			
			int booleanValue = computeLightIntensity(p , q , r , source);
			
			 output.write(booleanValue + " "); 
		}// for loop
		
	}// lightIntensity

	
	private static int computeLightIntensity(Vector<Float> p, Vector<Float> q, Vector<Float> r, Vector<Float> source) 
	{
		Vector<Float> normal_N= findNormal_N(p , q , r);
		
		// vector formed by centroid of a triangle and light location
		Vector<Float> lightLocation_Centroid = findVector_CentroidAndLight(source ,p , q , r);
		
		// Dot product n * v
		float n1 = normal_N.get(0);
		float n2 = normal_N.get(1);
		float n3 = normal_N.get(2);
		
		float v1 = lightLocation_Centroid.get(0);
		float v2 = lightLocation_Centroid.get(1);
		float v3 = lightLocation_Centroid.get(2);
		
		
		float dotProd = ( n1 * (-v1) ) + ( n2 * (-v2) ) + ( n3 * (-v3) );

		float mag_normal_N = (float) Math.sqrt( (Math.pow(n1, 2)) + (Math.pow(n2, 2)) + (Math.pow(n3, 2)));
		float mag_LightLocation_Centroid = (float) Math.sqrt( (Math.pow(v1, 2)) + (Math.pow(v2, 2)) 
				+ (Math.pow(v3, 2)));
		float multipleOfMag = mag_normal_N * mag_LightLocation_Centroid ;

		int boolValue;
		
		// if the light source is from the behind of the planar facet
		if(dotProd  < 0)
		{
			boolValue = 0;
		}// if
		
		// if light  is entering into the planar facet
		else
		{
			float x = dotProd / multipleOfMag ;
			boolValue =  (int)Math.acos(x);
		}// else
		return boolValue;
	}// computeLightIntensity method

	// find the vector formed by light source position and centroid of a triangle
	private static Vector<Float> findVector_CentroidAndLight(Vector<Float> location, Vector<Float> p,
			Vector<Float> q, Vector<Float> r) 
	{
		// point p
				float p1 = p.get(0);
				float p2 = p.get(1);
				float p3 = p.get(2);
				
				// point q
				float q1 = q.get(0);
				float q2 = q.get(1);
				float q3 = q.get(2);
				
				// point r
				float r1 = r.get(0);
				float r2 = r.get(1);
				float r3 = r.get(2);
				
				// find centroid of Triangle
				float c1 = (p1 + q1 + r1)/3 ;
				float c2 = (p2 + q2 + r2)/3 ;
				float c3 = (p3 + q3 + r3)/3 ;
				
				// l location coordinates
				float l1 = location.get(0);
				float l2 = location.get(1);
				float l3 = location.get(2);
				
				// vector v components
				float v1 = c1  - l1;
				float v2 = c2 - l2;
				float v3 = c3 - l3;
				
				float mag_v = (float) Math.sqrt( (Math.pow(v1, 2)) + (Math.pow(v2, 2)) + (Math.pow(v3, 2)));
				
				// normalized vector V
				Vector<Float> norm_vect = new Vector<>();
				norm_vect.add(v1 / mag_v);
				norm_vect.add(v2 / mag_v);
				norm_vect.add(v3 / mag_v);
				
				return norm_vect;
	
	}// 

	// find the vector formed by centroid of a triangle and eye's position
	private static Vector<Float> findVector_CentroidAndEye( Vector<Float> location, Vector<Float> p,
			Vector<Float> q, Vector<Float> r ) 
	{
		
		// point p
		float p1 = p.get(0);
		float p2 = p.get(1);
		float p3 = p.get(2);
		
		// point q
		float q1 = q.get(0);
		float q2 = q.get(1);
		float q3 = q.get(2);
		
		// point r
		float r1 = r.get(0);
		float r2 = r.get(1);
		float r3 = r.get(2);
		
		// find centroid of Triangle
		float c1 = (p1 + q1 + r1)/3 ;
		float c2 = (p2 + q2 + r2)/3 ;
		float c3 = (p3 + q3 + r3)/3 ;
		
		// l location coordinates
		float l1 = location.get(0);
		float l2 = location.get(1);
		float l3 = location.get(2);
		
		// vector v components
		float v1 = l1 - c1 ;
		float v2 = l2 - c2 ;
		float v3 = l3 - c3 ;
		
		float mag_v = (float) Math.sqrt( (Math.pow(v1, 2)) + (Math.pow(v2, 2)) + (Math.pow(v3, 2)));
		
		// normalized vector V
		Vector<Float> norm_vect = new Vector<>();
		norm_vect.add(v1 / mag_v);
		norm_vect.add(v2 / mag_v);
		norm_vect.add(v3 / mag_v);
		
		return norm_vect;
	}// findVector_CentroidAndPoint method

	
	private static String outputFile() 
	{
		String output_FileName = " ";
		
		switch(outputFileNumber) 
		{
			case 1: output_FileName = "abasnet_PA4_output_B_1.txt";
				break;

			case 2: output_FileName = "abasnet_PA4_output_B_2.txt";
				break;
	
			case 3: output_FileName = "abasnet_PA4_output_B_3.txt";
				break;
	
			case 4: output_FileName = "abasnet_PA4_output_B_4.txt";
				break;	
		
			case 5: output_FileName = "abasnet_PA4_output_B_5.txt";
				break;
				
			case 6: output_FileName = "abasnet_PA4_output_B_6.txt";
				break;

			case 7: output_FileName = "abasnet_PA4_output_B_7.txt";
				break;	
	
			case 8: output_FileName = "abasnet_PA4_output_B_8.txt";
				break;
				
			
		}// end of switch
		
		outputFileNumber++;
		return output_FileName;
	}// outputFile method
	
}// PA4 part B

class Triangle
{
	private Vector<Float> vect_P;
	private Vector<Float> vect_Q;
	private Vector<Float> vect_R;
	
	public Triangle(Vector<Float> vect_P, Vector<Float> vect_Q, Vector<Float> vect_R) 
	{
		this.vect_P = vect_P;
		this.vect_Q = vect_Q;
		this.vect_R = vect_R;
	}// constructor
	
	public Vector<Float> getP()
	{
		return vect_P;
	}
	public Vector<Float> getQ()
	{
		return vect_Q;
	}
	public Vector<Float> getR()
	{
		return vect_R;
	}
}// class triangle
