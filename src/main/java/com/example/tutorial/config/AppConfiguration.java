package com.example.tutorial.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Set package for component scan
 *
 * @author bytesTree
 * @see <a href="http://www.bytestree.com/">BytesTree</a>
 *
 */
@Configuration
@ComponentScan(value = {"com.example.tutorial"})
public class AppConfiguration {

}
