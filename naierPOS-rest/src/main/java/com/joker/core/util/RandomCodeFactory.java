/**
 * 
 */
package com.joker.core.util;

import java.util.Random;

/**
 * @author lvhaizhen
 *
 */
public class RandomCodeFactory {
	//private static Random random = new Random(System.currentTimeMillis());
		private static char[] ca = new char[]{'1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','J','K','M','N','P','Q','R','S','T','U','V','W','X','Y','Z'};
		
		//private static char[] ca2 = new char[]{'2','3','4','5','6','7','8','9'}; 
		
		public static String defaultGenerateMixed(){
			return RandomCodeFactory.generateMixed(36);
		}
		
		/**
		 * 产生一个给定长度的数字字串
		 * @param n
		 * @return
		 */
		public static  String generate(int n){
			Random random = new Random();
			char[] cr = new char[n];
			for (int i=0;i<n;i++) {
				int x = random.nextInt(10);
				cr[i] = Integer.toString(x).charAt(0);
			}
			return (new String(cr));
		}
		
		public static  String generateMixed(int n){
			Random random = new Random();
			char[] cr = new char[n];
			for (int i=0;i<n;i++){
				int x = random.nextInt(32);
				cr[i] = ca[x];
			}
			return (new String(cr));
		}
		
		public static  String generateMixed2(int n){
			Random random = new Random();
			char[] cr = new char[n];
			for (int i=0;i<n;i++){
				int x = random.nextInt(9);
				cr[i] = ca[x];
			}
			
			
			
			return (new String(cr));
		}
}
