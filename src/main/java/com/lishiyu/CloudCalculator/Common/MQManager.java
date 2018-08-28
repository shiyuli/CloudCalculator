package com.lishiyu.CloudCalculator.Common;

import java.util.List;

public interface MQManager {

    public void send(String message);
    public List<String> read();

}
