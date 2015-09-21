package org.vizweb.quadtree;

import java.awt.image.BufferedImage;

import org.sikuli.core.cv.ImagePreprocessor;
import org.vizweb.structure.EntropyComputer;

import com.googlecode.javacv.cpp.opencv_core.IplImage;

public class IntensityEntropyDecompositionStrategy implements QuadTreeDecompositionStrategy{

	public double computeNodeFeature(IplImage image) {
		return EntropyComputer.computeIntensityEntropy(image);
	}

	public boolean isNodeDecomposable(double featureValue) {
		return featureValue > 280;
	}

	public IplImage preprocess(BufferedImage image) {
		return ImagePreprocessor.createLab(image);
	}	

}