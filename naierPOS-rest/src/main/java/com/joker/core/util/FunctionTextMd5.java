 
  
package com.joker.core.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FunctionTextMd5 {
	private final static char[] dictionary = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

		public static void main(String[] args) {
			System.out.println(new FunctionTextMd5().md5_3("22222222"));
		}
		public String md5(String str) {
			String md5str = "";
			try {
				md5str = code(str, 32);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return md5str;
		}
		
		public String md5_3(String str) {
			MessageDigest md = null;
			try {
				md = MessageDigest.getInstance(System.getProperty("MD5.algorithm", "MD5"));
			} catch (NoSuchAlgorithmException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			byte[] temp = null;
			try {
				temp = md.digest(str.getBytes("utf-8"));//第一次加密
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			temp = md.digest(temp);//第二次加密
			temp = md.digest(temp);//第三次加密
			return bytesToHex(temp);
		}
		
		public String bytesToHex(byte[] bytes) {
			String str = "";
			int t;
			for (int i = 0; i < 16; i++) {
				t = bytes[i];
				if (t < 0)
					t += 256;
				str += dictionary[(t >>> 4)];
				str += dictionary[(t % 16)];
			}
			return str;
		}

		public String code(String str, int bit) throws Exception {
			try {
				MessageDigest md = MessageDigest.getInstance(System.getProperty("MD5.algorithm", "MD5"));
				if (bit == 16)
					return bytesToHex(md.digest(str.getBytes("utf-8"))).substring(8, 24);
				return bytesToHex(md.digest(str.getBytes("utf-8")));

			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
				throw new Exception("Could not found MD5 algorithm.", e);
			}
		}
	
}
  
