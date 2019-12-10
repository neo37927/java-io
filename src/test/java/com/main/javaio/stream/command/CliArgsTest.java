package com.main.javaio.stream.command;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Arrays;


/**
 * @author xiaolin
 * @date 2019/12/3
 **/
class CliArgsTest {
    /**
     * 命令行参数获取
     *
     * @throws Exception
     */
    @Test
    public void testSwitch() throws Exception {
        String[] args = {"-port", "9999", "-ha", "-conf", "val1", "val2"};

        CliArgs cliArgs = new CliArgs(args);
        //判断是否存在
        boolean ha = cliArgs.switchPresent("-ha");
        Assert.isTrue(ha, "存在");
        boolean has = cliArgs.switchPresent("-has");
        Assert.isTrue(!has, "不存在");

        //解析数值
        String port = cliArgs.switchValue("-port");
        long portLong = cliArgs.switchLongValue("-port");
        double portDouble = cliArgs.switchDoubleValue("-port");
        Assert.isTrue(!StringUtils.isEmpty(port) && portLong != 0 && portDouble != 0, "数值解析成功");

        //解析数值并赋值默认值
        String portDefault = cliArgs.switchValue("-portDefault", "9999");
        long portLongDefault = cliArgs.switchLongValue("-portDefault", 9999L);
        double portDoubleDefault = cliArgs.switchDoubleValue("-portDefault", 9999D);
        Assert.isTrue(portDefault.equals("9999") && portLongDefault == 9999L && portDoubleDefault == 9999D, "数值解析成功");

        //多值解析
        String[] conf = cliArgs.switchValues("-conf");
        Assert.isTrue(conf.length > 0, "多值解析成功");


        CliSwitches switches = cliArgs.switchPojo(CliSwitches.class);
        Assert.isTrue(switches.ha, "pojo 属性存在");
        Assert.isTrue(switches.port == 9999, "pojo 属性赋值成功");
        Assert.isTrue(switches.conf.length == 2, "pojo 多值属性赋值成功");

        Assert.isTrue(true, "参数行解析成功");
    }

    /**
     * 未知参数获取
     * 非以-开头的字符串
     */
    @Test
    public void testTargets() {
        String[] args = {"-port", "9999", "-ha", "-conf", "val1", "val2"};
        CliArgs cliArgs = new CliArgs(args);
        cliArgs.switchValue("-port");
        String[] targets = cliArgs.targets();
        Arrays.stream(targets).forEach(System.out::print);
    }
}