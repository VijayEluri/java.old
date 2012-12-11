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
package org.fanhongtao.tools.contact.bean;

import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * 
 * @author Fan Hongtao &ltfanhongtao@gmail.com&gt 
 */
@Root(name = "Contact")
public class Contact {
    @Element
    private String FileAs;

    @Element(required = false)
    private String FirstName;

    @Element(required = false)
    private String LastName;

    @Element(required = false)
    private String Company;

    @Element(required = false)
    private String Category;

    @Element
    private int SmsLock;

    @ElementList(name = "Phone")
    private List<PhoneElement> phone;

    public String getFileAs() {
        return FileAs;
    }

    public void setFileAs(String fileAs) {
        FileAs = fileAs;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public int getSmsLock() {
        return SmsLock;
    }

    public void setSmsLock(int smsLock) {
        SmsLock = smsLock;
    }

    public List<PhoneElement> getPhone() {
        return phone;
    }

    public void setPhone(List<PhoneElement> phone) {
        this.phone = phone;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        Company = company;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

}
