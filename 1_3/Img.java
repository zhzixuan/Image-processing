import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.image.DataBufferByte;

public class Img {
	public byte[] img;
	public int width;
	public int height;
	
	public Img(String fileName) {
		load(fileName);
	}
	
	private void load(String fileName) {
		try {
			BufferedImage bi = (ImageIO.read(new File(fileName)));
			width = bi.getWidth();
			height = bi.getHeight();
			img = ((DataBufferByte)bi.getRaster().getDataBuffer()).getData();
		} catch (Exception ee) {
			ee.printStackTrace();
		}
	}
}
