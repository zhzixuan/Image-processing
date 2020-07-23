/**

In this task you will implement the method cumulativeHistogram of the class Lab2_6 which will return a histogram of the image.  

The expected output is provided in the files solution1.png to solution4.png.

You may use the following command to check if your output is identical to ours. 

cmp solution1.png out.png

If this command has no output, it implies that your solution has produced the same file as ours.

**/

import java.util.Scanner;
public class Lab2_6 {
	public Lab2_6() {
		Img img = new Img("Fig03161.png");
		int[] h = cumulativeHistogram(img);
		img.saveHistogram(h);
	}

	public int[] cumulativeHistogram(Img i) {
		//Your code here    
        int[] histogram = new int[256];
        int[] returnValue = new int[256];
        
        for (int x=0; x<i.img.length; x++){
            histogram[(int)(i.img[x] & 0xFF)] += 1;
        }
        
        for(int k=0; k<256; k++){
            for(int g=0; g<=k; g++){
                returnValue[k] += histogram[g];
            }
        }
		return returnValue;
	}
		
	public static void main(String[] args) {
		new Lab2_6();
	}
}
