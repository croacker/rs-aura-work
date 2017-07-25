package com.invariant.saleserver.http;

import com.invariant.devices.posiflex.printer.Printer;
import com.invariant.devices.service.serial.SerialPortConfiguration;
import com.invariant.saleserver.service.DeviceService;
import com.invariant.saleserver.service.HttpService;
import com.invariant.saleserver.service.JsonService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component("devicesListHandler")
@Slf4j
public class DevicesListHandler implements HttpHandler {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private HttpService httpService;

    @Autowired
    private JsonService jsonService;

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String requestMethod = httpExchange.getRequestMethod();
        if (requestMethod.equals("GET")) {
            get(httpExchange);
        } else {
            String mesage = "405 Method " + requestMethod + " not allowed ";
            httpService.writeMethodNotAlowed(httpExchange, mesage.getBytes());
        }
    }

    private void get(HttpExchange httpExchange) throws IOException {
        List<SerialPortConfiguration> printersConfigurations = getDevicesConfigurations();
        httpService.writeJson(httpExchange, jsonService.toJson(printersConfigurations));
    }

    private Printer getDevice(String id) {
        return deviceService.getDevice(id);
    }

    private List<SerialPortConfiguration> getDevicesConfigurations() {
        return deviceService.getDevices().stream().map(device -> device.getConfiguration()).collect(Collectors.toList());
    }

}
