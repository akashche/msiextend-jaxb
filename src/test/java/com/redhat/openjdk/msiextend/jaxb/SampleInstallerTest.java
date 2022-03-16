/*
 * Copyright 2016 akashche@redhat.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.redhat.openjdk.msiextend.jaxb;

import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT;
import static org.apache.commons.io.IOUtils.closeQuietly;
import static org.apache.commons.io.output.NullOutputStream.NULL_OUTPUT_STREAM;

/**
 * User: akashche
 * Date: 5/4/16
 */
public class SampleInstallerTest {

    @Test
    public void test() throws Exception {
        JAXBContext jaxb = JAXBContext.newInstance(SampleInstallerTest.class.getPackage().getName());
        InputStream is = null;
        Writer writer = null;
        try {
            is = SampleInstallerTest.class.getResourceAsStream("SampleInstaller.wxs");
            Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
            Wix wix = (Wix) jaxb.createUnmarshaller().unmarshal(reader);
            wix.getProduct().setName(wix.getProduct().getName() + " (JAXB)");
//            OutputStream os = new FileOutputStream("SampleInstaller_JAXB.wxs");
            OutputStream os = NULL_OUTPUT_STREAM;
            writer = new OutputStreamWriter(os, StandardCharsets.UTF_8);
            Marshaller marshaller = jaxb.createMarshaller();
            marshaller.setProperty(JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(wix, writer);
        } finally {
            closeQuietly(is);
            closeQuietly(writer);
        }
    }
}
