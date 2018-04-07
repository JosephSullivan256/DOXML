package com.josephsullivan256.gmail.doxml.lexer;

import com.josephsullivan256.gmail.doxml.Token;

public class QuoteTokenMatcher implements TokenMatcher {

	private String quote;
	
	public QuoteTokenMatcher(String quote){
		this.quote = quote;
	}
	
	@Override
	public boolean matches(String str, Token previous) {
		return quote.substring(0,Math.min(str.length(),quote.length())).equals(str);
	}

}
