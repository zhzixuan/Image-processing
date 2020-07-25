/**

In this task you will implement the inverse Fourier transform and perform filtering in the frequency domain. You should reuse your implementation of the Fourier transform from the previous lab session (3_1). 

In the filterImage() method add one line of code that changes the Fourier transformed image such that it is 0 at the center. (Slide: https://docs.google.com/presentation/d/1lFj5u4lgOwLrUDgj74Jb_eadSx_32QWdmSnZuBj68d0/edit#slide=id.g8d7aafbe1b_0_108)

Implement the inverse Fourier transform in the method inverseFourierTransfrom().

You may use methods declared in the class Complex.java for your convenience.

The solution files are provided for qualitative comparison. Output could be different because of differences in floating point arithmetic and differences in the way the rescaling is performed. 

**/

import java.time.Instant;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class Lab3_2 {
	public Lab3_2() {
		Img img = new Img("ic128.png");
		Instant start = Instant.now();
		filterImage(img);
		Instant stop = Instant.now();
		System.out.println("Elapsed time: "+Duration.between(start, stop).getSeconds()+"s");
		img.save();
	}

    public void filterImage(Img i) {
		Complex[] F = fourierTransfrom(i);
		//Your code here
		F[(i.height/2)*i.width+(i.width/2)] = new Complex();
		inverseFourierTransfrom(F, i);
    }

    public Complex[] fourierTransfrom(Img i) {
    	//Change this to your code from 3_1
		Complex[] F = new Complex[i.width*i.height];
		for (int u=0; u<i.height; u++) {
			System.out.println(u);
			for (int v=0; v<i.width; v++) {
				F[u*i.width+v] = new Complex();
				for (int x=0; x<i.height; x++) {
					for (int y=0; y<i.width; y++) {
						double f = (double)(i.img[x*i.width+y]&0xFF) * Math.pow(-1, x+y);
						double theta = -2 * Math.PI * (u*x/(double)i.height + v*y/(double)i.width);
						F[u*i.width+v].plus(new Complex(Math.cos(theta)*f, Math.sin(theta)*f));

					}
				}
			}
		}
		return F;
	}

	private void inverseFourierTransfrom(Complex[] F, Img i) {
		//Your code here
		for (int x=0; x<i.height; x++) {
			System.out.println(x);
			for ( int y=0; y<i.width; y++) {
				Complex c = new Complex();
				for (int u=0; u<i.height; u++) {
					for (int v=0; v<i.width; v++) {
						double theta = 2 * Math.PI * (u*x/(double)i.height + v*y/(double)i.width);
						Complex e = new Complex(Math.cos(theta), Math.sin(theta));
						e.mul(F[u*i.width+v]);
						c.plus(e);
					}
				}
				c.div(i.height*i.width);
				c.mul(Math.pow(-1, x+y));
				if (c.r<0) c.r=0;
				i.img[x*i.width+y] = (byte)c.r;
			}
		}
	} 

	public static void main(String[] args) {
		new Lab3_2();
	}
}
