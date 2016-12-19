package com.training.network.security;

import java.io.FileOutputStream;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class Rsa {
	public static final String CIPGHER_ALGORITHM = "RSA/ECB/PKCS1Padding";
	public static final String KEY_ALGORITHM = "RSA";

	private static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQClmYhUcKVZW5g+gR33pg0XOTjB"
			+ "cVsmLOnzap6XmEhNvXIKpO9i5SnsIhGCw5RxjEw4pHvvAEw3ybw4xV/Cas6/deV9"
			+ "lfjPvDJ0YIKVByMCQc0+2M+bnxXyyZyMC8b2mXOYgDZhlEzZGEzOSF9+Pi/gWnM2"
			+ "6NIpfulQy8E7lx4jIQIDAQAB";

	private Key publicKey;
	private Key privateKey;

	public Rsa() throws GeneralSecurityException {
		this(PUBLIC_KEY);
	}

	public Rsa(String pubkey, String priv) throws GeneralSecurityException {
		this(pubkey);

		byte[] keyBytes = Base64.decryptbase64(priv.getBytes());

		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory privatekeyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		privateKey = privatekeyFactory.generatePrivate(pkcs8KeySpec);
	}

	public Rsa(String pubkey) throws GeneralSecurityException {
		privateKey = null;

		byte[] keyBytes = Base64.decryptbase64(pubkey.getBytes());

		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory pubkeyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		publicKey = pubkeyFactory.generatePublic(x509KeySpec);
	}

	/**
	 * 解密<br>
	 * 用私钥解密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public byte[] decryptByPrivateKey(byte[] data) throws Exception {
		Cipher cipher = Cipher.getInstance(CIPGHER_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, privateKey);

		return cipher.doFinal(data);
	}

	/**
	 * 加密<br>
	 * 用私钥加密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public byte[] encryptByPrivateKey(byte[] data) throws Exception {
		Cipher cipher = Cipher.getInstance(CIPGHER_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);

		return cipher.doFinal(data);
	}

	/**
	 * 解密<br>
	 * 用公钥解密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public byte[] decryptByPublicKey(byte[] data) throws Exception {

		Cipher cipher = Cipher.getInstance(CIPGHER_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, publicKey);

		return cipher.doFinal(data);
	}

	/**
	 * 加密<br>
	 * 用公钥加密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public byte[] encryptByPublicKey(byte[] data) throws Exception {
		Cipher cipher = Cipher.getInstance(CIPGHER_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);

		return cipher.doFinal(data);
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public static void genKey() throws Exception {
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");

		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
		keyPairGen.initialize(1024, random);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
		String publicKeyFilename = "publicKey";
		FileOutputStream fos = new FileOutputStream(publicKeyFilename);
		fos.write(Base64.encryptBase64(publicKeyBytes).getBytes());
		fos.close();

		byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
		String privateKeyFilename = "privateKey";
		fos = new FileOutputStream(privateKeyFilename);
		fos.write(Base64.encryptBase64(privateKeyBytes).getBytes());
		fos.close();
	}
}
