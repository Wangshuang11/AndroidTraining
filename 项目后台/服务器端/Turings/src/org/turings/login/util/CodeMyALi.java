package org.turings.login.util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


public class CodeMyALi {
	private DESUtil de1;  
	private String msg ="LTAI4FrAGuiodb8JgCBNPxDQ";
	private String msg2="puFDV1nJCw0ecT4Mb9JDBDKCd6M95K";
	private byte[] encontent;
	private byte[] encontent2;
	
	public CodeMyALi() {
		try {
			this.de1 = new DESUtil();
			this.encontent=de1.Encrytor(msg);
			this.encontent2=de1.Encrytor(msg2);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}  

//	private void code() {
//		try {
//			this.encontent=de1.Encrytor(msg);
//		} catch (InvalidKeyException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalBlockSizeException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (BadPaddingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}  
//	}
	
	public String deCode() {
		try {
			byte[] decontent = de1.Decryptor(this.encontent);
			return new String(decontent);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return "";
	}
	
	public String deCode2() {
		try {
			byte[] decontent = de1.Decryptor(this.encontent2);
			return new String(decontent);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return "";
	}
	
}
