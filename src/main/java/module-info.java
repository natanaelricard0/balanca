module br.com.cdp.balanca{
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.materialdesign;
    requires java.sql;
    requires java.naming;
    requires com.microsoft.sqlserver.jdbc;
    requires jssc;
    requires jasperreports;

    opens br.com.cdp.balanca.application to javafx.fxml;
    exports br.com.cdp.balanca.application;
    opens br.com.cdp.balanca.controller to javafx.fxml;
    exports br.com.cdp.balanca.model.entities to javafx.fxml;
    opens br.com.cdp.balanca.model.entities;

}