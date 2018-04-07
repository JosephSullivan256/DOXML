package com.josephsullivan256.gmail.doxml.lexer;

import com.josephsullivan256.gmail.doxml.Token;

public interface TokenMatcher {
	public boolean matches(String str, Token previous);
	
	public static final TokenMatcher literalTokenMatcher = new TokenMatcher(){
		@Override
		public boolean matches(String str, Token previous) {
			if(!str.substring(0,1).equals("\"")) return false;
			
			for(int i = str.indexOf("\"",1); i > 0 && i < str.length()-1; i=str.indexOf("\"",i)){
				if(!str.substring(i-1,i).equals("\\")) return false;
			}
			
			return true;
		}
	};
	
	public static final TokenMatcher literalTokenMatcher1 = new TokenMatcher(){
		@Override
		public boolean matches(String str, Token previous) {
			if(previous != null && !previous.getToken().equals(">")) return false;
			final String[] nonos = new String[]{"<!","<?","</","<","?>",">","="};
			for(String nono: nonos){
				if(str.indexOf(nono) > -1) return false;
			}
			return true;
		}
	};
}
