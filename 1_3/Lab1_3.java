/**

In this task you will implement the method getColourValue of the class Lab1_3. 

To check the actual intensity value of a pixel at x=111 y=20 you may use the following command in the terminal. 

convert 'example.png[1x1+20+111]' txt:

Note that the colour image is stored is of type TYPE_3BYTE_BGR which stores the blue component first, then the green component and finally the red component. As before, all pixels are stored in a row major 1D array. 

Here a few test cases:

Input coordinates: 1 1
Colour value (rgb) at (1,1) is 224 17 95

Input coordinates: 100 20
Colour value (rgb) at (100,20) is 234 18 99

Input coordinates: 100 100
Colour value (rgb) at (100,100) is 0 0 0

**/

import java.util.Scanner;
public class Lab1_3 {
	public Lab1_3() {
		Img img = new Img("example.png");
		System.out.print("Input coordinates: ");
		Scanner in = new Scanner(System.in);
		int x = in.nextInt();
		int y = in.nextInt();
		int[] i = getColourValue(img, x, y);
		System.out.println("Colour value (rgb) at ("+x+","+y+") is "+i[0]+" "+i[1]+" "+i[2]);
	}
	/**
	 * Retrieve the colour value at location (x, y) of the image and
	 * return it as an RGB integer array.
	 * @param img in row major format
	 * @param x coordinate
	 * @param y coordinate
	 * @return the intensity value at (x, y)
	 */
	public int[] getColourValue(Img i, int x, int y) {
		//Your code here
		return new int[]{(int)(i.img[(x*i.width+y)*3 + 2] & 0xff),
						 (int)(i.img[(x*i.width+y)*3 + 1] & 0xff),
						 (int)(i.img[(x*i.width+y)*3 + 0] & 0xff)};
	}
	public static void main(String[] args) {
		new Lab1_3();
	}
}
