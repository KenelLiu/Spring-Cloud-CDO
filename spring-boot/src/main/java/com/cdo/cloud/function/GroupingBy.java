package com.cdo.cloud.function;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;



public class GroupingBy<T, K> implements Collector<T, Map<K, List<T>>, Map<K, List<T>>> {

    private final Function<? super T, ? extends K> classifier;

    public GroupingBy(Function<? super T, ? extends K> classifier) {
        this.classifier = classifier;
}
	@Override
	public Supplier<Map<K, List<T>>> supplier() {
		// TODO Auto-generated method stub
		
		return ()->new HashMap<K,List<T>>();
	}

	@Override
	public BiConsumer<Map<K, List<T>>, T> accumulator() {
		// TODO Auto-generated method stub
			    
		BiConsumer<Map<K, List<T>>, T> bi=(map,t)->{
			K k=this.classifier.apply(t);
			List<T> list=null;
			if(map.containsKey(k)){
				list=map.get(k);
			}else{
				list=new ArrayList<T>();
			}
			list.add(t);
			map.put(k, list);
		};		
		return bi;
	}

	@Override
	public BinaryOperator<Map<K, List<T>>> combiner() {
		// TODO Auto-generated method stub
		return (map1,map2)->{map1.putAll(map2);return map1;};
	}

	@Override
	public Function<Map<K, List<T>>, Map<K, List<T>>> finisher() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<java.util.stream.Collector.Characteristics> characteristics() {
		// TODO Auto-generated method stub
		return null;
	}

}
