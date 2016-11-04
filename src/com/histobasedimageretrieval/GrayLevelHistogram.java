package com.histobasedimageretrieval;

import java.io.Serializable;
import java.util.Arrays;

/**
 * This class correspond to an image histogram based on gray level
 * @author Loïc Carbonne
 *
 */
public class GrayLevelHistogram extends Histogram implements Serializable{
	
	private static final long serialVersionUID = 3688896742360518215L;
	
	private int [] histogram;
	
	/**
	 * Construct a Gray level based histogram for a picture with a given bins number.
	 * @param picture the picture
	 * @param binsNumber the number of bins to use
	 */
	private GrayLevelHistogram(JPicture picture, int binsNumber){
		this.binsNumber = binsNumber;
		histogram = new int[binsNumber];
		double interval = 1/(double)binsNumber;
        for(int i=0 ; i < binsNumber ; ++i){
        	histogram[i]=0;
        }
        double niveauGris;
        for(int i = 0; i < picture.width; ++i) {
            for(int j = 0; j < picture.height; ++j) {
            	niveauGris = (0.299*picture.pixels[i][j][JPicture.RED]) +
            			(0.587*picture.pixels[i][j][JPicture.GREEN]) +
            			(0.114*picture.pixels[i][j][JPicture.BLUE]);
                int l = 0;
				while(niveauGris > interval*(l+1)){
				    ++l;
				}
				histogram[l]=histogram[l]+1;
            }
        }
	}
	
	/**
	 * Construct a Gray level based histogram for a picture with a given bins number.
	 * @param pictureLocation the location of the picture
	 * @param binsNumber the number of bins to use
	 */
	public GrayLevelHistogram(String pictureLocation, int binsNumber){
		this(new JPicture(pictureLocation),binsNumber);
		this.imgName=pictureLocation.substring(pictureLocation.lastIndexOf("/")+1);
	}
	
	public int[] getHistogram() {
		return histogram;
	}
	public void setHistogram(int[] histogram) {
		this.histogram = histogram;
	}
	@Override
	public String toString() {
		return "GrayLevelHistogram [histogram=" + Arrays.toString(histogram) + "]";
	}

	/**
	 * @see Histogram#distance(Histogram,...)
	 */
	private double distance(GrayLevelHistogram h) {
		double retour = 0;
		for( int i = 0 ; i < histogram.length ; ++i ){
		    retour += Math.pow((histogram[i]-h.getHistogram()[i]),2.0);
		}
		return Math.pow(retour, 0.5);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double distance(Histogram h) {
		return distance((GrayLevelHistogram)h);
	}

}
