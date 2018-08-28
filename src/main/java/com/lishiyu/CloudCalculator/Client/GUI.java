package com.lishiyu.CloudCalculator.Client;

import com.lishiyu.CloudCalculator.Common.Core;
import com.lishiyu.CloudCalculator.Common.MQManager;
import com.lishiyu.CloudCalculator.Common.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.List;

public class GUI implements UI {

    private JFrame frame;
    private JPanel panelNumber;
    private JPanel panelOperator;
    private JTextField textField;
    private LayoutManager layoutManager;

    private final List<JButton> numberButtonList;
    private final Map<String, JButton> operateButtonMap;
    private String calcString;
    private String calcResult;

    public GUI() {
        numberButtonList = new ArrayList<JButton>();
        operateButtonMap = new HashMap<String, JButton>();
        calcString = "";
        createUI();
        initStyle();
    }

    public void show() {
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    private void createUI() {
        //Create and set up the window.
        frame = new JFrame("Calc");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        layoutManager = new SpringLayout();
        frame.getContentPane().setLayout(layoutManager);

        panelNumber = new JPanel(layoutManager);
        panelOperator = new JPanel(layoutManager);
        frame.getContentPane().add(panelNumber);
        frame.getContentPane().add(panelOperator);

        textField = new JFormattedTextField();
        frame.getContentPane().add(textField);

        //Add a label.
//        JLabel label = new JLabel("Test Label");
//        frame.getContentPane().add(label);

        //Add number buttons
        createNumberButtons();

        //Add operate buttons
        createOperateButtons();
    }

    private void createNumberButtons() {
        for(int i = 0; i <= 9; ++i) {
            String buttonName = String.valueOf(i);
            final JButton button = new JButton(buttonName);
            button.setName(buttonName);
            button.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    OnNumberButtonClick(button);
                }

                @Override
                public void mousePressed(MouseEvent e) {
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                }

                @Override
                public void mouseExited(MouseEvent e) {
                }
            });
            panelNumber.add(button);

            numberButtonList.add(button);
            System.out.println(button.getName());
        }
    }

    private void createOperateButtons() {
        createOperateButton(Core.CALC_ADD);
        createOperateButton(Core.CALC_SUB);
        createOperateButton(Core.CALC_MUL);
        createOperateButton(Core.CALC_DIV);
        createOperateButton(Core.CALC_EQL);
        createOperateButton(Core.CALC_CLS);
    }

    private void createOperateButton(String buttonName) {
        final JButton button = new JButton(buttonName);
        button.setName(buttonName);
        button.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                OnOperateButtonClick(button);
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        panelOperator.add(button);
        operateButtonMap.put(buttonName, button);
//        SpringUtilities.setPos(button, panelOperator, 20, 15, 5, 5, 2, 2);
    }

    private void initStyle() {
//        SpringUtilities.setPos(panelNumber, frame.getContentPane(), 2, 2, 0, 0, 2, 2);
//        SpringUtilities.setPos(panelOperator, frame.getContentPane(), 20, 15, 100, 20, 2, 2);

        SpringUtilities.makeGrid(frame.getContentPane(), 1, 3, 5, 5, 5, 5);
        SpringUtilities.makeGrid(panelNumber, 3, 3, 5, 5, 5, 5);
        SpringUtilities.makeGrid(panelOperator, 3, 2, 150, 5, 5, 5);

        for (int i = 0; i < panelNumber.getComponentCount(); i++) {
            Component control = panelNumber.getComponent(i);
            String controlName = control.getName();
            if("9".equals(controlName)) {
                Utils.debug("find 9 control");
                SpringUtilities.setPos(control, panelNumber, 2000, 2000, 2, 2, 5, 5);
            }
        }
    }

    private void appendCalcString(String str) {
        calcString += str;
        textField.setText(calcString);
    }

    private void resetCalc() {
        calcString = "";
        calcResult = "";
    }

    public static String cloudParse(String calcString) {
        String result = null;
        List<String> resultList;

        try {
            ClientMQManager.getInstance().send(calcString);
        } catch (Exception e) {
            return "cloudParse: send calcString error";
        }

        try {
            resultList = ClientMQManager.getInstance().read();
        } catch (Exception e) {
            return "cloudParse: read calcResult error";
        }

        if(resultList == null) {
            return null;
        }

        for(String message: resultList) {
//            if(!"START".equals(message)) {
//                continue;
//            }
        }

        return result;
    }

    private void OnNumberButtonClick(JButton sender) {
        String buttonName = sender.getName();
        Utils.debug("buttonName: " + buttonName);

        appendCalcString(buttonName);

        Utils.debug(calcString);
    }

    private void OnOperateButtonClick(JButton sender) {
        String buttonName = sender.getName();
        Utils.debug("buttonName: " + buttonName);

        if(buttonName.equals(Core.CALC_EQL)) {
            calcResult = cloudParse(calcString);

            if(calcResult != null)
            {
                textField.setText(calcResult);
            } else {
                textField.setText("calc error");
            }

            resetCalc();
        } else if(buttonName.equals(Core.CALC_CLS)) {
            resetCalc();
            textField.setText("");
        } else {
            appendCalcString(buttonName);
        }

        Utils.debug(calcString);
    }

}
