package com.axe.net;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ServiceMethod
{
	public int id();
	public boolean reliable() default true;
	public boolean ordered() default false;
	public int retryCount() default 0;
	public int channel() default 0;
}
