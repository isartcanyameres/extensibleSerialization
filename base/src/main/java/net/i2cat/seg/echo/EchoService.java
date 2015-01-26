package net.i2cat.seg.echo;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.i2cat.seg.echo.model.Message;

public class EchoService implements IEcho {
	
	public static final String SERVICE_URL = "http://localhost:9000/echo/";
	
	private static final Logger	log	= LoggerFactory.getLogger(EchoService.class);
	
	private Server server;
	
	public void publish() {
		
		log.info("Publishing EchoService");
		
		JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();
		sf.setAddress(SERVICE_URL);
		sf.setResourceClasses(EchoService.class);
		server = sf.create();
		
		log.info("EchoService published");
	}
	
	public void unpublish() {
		log.info("Unpublishing EchoService");
		if (server != null) {
			server.destroy();
			log.info("EchoService unpublished");
		}	
	}

	@Override
	public Message echo(Message message) {
		return message;
	}

}
