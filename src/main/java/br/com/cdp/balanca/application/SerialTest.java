package br.com.cdp.balanca.application;

import jssc.SerialPort;
import jssc.SerialPortException;



public class SerialTest {

    public static void main(String[] args) {
        SerialPort serialPort = new SerialPort("COM1");

        try {

            System.out.println("Port aberta: " + serialPort.openPort() );
            System.out.println("Params seated: " + serialPort.setParams(9600,8,1,0));
            System.out.println("\"Hello World!!!\" successfully writen to port: " + serialPort.writeBytes("Hello World!!!".getBytes()));
            System.out.println("Get port " + serialPort.getPortName());
            System.out.println("Estado da porta: " + serialPort.isOpened());
            System.out.println("aaaaaaaaaaaaaaaaa");



            System.out.println("Port closed: " + serialPort.closePort());

        }
        catch (SerialPortException ex) {
            System.out.println(ex);
        }
    }
}
