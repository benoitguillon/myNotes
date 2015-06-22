// ============================================================================
//
// Copyright (C) 2006-2015 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.bgi.hibernate.cache;


public class Contact {
    
    private long id;
    
    private String firstName;
    
    private String lastName;
    
    private String address;
    
    private String city;
    
    private String zipCode;
    
    private String country;

    
    public long getId() {
        return id;
    }

    
    public void setId(long id) {
        this.id = id;
    }

    
    public String getFirstName() {
        return firstName;
    }

    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    
    public String getLastName() {
        return lastName;
    }

    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    
    public String getAddress() {
        return address;
    }

    
    public void setAddress(String address) {
        this.address = address;
    }

    
    public String getCity() {
        return city;
    }

    
    public void setCity(String city) {
        this.city = city;
    }

    
    public String getZipCode() {
        return zipCode;
    }

    
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    
    public String getCountry() {
        return country;
    }

    
    public void setCountry(String country) {
        this.country = country;
    }
    
    

}
