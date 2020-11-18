package Encrytion;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
 
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
 
import org.apache.commons.codec.binary.Base64;

public class AES256 {
	 private String iv;
	 private Key keySpec;
	 
	 /**
	  * 16�ڸ��� Ű���� �Է��Ͽ� ��ü�� �����Ѵ�.
	  * @param key ��/��ȣȭ�� ���� Ű��
	  * @throws UnsupportedEncodingException Ű���� ���̰� 16������ ��� �߻�
	  */
	 public AES256(String key) {
		 try {
			 this.iv = key.substring(0, 16);
			 byte[] keyBytes = new byte[16];
			 byte[] b = key.getBytes("UTF-8");
			 int len = b.length;
			 if(len > keyBytes.length){
				 len = keyBytes.length;
			 }
			 System.arraycopy(b, 0, keyBytes, 0, len);
			 SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
	        
			 this.keySpec = keySpec;
		 }catch(UnsupportedEncodingException e) {
			 e.printStackTrace();
		 }
	 }
	 
	 /**
	  * AES256 ���� ��ȣȭ �Ѵ�.
	  * @param str ��ȣȭ�� ���ڿ�
	  * @return
	  * @throws NoSuchAlgorithmException
	  * @throws GeneralSecurityException
	  * @throws UnsupportedEncodingException
	  */
	 public String encrypt(String str) {
		 String enStr = null;
		 try {
			 Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
			 c.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
			 byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
			 enStr = new String(Base64.encodeBase64(encrypted));
		 }catch(NoSuchAlgorithmException e) {
			 e.printStackTrace();
		 }
		 catch(GeneralSecurityException e) {
			 e.printStackTrace();
		 }
		 catch(UnsupportedEncodingException e) {
			 e.printStackTrace();
		 }
		 return enStr;
	 }
	 
	 /**
	  * AES256���� ��ȣȭ�� txt �� ��ȣȭ�Ѵ�.
	  * @param str ��ȣȭ�� ���ڿ�
	  * @return
	  * @throws NoSuchAlgorithmException
	  * @throws GeneralSecurityException
	  * @throws UnsupportedEncodingException
	  */
	 public String decrypt(String str) {
		 Cipher c = null;
		 byte[] byteStr = null;
		 String s = null;
		 try {
			 c = Cipher.getInstance("AES/CBC/PKCS5Padding");
			 c.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
			 byteStr = Base64.decodeBase64(str.getBytes());
			 s = new String(c.doFinal(byteStr), "UTF-8");
		 }catch(NoSuchAlgorithmException e) {
			 e.printStackTrace();
		 }
		 catch(GeneralSecurityException e) {
			 e.printStackTrace();
		 }
		 catch(UnsupportedEncodingException e) {
			 e.printStackTrace();
		 }
		 return s;
	 }
}
