package com.cdo.cloud.function;

import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class MapCollotor implements Collector<String,Map<String,Integer>, Map<String,Integer>> {

	@Override
	public Supplier<Map<String, Integer>> supplier() {
		// TODO Auto-generated method stub
		return ()->new HashMap<String,Integer>();
	}

	@Override
	public BiConsumer<Map<String, Integer>, String> accumulator() {
		// TODO Auto-generated method stub
		BiConsumer <Map<String, Integer>, String> con=(map,str)->{
			int value=1;
			if(map.containsKey(str)){
				value=map.get(str).intValue()+1;
			}
			map.put(str, value);
			};
		return con;
	}

	@Override
	public BinaryOperator<Map<String, Integer>> combiner() {
		// TODO Auto-generated method stub
		return (map1,map2)->{map1.putAll(map2);return map1;};
	}

	@Override
	public Function<Map<String, Integer>, Map<String, Integer>> finisher() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<java.util.stream.Collector.Characteristics> characteristics() {
		// TODO Auto-generated method stub
		return  Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.IDENTITY_FINISH));

	}

}
