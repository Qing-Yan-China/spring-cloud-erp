package com.slq.common;

import java.util.concurrent.TimeUnit;

import lombok.Data;
@Data
public class Time {

	private long time;
	
	private TimeUnit timeUnit;

	
	public Time(long time, TimeUnit timeUnit) {
		super();
		this.time = time;
		this.timeUnit = timeUnit;
	}

	public Time() {
		super();
		this.time=60*30;
		this.timeUnit=TimeUnit.SECONDS;
	}
	
	
}
