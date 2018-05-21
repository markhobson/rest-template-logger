/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hobsoft.spring.resttemplatelogger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * {@code RestTemplateCustomizer} that configures the {@code RestTemplate} to log requests and responses.
 */
public class LoggingCustomizer implements RestTemplateCustomizer
{
	// ----------------------------------------------------------------------------------------------------------------
	// fields
	// ----------------------------------------------------------------------------------------------------------------
	
	private final Log log;
	
	private final LogFormatter formatter;
	
	// ----------------------------------------------------------------------------------------------------------------
	// constructors
	// ----------------------------------------------------------------------------------------------------------------
	
	public LoggingCustomizer()
	{
		this(LogFactory.getLog(LoggingCustomizer.class));
	}
	
	public LoggingCustomizer(Log log)
	{
		this(log, new DefaultLogFormatter());
	}
	
	public LoggingCustomizer(Log log, LogFormatter formatter)
	{
		this.log = log;
		this.formatter = formatter;
	}
	
	// ----------------------------------------------------------------------------------------------------------------
	// RestTemplateCustomizer methods
	// ----------------------------------------------------------------------------------------------------------------
	
	@Override
	public void customize(RestTemplate restTemplate)
	{
		restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(restTemplate.getRequestFactory()));
		restTemplate.getInterceptors().add(new LoggingInterceptor(log, formatter));
	}
}
