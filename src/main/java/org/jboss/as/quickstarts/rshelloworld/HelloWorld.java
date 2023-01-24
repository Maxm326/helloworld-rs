/*
 * JBoss, Home of Professional Open Source
 * Copyright 2015, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.as.quickstarts.rshelloworld;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.sql.DataSource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

/**
 * A simple REST service which is able to say hello to someone using HelloService Please take a look at the web.xml where JAX-RS
 * is enabled
 *
 * @author gbrey@redhat.com
 *
 */

@Path("/")
public class HelloWorld {
    @Inject
    HelloService helloService;

    @Resource(lookup="java:jboss/DATASOURCE")
    private DataSource ds;

    @GET
    @Path("/db")
    @Produces({"application/json"})
    public void getDBTables() throws SQLException {
       Connection con = ds.getConnection();
        System.out.println("Connection established......");
        System.out.println("DB Name = " + con.getCatalog());
        //Creating the DatabaseMetaData object
        DatabaseMetaData dbMetadata = con.getMetaData();
        //invoke the supportsBatchUpdates() method.
        boolean bool = dbMetadata.supportsBatchUpdates();
        if(bool) {
            System.out.println("Underlying database supports batch updates");
        } else {
            System.out.println("Underlying database doesn’t support batch updates");
        }
        //Retrieving the driver name
        System.out.println("Driver name: "+dbMetadata.getDriverName());
        //Retrieving the driver version
        System.out.println("Database version: "+dbMetadata.getDriverVersion());
        //Retrieving the user name
        System.out.println("User name: "+dbMetadata.getUserName());
        //Retrieving the URL
        System.out.println("URL for this database: "+dbMetadata.getURL());
    }

    @GET
    @Path("/json")
    @Produces({ "application/json" })
    public String getHelloWorldJSON() {
        return "{\"result\":\"" + helloService.createHelloMessage("World") + "\"}";
    }

    @GET
    @Path("/xml")
    @Produces({ "application/xml" })
    public String getHelloWorldXML() {
        return "<xml><result>" + helloService.createHelloMessage("World") + "</result></xml>";
    }

}
