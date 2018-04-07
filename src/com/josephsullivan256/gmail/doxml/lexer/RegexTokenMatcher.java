package com.josephsullivan256.gmail.doxml.lexer;

import java.util.regex.Pattern;

import com.josephsullivan256.gmail.doxml.Token;

public class RegexTokenMatcher implements TokenMatcher{

	private Pattern p;
	
	public RegexTokenMatcher(String regex){
		this.p = Pattern.compile(regex);
	}
	
	@Override
	public boolean matches(String str, Token previous) {
		return p.matcher(str).matches();
	}

}
