/**
 *  LICENSE AND TRADEMARK NOTICES
 *  
 *  Except where noted, sample source code written by Motorola Mobility Inc. and
 *  provided to you is licensed as described below.
 *  
 *  Copyright (c) 2012, Motorola, Inc.
 *  All  rights reserved except as otherwise explicitly indicated.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  - Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *
 *  - Redistributions in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 *
 *  - Neither the name of Motorola, Inc. nor the names of its contributors may
 *  be used to endorse or promote products derived from this software without
 *  specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *  AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *  ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 *  LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *  SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *  INTERRUPTION) HOWEVER  CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *  CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *  POSSIBILITY OF SUCH DAMAGE.
 *  
 *  Other source code displayed may be licensed under Apache License, Version
 *  2.
 *  
 *  Copyright ¬© 2012, Android Open Source Project. All rights reserved unless
 *  otherwise explicitly indicated.
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy
 *  of the License at
 *  
 *  http://www.apache.org/licenses/LICENSE-2.0.
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations
 *  under the License.
 *  
 */

// Please refer to the accompanying article at 
// http://developer.motorola.com/docs/using_the_advanced_encryption_standard_in_android/

package com.training.network.security;

// A tutorial guide to using AES encryption in Android
// First we generate a 256 bit secret key; then we use that secret key to AES encrypt a plaintext message.
// Finally we decrypt the ciphertext to get our original message back.
// We don't keep a copy of the secret key - we generate the secret key whenever it is needed, 
// so we must remember all the parameters needed to generate it -
// the salt, the IV, the human-friendly passphrase, all the algorithms and parameters to those algorithms.
// Peter van der Linden, April 15 2012


import com.orhanobut.logger.Logger;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;


public class AES {
	private final String CIPHERMODEPADDING = "AES/CBC/PKCS5Padding";
	private final String KEY_GENERATION_ALG = "PBKDF2WithHmacSHA1";
	private final int HASH_ITERATIONS = 1000;
	private final int KEY_LENGTH = 128;
	
	private byte[] salt = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0xA, 0xB, 0xC, 0xD,
			0xE, 0xF }; // must save this for next time we want the key

	private SecretKeyFactory keyfactory = null;
	private PBEKeySpec myKeyspec = null;
	private SecretKey sk = null;
	private SecretKeySpec skforAES = null;
	
	private byte[] iv = { 0xA, 1, 0xB, 5, 4, 0xF, 7, 9, 0x17, 3, 1, 6, 8, 0xC,
			0xD, 91 };

	private IvParameterSpec IV;

	public AES(String password) {
		try {
			keyfactory = SecretKeyFactory.getInstance(KEY_GENERATION_ALG);
			myKeyspec = new PBEKeySpec(password.toCharArray(), salt,
					HASH_ITERATIONS, KEY_LENGTH);
			sk = keyfactory.generateSecret(myKeyspec);

		} catch (NoSuchAlgorithmException nsae) {
			nsae.printStackTrace();
		} catch (InvalidKeySpecException ikse) {
			ikse.printStackTrace();
		}

		// This is our secret key. We could just save this to a file instead of
		// regenerating it
		// each time it is needed. But that file cannot be on the device (too
		// insecure). It could
		// be secure if we kept it on a server accessible through https.
		byte[] skAsByteArray = sk.getEncoded();
		skforAES = new SecretKeySpec(skAsByteArray, "AES");

		IV = new IvParameterSpec(iv);

	}

	public String encrypt(String plaintext) {
		byte[] ciphertext = encrypt(CIPHERMODEPADDING, skforAES, IV, plaintext.getBytes());
		String base64_ciphertext = Base64.encryptBase64(ciphertext);
		return base64_ciphertext;
	}

	public String decrypt(String ciphertext_base64) {
		byte[] s = Base64.decryptbase64(ciphertext_base64.getBytes());
		String decrypted = new String(decrypt(CIPHERMODEPADDING, skforAES, IV,
				s));
		return decrypted;
	}

	private byte[] encrypt(String cmp, SecretKey sk, IvParameterSpec IV,
			byte[] msg) {
		try {
			Cipher c = Cipher.getInstance(cmp);
			c.init(Cipher.ENCRYPT_MODE, sk, IV);
			return c.doFinal(msg);
		} catch (NoSuchAlgorithmException nsae) {
			Logger.e("AESdemo no cipher getinstance support for " + cmp);
		} catch (NoSuchPaddingException nspe) {
			Logger.e("AESdemo no cipher getinstance support for padding " + cmp);
		} catch (InvalidKeyException e) {
			Logger.e("AESdemo invalid key exception");
		} catch (InvalidAlgorithmParameterException e) {
			Logger.e("AESdemo invalid algorithm parameter exception");
		} catch (IllegalBlockSizeException e) {
			Logger.e("AESdemo illegal block size exception");
		} catch (BadPaddingException e) {
			Logger.e("AESdemo bad padding exception");
		}
		return null;
	}

	private byte[] decrypt(String cmp, SecretKey sk, IvParameterSpec IV,
			byte[] ciphertext) {
		try {
			Cipher c = Cipher.getInstance(cmp);
			c.init(Cipher.DECRYPT_MODE, sk, IV);
			return c.doFinal(ciphertext);
		} catch (NoSuchAlgorithmException nsae) {
			System.out.println("AESdemo no cipher getinstance support for " + cmp);
		} catch (NoSuchPaddingException nspe) {
			System.out.println("AESdemo no cipher getinstance support for padding " + cmp);
		} catch (InvalidKeyException e) {
			System.out.println("AESdemo invalid key exception");
		} catch (InvalidAlgorithmParameterException e) {
			System.out.println("AESdemo invalid algorithm parameter exception");
		} catch (IllegalBlockSizeException e) {
			System.out.println("AESdemo illegal block size exception");
		} catch (BadPaddingException e) {
			System.out.println("AESdemo bad padding exception");
			e.printStackTrace();
		}
		return null;
	}

}