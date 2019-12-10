package com.main.javaio.stream.command;

/**
 * @author xiaolin
 * @date 2019/12/3
 **/
public class CliSwitches {
    public boolean ha = false;
    public int port = 8080;
    public byte port2 = 123;
    public String[] conf = null;

    public CliSwitches() {
    }

    public CliSwitches(boolean ha, int port, byte port2, String[] conf) {
        this.ha = ha;
        this.port = port;
        this.port2 = port2;
        this.conf = conf;
    }
}
