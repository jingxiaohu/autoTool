package com.iapppay.demo;

import com.iapppay.sign.SignHelper;




public class IapppayUtil
{
	
	// 私钥
	//static String priKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKz0WssMzD9pwfHlEPy8+NFSnsX+CeZoogRyrzAdBkILTVCukOfJeaqS07GSpVgtSk9PcFk3LqY59znddga6Kf6HA6Tpr19T3Os1U3zNeU79X/nT6haw9T4nwRDptWQdSBZmWDkY9wvA28oB3tYSULxlN/S1CEXMjmtpqNw4asHBAgMBAAECgYBzNFj+A8pROxrrC9Ai6aU7mTMVY0Ao7+1r1RCIlezDNVAMvBrdqkCWtDK6h5oHgDONXLbTVoSGSPo62x9xH7Q0NDOn8/bhuK90pVxKzCCI5v6haAg44uqbpt7fZXTNEsnveXlSeAviEKOwLkvyLeFxwTZe3NQJH8K4OqQ1KzxK+QJBANmXzpVdDZp0nAOR34BQWXHHG5aPIP3//lnYCELJUXNB2/JYTN57dv5LlE5/Ckg0Bgak764A/CX62bKhe/b+FMsCQQDLe4F2qHGy7Sa81xatm66mEkG3u88g9qRARdEvgx9SW+F1xBt2k/bU2YI31hB8IYXzL8KW9NzDfQPihBBUFn4jAkEAzbrmq/pLPlo6mHV3qE5QA2+J+hRh0UYVKsVDKkJGLH98gepS45hArbawBne/NP1bJTUVGKP9w7sl0es01hbteQJATzLO/QQq3N15Cl8dMI07uN+6PG0Y/VeCLpH+DWQXuNKSOmgN2GVW2RmfmWP0Hpxdqn2YW3EKy/vIm02TnWbzyQJAXwujUR9u9s8BZI33kw3gQ7bvWVYt8yyiYzWD2Qrnyg08tN5o+JsjW3fEDWHm70jjZIc+l/5FaZ7H5NOYpnVcpA==";
	private static String priKey = "MIICXAIBAAKBgQCfav0uN57mQtbSy+Kc5nobxMF7sob5Wby/VpG8fr3i3xp9MH2hRLmzkt/M57Uh0Y4wJNEAt7XyCCdWpKRA9Wgp/uqcCqo4FyT4HuJpZyvgaIR/jcINEnwM3CC3nIKaEagf+zzhIqQ9aUFtoGjph3R0TJ+mrUby4ijnZDh9k36/nwIDAQABAoGADZj8m3KXFSDiXfySCbwG8lNoBXsG1EH5PYDO0XsmCzHoeoEmVhkcldqTSFEDPC05qZWaVitVU52qbVOBxyCnj7pRQHtQQhAjhk1VCV0iq67ETGMO2a2ja4MYrhVtXKAUr/22DVU2l8QHhkD+PAr3l46Pw3wgV+J5o9ilRxaLl7ECQQD2KjMLUkBqnFUE6OjOrNwMkuSfRMxt8V2ZhWg0JA6YeBGaN11V2Vw7n4Db33F3DMx1P4MoVzYH43euL8l3AWZlAkEApcmIYzycJ755HO+UGNCuA3d1xlC0fAH5+cg6Vir53g/nbx/v6Y5K2NnIeTnT97EWqScTIRvidaIc5hGCM6GbswJBAObhlZacwsY7cpJ0UUbnYkx4ye2H/aVwqLWBJscgun/OvdPC8cu/Xn6dj6Kp5CYg3vNpan6/rXGgq3O4ZmEz8vECQF7QkKD4o/IS/B8xYYiDqFGxO3ks5NvbIYTaH4ryTy7R97FVGZeAWowEmaPDD9gjvmsFPup7kRtnmJF3x/gRYAMCQCqKL4iXwv5cnOOJu2yaMfwwVBU7VO2f7KclAEGGWrFw0j8nIEQ/gvUqM1YQ3w6O+c8b+1ZAZ8fG7aN33U2Lm7E=";
	// 公钥
	//	static String pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCs9FrLDMw/acHx5RD8vPjRUp7F/gnmaKIEcq8wHQZCC01QrpDnyXmqktOxkqVYLUpPT3BZNy6mOfc53XYGuin+hwOk6a9fU9zrNVN8zXlO/V/50+oWsPU+J8EQ6bVkHUgWZlg5GPcLwNvKAd7WElC8ZTf0tQhFzI5raajcOGrBwQIDAQAB";
	private static String pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCg9wX82rJzFuh6py3Qy6ADUSq5GKtUzjfN0MOG1kynFjEOIqZEdFTHqsZhXcF13GQPCzQTWcWarWmIoPUAo6ETvfDw5XAL/dUxgjvv/ul93tFVaNVyGsSFF3U5vgmTTh6BWJLeEH400XSc0qb/osJXjtnbqHi5IvotAA0ElPF59QIDAQAB";
	//商户ID
	public static String  appuserid = "3001818505";
	/**
	 *类名：demo
	 *功能  服务器端签名与验签Demo
	 *版本：1.0
	 *日期：2014-06-26
	 '说明：
	 '以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己的需要，按照技术文档编写,并非一定要使用该代码。
	 '该代码仅供学习和研究爱贝云计费接口使用，只是提供一个参考。
	*/
	//
	
	public static boolean verify(String content,String sign)
	{
		// 验签
		return SignHelper.verify(content, sign, pubKey);
		
	}
	
	
	public static String sign(String content){
		// 签名
		return SignHelper.sign(content, priKey);
	}
	
}
