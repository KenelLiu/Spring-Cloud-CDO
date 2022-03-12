package com.cdo.cloud.function;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.GZIPOutputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;


public class CharpExample {

	public static void main(String[] args){
		Predicate<Integer> isBig=x->x>5;
		Predicate<Integer> isSmall=x->x<2;
		System.out.println("Predicate:"+isBig.test(6));
		System.out.println("Predicate:"+isBig.or(isSmall).test(1));
		
		Consumer<Integer> cousumer=x->System.out.println("cousumer:"+(x+1));
		Consumer<Integer> cousumer2=x->System.out.println("cousumer2:"+(x+2));
		cousumer.andThen(cousumer2).accept(1);
				
		Function<Integer,String> sub=x->{x=x+1;return "result:"+x;};
		System.out.println("Function="+sub.apply(2));
		
		Supplier<A> supplier=()->{CharpExample charpExample=new CharpExample(); return charpExample.new A("name");};
		A clsA=supplier.get();
		System.out.println(clsA);
		
		UnaryOperator<String> out=x->x+" add inform";
		System.out.println("UnaryOperator mul="+out.apply("测试"));
		
		BinaryOperator<Integer> mul=(t,a)->{t=t+1;return t*a;};			
		System.out.println("BinaryOperator mul="+mul.apply(1, 2));
		System.out.println("BinaryOperator mul andThen="+mul.andThen(sub).apply(3, 4));
		
		Supplier<Map<String,String>> supplier1=()->{System.out.println("supplier1");return new HashMap<String,String>();};		
		BiConsumer<Map<String,String>,String> accumulator=(map,str)->{System.out.println("accumulator="+str);map.put(str, str);};
//		BiConsumer<List<String>, List<String>> combiner=(list,list1)->{System.out.println("combiner");list.addAll(list1);};
		BiConsumer<Map<String,String>,Map<String,String>> combiner=(str,str1)->{};
		Map<String,String> collected=Stream.of("a","b","c").collect(supplier1, accumulator, combiner);
		System.out.println(collected);		
		
		BinaryOperator<Integer> add=(x,y)->x+y;		
		int count=Stream.of(1,2,3).reduce(add).get();
		System.out.println(count);
		BiFunction<StringCombine, ? super Integer,StringCombine> accum=(x,y)->x.add(y+"");
		BinaryOperator<StringCombine> com=(t,t1)->t.merge(t1);
		
		StringCombine strCombine=Stream.of(1,2,3).map(x->{x=x+1;return String.valueOf(x);}).reduce(new StringCombine(",","[","]"),StringCombine::add,StringCombine::merge);
		System.out.println(strCombine.toString());

		
		String value=Stream.of(1,2,3,4,5).map(x->{x=x+1;return String.valueOf(x);})
				.peek(x->System.out.print("peek="+x)).collect(new StringCollector(",","[","]"));
		System.out.println(value);
		Map<String,Long> x=Stream.of("JEO","JACK","JEO","MIKE","x").collect(Collectors.groupingBy(y->y.toString(),Collectors.counting()));
		System.out.println(x);
		Map<String,Integer> z=Stream.of("JEO","JACK","JEO","MIKE","x").collect(new MapCollotor());
		System.out.println(z);
		/**
		RecursiveTask
		ForkJoinPool forkJoinPool =new ForkJoinPool();
		ForkJoinTask<String> a=forkJoinPool.submit(new RecursiveTask<String>() {

			@Override
			protected String compute() {
				// TODO Auto-generated method stub
				return null;
			}
		});**/
		
		try{
		     //获取Bar实例
	        Bar bar = new Bar();
	        //获取Bar的val字段
	        Field field = Bar.class.getDeclaredField("val");
//	        Method method = Bar.class.getDeclaredMethod("setList");
	        //获取val字段上的Foo注解实例
	        Foo foo = field.getAnnotation(Foo.class);

	        //获取 foo 这个代理实例所持有的 InvocationHandler
	        InvocationHandler h = Proxy.getInvocationHandler(foo);
	        // 获取 AnnotationInvocationHandler 的 memberValues 字段
	        Field hField = h.getClass().getDeclaredField("memberValues");
//	        Method methodField = h.getClass().getDeclaredMethod("memberMethods");
	        // 因为这个字段事 private final 修饰，所以要打开权限
	        hField.setAccessible(true);
	        // 获取 memberValues
	        Map memberValues = (Map) hField.get(h);	        
	        // 修改 value 属性值
	        memberValues.put("value", "xxxxx");
	        // 获取 foo 的 value 属性值
	        value = foo.value();
//	        AnnotationParser.	        
//	        ClassDecorateModel.
	        System.out.println(value); // ddd
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private static void testMacro(){
		Macro macro=new Macro();
		Editor editor=new Editor() {
			
			@Override
			public void save() {
				// TODO Auto-generated method stub
				System.out.println("editor save");
			}
			
			@Override
			public void open() {
				// TODO Auto-generated method stub
				System.out.println("editor open");
			}
			
			@Override
			public void close() {
				// TODO Auto-generated method stub
				System.out.println("editor close");
				
			}
		};
		macro.record(()->editor.open());
		macro.record(editor::save);		
		macro.run();
	}
	class A{
		private String name;
		public A(String name){
			this.name=name;
					
		}
		public String toString(){
			return this.name;
		}
	}
}
