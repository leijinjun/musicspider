package musicspider;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;

public class Image {

	public static int comperssImage(File src,File dest, float quality){
		try {
			Thumbnails.of(src).scale(1).outputQuality(quality).toFile(dest);
			return 1;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public static int rorateImage(File src,File dest,int rorate){
		try {
			BufferedImage srcImg = ImageIO.read(src);
			Thumbnails.of(srcImg).size(srcImg.getWidth(), srcImg.getHeight()).rotate(rorate).toFile(dest);
//			Thumbnails.of(src).size(500, 1800).rotate(rorate).toFile(dest);
			return 1;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}
}
