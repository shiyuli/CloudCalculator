package com.lishiyu.CloudCalculator.Client;

import com.lishiyu.CloudCalculator.Common.Utils;

public class Main {

    public static void main(String[] args) {
        Utils.debug("App run!");
        UI ui = new GUI();
        ui.show();
    }

}
