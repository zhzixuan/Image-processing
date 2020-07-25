/**

In this task you will implement the Fourier transform and change the image to the Fourier spectrum in the method fourierSpectrum(). Your task is to implement the missing code in the method fourierTransform(). 

Use the log transformation and ensure that all values are in the range 0 ... 255. 

You may use methods declared in the class Complex.java for your convenience.

The solution files are provided for qualitative comparison. Output could be different because of differences in floating point arithmetic and differences in the way the rescaling is performed. 

**/

import java.time.Instant;
import java.time.Duration;
public class Lab3_1 {
	public Lab3_1() {
		Img img = new Img("ic256.png");
		Instant start = Instant.now();
		fourierSpectrum(img);
		Instant stop = Instant.now();
		System.out.println("Elapsed time: "+Duration.between(start, stop).getSeconds()+"s");
		img.save();
	}

    public void fourierSpectrum(Img i) {
    	Complex[] F = fourierTransfrom(i);
		double max = Double.NEGATIVE_INFINITY;
		for (int x = 0; x < F.length; x++)
			max = Math.max(F[x].getNorm(), max);
		for (int x = 0; x < i.img.length; x++)
			i.img[x] = (byte)(255 / Math.log(256)*Math.log(255/max*F[x].getNorm()+1));
    }

    public Complex[] fourierTransfrom(Img i) {
    	//Change this code
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
		
	public static void main(String[] args) {
		new Lab3_1();
	}
}
