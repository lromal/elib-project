/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.tutorial.services;


import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.tapestry5.services.ComponentEventResultProcessor;
import org.apache.tapestry5.services.Response;


/**
 *
 * 
 */
public class OutputStreamResponseResultProcessor implements
                ComponentEventResultProcessor<OutputStreamResponse> {
    private static final int BUFFER_SIZE = 5000;

    private final Response response;

    public OutputStreamResponseResultProcessor(Response response) {
        this.response = response;
    }

    /**
     * Handles OutputStreamResponse
     *
     * @param Callback for streaming arbitrary data to the client using the response {@link OutputStream}
     *
     * @see ComponentEventResultProcessor#processResultValue(Object)
     */
     @Override
     public void processResultValue(OutputStreamResponse streamResponse)
                throws IOException {
        OutputStream out = null;
        try {
                streamResponse.prepareResponse(response);
                out = new BufferedOutputStream(response.getOutputStream(streamResponse.getContentType()), BUFFER_SIZE);
                streamResponse.writeToStream(out);
                out.flush();
                out.close();
                out = null;
        } finally {
                if(out != null) { //can only be the case if an Exception was thrown because out was set to null before
                        try {
                                out.close();
                        } catch(IOException ioe) {
                                //ignores this IO exception because an exception is already on the way
                        }
                }
        }
     }
}
