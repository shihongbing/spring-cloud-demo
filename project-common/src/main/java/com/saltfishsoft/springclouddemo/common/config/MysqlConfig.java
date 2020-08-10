package com.saltfishsoft.springclouddemo.common.config;

import org.hibernate.dialect.MySQL57Dialect;

/**
 * Created by Shihongbing on 2020/6/16.
 */
public class MysqlConfig extends MySQL57Dialect {
    @Override
    public String getTableTypeString() {
        return " ENGINE=InnoDB DEFAULT CHARSET=utf8";
    }

}
