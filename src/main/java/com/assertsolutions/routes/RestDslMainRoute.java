package com.assertsolutions.routes;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.assertsolutions.beans.ResponseHandler;
import com.assertsolutions.dto.Request;
import com.assertsolutions.dto.Response;

import io.swagger.annotations.Api;

/**
 * 
 * @author Assert Solutions S.A.S
 *
 */
@Component
@Api(value = "Initial Proyect Camel-REST", description = "Estrucutura Basica Proyecto Rest Y Camel")
public class RestDslMainRoute extends RouteBuilder {

	@Value("${camel.component.servlet.mapping.context-path}")
	private String contextPath;

	@Autowired
	private Environment env;
	private Logger log = LoggerFactory.getLogger(RestDslMainRoute.class);

	@Override
	public void configure() throws Exception {
	// @formatter:off
        restConfiguration()
            .component("servlet")
            .bindingMode(RestBindingMode.json)
            .dataFormatProperty("prettyPrint", "true")
            .enableCORS(true)
            .port(env.getProperty("server.port", "8080"))
            .contextPath(contextPath.substring(0, contextPath.length() - 2))
            // turn on swagger api-doc
            .apiContextPath("/api-doc")
            .apiProperty("api.title",  env.getProperty("api.title"))
            .apiProperty("api.version", env.getProperty("api.version"));
        
        rest("/services/cursos").description(env.getProperty("api.description"))
            .consumes("application/json")
            .produces("application/json")
        
        .get().description(env.getProperty("api.description.service")).outType(Response.class)
            .responseMessage().code(200).message("All users successfully returned").endResponseMessage()
            .to("direct:listAllCourses")
         .post().description(env.getProperty("api.description.service")).type(Request.class).description(
                 env.getProperty("api.description.input.post")).outType(Response.class) 
             .responseMessage().code(200).message("All users successfully created").endResponseMessage()
             .to("direct:createCourse")
        .put("/{document}")
        	.description(env.getProperty("api.description.service"))
        	.type(Request.class)
        		.description(env.getProperty("api.description.input.post"))
        	.outType(Response.class) 
        	.responseMessage()
            	.code(200)
            	.message("All users successfully created")
        	.endResponseMessage()
        	.param()
    			.dataType("string")
    			.name("document")
    			.type(RestParamType.path)
			.endParam()
        	.to("direct:updateCourse")
        .delete("/{document}").description(env.getProperty("api.description.service")).type(Request.class).description(
        		env.getProperty("api.description.input.post")).outType(Response.class) 
        	.responseMessage().code(200).message("All users successfully created").endResponseMessage()
        	.param()
    			.dataType("string")
    			.name("document")
    			.type(RestParamType.path)
			.endParam()
        	.to("direct:deleteCourse");
       
        from("direct:update-user")
            .bean(ResponseHandler.class)
            .log(LoggingLevel.INFO,log,"Body: ${body}")
            .setHeader("Content-Type", simple("application/json"))
            .setHeader("Accept", simple("application/json"))
            .log(LoggingLevel.INFO,log,"Response Body: ${body}");
        
        from("direct:createCourse")
        	.setProperty("serviceRest", simple("create"))
        	.process("courseProcessor");
        from("direct:updateCourse")
        	.setProperty("serviceRest", simple("update"))
        	.process("courseProcessor");
        from("direct:deleteCourse")
        	.setProperty("serviceRest", simple("delete"))
        	.process("courseProcessor");
        from("direct:listCourse")
        	.setProperty("serviceRest", simple("list"))
        	.process("courseProcessor");
        from("direct:listAllCourses")
        	.setProperty("serviceRest", simple("listall"))
        	.process("courseProcessor");
        // @formatter:on
	}

}
