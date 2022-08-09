package br.com.cdp.balanca.application;

import jssc.SerialPort;
import jssc.SerialPortException;



public class SerialTest {

    public static void main(String[] args) {
        SerialPort serialPort = new SerialPort("COM4");

        try {

            System.out.println("Port aberta: " + serialPort.openPort() );
            System.out.println("Params seated: " + serialPort.setParams(9600,8,1,0));
            System.out.println("\"Hello World!!!\" successfully writen to port: " + serialPort.writeBytes("Hello World!!!".getBytes()));
            serialPort.writeString("s");
            System.out.println("Port closed: " + serialPort.closePort());

        }
        catch (SerialPortException ex) {
            System.out.println(ex);
        }
    }
}
