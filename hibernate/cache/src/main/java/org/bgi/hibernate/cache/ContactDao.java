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

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


public class ContactDao {
    
    private SessionFactory sessionFactory;

    public Contact getById(long id){
        Session session = sessionFactory.openSession();
        try {
            return (Contact) session.get(Contact.class, id);
        }
        finally {
            session.close();
        }
    }
    
    public long save(Contact c){
        Session session = sessionFactory.openSession();
        try {
            return (long)session.save(c);
        }
        finally {
            session.close();
        }
    }
    
    public void update(Contact c){
        Session session = sessionFactory.openSession();
        Transaction t = session.beginTransaction();
        try {
        	session.saveOrUpdate(c);
        	t.commit();
        }
        catch(Exception e){
        	t.rollback();
        }
        finally {
        	session.close();
        }
    }
    
    
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    

}
