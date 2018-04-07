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
			
			int xi = 0;
			int xf = 1;
			int len = input.length();
			
			TokenType previousType = null;
			
			while(xf <= len) {
				Pair<Boolean,TokenType> temp = match(input.substring(xi,xf),tokens);
				if(!temp.a) {
					if(previousType!=null) {
						tokens.add(new Token(previousType,input.substring(xi,xf-1)));
						xi = xf-1;
						//xf++;
					} else {
						xi = xf;
						xf++;
					}
					previousType = null;
				} else {
					previousType = temp.b;
					xf++;
				}
			}
			String substr = input.substring(xi,xf-1);
			Pair<Boolean,TokenType> finalTemp = match(substr,tokens);
			if(finalTemp.a) {
				tokens.add(new Token(finalTemp.b,substr));
			}
			
			return tokens;
		}
		
		private Pair<Boolean,TokenType> match(String str,List<Token> tokens) {
			for(Pair<TokenType,TokenMatcher> entry : tokenTypes) {
				Token previous = null;
				if(tokens.size()-1 >= 0) previous = tokens.get(tokens.size()-1);
				if(entry.b.matches(str,previous)) {
					return new Pair<Boolean,TokenType>(true,entry.a);
				}
			}
			return new Pair<Boolean,TokenType>(false,null);
		}
		
	}
	
	public Lexer build() {
		return new InnerLexer(tokenTypes);
	}
}
