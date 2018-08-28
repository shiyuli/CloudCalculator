package com.lishiyu.CloudCalculator.Common;

import javax.script.*;

public class Core {

    public static final String CALC_ADD = "+";
    public static final String CALC_SUB = "-";
    public static final String CALC_MUL = "*";
    public static final String CALC_DIV = "/";
    public static final String CALC_EQL = "=";
    public static final String CALC_CLS = "C";

    private Core() {}

    /**
     * @param calcString raw String to parse
     * @return parse result
     */
    public static String parse(String calcString) {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        Object result = null;
        try {
            result = engine.eval(calcString);
        } catch (Exception e) {
            result = "Core.parse: error";
        }

        return result != null ? String.valueOf(result) : null;
    }

    private static boolean isNumber(char number) {
        try {
            Integer.parseInt(String.valueOf(number));
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    private static boolean isOperator(char operator) {
        String operatorString = String.valueOf(operator);

        if(operatorString.equals(CALC_ADD)) {
        } else if (operatorString.equals(CALC_SUB)) {
        } else if (operatorString.equals(CALC_MUL)) {
        } else if (operatorString.equals(CALC_DIV)) {
        } else if (operatorString.equals(CALC_EQL)) {
        } else if (operatorString.equals(CALC_CLS)) {
        } else {
            return false;
        }

        return true;
    }

    private static String legacyParse(String calcString) {
        String result;
        result = "Test123";

        String number = "";
        for(char c:calcString.toCharArray()) {
            if(isNumber(c)) {
                number += c;
            }
            else
            {

            }
            Utils.debug(String.valueOf(c));
        }

        return result;
    }

}
