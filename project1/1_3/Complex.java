/**
 * This is a helper class that you may use.
 */
public final class Complex {
	public double r;
	public double i;
    
	public Complex() {
		this.r = 0;
		this.i = 0;
	}
    
	public Complex(double r, double i) {
		this.r = r;
		this.i = i;
	}
      
	public void plus(Complex op) {
		this.r += op.r;
		this.i += op.i;
	}

	public void minus(Complex op) {
		this.r -= op.r;
		this.i -= op.i;
	}
      
	public void mul(double op) {
		this.r *= op;
		this.i *= op;
	}

	public void mul(Complex op) {
		this.r = this.r * op.r - this.i * op.i;
		this.i = this.r * op.i + this.i * op.r;
	}

	public void div(double op) {
		this.r /= op;
		this.i /= op;
	}


	public double getNorm() {
		return Math.sqrt(this.r * this.r + this.i * this.i);
	}
}