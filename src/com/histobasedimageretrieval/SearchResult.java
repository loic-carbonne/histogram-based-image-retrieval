package com.histobasedimageretrieval;
/**
 * This class is used to store results of comparaison between histogram and their score
 * @author Loïc Carbonne
 *
 */
public class SearchResult{
	private double score;
	private Histogram histogram;
	public SearchResult(Histogram histo,double score){
	    this.score=score;
	    this.histogram=histo;
	}
	public double getScore(){
	    return score;
	}
	public Histogram getHistogram(){
	    return histogram;
	}
}