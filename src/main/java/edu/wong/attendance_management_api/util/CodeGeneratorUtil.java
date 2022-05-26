package edu.wong.attendance_management_api.util;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.io.Serializable;
import java.util.Collections;

/**
 * 代码生成器
 * <a href="https://baomidou.com/pages/779a6e/">参考模板地址</a>
 */
public class CodeGeneratorUtil implements Serializable {
    public static final String url = "jdbc:mysql://localhost:3306/attend_mgt?useUnicode=true&characterEncoding=UTF-8";
    private static final String root = "root";
    private static final String password = "123456";
    private static final String outputDir = "E://IdeaProjects//attendance_management_api//src//main//java";
    private static final String xmlOutputDir = "E://IdeaProjects//attendance_management_api//src//main//resources//mapper";

    public static void main(String[] args) {
        FastAutoGenerator.create(url, root, password)
                .globalConfig(builder -> {
                    builder.author("WongSilver") // 设置作者
                            //.enableSwagger() // 开启 swagger 模式
                            //.fileOverride() // 覆盖已生成文件
                            .outputDir(outputDir); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("edu.wong.attendance_management_api") // 设置父包名
                            .moduleName("") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, xmlOutputDir)); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("t_check", "t_course", "t_group", "t_leave",
                                    "t_right", "t_role", "t_role_right", "t_selected_course",
                                    "t_user", "t_user_role") // 设置需要生成的表名
                            .addTablePrefix("t_", "c_"); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}