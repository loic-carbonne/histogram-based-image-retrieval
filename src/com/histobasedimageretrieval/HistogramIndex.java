package com.histobasedimageretrieval;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.activation.MimetypesFileTypeMap;
/**
 * This class correspond to histograms index
 * @author Loïc Carbonne
 *
 */
public class HistogramIndex implements Serializable{
	
	private static final long serialVersionUID = -6399098008553214097L;

	private Histogram[] histogramList;
	
	private char type;
	
	private int binNumber;
	
	/**
	 * Construct an index from a serialized file
	 * @param fileLocation the serialized file
	 */
	public HistogramIndex(String fileLocation){
		try {
			FileInputStream streamIn = new FileInputStream(fileLocation);
			ObjectInputStream objectinputstream = new ObjectInputStream(streamIn);
			HistogramIndex hi = (HistogramIndex) objectinputstream.readObject();
			objectinputstream.close();
			this.histogramList = hi.getHistogramList();
			this.type = hi.getType();
			this.binNumber = hi.getBinNumber();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * Construct an index taking all images contained in the given folder,
	 * using given histogram type and bin number.
	 * @param folderLocation the folder where images to index are located
	 * @param histogramType the type of histogram ('G':Gray level based;'C':Color level based)
	 * @param binNumber the number of bins to use (the precision)
	 */
	public HistogramIndex(String folderLocation, char histogramType, int binNumber){
		this.type=histogramType;
		this.binNumber=binNumber;
		ArrayList<String> listImages = new ArrayList<String>();
		File folder = new File(folderLocation);
	    for (final File fileEntry : folder.listFiles()) {
	        if (!fileEntry.isDirectory()) {
	            if(new MimetypesFileTypeMap().getContentType(fileEntry).split("/")[0].equals("image"));
	            listImages.add(fileEntry.getName());
	        }
	    }
	    histogramList = new Histogram[listImages.size()];
	    int i = 0;
		try {
		    for(String imageName : listImages){
		    	if(histogramType=='G'){
		    		histogramList[i++]=new GrayLevelHistogram(folderLocation+imageName, binNumber);
		    	}else if(histogramType=='C'){
		    		histogramList[i++]=new RGBLevelHistogram(folderLocation+imageName, binNumber);
		    	}else{
					throw new Exception("Histogram type not understanded");
		    	}
		    }
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * Serialize the index into a file
	 * @param filename the name where the index will be saved
	 */
	public void saveInFile(String filename){
		try{
			FileOutputStream fout = new FileOutputStream(filename);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(this);
			oos.close();
		} catch (IOException e){
			System.err.println("Error while writing in file "+filename);
		}
	}

	public Histogram[] getHistogramList() {
		return histogramList;
	}

	public void setHistogramList(Histogram[] histogramList) {
		this.histogramList = histogramList;
	}

	public char getType() {
		return type;
	}

	public void setType(char type) {
		this.type = type;
	}

	public int getBinNumber() {
		return binNumber;
	}

	public void setBinNumber(int binNumber) {
		this.binNumber = binNumber;
	}
	
	private transient SearchResult[] searchResults;
	
	/**
	 * The function add a result to array searchResults
	 * while keeping the array ordered by score.
	 * @param r
	 */
    private void addResult(SearchResult r){
		int i = searchResults.length;
		while( i > 0 && (searchResults[i-1]==null || r.getScore() < searchResults[i-1].getScore()) ){
		    --i;
		}
		if( i != searchResults.length ){
		    for(int j = searchResults.length -1 ; j > i ; --j)searchResults[j]=searchResults[j-1];
		    searchResults[i] = r;
		}
    }
    
	/**
	 * Compare a given histogram with all histograms contained in the index,
	 * and return an array ordered with closest histogram first.
	 * @param histoRef the histogram to compare
	 * @return an ordered array of SearchResult
	 */
	public SearchResult[] search(Histogram histoRef){
		return search(histoRef, histogramList.length );
	}
	
	/**
	 * Compare a given histogram with all histograms contained in the index,
	 * and return an array ordered with closest histogram containing k results.
	 * @param histoRef the histogram to compare
	 * @param k the number of result wanted
	 * @return an ordered array of k SearchResult
	 */
	public SearchResult[] search(Histogram histoRef, int k){
		searchResults = new SearchResult[k];
		for(Histogram h : histogramList){
			addResult(new SearchResult(h, histoRef.distance(h)));
		}
		return searchResults;
	}
}
