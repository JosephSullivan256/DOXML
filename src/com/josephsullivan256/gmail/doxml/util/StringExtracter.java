package com.josephsullivan256.gmail.doxml.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class StringExtracter {
	
	public StringExtracter(){
		
	}
	
	public String read(InputStream in){
		Scanner s = new Scanner(in);
		s.useDelimiter("\\A");
		String t = s.hasNext() ? s.next() : "";
		s.close();
		return t;
	}
	
	public String read(File f) throws FileNotFoundException{
		return read(new FileInputStream(f));
	}
	
	public String read(String path){
		return read(getClass().getResourceAsStream(path));
	}
}
