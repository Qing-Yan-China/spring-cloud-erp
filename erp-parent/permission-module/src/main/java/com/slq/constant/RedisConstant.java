package com.slq.constant;

import java.util.concurrent.TimeUnit;

import com.slq.common.Time;

public interface RedisConstant {

	Time DEFAULT_EXPIRE=new Time(30, TimeUnit.MINUTES);
}
