package org.usfirst.frc.team4738.robot;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.RawData;


public class ImageUtils {

	/**
	 * Resizes an image using a Graphics2D object backed by a BufferedImage.
	 * @param srcImg - source image to scale
	 * @param w - desired width
	 * @param h - desired height
	 * @return - the new resized image
	 */
	public static BufferedImage getScaledImage(Image srcImg, int w, int h){
	    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TRANSLUCENT);
	    Graphics2D g2 = resizedImg.createGraphics();
	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, w, h, null);
	    g2.dispose();
	    return resizedImg;
	}
	public static  ByteBuffer bufftobyte(BufferedImage image){
		
		BufferedImage originalImage =image;
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write( originalImage, "jpg", baos );
			baos.flush();
			byte[] imageInByte = baos.toByteArray();
			baos.close();
			ByteBuffer buf = ByteBuffer.wrap(imageInByte);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	
	}
	
	public static BufferedImage bytetobuff(ByteBuffer buff){
		byte[] data= buff.array();	
	
		InputStream in = new ByteArrayInputStream(data);


		try {
			BufferedImage bImageFromConvert = ImageIO.read(in);
			ImageIO.write(bImageFromConvert, "jpg", new File(
					"c:/new-darksouls.jpg"));
			return bImageFromConvert;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static  ByteBuffer scaleDown(ByteBuffer bb){
		
		return bufftobyte(getScaledImage(bytetobuff(bb),10,10));
	}
	
	
	
}
