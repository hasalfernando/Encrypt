package com.company;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Encryptor extends JFrame {
    private JFileChooser fileChooser;
    private JPanel mainJrame;
    private JLabel keyLabel;
    private JTextField keyField;
    private JLabel bitLabel;
    private JSpinner shiftSpinner;
    private JButton encryptButton;
    private JButton unEnrcyptButton;
    private JLabel directLabel;
    private JRadioButton leftRadioButton;
    private JRadioButton rightRadioButton;
    private Encrypt.Directions direct;

    public Encryptor() {
        super("Encryptor");
        setLayout(new GridLayout());
        setSize(600,75);
        initComp();
        add(mainJrame);
        Actions();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComp() {
        mainJrame=new JPanel();
        keyLabel=new JLabel("Key: ");
        keyField=new JTextField();
        keyField.setPreferredSize(new Dimension(80,20));
        shiftSpinner=new JSpinner();
        bitLabel=new JLabel("Bits: ");
        shiftSpinner.setValue(1);
        encryptButton=new JButton("Encrypt");
        unEnrcyptButton=new JButton("UnEncrypt");
        directLabel=new JLabel("Direction: ");
        leftRadioButton=new JRadioButton("LEFT",true);
        rightRadioButton=new JRadioButton("RIGHT");
        fileChooser=new JFileChooser();
        mainJrame.add(keyLabel);
        mainJrame.add(keyField);
        mainJrame.add(encryptButton);
        mainJrame.add(unEnrcyptButton);
        mainJrame.add(bitLabel);
        mainJrame.add(shiftSpinner);
        ButtonGroup group= new ButtonGroup();
        group.add(leftRadioButton);
        group.add(rightRadioButton);
        mainJrame.add(directLabel);
        mainJrame.add(leftRadioButton);
        mainJrame.add(rightRadioButton);
    }

    private void Actions () {
        shiftSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if((int)shiftSpinner.getValue()<1) {
                    shiftSpinner.setValue(1);
                }
            }
        });
        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser.setDialogTitle("Chose file");
                int result=fileChooser.showOpenDialog(null);
                if(result==JFileChooser.APPROVE_OPTION) {
                    if(leftRadioButton.isSelected()) {
                        direct= Encrypt.Directions.LEFT;
                    } else {
                        direct= Encrypt.Directions.RIGHT;
                    }
                    try {
                        BufferedReader br = new BufferedReader(new FileReader(fileChooser.getSelectedFile().getAbsoluteFile().toString()));
                        BufferedWriter bw = new BufferedWriter(new FileWriter(fileChooser.getCurrentDirectory()+"\\Encrypted.txt"));
                        String line;
                        while ((line = br.readLine()) != null) {
                            bw.write(new Encrypt(line, keyField.getText(), direct, (int)shiftSpinner.getValue()).encrypt());
                            bw.write('\n');
                        }
                        JOptionPane.showMessageDialog(null,"Message was encrypted");
                        br.close();
                        bw.close();
                    } catch (IOException ex) {

                    }
                }
            }
        });
        unEnrcyptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser.setDialogTitle("Chose file");
                int result=fileChooser.showOpenDialog(null);
                if(result==JFileChooser.APPROVE_OPTION) {
                    if(leftRadioButton.isSelected()) {
                        direct= Encrypt.Directions.LEFT;
                    } else {
                        direct= Encrypt.Directions.RIGHT;
                    }
                   try {
                        BufferedReader br = new BufferedReader(new FileReader(fileChooser.getSelectedFile().getAbsoluteFile().toString()));
                        BufferedWriter bw = new BufferedWriter(new FileWriter(fileChooser.getCurrentDirectory()+"\\UnEncrypted.txt"));
                        String line;
                        while ((line = br.readLine()) != null) {
                            bw.write(new Encrypt(line, keyField.getText(), direct, (int)shiftSpinner.getValue()).unEncrypt());
                            bw.write('\n');
                        }
                       JOptionPane.showMessageDialog(null,"Message was unencrypted");
                        br.close();
                        bw.close();
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        });
    }
}
