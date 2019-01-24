/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.spring.initializr.actuate.autoconfigure;

import io.micrometer.core.instrument.MeterRegistry;
import io.spring.initializr.actuate.metric.ProjectGenerationMetricsListener;
import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.autoconfigure.metrics.CompositeMeterRegistryAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.FilteredClassLoader;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link InitializrMetricsAutoConfiguration}.
 *
 * @author Madhura Bhave
 */
public class InitializrMetricsAutoConfigurationTests {

	private ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(MetricsAutoConfiguration.class,
					CompositeMeterRegistryAutoConfiguration.class,
					InitializrMetricsAutoConfiguration.class));

	@Test
	public void autoConfigRegistersProjectGenerationMetricsListenerBean() {
		this.contextRunner.run((context) -> assertThat(context)
				.hasSingleBean(ProjectGenerationMetricsListener.class));
	}

	@Test
	public void autoConfigConditionalOnMeterRegistryClass() {
		this.contextRunner.withClassLoader(new FilteredClassLoader(MeterRegistry.class))
				.run((context) -> assertThat(context)
						.doesNotHaveBean(ProjectGenerationMetricsListener.class));
	}

}
