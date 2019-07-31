package io.pileworx.cqrs.projections;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Autowired
	@Qualifier("domainDataSource")
	private DataSource domainDataSource;

	@Autowired
	@Qualifier("projectionDataSource")
	private DataSource projectionDataSource;

	@PostConstruct
	public void initDomainDb() {
		var flyway = Flyway.configure()
				.dataSource(domainDataSource).locations("db/migration/domain")
				.load();
		flyway.migrate();
	}

	@PostConstruct
	public void initProjectionDb() {
		var flyway = Flyway.configure()
				.dataSource(projectionDataSource).locations("db/migration/projection")
				.load();
		flyway.migrate();
	}
}