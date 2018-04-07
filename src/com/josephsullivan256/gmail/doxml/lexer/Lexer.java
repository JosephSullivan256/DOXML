package com.josephsullivan256.gmail.doxml.lexer;

import java.util.List;
import com.josephsullivan256.gmail.doxml.Token;

public interface Lexer {
	public List<Token> lex(String input);
}