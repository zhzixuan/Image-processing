/**

In this task you will implement the method medianFilter of the class Lab2_9 which applies the median filter on the image. 

You should handle the boundary case by keeping the pixels unchanged. 

The expected output is provided in the files solution3.png and solution7.png, where the digit in the filename is the threshold. 

You may use the following command to check if your output is identical to ours. 

cmp solution7.png out.png

If this command has no output, it implies that your solution has produced the same file as ours.

**/

import java.util.Scanner;
import java.time.Instant;
import java.time.Duration;
import java. util. Arrays;
public class Lab2_9 {
	public Lab2_9() {
		Img img = new Img("Fig0441.png");
		System.out.print("Size: ");
		Scanner in = new Scanner(System.in);
		int size = in.nextInt();
		Instant start = Instant.now();
		medianFilter(img, size);
		Instant stop = Instant.now();
		//System.out.println("Elapsed time: "+Duration.between(start, stop).toMillis()+"ms");
		img.save();
	}

	public void medianFilter(Img i, int size) {
		//Your code here
        int median;
        int f = size/2;
        int[] filter = new int[size*size];
        byte[] i2 = new byte[i.img.length];
        System.arraycopy(i.img, 0, i2, 0, i.img.length);
                    
        for (int x=f; x<i.height-f; x++) {
			for (int y=f; y<i.width-f; y++) {
                
                int c = 0;
                for(int j=-f; j<=f; j++){
                    for(int k=-f; k<=f; k++){
                        filter[c] = (int)(i2[(x+j)*i.width+y+k] & 0xFF);
                        c++;
                    }
                }
                Arrays.sort(filter);
                int mid = size*size / 2;
                if(size %2 == 1)
                    median = filter[mid];
                else
                    median = (int)(filter[mid-1] + filter[mid])/2;
//                 System.out.println(median);
                i.img[x*i.width+y] = (byte)(median);
                
			}
		}
	}
		
	public static void main(String[] args) {
		new Lab2_9();
	}
}
