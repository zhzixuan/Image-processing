/**

In this task you will implement the method getIntensityValue of the class Lab1_1. We assume that you are using a unix style OS (such as MacOS or Linux).

You do not edit any other code. In particular, code in the Img class does not need to be edited because it simply provides a data structure for images with a function to load an image from a file. The load function in Img returns an image as a byte array.

Recall that byte is a signed data type in Java. Consider the following code that converts an int to a byte and back. 

import java.util.Scanner;
class Main {
	public static void main(String args[]) {
		System.out.print("i: ");
		Scanner in = new Scanner(System.in);
		int i = in.nextInt();
		byte b = (byte)i;
		System.out.println("b: "+b);
		System.out.print("i: ");
		System.out.println(b&0XFF);
	}	
}

The & in line 10 is a bitwise AND operation. 

When implementing getIntensityValue function, recall that we use a coordinate system where the x axis is vertical and the y is horizontal. This is important when transforming a 2D coordinate value to a 1D lookup in the byte array. 

To check the actual intensity value of a pixel at x=111 y=20 you may use the following command in a unix style terminal. 

convert 'Fig0314a.png[1x1+20+111]' txt:

Note that you need ImageMagick(imagemagick.org) for the above command to work. Alternatively, we recommend ImageJ(imagej.nih.gov).

**/

import java.util.Scanner;
public class Lab1_1 {
	public Lab1_1() {
		Img img = new Img("Fig0314a.png");
		System.out.print("Input coordinates: ");
		Scanner in = new Scanner(System.in);
		int x = in.nextInt();
		int y = in.nextInt();
		int i = getIntensityValue(img, x, y);
		System.out.println("Intensity at ("+x+","+y+") is "+i);
	}
	/**
	 * Retrieve the intensity value at location (x, y) of the image and return it
	 * @param img in row major format
	 * @param x coordinate
	 * @param y coordinate
	 * @return the intensity value at (x, y)
	 */
	public int getIntensityValue(Img i, int x, int y) {
		//Your code here
		return (int)(i.img[x*i.width+y] & 0XFF);
	}
	public static void main(String[] args) {
		new Lab1_1();
	}
}
