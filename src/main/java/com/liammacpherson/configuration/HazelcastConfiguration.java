package com.liammacpherson.configuration;

import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.DiscoveryStrategyConfig;
import com.hazelcast.kubernetes.HazelcastKubernetesDiscoveryStrategyFactory;
import com.hazelcast.kubernetes.KubernetesProperties;
import com.hazelcast.spi.properties.ClusterProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

import static com.liammacpherson.utils.PropertyHelper.propertyEquals;

@Configuration
public class HazelcastConfiguration {

    private final static String HAZELCAST_SERVICE_NAME = "service-hazelcast-server.default.svc.cluster.local";

    @Bean
    public ClientConfig clientConfig() {
        final boolean isLocal = propertyEquals("IS_LOCAL", "true", "true");
        final ClientConfig clientConfig = new ClientConfig();

        if (isLocal) {
            // Default IP address for local docker run is 172.17.0.2.
            clientConfig
                    .getNetworkConfig()
                    .setAddresses(Collections.singletonList("172.17.0.2:5701"));
        } else {
            HazelcastKubernetesDiscoveryStrategyFactory hazelcastKubernetesDiscoveryStrategyFactory
                    = new HazelcastKubernetesDiscoveryStrategyFactory();
            DiscoveryStrategyConfig discoveryStrategyConfig =
                    new DiscoveryStrategyConfig(hazelcastKubernetesDiscoveryStrategyFactory);
            discoveryStrategyConfig.addProperty(KubernetesProperties.SERVICE_DNS.key(),
                    HAZELCAST_SERVICE_NAME);

            clientConfig.setProperty(ClusterProperty.DISCOVERY_SPI_ENABLED.toString(), "true");
            clientConfig
                    .getNetworkConfig()
                    .getDiscoveryConfig()
                    .addDiscoveryStrategyConfig(discoveryStrategyConfig);
        }

        return clientConfig;
    }
}
