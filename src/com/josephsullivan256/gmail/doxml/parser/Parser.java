package com.josephsullivan256.gmail.doxml.parser;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import com.josephsullivan256.gmail.doxml.Document;
import com.josephsullivan256.gmail.doxml.Element;
import com.josephsullivan256.gmail.doxml.Token;
import com.josephsullivan256.gmail.doxml.TokenType;

public class Parser {
	
	private Token currentToken = new Token(TokenType.filler,"");
	private ListIterator<Token> it;
	
	public Document parse(List<Token> tokens) throws InvalidSyntaxException{
		
		List<Element> header = new LinkedList<Element>();
		Element root;
		
		it = tokens.listIterator();
		currentToken = it.next();
		
		boolean headerPresent = true;
		while(headerPresent){
			Element headerElement = headerElement();
			if(headerElement != null){
				header.add(headerElement);
			} else {
				headerPresent = false;
			}
		}
		
		root = element();
		
		it = null;
		currentToken = null;
		
		return new Document(header,root);
	}
	
	private TokenAccept accept(TokenType type){
		if(currentToken.getTokenType().equals(type)) {
			String temp = currentToken.getToken();
			if(it.hasNext()){
				currentToken = it.next();
			}
			return new TokenAccept(true, temp);
		}
		return new TokenAccept(false, "");
	}
	
	private boolean accept(TokenType type, String quote){
		if(currentToken.getTokenType().equals(type) && currentToken.getToken().equals(quote)){
			if(it.hasNext()){
				currentToken = it.next();
			}
			return true;
		}
		return false;
	}
	
	private String expect(TokenType type) throws InvalidSyntaxException{
		TokenAccept temp = accept(type);
		if(!temp.accepted) throw new InvalidSyntaxException();
		return temp.token;
	}
	
	private boolean expect(TokenType type, String quote) throws InvalidSyntaxException{
		if(!accept(type,quote)) throw new InvalidSyntaxException();
		return true;
	}
	
	private Element element() throws InvalidSyntaxException{
		boolean begins = accept(TokenType.separator,"<");
		if(!begins) return null;
		
		String name = expect(TokenType.identifier);
		
		Element element = new Element(name);
		
		boolean attributesPresent = true;
		
		while(attributesPresent){
			TokenAccept key = accept(TokenType.identifier);
			if(key.accepted){
				expect(TokenType.separator,"=");
				String value = expect(TokenType.literal);
				element.addAttribute(key.token, value.substring(1,value.length()-1));
			} else {
				attributesPresent = false;
			}
		}
		
		expect(TokenType.separator,">");
		
		
		TokenAccept text = accept(TokenType.literal);
		if(text.accepted){
			element.setText(text.token);
		} else {
			boolean childrenPresent = true;
			while(childrenPresent){
				Element child = element();
				if(child != null) {
					element.addChild(child);
				} else {
					childrenPresent = false;
				}
			}
		}
		
		expect(TokenType.separator,"</");
		expect(TokenType.identifier,name);
		expect(TokenType.separator,">");
		
		return element;
	}
	
	private Element headerElement() throws InvalidSyntaxException{
		boolean begins = accept(TokenType.separator,"<?");
		if(!begins) return null;
		
		String name = expect(TokenType.identifier);
		
		Element element = new Element(name,true);
		
		boolean attributesPresent = true;
		
		while(attributesPresent){
			TokenAccept key = accept(TokenType.identifier);
			if(key.accepted){
				expect(TokenType.separator,"=");
				String value = expect(TokenType.literal);
				element.addAttribute(key.token, value.substring(1,value.length()-1));
			} else {
				attributesPresent = false;
			}
		}
		
		expect(TokenType.separator,"?>");
		
		return element;
	}
}
