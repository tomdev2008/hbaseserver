package com.info.comm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Input {
	static InputStreamReader in;
	static BufferedReader reader;
	static {
		in = new InputStreamReader(System.in);
		reader = new BufferedReader(in);
	}

	public static String readString() {
		String s = "";
		try {
			s = reader.readLine();
		}catch (IOException e) {
			System.out.println(e);
			System.exit(0);
		}
		return s;
	}
}
