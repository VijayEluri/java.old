/*
 * Copyright (C) 2012 Fan Hongtao (http://www.fanhongtao.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.fanhongtao.tools.contact;

import java.io.File;
import java.util.List;

import org.fanhongtao.lang.StringUtils;
import org.fanhongtao.tools.contact.bean.Contact;
import org.fanhongtao.tools.contact.bean.ContactTable;
import org.fanhongtao.tools.contact.bean.PhoneElement;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

/**
 * 将魅族M8备份目录下的通讯录从XML格式转换成 CSV格式，以便可以通过豌豆荚导入到Android手机中
 * @author Fan Hongtao &ltfanhongtao@gmail.com&gt 
 */
public class M8ContactConverter {

    private String skip(String s) {
        return s == null ? "" : s;
    }

    public void convert() {
        Serializer serializer = new Persister();
        File source = new File("d:/Contact.xml");
        try {
            ContactTable contactTable = serializer.read(ContactTable.class, source);
            List<Contact> contactList = contactTable.getContactList();
            // System.out.println("Contact num: " + contactList.size());
            for (Contact contact : contactList) {
                StringBuilder sb = new StringBuilder(256);
                sb.append("BEGIN:VCARD").append(StringUtils.CRLF);
                sb.append("VERSION:3.0").append(StringUtils.CRLF);

                sb.append("FN:").append(contact.getFileAs()).append(StringUtils.CRLF);
                sb.append("N:").append(skip(contact.getFirstName())).append(";");
                sb.append(";").append(skip(contact.getLastName()));
                sb.append(";;").append(StringUtils.CRLF);

                List<PhoneElement> phoneList = contact.getPhone();
                for (PhoneElement phoneElement : phoneList) {
                    sb.append("TEL;TYPE=CELL:").append(phoneElement.getValue()).append(StringUtils.CRLF);
                }

                sb.append("END:VCARD").append(StringUtils.CRLF);
                System.out.print(sb.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new M8ContactConverter().convert();
    }
}
