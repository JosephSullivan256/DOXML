package com.josephsullivan256.gmail.doxml.lexer;

import java.util.ArrayList;
import java.util.List;

import com.josephsullivan256.gmail.doxml.Token;
import com.josephsullivan256.gmail.doxml.TokenType;
import com.josephsullivan256.gmail.doxml.util.Pair;

public class LexerBuilder {
	
	private List<Pair<TokenType,TokenMatcher>> tokenTypes;
	
	public LexerBuilder() {
		this.tokenTypes = new ArrayList<Pair<TokenType,TokenMatcher>>();
	}
	
	public LexerBuilder with(TokenType tokenType, TokenMatcher matcher) {
		tokenTypes.add(new Pair<TokenType,TokenMatcher>(tokenType, matcher));
		return this;
	}
	
	private static class InnerLexer implements Lexer{
		
		private List<Pair<TokenType,TokenMatcher>> tokenTypes;
		
		public InnerLexer(List<Pair<TokenType,TokenMatcher>> tokenTypes) {
			this.tokenTypes = tokenTypes;
		}
		
		@Override
		public List<Token> lex(String input) {
			List<Token> tokens = new ArrayList<Token>();
			
			Token previous = null;
			int index = 0;
			
			while(index < input.length()){
				Pair<TokenType,Pair<Integer,String>> match = match(input.substring(index),previous);
				if(match.b.a == 0) index++;
				else {
					Token current = new Token(match.a,match.b.b);
					tokens.add(current);
					previous = current;
					index+=match.b.a;
				}
			}
			
			return tokens;
		}
		
		private Pair<TokenType,Pair<Integer,String>> match(String str,Token previous) {
			
			Pair<TokenType,TokenMatcher> firstEntry = tokenTypes.get(0);
			Pair<TokenType,Pair<Integer,String>> longest = Pair.init(firstEntry.a,firstEntry.b.match(str, previous));
			
			for(Pair<TokenType,TokenMatcher> entry : tokenTypes) {
				Pair<TokenType,Pair<Integer,String>> temp = Pair.init(entry.a,entry.b.match(str, previous));
				if(temp.b.a > longest.b.a) longest = temp;
			}
			
			return longest;
		}
		
	}
	
	public Lexer build() {
		return new InnerLexer(tokenTypes);
	}
}
