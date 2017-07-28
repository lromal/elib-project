/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.tutorial.services;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.tapestry5.services.Response;

/**
 * Class for write file in server.
 * 
 */
public interface OutputStreamResponse {
    /**
     * Returns the content type to be reported to the client.
     */
    String getContentType();

    /**
     * Implements a callback to directly write to the output stream.
     * The stream will be closed after this method returns.
     * The provided stream is wrapped in a {@link BufferedOutputStream} for efficiency.
     */
    public void writeToStream(OutputStream out) throws IOException;

    /**
     * Prepares the response before it is sent to the client. This is the place to set any response headers (e.g.
     * content-disposition).
     *
     * @param response Response that will be sent.
     */
    void prepareResponse(Response response);
}
