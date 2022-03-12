package com.cdo.cloud.function;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class StringCollector implements Collector<String, StringCombine, String> {

	private String prefix;
	private String suffix;
	private String delimiter;
	
	public StringCollector(String delimiter,String prefix,String suffix){
		this.delimiter=delimiter;
		this.prefix=prefix;
		this.suffix=suffix;
	}
	@Override
	public Supplier<StringCombine> supplier() {
		// TODO Auto-generated method stub
		System.out.println("StringCollector Supplier....");
		return ()->new StringCombine(delimiter, prefix, suffix);
	}

	@Override
	public BiConsumer<StringCombine, String> accumulator() {
		// TODO Auto-generated method stub
		System.out.println("StringCollector BiConsumer....");
		BiConsumer<StringCombine, String> t=(t1,a1)->t1.add(a1);		
		return t;
	}

	@Override
	public BinaryOperator<StringCombine> combiner() {
		// TODO Auto-generated method stub
		System.out.println("StringCollector BinaryOperator....");
		return StringCombine::merge;
	}

	@Override
	public Function<StringCombine, String> finisher() {
		// TODO Auto-generated method stub
		System.out.println("StringCollector Function....");
		return StringCombine::toString;
	}

	@Override
	public Set<java.util.stream.Collector.Characteristics> characteristics() {
		// TODO Auto-generated method stub		
		return Collections.emptySet();
	}

}
