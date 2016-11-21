package com.imgretrievaldemo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.springframework.web.multipart.MultipartFile;

import com.histobasedimageretrieval.GrayLevelHistogram;
import com.histobasedimageretrieval.Histogram;
import com.histobasedimageretrieval.HistogramIndex;
import com.histobasedimageretrieval.RGBLevelHistogram;
import com.histobasedimageretrieval.SearchResult;

public class Tools {
	static Path temp;
	static final String INDEX_FOLDER_LOCATION= "src/main/webapp/indexes/";
	static final String IMAGES_FOLDER_LOCATION= "src/main/resources/static/images/";
	static final String TEMP_FOLDER_LOCATION= "src/main/resources/static/search/";
	
	static public ArrayList<Integer> getIndexes(char type){
		ArrayList<Integer> ret = new ArrayList<Integer>();
	    for (final File fileEntry : (new File(INDEX_FOLDER_LOCATION)).listFiles()) {
	        if (!fileEntry.isDirectory() && fileEntry.getName().charAt(0) == type) {
	            ret.add(Integer.parseInt(fileEntry.getName().substring(1)));
	        }
	    }
		return ret;
	}
	
	static void deleteIndex(String type, Integer bin){
		File f = new File(INDEX_FOLDER_LOCATION+type+bin);
		f.delete();
	}
	
	static void createIndex(String type, Integer bin){
		HistogramIndex hi = new HistogramIndex(IMAGES_FOLDER_LOCATION, type.charAt(0), bin);
		hi.saveInFile(INDEX_FOLDER_LOCATION+type+bin);
	}
	
	static SearchResult[] search(MultipartFile file,String type, Integer bin){
		temp = Paths.get("");
		temp = temp.resolve(TEMP_FOLDER_LOCATION);
		temp = temp.resolve(file.getOriginalFilename());
		try {
			Files.copy(file.getInputStream(),temp);
		} catch (IOException e) {
		}
		HistogramIndex hi = new HistogramIndex(INDEX_FOLDER_LOCATION+type+bin);
		Histogram histo = null;
		if(type.equals("C")){
			histo = new RGBLevelHistogram(temp.toString(), bin);
		}else{
			histo = new GrayLevelHistogram(temp.toString(), bin);
		}
		File f = new File(temp.toString());
		f.delete();
		return hi.search(histo,50);
	}
}
