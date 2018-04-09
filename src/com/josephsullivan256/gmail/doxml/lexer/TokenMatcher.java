package com.josephsullivan256.gmail.doxml.lexer;

import com.josephsullivan256.gmail.doxml.Token;
import com.josephsullivan256.gmail.doxml.util.Pair;

public interface TokenMatcher {
	public Pair<Integer,String> match(String str, Token previous);
	
	public static final TokenMatcher literalTokenMatcher = new TokenMatcher(){
		@Override
		public Pair<Integer,String> match(String str, Token previous) {
			if(!str.substring(0,1).equals("\"")) return Pair.init(0, "");
			
			for(int i = str.indexOf("\"",1); i > 0; i=str.indexOf("\"",i)){
				if(!str.substring(i-1,i).equals("\\")) return Pair.init(i+1, str.substring(0,i+1));
			}
			
			return Pair.init(0, "");
		}
	};
	
	public static final TokenMatcher literalTokenMatcher1 = new TokenMatcher(){
		@Override
		public Pair<Integer,String> match(String str, Token previous) {
			if(previous != null && !previous.getToken().equals(">")) return Pair.init(0, "");
			if(previous == null) return Pair.init(0, "");
			int end = str.indexOf("<");
			if(end < 0) return Pair.init(0, "");
			String tempWithNewlines = str.substring(0, end);
			String temp = tempWithNewlines.replace("\n", "").replace("\r", "").trim();
			
			if(temp.isEmpty()) return Pair.init(0, "");
			
			return Pair.init(temp.length(), temp);
		}
	};
}
