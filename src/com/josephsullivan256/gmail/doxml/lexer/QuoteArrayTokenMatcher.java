package com.josephsullivan256.gmail.doxml.lexer;

import com.josephsullivan256.gmail.doxml.Token;

public class QuoteArrayTokenMatcher implements TokenMatcher{
	
	private QuoteTokenMatcher[] matchers;
	
	public QuoteArrayTokenMatcher(String[] quotes){
		matchers = new QuoteTokenMatcher[quotes.length];
		for(int i = 0; i < quotes.length; i++){
			matchers[i] = new QuoteTokenMatcher(quotes[i]);
		}
	}

	@Override
	public boolean matches(String str, Token previous) {
		for(QuoteTokenMatcher m: matchers){
			if(m.matches(str,previous)) return true;
		}
		return false;
	}
}
