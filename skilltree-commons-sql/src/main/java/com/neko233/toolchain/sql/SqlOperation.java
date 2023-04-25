package com.neko233.toolchain.sql;

/**
 * SQL 操作符
 *
 * @author SolarisNeko on 2022-01-01
 **/
public enum SqlOperation {

    // =
    EQ,

    // !=
    NOT_EQ,

    // <
    LT,

    // <=
    LTE,

    // >
    GT,

    // >=
    GTE,

    // in ( ... )
    IN,

    // not in (..)
    NOT_IN,

}