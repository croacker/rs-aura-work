package com.invariant.rs.service.serial;

/**
 *
 */
public interface Configuration {

    String getName();

    int getBaudRate();

    int getDataBits();

    int getstopBits();

    int getParity();

}