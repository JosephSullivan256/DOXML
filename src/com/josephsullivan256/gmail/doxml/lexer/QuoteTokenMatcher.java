package com.josephsullivan256.gmail.doxml.lexer;

import com.josephsullivan256.gmail.doxml.Token;
import com.josephsullivan256.gmail.doxml.util.Pair;

public class QuoteTokenMatcher implements TokenMatcher {

	private String quote;
	
	public QuoteTokenMatcher(String quote){
		this.quote = quote;
	}
	
	@Override
	public Pair<Integer,String> match(String str, Token previous) {
		return str.substring(0,Math.min(str.length(),quote.length())).equals(quote) ? Pair.init(quote.length(), quote) : Pair.init(0, "");
	}

}
