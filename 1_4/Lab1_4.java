/**

In this you task will implement the method getMostFrequentIntensityValue that will return the most frequent intensity value in the image, i.e., the intensity that appears most often. 

Your program should output the following.

Most frequent intensity value is 194

**/

import java.util.Scanner;
import java.util.Arrays;
// import java.util.Collections;
public class Lab1_4 {
	public Lab1_4() {
		Img img = new Img("Fig0314a.png");
		int i = getMostFrequentIntensityValue(img);
		System.out.println("Most frequent intensity value is "+i);
	}
	/**
	 * Retrieve the intensity value that occurs most often in the image
	 * @param img 
	 * @return the intensity value that occurs most often in the image
	 */
	public int getMostFrequentIntensityValue(Img i) {
		//Your code here
		int[] intensityArray = new int[256];
		Arrays.fill(intensityArray, 0);
		
		int pixel;
		int max = 0;
		int maxIndex = -1;
		for (int y=0; y<i.width; y++) {
			for (int x=0; x<i.height; x++) {
				pixel = (int)(i.img[x*i.width+y] & 0XFF);
				intensityArray[pixel] += 1;
				if (intensityArray[pixel]>max){
					max = intensityArray[pixel];
					maxIndex = pixel;
				}
			}
		}
		return maxIndex;
	}
	public static void main(String[] args) {
		new Lab1_4();
	}
}
