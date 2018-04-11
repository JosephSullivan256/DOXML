package com.josephsullivan256.gmail.doxml;

import java.util.List;

import com.josephsullivan256.gmail.doxml.lexer.Lexer;
import com.josephsullivan256.gmail.doxml.lexer.LexerBuilder;
import com.josephsullivan256.gmail.doxml.lexer.QuoteArrayTokenMatcher;
import com.josephsullivan256.gmail.doxml.lexer.RegexTokenMatcher;
import com.josephsullivan256.gmail.doxml.lexer.TokenMatcher;
import com.josephsullivan256.gmail.doxml.parser.InvalidSyntaxException;
import com.josephsullivan256.gmail.doxml.parser.Parser;

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
	
	public static Document parse(String source) throws InvalidSyntaxException{
		Lexer lexer = new LexerBuilder()
				.with(TokenType.separator, new QuoteArrayTokenMatcher(new String[]{"<","<?","<!","</","?>",">","="}))
				.with(TokenType.literal, TokenMatcher.literalTokenMatcher1)
				.with(TokenType.identifier, new RegexTokenMatcher("[a-zA-Z0-9_:]*"))
				.with(TokenType.literal, TokenMatcher.literalTokenMatcher)
				.build();
		Parser parser = new Parser();
		return parser.parse(lexer.lex(source));
	}
}
