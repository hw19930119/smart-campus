package com.sunsharing.smartcampus.utils;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

// 演示例子，执行 main 方法控制台输入模块表名回车自动生成对应项目目录中
public class CodeGenerator {
    static String packageName = "";

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        //String entityName=scanner("实体名称");
        //String tableName=scanner("表名");
        packageName = "audit";
        Map<String, String> map = new HashMap<>();
        /*map.put("T_SC_APPLY_USER","ApplyUser");
        map.put("T_SC_AUDIT_USER","AuditUser");
        map.put("T_SC_ROLE","Role");
        map.put("T_SCF_PROCDEF","ProcDef");
        map.put("T_SCF_PDNODE","PdNode");
        map.put("T_SCF_PROCINST","ProcInst");
        map.put("T_SCF_TODO_DONE","TodoDone");
        map.put("T_SCF_AUDIT_RECORD","AuditRecord");
        map.put("T_SCF_NODE_PERSON","NodePerson");
        map.put("T_SCF_PERSON_TODO_DONE","PersonTodoDone");*/
        //指标和评分
        map.put("T_SCF_RETURN_BASE", "ReturnBase");

       /*

        */

        //map.put("T_ZHXY_JCSS", "TzhxyJcss");
        //map.put("T_ZHXY_BASEINFO", "TzhxyBaseInfo");
        //map.put("T_ZHXY_SCHOOL", "TzhxySchool");
        //map.put("T_ZHXY_MENU", "Menu");
        for (String key : map.keySet()) {
            System.out.println("table= " + key + " and model= " + map.get(key));
            String tableName = key;//"T_SCF_PROCDEF";
            String entityName = map.get(key);//"ProcDef";
            generatorCode(entityName, tableName);
        }
    }

    private static void generatorCode(String entityName, String tableName) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("CodeGenerator");
        gc.setOpen(false);
        gc.setDateType(DateType.ONLY_DATE);

        gc.setEntityName(entityName);
        gc.setServiceName(entityName + "Service");
        gc.setServiceImplName(entityName + "ServiceImpl");
        gc.setMapperName(entityName + "Mapper");
        gc.setControllerName(entityName + "Controller");
        // gc.setSwagger2(true); 实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://192.168.0.59:3306/SMART_CAMPUS?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("sunsharing_DDK");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.sunsharing.smartcampus");
        mpg.setPackageInfo(pc);
        pc.setEntity("model.pojo." + packageName);
        pc.setMapper("mapper." + packageName);
        pc.setXml("mappers." + packageName);
        pc.setService("service." + packageName);
        pc.setServiceImpl("service.impl." + packageName);
        pc.setController("web." + packageName);
        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mappers/" + packageName + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        /*
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir("调用默认方法创建的目录，自定义目录用");
                if (fileType == FileType.MAPPER) {
                    // 已经生成 mapper 文件判断存在，不想重新生成返回 false
                    return !new File(filePath).exists();
                }
                // 允许生成模板文件
                return true;
            }//T_SCF_PROCDEF
        });
        */

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();

        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        //strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
        strategy.setEntityLombokModel(true);
        //strategy.setRestControllerStyle(true);
        // 公共父类
        //strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");
        // 写于父类中的公共字段
        //strategy.setSuperEntityColumns("id");
        strategy.setInclude(tableName.split(","));
        //strategy.setControllerMappingHyphenStyle(true);
        //strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

}