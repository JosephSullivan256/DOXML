package com.josephsullivan256.gmail.doxml.lexer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.josephsullivan256.gmail.doxml.Token;
import com.josephsullivan256.gmail.doxml.util.Pair;

public class RegexTokenMatcher implements TokenMatcher{

	private Pattern p;
	
	public RegexTokenMatcher(String regex){
		this.p = Pattern.compile(regex);
	}
	
	@Override
	public Pair<Integer,String> match(String str, Token previous) {
		Matcher matcher = p.matcher(str);
		boolean found = matcher.find();
		if(!found) return Pair.init(0, "");
		int start = matcher.start();
		if(start != 0) return Pair.init(0, "");
		int end = matcher.end();
		return Pair.init(end,str.substring(0,end));
	}

}
