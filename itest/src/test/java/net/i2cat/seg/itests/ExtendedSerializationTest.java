package net.i2cat.seg.itests;

import java.io.File;

import net.i2cat.seg.echo.EchoService;
import net.i2cat.seg.echo.IEcho;
import net.i2cat.seg.echo.model.Message;
import net.i2cat.seg.extension.model.ExtendedMessage;

import org.apache.cxf.common.util.ProxyClassLoader;
import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.CoreOptions;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.karaf.options.KarafDistributionOption;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerClass;

@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class ExtendedSerializationTest {
	
	@Configuration
	public Option[] config() {
		// FIXME Read features version from maven.
		// now features version in this file must be changed manually in each release!
		return new Option[] {
				// distribution to test: Karaf 3.0.1
				KarafDistributionOption.karafDistributionConfiguration()
						.frameworkUrl(CoreOptions.maven().groupId("org.apache.karaf").artifactId("apache-karaf").type("tar.gz").version("3.0.1"))
						.karafVersion("3.0.1").name("Apache Karaf").useDeployFolder(false)
						// keep deployed Karaf
						.unpackDirectory(new File("target/paxexam")),
				// no local and remote consoles
				KarafDistributionOption.configureConsole().ignoreLocalConsole(),
				KarafDistributionOption.configureConsole().ignoreRemoteShell(),
				// keep runtime folder allowing analysing results
				KarafDistributionOption.keepRuntimeFolder(),
				// use custom logging configuration file with a custom appender
				KarafDistributionOption.replaceConfigurationFile("etc/org.ops4j.pax.logging.cfg", new File(
						"src/test/resources/org.ops4j.pax.logging.cfg")),
				// maintain our log configuration
				KarafDistributionOption.doNotModifyLogConfiguration(),
				// add our own features
				KarafDistributionOption.features(CoreOptions.maven().groupId("net.i2cat.seg").artifactId("ext-serialization").classifier("features")
						.type("xml").version("0.0.1-SNAPSHOT"), "all"),
		// debug option
		// KarafDistributionOption.debugConfiguration(),
		};
	}
	
	
	@Test
	public void messageEchoTest() {
		
		ProxyClassLoader classLoader = new ProxyClassLoader(EchoService.class.getClassLoader());
		classLoader.addLoader(JAXRSClientFactoryBean.class.getClassLoader());
		
		JAXRSClientFactoryBean cf = new JAXRSClientFactoryBean();
		cf.setAddress(EchoService.SERVICE_URL);
		cf.setClassLoader(classLoader);
		cf.setResourceClass(IEcho.class);
		IEcho client = cf.create(IEcho.class);
		
		Message message = new Message();
		message.setBody("BODY");
		message.setSubject("SUBJECT");
		
		Message returned = client.echo(message);
		Assert.assertEquals(message, returned);
	}
	
	@Test
	public void extendedMessageEchoTest() {
		
		ProxyClassLoader classLoader = new ProxyClassLoader(EchoService.class.getClassLoader());
		classLoader.addLoader(JAXRSClientFactoryBean.class.getClassLoader());
		
		JAXRSClientFactoryBean cf = new JAXRSClientFactoryBean();
		cf.setAddress(EchoService.SERVICE_URL);
		cf.setClassLoader(classLoader);
		cf.setResourceClass(IEcho.class);
		IEcho client = cf.create(IEcho.class);
		
		ExtendedMessage message = new ExtendedMessage();
		message.setBody("BODY");
		message.setSubject("SUBJECT");
		message.setDate(System.currentTimeMillis());
		
		Message returned = client.echo(message);
		Assert.assertEquals(message, returned);
	}
	
	

}
