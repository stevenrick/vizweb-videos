package org.vizweb.examples;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
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
		
		File folder = new File("/VOLUMES/SSD/IMAGES");
		File[] files = folder.listFiles();
		
		String output = "/VOLUMES/SSD/IMAGES/results.csv";
		FileWriter writer = new FileWriter(output);
		 
	    writer.append("Image");
	    writer.append(',');
	    writer.append("Colorfulness_1");
	    writer.append(',');
	    writer.append("Colorfulness_2");
	    writer.append(',');
	    writer.append("Leaves");
	    writer.append(',');
	    writer.append("Groups_Text");
	    writer.append(',');
	    writer.append("H_Balance");
	    writer.append(',');
	    writer.append("H_Symm");
	    writer.append(',');
	    writer.append("V_Balance");
	    writer.append(',');
	    writer.append("V_Symm");
	    writer.append('\n');
	    writer.flush();
		
		for (File file : files) {
			
			System.out.println("Processing: " + file.getName());
			
			BufferedImage input = ImageIO.read(file);

			/*******************************
			 * Compute color features
			 *******************************/
			
//			Map<NamedColor, Double> colorDist = ColorFeatureComputer.computeColorDistribution(input);
//			System.out.println("Color distribution: " + colorDist);
			
//			double[] hue = ColorFeatureComputer.computeAverageHSV(input);
//			System.out.println("Average Hue: " + Arrays.toString(hue));
			
			double col = ColorFeatureComputer.computeColorfulness(input);
			//System.out.println("Colorfulness (method 1): " + col);

			double col2 = ColorFeatureComputer.computeColorfulness2(input);
			//System.out.println("Colorfulness (method 2): " + col2);

			/*******************************
			 * Compute xy decomposition features (it may take a while)
			 *******************************/
			Block root = XYFeatureComputer.getXYBlockStructure(input);
			
//			ImageIO.write(root.toImage(input), "png",
//					new File("C:\\Users\\Stoves\\Desktop\\image_analysis\\"+
//							file.getName().replaceFirst("[.][^.]+$", "")+"_decomp.png"));
			
			int leaves = XYFeatureComputer.countNumberOfLeaves(root);
			//System.out.println("Number of leaves: " + leaves);
			
			int textGroups = XYFeatureComputer.countNumberOfTextGroup(root);
			//System.out.println("Number of group of text: " + textGroups);

			/*******************************
			 * Compute quadtree features
			 *******************************/
			Quadtree qtColor = QuadtreeFeatureComputer.getQuadtreeColorEntropy(input);
			
			double hBalance = QuadtreeFeatureComputer.computeHorizontalBalance(qtColor);
			//System.out.println("Horizontal Balance: " + hBalance);
			
			double hSymmetry = QuadtreeFeatureComputer.computeHorizontalSymmetry(qtColor);
			//System.out.println("Horizontal Symmetry: " + hSymmetry);
			
			double vBalance = QuadtreeFeatureComputer.computeVerticalBalance(qtColor);
			//System.out.println("Vertical Balance: " + vBalance);
			
			double vSymmetry = QuadtreeFeatureComputer.computeVerticalSymmetry(qtColor);
			//System.out.println("Vertical Symmetry: " + vSymmetry);
			
		    writer.append(""+file.getName());
		    writer.append(',');
		    writer.append(""+col);
		    writer.append(',');
		    writer.append(""+col2);
		    writer.append(',');
		    writer.append(""+leaves);
		    writer.append(',');
		    writer.append(""+textGroups);
		    writer.append(',');
		    writer.append(""+hBalance);
		    writer.append(',');
		    writer.append(""+hSymmetry);
		    writer.append(',');
		    writer.append(""+vBalance);
		    writer.append(',');
		    writer.append(""+vSymmetry);
		    writer.append('\n');
		    writer.flush();
			
		}
		
	    writer.close();
		
	}

}
