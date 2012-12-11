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

import org.simpleframework.xml.Attribute;

/**
 * 
 * @author Fan Hongtao &ltfanhongtao@gmail.com&gt 
 */
public class PhoneElement {
    @Attribute
    private String IsPrimary;

    @Attribute
    private String Value;

    @Attribute
    private String Type;

    public String getIsPrimary() {
        return IsPrimary;
    }

    public void setIsPrimary(String isPrimary) {
        IsPrimary = isPrimary;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
