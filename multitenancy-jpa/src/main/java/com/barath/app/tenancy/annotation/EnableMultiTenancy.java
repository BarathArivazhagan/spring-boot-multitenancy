package com.barath.app.tenancy.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.barath.app.tenancy.configuration.MultitenantConfiguration;



@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(MultitenantConfiguration.class)
public @interface EnableMultiTenancy {

}
