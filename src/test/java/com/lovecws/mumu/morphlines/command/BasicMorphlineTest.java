package com.lovecws.mumu.morphlines.command;

import org.junit.Test;
import org.kitesdk.morphline.api.Command;
import org.kitesdk.morphline.api.MorphlineContext;
import org.kitesdk.morphline.api.Record;
import org.kitesdk.morphline.base.Compiler;
import org.kitesdk.morphline.base.Fields;
import org.kitesdk.morphline.base.Notifications;

import java.io.File;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: Morphline测试
 * @date 2018-01-11 17:35:
 */
public class BasicMorphlineTest {

    @Test
    public void testAddValues() throws Exception {
        MorphlineContext context = new MorphlineContext.Builder().build();
        File configFile = new File(BasicMorphlineTest.class.getResource("/morphlines/addValues.conf").getPath());
        Command morphline = new Compiler().compile(configFile, null, context, null);

        Record record = new Record();
        record.put("first_name", "Nadja2");
        //record.put(Fields.ATTACHMENT_BODY,"");
        boolean process = morphline.process(record);
        System.out.println(process);

        System.out.println(record);

        Notifications.notifyCommitTransaction(morphline);
    }

    @Test
    public void sysLogTest() {
        MorphlineContext context = new MorphlineContext.Builder().build();
        File configFile = new File(BasicMorphlineTest.class.getResource("/morphlines/syslog.conf").getPath());
        Command morphline = new Compiler().compile(configFile, null, context, null);
        Record record = new Record();
        record.put(Fields.ATTACHMENT_BODY, BasicMorphlineTest.class.getResourceAsStream("/log/syslog.log"));
        boolean process = morphline.process(record);
        System.out.println(process);
    }

    @Test
    public void tomcatAccessLogTest() {
        MorphlineContext context = new MorphlineContext.Builder().build();
        File configFile = new File(BasicMorphlineTest.class.getResource("/morphlines/tomcat.conf").getPath());
        Command morphline = new Compiler().compile(configFile, null, context, null);
        Record record = new Record();
        record.put(Fields.ATTACHMENT_BODY, BasicMorphlineTest.class.getResourceAsStream("/log/tomcat_accesslog.txt"));
        boolean process = morphline.process(record);
        System.out.println(process);
    }
}
