package com.apirestproyangular.core.objects;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class PonerFecha {
	
	public PonerFecha() {}
	
	// solo fecha sin String
	public Date ponerSoloFechaActual() {
		
		Date objDate = new Date();
		SimpleDateFormat sf= new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			
			objDate = sf.parse(sf.format(objDate.toString()));
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return objDate;
		
	}
	
	// solo fecha con String
	public Date ponerSoloFecha(String fechaString) {
		
		Date objDate = new Date();
		
		try {
			
			SimpleDateFormat sf= new SimpleDateFormat("yyyy-MM-dd");
			objDate = sf.parse(fechaString);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return objDate;
		
	}
	
	// fecha hora sin String
	public LocalDateTime ponerFechaHoraActual() {
		
		LocalDateTime localDateNow = LocalDateTime.now();
		
		/*
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		Integer anyo = localDateNow.getYear();
		Integer mes = localDateNow.getMonthValue();
		Integer dia = localDateNow.getDayOfMonth();
		
		Integer hora = localDateNow.getHour();
		Integer min = localDateNow.getMinute();
		Integer seg = localDateNow.getSecond();
		
		String fechaCompleta = anyo + "-" + mes + "-" + dia + " " + hora + ":" + min + ":" + seg;
		
		//LocalDateTime localDateNowDev = LocalDateTime.parse(fechaCompleta, formatter);
		
		System.out.println("Fecha string: " + fechaCompleta);
		//System.out.println("Fecha devuelta: " + localDateNowDev);
		*/
		
		return localDateNow;
		
	}
	
	// fecha hora con String
	public LocalDateTime ponerFechaHora(String fecha, String hora) {
		
		String fechaHora = "";
		fechaHora = fecha + hora;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		LocalDateTime dateTime = LocalDateTime.parse(fechaHora, formatter);
		
		return dateTime;
		
	}
	
	
	
}
