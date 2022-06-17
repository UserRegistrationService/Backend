package com.user.registration.utility;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.LoadingCache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;

public class OtpUtil {

	private static final Integer EXPIRE_MINS = 5;
//https://dzone.com/articles/otp-one-time-password-using-spring-boot-web-applic
	private LoadingCache<String, Integer> otpCache;

	public OtpUtil() {
		super();
		otpCache = CacheBuilder.newBuilder().expireAfterWrite(EXPIRE_MINS, TimeUnit.MINUTES)
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