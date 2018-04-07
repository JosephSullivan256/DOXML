package com.josephsullivan256.gmail.doxml;

import java.util.List;

public class Document {
	
	private List<Element> header;
	private Element root;
	
	public Document(List<Element> header, Element root){
		this.header = header;
		this.root = root;
	}
	
	public List<Element> getHeaderElements(){
		return header;
	}
	
	public Element getRoot(){
		return root;
	}
	
	@Override
	public String toString(){
		String temp = "";
		for(Element headerElement: header){
			temp+=headerElement.toString();
		}
		temp+=root.toString();
		return temp;
	}
}
