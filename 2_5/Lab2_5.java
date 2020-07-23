/**

In this task you will implement the method histogram of the class Lab2_5 which will return a histogram of the image.  

Try the following code to generate an example histogram.

        int[] returnValue = new int[256];
        for (int x=0;x<256;x++)
            returnValue[x] = x;
        return returnValue;

The expected output is provided in the files solution1.png to solution4.png.

You may use the following command to check if your output is identical to ours. 

cmp solution1.png out.png

If this command has no output, it implies that your solution has produced the same file as ours.

**/
import java.util.Scanner;
public class Lab2_5 {
	public Lab2_5() {
		Img img = new Img("Fig03161.png");
		int[] h = histogram(img);
		img.saveHistogram(h);
	}

	public int[] histogram(Img i) {
		//Your code here
		int[] histogram = new int[256];
        for (int x=0; x<i.img.length; x++){
            histogram[(int)(i.img[x] & 0xFF)] += 1;
        }
		return histogram;
	}
		
	public static void main(String[] args) {
		new Lab2_5();
	}
}
