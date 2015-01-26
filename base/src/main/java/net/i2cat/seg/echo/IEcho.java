package net.i2cat.seg.echo;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.i2cat.seg.echo.model.Message;

@Path("/")
public interface IEcho {
	
	@Path("echo")
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Message echo(Message message);

}
