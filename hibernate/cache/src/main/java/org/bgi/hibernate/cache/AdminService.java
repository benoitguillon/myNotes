package org.bgi.hibernate.cache;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;

@Path("/admin")
public class AdminService {
	
	private SessionFactory sessionFactory;
	
	@POST
	@Path("/index")
	public Response launchIndex() throws Exception {
		Session session = this.sessionFactory.openSession();
		FullTextSession fullTextSession = Search.getFullTextSession(session);
		try {
			fullTextSession.createIndexer().startAndWait();
		}
		finally {
			fullTextSession.close();
		}
		return Response.ok().build();
	}
	
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
