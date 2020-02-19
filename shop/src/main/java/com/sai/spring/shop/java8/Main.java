package com.sai.spring.shop.java8;

import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

public class Main {

	public static void main(String[] args) {

		Optional<String> optional = Optional.empty();
		// String result = optional.orElseThrow(new T()::thr);

		Integer sum = Arrays.asList("", "", "").stream().mapToInt(e -> 1).sum();

		System.out.println(sum);
		new Observer() {
			
			@Override
			public void update(Observable o, Object arg) {
		
				
			}
		};
	}

}

class T {}
