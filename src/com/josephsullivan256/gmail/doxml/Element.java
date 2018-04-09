package com.josephsullivan256.gmail.doxml;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Element {
	
	private String name;
	private String text;
	
	private boolean isHeader;
	
	private Map<String,String> attributes;
	private List<Element> children;
	
	public Element(String name){
		this.name = name;
		this.text = "";
		this.isHeader = false;
		
		this.attributes = new HashMap<String,String>();
		this.children = new LinkedList<Element>();
	}
	
	public Element(String name, boolean isHeader){
		this(name);
		this.isHeader = isHeader;
	}
	
	public void addAttribute(String key, String value){
		attributes.put(key, value);
	}
	
	public void removeAttribute(String key){
		attributes.remove(key);
	}
	
	public void addChild(Element c){
		children.add(c);
	}
	
	public void removeChild(Element c){
		children.remove(c);
	}
	
	public void setText(String text){
		this.text = text;
	}
	
	public String get(String key){
		return attributes.get(key);
	}
	
	public List<Element> getChildren(ElementCriteria ec){
		List<Element> returnValues = new LinkedList<Element>();
		
		for(Element e: children){
			if(ec.isMetBy(e)){
				returnValues.add(e);
			}
		}
		
		return returnValues;
	}
	
	public Element getChild(ElementCriteria ec){
		for(Element e: children){
			if(ec.isMetBy(e)){
				return e;
			}
		}
		
		return null;
	}
	
	public List<Element> getChildren(String name){
		return getChildren((e)->e.getName().equals(name));
	}
	
	public Element getChild(String name){
		return getChild((e)->e.getName().equals(name));
	}
	
	public List<Element> getChildren(){
		return children;
	}
	
	public String getName(){
		return name;
	}
	
	public String getText(){
		return text;
	}
	
	public static interface ElementCriteria {
		public boolean isMetBy(Element e);
	}
	
	@Override
	public String toString(){
		String temp = "";
		
		if(isHeader){
			temp+="<?";
		} else {
			temp+="<";
		}
		temp+=name;
		for(Entry<String,String> entry: attributes.entrySet()){
			temp+=" ";
			temp+=entry.getKey();
			temp+="=";
			temp+="\"";
			temp+=entry.getValue();
			temp+="\"";
		}
		if(isHeader){
			temp+="?>";
		} else {
			temp+=">";
			temp+=text;
			
			for(Element child: children){
				temp+=child.toString();
			}
			
			temp+="</";
			temp+=name;
			temp+=">";
		}
		
		return temp;
	}
}
