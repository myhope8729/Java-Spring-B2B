package com.kpc.eos.core.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Filename		: CipherUtil.java
 * Description	: Class for encoding and decoding any string using a given algorithm
 * 2017
 * @author		: RKRK
 */
public class CipherUtil {

	/**
	 * Description	: Creating Security key (SecretKey) for a given algorithm.
	 * @author 		: RKRK
	 * @param algorithm
	 * @return
	 * @throws NoSuchAlgorithmException
	 * 2017
	 */
	public static Key generateKey(String algorithm)
			throws NoSuchAlgorithmException {

		KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
		SecretKey key = keyGenerator.generateKey();
		return key;
	}

	/**
	 * Description	: Creating Security key (SecretKey) for a given algorithm using a given data. 
	 * @author 		: RKRK
	 * @param algorithm (DES/DESede/TripleDES/AES)
	 * @param keyData
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws InvalidKeySpecException
	 * 2017
	 */
	public static Key generateKey(String algorithm, byte[] keyData)
			throws NoSuchAlgorithmException, InvalidKeyException,
			InvalidKeySpecException {

		String upper = algorithm.toUpperCase();

		if ("DES".equals(upper)) {
			KeySpec keySpec = new DESKeySpec(keyData);
			SecretKeyFactory secretKeyFactory = SecretKeyFactory
					.getInstance(algorithm);
			SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);
			return secretKey;
		} else if ("DESede".equals(upper) || "TripleDES".equals(upper)) {
			KeySpec keySpec = new DESedeKeySpec(keyData);
			SecretKeyFactory secretKeyFactory = SecretKeyFactory
					.getInstance(algorithm);
			SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);
			return secretKey;
		} else {
			SecretKeySpec keySpec = new SecretKeySpec(keyData, algorithm);
			return keySpec;
		}
	}

	/**
	 * 원천문자렬을 AES128비트암호화를 사용하여 암호화한다.
	 * 
	 * encrypt
	 * ===================
	 * detailed description of the method
	 * @param srcString
	 * @param keyString
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String srcString, String keyString)
			throws Exception {

		byte[] passwordBytes = keyString.getBytes("utf-8");
		int len = passwordBytes.length;
		byte[] keyBytes = new byte[16];

		if (len >= 16) {
			System.arraycopy(passwordBytes, 0, keyBytes, 0, 16);
		} else {
			System.arraycopy(passwordBytes, 0, keyBytes, 0, len);
			for (int i = 0; i < (16 - len); i++) {
				keyBytes[len + i] = passwordBytes[i % len];
			}
		}

		Key key = generateKey("AES", keyBytes);
		String transformation = "AES/ECB/PKCS5Padding";
		Cipher cipher = Cipher.getInstance(transformation);
		cipher.init(Cipher.ENCRYPT_MODE, key);

		byte[] plain = srcString.getBytes();

		byte[] encrypt = cipher.doFinal(plain);

		return ByteUtils.toHexString(encrypt);
	}

	/**
	 * AES128비트암호화된 문자렬을 복호화한다.
	 * 
	 * decrypt
	 * ===================
	 * detailed description of the method
	 * @param encString
	 * @param keyString
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String encString, String keyString)
			throws Exception {
		byte[] passwordBytes = keyString.getBytes("utf-8");
		int len = passwordBytes.length;
		byte[] keyBytes = new byte[16];

		if (len >= 16) {
			System.arraycopy(passwordBytes, 0, keyBytes, 0, 16);
		} else {
			System.arraycopy(passwordBytes, 0, keyBytes, 0, len);
			for (int i = 0; i < (16 - len); i++) {
				keyBytes[len + i] = passwordBytes[i % len];
			}
		}

		Key key = generateKey("AES", keyBytes);
		String transformation = "AES/ECB/PKCS5Padding";
		Cipher cipher = Cipher.getInstance(transformation);
		cipher.init(Cipher.DECRYPT_MODE, key);

		byte[] decrypt = cipher.doFinal(ByteUtils
				.toBytesFromHexString(encString));

		return new String(decrypt);
	}

	/**
	 * MD5해쉬함수를 리용한 암호화처리
	 * 
	 * encryptByMD5
	 * ===================
	 * detailed description of the method
	 * @param src
	 * @return
	 */
	public static String encryptByMD5(String src) {
		String result = src;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] b = md.digest(src.getBytes("UTF-8"));
			result = ByteUtils.toHexString(b);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return result;
	}
	
	/**
	 * SHA512를 리용한 암호화처리
	 * 
	 * encryptBySHA
	 * ===================
	 * detailed description of the method
	 * @param src
	 * @return
	 */
	public static String encryptBySHA(String src) {
		String result = src;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA1");
			byte[] b = md.digest(src.getBytes("UTF-8"));
			result = ByteUtils.toHexString(b);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return result;
	}
	
	public static String createAuthKey() {
		return encryptByMD5(encryptByMD5((new Date()).getTime() + ""));
	}
	
	public static void main(String[] args) throws Exception {
//		System.out.println(encrypt("1234567890234235345643", "1234567890"));
//		System.out.println(encryptByMD5(encrypt("12345", "12345")));
//		System.out.println(encryptJuminNo("841204-1232344"));
//		System.out.println(encryptBySHA("damo1234"));
		HttpUtil.getResponseFromURL("http://url.tlab.kr/jfxysb", "utf-8");
	}
}
