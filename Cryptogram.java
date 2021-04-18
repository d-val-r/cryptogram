/*

   A program that encrypts a message into decimal/decimal format based
   on matrix algebra operations and the concept of the inverse matrix

   2x2 matrix edition

   Written by David Rudenya at about 2 AM on Sunday, 2-28-21
   Last update: 2-28-21
 
 */

import java.lang.String;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;

public class Cryptogram
{
	// write error handling method
	public static void main(String[] args) throws FileNotFoundException
	{
		String message;


		if (args.length == 0)
			System.out.println("invalid arguments: type -h or --help for help");
		else if (args[0].equals("-h") || args[0].equals("--help"))
		{
			System.out.println("usage: <optional filename> <args>");
			System.out.println("-h or --help: this help page");
			System.out.println("-e: encrypts either the file or allows user to input message");
			System.out.println("-d: decrypts either the file or allows user to input message");
		}
		else if (args[0].length() > 2)
		{
			message = readFile(args[0]);

			if (args[1].equals("-e"))
			{
				encrypt(message);
			} else if (args[1].equals("-d"))
			{
				decrypt(message);
			} else
				System.out.println("Please enter a valid command");
		} else if (args[0].length() == 2)
		{
			Scanner c = new Scanner(System.in);
			System.out.print("Enter a message: ");
			message = c.nextLine();
			c.close();
		}
		
	
	}


	public static int[] multiply(int[][] a, int[] b)
	{
		
		int[] c = new int[b.length];
		
		int count = 0;
		// accumulator to instantiate matrix values 
		
		int k = 0;
		// counter to iterate through c

		for (int i = 0; i < a.length; i++)
		// loop used to keep track of the current column of matrix a,
		// which needs to update before changing rows
		{
			for (int j = 0; j < a.length; j++)
			{
				count += b[k]*a[j][i];
				k++;
			}

			c[i] = count;
			count = k = 0;
		}		

		return c;
	}

	public static String readFile(String filename) throws FileNotFoundException
	{
		String message = "";
		File infile = new File(filename);
		Scanner c = new Scanner(infile);

		while (c.hasNext())
		{
			message += c.next();
			message += " ";
		}
		

		c.close();
		return message;
	}

	public static void encrypt(String message)
	{


		int iterator = 0;	
		int[][] key_matrix = {{1,1},
		                      {1,2}};	
		// matrix used for encryption

		int[][] message_matrix = new int[message.length()/2 + 1][2];
		// by rules of matrix multiplication, the message has to be
		// divided into 1xN matrices, where N = the number of rows/columns
		// in the key matrix (N = key_matrix.length = key_matrix[i].length)
		// Empty spaces are left as zeroes, which won't affect the
		// encrypting process


		for (int i = 0; i < message_matrix.length; i++)
		{
			for (int j = 0; j < 2; j++)
			{
				if (iterator < message.length())
				{
					message_matrix[i][j] = message.charAt(iterator);
					iterator++;
				}
			}

			message_matrix[i] = multiply(key_matrix, message_matrix[i]);	
			// as soon as one of the 1xN matrices is populated 
			// inside the message matrix, encrypt it

		}	

		
		for (int i = 0; i < message_matrix.length; i++)
		{
			for (int j = 0; j < 2; j++)
			{
				System.out.print(message_matrix[i][j]);
				System.out.print(" ");	
			}

			System.out.print(" ");
		}

		System.out.println();
	}

	public static void decrypt(String message)
	{

		int length = 0;
		Scanner counter = new Scanner(message).useDelimiter(" ");
		while (counter.hasNext())
		{
			counter.next();
			length++;
		}

		counter.close();

		int[][] key_matrix = {{2,-1},
		                      {-1,1}};	
		// matrix used for decryption; the inverse of the encryption
		// matrix

		int[][] message_matrix = new int[length/2 + 1][2];
		// THE ERROR IS HERE: the length of the matrix is decided based
		// on each individual character in the string; if something like
		// "H" is represented in the encrypted matrix as 173, that's
		// already 3 times longer than it should be


		Scanner parser = new Scanner(message);

		for (int i = 0; i < message_matrix.length; i++)
		{
			for (int j = 0; j < 2 && parser.hasNext(); j++)
			{
				message_matrix[i][j] = parser.nextInt();
			}
			
			message_matrix[i] = multiply(key_matrix, message_matrix[i]);	
			// as soon as one of the 1xN matrices is populated 
			// inside the message matrix, encrypt it


		}	
		parser.close();	

		for (int i = 0; i < message_matrix.length; i++)
		{
			for (int j = 0; j < 2; j++)
			{
				System.out.print((char) message_matrix[i][j]);
			}

		}

		System.out.println();

	}
}
