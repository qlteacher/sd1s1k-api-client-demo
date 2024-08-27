package com.qlteacher.demo.pojo.conf;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;

/**
 * @author 江立国 2024/8/12 11:19
 */
public class ConfigUtil {

    private static Sd1s1kConfiguration config;

    public static Sd1s1kConfiguration getConfig() {
        if(config == null){
            Yaml yaml = new Yaml();
            InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("application.yaml");
            config = yaml.loadAs(stream, Sd1s1kConfiguration.class);
        }
        return config;
    }

    public static void main(String[] args) {
        System.out.println(getConfig().toString());
    }

}
