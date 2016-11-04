package com.histobasedimageretrieval;

import java.io.Serializable;
/**
 * This abstract class can be extends by classes representing Histograms
 * @author Loïc Carbonne
 *
 */
abstract public class Histogram  implements Serializable {

	private static final long serialVersionUID = -5694010895659101237L;
	
	protected int binsNumber;
	protected String imgName;
	/**
	 * Calculate a score between this Histogram and another Histogram
	 * @param h the histogram to compare
	 * @return the distance
	 */
	abstract public double distance(Histogram h);
	public int getBinsNumber() {
		return binsNumber;
	}
	public void setBinsNumber(int binsNumber) {
		this.binsNumber = binsNumber;
	}
	public String getImgName() {
		return imgName;
	}
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
}
