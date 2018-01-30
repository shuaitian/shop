package cn.shuaitian.shop.utils;

public class SizeUtils {
	public static long parse(String size) {
		if(size.endsWith("m".toLowerCase())) {
			long num = Long.parseLong(size.substring(0,size.length()-1));
			return num * 1024 * 1024;
		}
		
		if(size.endsWith("kb".toLowerCase())) {
			long num = Long.parseLong(size.substring(0,size.length()-2));
			return num * 1024;
		}
		
		return Long.parseLong(size);
	}
}
