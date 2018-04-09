package com.josephsullivan256.gmail.doxml.lexer;

import com.josephsullivan256.gmail.doxml.Token;
import com.josephsullivan256.gmail.doxml.util.Pair;

public class QuoteArrayTokenMatcher implements TokenMatcher{
	
	private QuoteTokenMatcher[] matchers;
	
	public QuoteArrayTokenMatcher(String[] quotes){
		matchers = new QuoteTokenMatcher[quotes.length];
		for(int i = 0; i < quotes.length; i++){
			matchers[i] = new QuoteTokenMatcher(quotes[i]);
		}
	}

	@Override
	public Pair<Integer,String> match(String str, Token previous) {
		
		Pair<Integer,String> longest = matchers[0].match(str, previous);
		
		for(QuoteTokenMatcher m: matchers){
			Pair<Integer,String> match = m.match(str, previous);
			if(match.a > longest.a) longest = match;
		}
		
		if(longest.a <= 0) return Pair.init(0, "");
		return longest;
	}
}
