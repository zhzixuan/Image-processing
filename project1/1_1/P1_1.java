/**

 In this task 1 of project 1 you will implement the fast Fourier transform and change the image to the Fourier spectrum in the method fourierSpectrum(). Your task is to implement the missing code in the method fastFourierTransform(). The implementation details of the FFT can be obtained in section 4.11 of our Textbook.

 Use the log transformation and ensure that all values are in the range 0 ... 255.

 You may use methods declared in the class Complex.java for your convenience and you may add new methods to that class if necessary.

 The solution files are provided for qualitative comparison. Output could be different because of differences in floating point arithmetic and differences in the way the rescaling is performed.

 For your reference, we are able generate the Fourier spectrum of the file rectangle1024.png in < 1 seconds.

 **/

import java.time.Duration;
import java.time.Instant;

public class P1_1 {
	public P1_1() {
		Img img = new Img("ic512.png");
		Instant start = Instant.now();
		fourierSpectrum(img);
		Instant stop = Instant.now();
		System.out.println("Elapsed time: " + Duration.between(start, stop).getSeconds() + "s");
//		System.out.println("Elapsed time: " + Duration.between(start, stop).toMillis() + "ms");
		img.save();
	}

	public void fourierSpectrum(Img i) {
		Complex[] F = fastFourierTransfrom(i);
		double max = Double.NEGATIVE_INFINITY;
		for (int x = 0; x < F.length; x++)
			max = Math.max(F[x].getNorm(), max);
		for (int x = 0; x < i.img.length; x++)
			i.img[x] = (byte) (255 / Math.log(256) * Math.log(255 / max * F[x].getNorm() + 1));
	}

	public Complex[] FFT(Complex[] row) {
		int n = row.length;
		if (n == 1) {
			return new Complex[]{row[0]};
		}

		int K = n / 2;
		Complex[] even = new Complex[K];
		for (int x = 0; x < K; x++) {
			even[x] = row[2 * x];
		}
		Complex[] evenFFT = FFT(even);

		Complex[] odd = even;
		for (int x = 0; x < K; x++) {
			odd[x] = row[2 * x + 1];
		}
		Complex[] oddFFT = FFT(odd);

		Complex[] F = new Complex[n];
		for (int u = 0; u < K; u++) {
			double theta = -2 * Math.PI * u / (double) n;
			Complex W = new Complex(Math.cos(theta), Math.sin(theta));
			W.mul(oddFFT[u]);
			F[u] = new Complex();
			F[u].plus(evenFFT[u]);
			F[u].plus(W);
			F[u + K] = new Complex();
			F[u + K].plus(evenFFT[u]);
			F[u + K].minus(W);
		}
		return F;
	}

	public Complex[] fastFourierTransfrom(Img i) {
		//Change this code
		// fft for row
		Complex[] F_row = new Complex[i.width * i.height];
		for (int x = 0; x < i.height; x++) {
			Complex[] row = new Complex[i.width];
			for (int y = 0; y < i.width; y++) {
				row[y] = new Complex((double) (i.img[x * i.width + y] & 0xFF) * Math.pow(-1, x + y), 0);
			}
			Complex[] fft = FFT(row);
			for (int y = 0; y < i.width; y++) {
				F_row[x * i.width + y] = fft[y];
			}
		}

		// fft for column
		Complex[] F = new Complex[i.width * i.height];
		for (int y = 0; y < i.width; y++) {
			Complex[] column = new Complex[i.height];
			for (int x = 0; x < i.height; x++) {
				column[x] = F_row[x * i.width + y];
			}
			Complex[] fft = FFT(column);
			for (int x = 0; x < i.height; x++) {
				F[x * i.width + y] = fft[x];
			}
		}
		return F;
	}

	public static void main(String[] args) {
		new P1_1();
	}
}
