package com.training.network.security;


public class RSATest {
	public static void main(String[] args) {
		String private_key = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEA" +
				"AoGBAKWZiFRwpVlbmD6BHfemDRc5OMFxWyYs6fNqnpeYSE29cgqk72LlKewiEY" +
				"LDlHGMTDike+8ATDfJvDjFX8Jqzr915X2V+M+8MnRggpUHIwJBzT7Yz5u" +
				"fFfLJnIwLxvaZc5iANmGUTNkYTM5IX34+L+Baczbo0il+6VDLwTuXHiMhAgM" +
				"BAAECgYB7omnfKQ657SF4IOvftfB2Ezmlat0zXjr4ifSHl6D7sWHQQp2bBx7KdhD+wM" +
				"g2Ehnh/COvJ1jAGfRVqj45J4bcxVB6vtMdK3oHSIGrcybEVg1l2LYFSP5ebLlFV78a51" +
				"HvsYSVvsCopcJSLcOWRzyL3tkNAhe02sfIFMKms07WwQJBANBWxplO3DpGsLyVDms+m" +
				"0SBKvsZMuM4krGvlvvKOIEur0XuhePXOV8hBYXMYuRiXfXeWXrK5nHa+Qp+PO2i6Jk" +
				"CQQDLe8OQMtPTAFaFz/GkcgwwE2jZmyZpe3yCI2QyYBXxvF6fW4zgeNmagYsdh7oTu" +
				"a85dY1AtwM/ouh9+onklHvJAkA5z/qoTDPckAU3L32i0OqxJc7RgvqWBvreB8Wz9Tec0WGd" +
				"3ESXJwAqn7UynbbLfWhpc9wMsQUljwgQm1s47j3xAkBcCy7qMmOpBXUd8HMg7MngkVcTX+Af" +
				"RNGMWJABTX9/qrKuqQ3vmBrujfysrfGY7Jx7hFYR2PcqOPmrysHHWPcpAkEAkPk65A323MwcJ" +
				"1BaJhB+jxwndQeSLypF3zEfdmEER2sahqLf97TPkvqFUK29iF8pVTbHnnOU8A1eX8P1pEvYhw==";
		
		String public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQClmYhUcKVZW5g+gR33pg0XOTjB\t"
							+ "cVsmLOnzap6XmEhNvXIKpO9i5SnsIhGCw5RxjEw4pHvvAEw3ybw4xV/Cas6/deV9\t"
							+ "lfjPvDJ0YIKVByMCQc0+2M+bnxXyyZyMC8b2mXOYgDZhlEzZGEzOSF9+Pi/gWnM2\t"
							+ "6NIpfulQy8E7lx4jIQIDAQAB\t";
		
//		String private_key = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMKqQl/7/0JVQsaes+mJnzPAGTommWX6r3WM1TaWv0HDU6iRFMyqqKhM3/a3KYSRgESFvfELFgORs1kZMPKI4ItFf2kqHOsYYl24aDyB8414po8X77W9j3cr5W+agkX6DCxmK5cwZf4tpNXyZIib3ImmsCmWOjWoIYVm5yWMIug/AgMBAAECgYB0NprbnKCeJDJySwqTUHK3vWYSHjsT853OKp6bMVHK2+ZUyxBEw1le6E4Ihv6FQyv1PHbRsVGaHeCZP33GPUr3LXYRgV4Q0XQauvg1J6IhbmG8ldAwICJndia5ctyKky63nc5Fta/XpNI2HFEcrjsInFgZBI2lpicpsBi91+CbyQJBAP2cp73W7WLLpmrjpbxX7B0Sx5A2+rRkzXp7tkgropjTcgi9kH8Ng6nnOvx8C0orqD+SFs8HKkobNEctaCp+xNUCQQDEf4Jy8ZOwBWKltOcR++l8YHtChDI627dRmBqvpmwa7vFCJvbg1q4paDl1Or/yEKCNJecyVQ+RvHw4yhrCzBLDAkBIaCyAB9f4p/oYdGxCLLwNxOnTI89KJ6l+ucleK48doOji0/RoQdyAarMtmODESVic5cG7U+lgBQpU9ALUdyMNAkEAjCO0UW3hOqRcmnnOYpEbEE1vGx1VBY01zMeCIMu8Rekrv1YytF/njjh64hSoWGdrWb8yiD8J/5JBte7N7yfEKQJAGFRHm7qTFPQYDNBu5BBrmtfJmmt9asNbe34dYSg5LeelbhsCvLUWW4b3YpOTASlLS0g/btgwzlAtEaEiIMXnDg==";
//		
//		String public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDCqkJf+/9CVULGnrPpiZ8zwBk6Jpll+q91jNU2lr9Bw1OokRTMqqioTN/2tymEkYBEhb3xCxYDkbNZGTDyiOCLRX9pKhzrGGJduGg8gfONeKaPF++1vY93K+VvmoJF+gwsZiuXMGX+LaTV8mSIm9yJprApljo1qCGFZucljCLoPwIDAQAB";
		
		try {
			String secKey = "1234567891234567";
			Rsa rsa = new Rsa(public_key, private_key);
			byte[] encBytes = rsa.encryptByPublicKey(secKey.getBytes());
			System.out.println("---------encryptByPublicKey-------");
			String encContent = Base64.encryptBase64(encBytes);
			System.out.println(encContent);
			
			byte[] decBytes = rsa.decryptByPrivateKey(Base64.decryptbase64(encContent.getBytes()));
			System.out.println("---------decryptByPrivateKey-------");
			String decContent = new String(decBytes, "UTF-8");
			System.out.println(decContent);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
