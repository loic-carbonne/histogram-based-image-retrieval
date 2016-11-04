package com.histobasedimageretrieval;

import java.util.Arrays;

/**
 * This class correspond to an image histogram based on RGB level
 * @author Loïc Carbonne
 *
 */
public class RGBLevelHistogram extends Histogram{

	private static final long serialVersionUID = -1941025793981454089L;
	
	private int [][][] histogram;
	
	private RGBLevelHistogram(JPicture picture, int binsNumber){
		this.binsNumber = binsNumber;
		histogram = new int[binsNumber][binsNumber][binsNumber];
		double interval = 1/(double)binsNumber;
        for(int i=0 ; i < binsNumber ; ++i){
        	for( int j=0 ; j < binsNumber ; ++j){
        			for( int k=0; k < binsNumber ; ++k){
        				histogram[i][j][k]=0;
        			}
        		}
        }
        for(int i = 0; i < picture.width; ++i) {
            for(int j = 0; j < picture.height; ++j) {
                int r,g,b;
				r = 0;
				g = 0;
				b = 0;
				while( picture.pixels[i][j][JPicture.RED] > interval*(r+1)){
				    ++r;
				}
				while( picture.pixels[i][j][JPicture.GREEN] > interval*(g+1)){
				    ++g;
				}
			    while( picture.pixels[i][j][JPicture.BLUE] > interval*(b+1)){
				    ++b;
				}
			    histogram[r][g][b]=histogram[r][g][b]+1;
		    }
        }
	}
	
	public RGBLevelHistogram(String pictureLocation, int binsNumber){
		this(new JPicture(pictureLocation),binsNumber);
		this.imgName=pictureLocation.substring(pictureLocation.lastIndexOf("/")+1);
	}
	
	public int[][][] getHistogram() {
		return histogram;
	}
	
	public void setHistogram(int[][][] histogram) {
		this.histogram = histogram;
	}
	
	@Override
	public String toString() {
		return "RGBLevelHistogram [histogram=" + Arrays.toString(histogram) + "]";
	}

	/**
	 * @see Histogram#distance(Histogram,...)
	 */
	double distance(RGBLevelHistogram h) {
		double retour = 0;
		for( int i = 0 ; i < histogram.length ; ++i ){
			for( int j = 0 ; j < histogram.length ; ++j ){
				for( int k = 0 ; k < histogram.length ; ++k ){
					retour += Math.pow((histogram[i][j][k]-h.getHistogram()[i][j][k]),2.0);
				}
			}
		}
		return Math.pow(retour, 0.5);
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public double distance(Histogram h) {
		return distance((RGBLevelHistogram)h);
	}

}