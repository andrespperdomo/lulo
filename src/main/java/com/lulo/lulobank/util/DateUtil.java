package com.lulo.lulobank.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.lulo.lulobank.service.AccountTransactionServiceImpl;


public class DateUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);
	
	 static ResourceBundle constantes = ResourceBundle.getBundle("application");
	
	public static Long convertDateTransMinutes(String fechaString) {
		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		String pat=constantes.getString("patternDate");
		logger.info("patternDate {}",pat);
		long minutes=0;
		try {
			Date fechaTrans =new SimpleDateFormat(pat).parse(fechaString);
			Date sysdate =new Date();
			logger.info("fechaTrans:{}",fechaTrans);
			logger.info("sysdate:{}",sysdate);
			long diferencia=sysdate.getTime()-fechaTrans.getTime();
		    logger.info("Diferencia: {}",diferencia+"");
			minutes = TimeUnit.MILLISECONDS.toMinutes(diferencia);
			logger.info("minutes: {}",minutes+"");
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		return minutes;
	}
	
	public static String sysdateString() {
       String dateString = new SimpleDateFormat(constantes.getString("patternDate")).format(new Date());
       logger.info("dateString: {}",dateString);
       return dateString;
	}

}
