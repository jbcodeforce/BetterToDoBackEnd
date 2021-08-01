package jbcodeforce.app;

import java.util.Collections;
import java.util.Map;

import org.testcontainers.containers.PostgreSQLContainer;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

public class DatabaseResource implements QuarkusTestResourceLifecycleManager {

    public static final PostgreSQLContainer DATABASE = new PostgreSQLContainer<>("postgres")
            .withDatabaseName("bettertodo")
            .withUsername("pguser")
            .withPassword("passw0rd")
            .withExposedPorts(5432);

    @Override
    public Map<String, String> start() {
        DATABASE.start();
        return Collections.singletonMap("quarkus.datasource.reactive.url", DATABASE.getJdbcUrl().replace("jdbc:", ""));
    }

    @Override
    public void stop() {
        DATABASE.stop();
    }
}
