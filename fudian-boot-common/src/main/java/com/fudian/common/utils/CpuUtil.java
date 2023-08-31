package com.fudian.common.utils;


import lombok.extern.slf4j.Slf4j;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.ComputerSystem;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;

/**
 * @Description
 * @ClassName CpuUtil
 * @Author mr.hou
 * @Date 2019/9/24 3:32 下午
 * @Version 1.0.0
 **/
@Slf4j
public class CpuUtil {

    private static SystemInfo systemInfo = new SystemInfo();

    public static String generateLicenseKey() throws Exception {
        HardwareAbstractionLayer hardwareAbstractionLayer = systemInfo.getHardware();
        ComputerSystem computerSystem = hardwareAbstractionLayer.getComputerSystem();
        String serialNumber = computerSystem.getSerialNumber();
        log.info("服务器序列号:==>" + serialNumber);
        return serialNumber;
    }

    public static void main(String[] arguments) throws Exception {
        String identifier = generateLicenseKey();
        System.out.println(identifier);
    }
}

