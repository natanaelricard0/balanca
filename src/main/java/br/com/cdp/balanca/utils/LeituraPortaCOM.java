package br.com.cdp.balanca.utils;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortTimeoutException;

import static jssc.SerialPort.*;

public class LeituraPortaCOM {
    private static SerialPort port;

    public static double leituraPeso() {
        double peso = 0;
        try {
            port = new SerialPort("COM1");
            port.openPort();
            port.setParams(BAUDRATE_9600, DATABITS_8, STOPBITS_2, PARITY_NONE);
//            port.writeBytes(new byte[]{0x04});
            port.setEventsMask(MASK_RXCHAR);
            byte[] buffer = port.readBytes(8, 6000);
            String valor = new String(buffer);
            peso = Double.parseDouble(convertStringToDigit(valor));
            return peso;
        } catch (SerialPortException e) {
            System.out.println(e.getMessage());
        } catch (SerialPortTimeoutException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e){
            e.printStackTrace();
            System.out.println("ERRO:" + e.getMessage());
        }
        finally {
            try {
                port.closePort();
            }catch (SerialPortException e){
                e.getMessage();
            }
        }
        return peso;
    }

    private static String convertStringToDigit(String valor) {
        StringBuffer buffer = new StringBuffer();
        char [] chars = valor.toCharArray();
        boolean stop = false;
        for (Character cr : chars) {
            if(cr == 'E'){
                stop = true;
            }
            if (Character.isDigit(cr) && stop == false) {
                buffer.append(cr);
            }
        }
        return buffer.toString();
    }
}
