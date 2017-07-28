/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.tutorial.commons;

import org.apache.tapestry5.Link;

/**
 * IIntermediate page - implement by user login class
 * @author roma
 */
public interface IIntermediatePage {
    /**
	 * For redirect to some page
	*/
    void setNextPageLink(Link nextPageLink);

}
