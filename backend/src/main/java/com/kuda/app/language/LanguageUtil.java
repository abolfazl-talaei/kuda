package com.kuda.app.language;

import java.util.Arrays;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.kuda.app.exception.Errors;
import com.kuda.app.exception.KudaException;
import com.kuda.app.exception.Resources;

import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class LanguageUtil {

	private LanguageUtil() throws KudaException {
		throw new KudaException(Errors.INTERNAL_ERROR.getName(), HttpStatus.INTERNAL_SERVER_ERROR,
				Resources.UTIL.getName(), "100");
	}

	public static String getLanguage() {

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();
		for (Cookie item : request.getCookies()) {
			if ("language".equalsIgnoreCase(item.getName()) && Arrays.asList("fa", "en").contains(item.getValue())) {
				return item.getValue();
			}
		}

		return "en";
	}
}
