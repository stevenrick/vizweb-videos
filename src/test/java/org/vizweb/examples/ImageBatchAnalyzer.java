package org.vizweb.examples;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import javax.imageio.ImageIO;

import org.vizweb.ColorFeatureComputer;
import org.vizweb.XYFeatureComputer;
import org.vizweb.color.NamedColor;
import org.vizweb.QuadtreeFeatureComputer;
import org.vizweb.quadtree.Quadtree;
import org.vizweb.structure.Block;

public class ImageBatchAnalyzer {
	
	public static void main(String[] args) throws IOException {
		
		File folder = new File("C:\\Users\\Stoves\\Desktop\\image_analysis");
		File[] files = folder.listFiles();
		
		for (File file : files) {
			
			BufferedImage input = ImageIO.read(file);

			/*******************************
			 * Compute color features
			 *******************************/
			
//			Map<NamedColor, Double> colorDist = ColorFeatureComputer.computeColorDistribution(input);
//			System.out.println("Color distribution: " + colorDist);
			
//			double[] hue = ColorFeatureComputer.computeAverageHSV(input);
//			System.out.println("Average Hue: " + Arrays.toString(hue));
			
			double col = ColorFeatureComputer.computeColorfulness(input);
			System.out.println("Colorfulness (method 1): " + col);

			double col2 = ColorFeatureComputer.computeColorfulness2(input);
			System.out.println("Colorfulness (method 2): " + col2);

			/*******************************
			 * Compute xy decomposition features (it may take a while)
			 *******************************/
			Block root = XYFeatureComputer.getXYBlockStructure(input);
			
			ImageIO.write(root.toImage(input), "png",
					new File("C:\\Users\\Stoves\\Desktop\\image_analysis\\"+
							file.getName().replaceFirst("[.][^.]+$", "")+"_decomp.png"));
			
			System.out.println("Number of leaves: " + XYFeatureComputer.countNumberOfLeaves(root));
			System.out.println("Number of group of text: " + XYFeatureComputer.countNumberOfTextGroup(root));

			/*******************************
			 * Compute quadtree features
			 *******************************/
			Quadtree qtColor = QuadtreeFeatureComputer.getQuadtreeColorEntropy(input);
			
			double hBalance = QuadtreeFeatureComputer.computeHorizontalBalance(qtColor);
			System.out.println("Horizontal Balance: " + hBalance);
			
			double hSymmetry = QuadtreeFeatureComputer.computeHorizontalSymmetry(qtColor);
			System.out.println("Horizontal Symmetry: " + hSymmetry);
			
			double vBalance = QuadtreeFeatureComputer.computeVerticalBalance(qtColor);
			System.out.println("Vertical Balance: " + vBalance);
			
			double vSymmetry = QuadtreeFeatureComputer.computeVerticalSymmetry(qtColor);
			System.out.println("Vertical Symmetry: " + vSymmetry);
		}
		
	}

}
