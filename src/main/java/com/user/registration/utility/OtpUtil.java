package com.user.registration.utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

@Component
public class OtpUtil {

	private static  Integer EXPIRE_SECS;
	private static  Properties properties;
	private LoadingCache<String, Integer> otpCache;
	
	static {
		properties= new Properties();
		URL url = new SendSMSUtil().getClass().getClassLoader().getResource("application.properties");
		try {
			properties.load(new FileInputStream(url.getPath()));
			
			
		}
		catch(FileNotFoundException e)
		{e.printStackTrace();
			
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public OtpUtil() {
		super();
		String timeOutString= properties.getProperty("OTPUtil.TIMEOUT_IN_SECS");
		try
		{
			EXPIRE_SECS= Integer.parseInt(timeOutString);
		}
		catch(Exception e)
		{
			//Do Nothing Use Default Timeout
		}
		otpCache = CacheBuilder.newBuilder().expireAfterWrite(EXPIRE_SECS, TimeUnit.SECONDS)
				.build(new CacheLoader<String, Integer>() {
					public Integer load(String key) {
						return 0;
					}
				});
	}

	public int generateOTP(String key) {

		Random random = new Random();
		int otp = 100000 + random.nextInt(900000);
		otpCache.put(key, otp);
		return otp;
	}

	public int getOtp(String key) {
		try {
			return otpCache.get(key);
		} catch (Exception e) {
			return 0;
		}
	}

	public void clearOTP(String key) {
		otpCache.invalidate(key);
	}

}
