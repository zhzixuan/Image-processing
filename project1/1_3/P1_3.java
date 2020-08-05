/**

In this task 3 of project 1 you will design an appropriate filter and apply it to the car image to attenuate the impulse-like bursts in the image. You may reuse existing code from your tasks 1 and 2. 

Hint: Note that the spatial resolution of the car.png is not a power of 2, i.e., it is not of the form 2^m x 2^n. You should create a new suitable image with padded black pixels by changing code in the constructor of the class P1_3. 

To avoid reverse engineering, we do not provide a sample solution for this task.

**/

import java.time.Instant;
import java.time.Duration;
public class P1_3 {
	public P1_3() {
		//Change this code. 
		Img img = new Img("car.png");
		Instant start = Instant.now();
		filterImage(img);
		Instant stop = Instant.now();
		System.out.println("Elapsed time: "+Duration.between(start, stop).getSeconds()+"s");
		img.save();
	}

    public void filterImage(Img i) {
		Complex[] F = fastFourierTransfrom(i);
		//Your code here

		inverseFastFourierTransfrom(F, i);
    }

    public Complex[] fastFourierTransfrom(Img i) {
    	//Change this to your code from P1_1
    	Complex[] F = new Complex[i.width*i.height];
		for (int x=0;x<i.img.length;x++)
			F[x] = new Complex();
		return F;
	}

	private void inverseFastFourierTransfrom(Complex[] F, Img i) {
		//Change this to your code from P1_2

	}

	public static void main(String[] args) {
		new P1_3();
	}
}
