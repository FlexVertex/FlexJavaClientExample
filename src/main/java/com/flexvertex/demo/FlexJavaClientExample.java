/**
 * FlexJavaClientExample: Connect to a FlexVertex instance, query, retrieve, and display objects
 *
 * <p>This sample demonstrates how to connect to a FlexVertex instance, run a SQL query, retrieve objects, and display in JSON format
 *
 *
 * @author      FlexVertex
 * @version     1.0
 * @since       2025-03-16
 * @copyright   Copyright 2023-2025, FlexVertex. All rights reserved.
 * @license     Sample Code – Not for production
 */

package com.flexvertex.demo;

import com.flexvertex.messaging.client.drivers.FlexDirectClientDriver;
import com.flexvertex.messaging.client.interfaces.IFlexClientDriver;

import com.flexvertex.multiverse.client.FlexMultiverse;

import com.flexvertex.multiverse.shared.interfaces.*;
import com.flexvertex.multiverse.shared.schema.*;

import com.flexvertex.universe.commons.FlexCommons;

import com.flexvertex.security.shared.interfaces.IFlexAuthenticationToken;
import com.flexvertex.security.shared.tokens.FlexUsernamePasswordToken;

public class FlexJavaClientExample
{
	public static void main(String[] args) throws Exception
	{
		try
		{
			// This is required for all clients before connecting to FlexVertex.
			FlexMultiverse.initialize();
			
			FlexJavaClientExample demo = new FlexJavaClientExample();
			demo.execute();
		}
		catch(Exception ex)
		{
			System.out.println("Something bad happened: " + ex);	
		}

		System.exit(0);
	}

	public FlexJavaClientExample()
	{
	}

	public void execute() throws Exception
	{
		String host = "localhost";
		int port = 10000;

		IFlexClientDriver driver = new FlexDirectClientDriver(host, port);

		String username = "marco@flexvertex.com";
		String password = "Polo";

		IFlexAuthenticationToken userToken = new FlexUsernamePasswordToken(username, password);

		try(IFlexSession session = FlexMultiverse.openSession(driver, userToken))
		{
			try(IFlexSchema schema = session.openSchema("/Expedia/North-America/Social"))
			{
				FlexResultSet rs = schema.sql("select * from Person");

				rs.getObjects().forEach(obj ->
				{
					System.out.println("--- obj = " + obj.toJSON());
				});
			}
		}
	}
}