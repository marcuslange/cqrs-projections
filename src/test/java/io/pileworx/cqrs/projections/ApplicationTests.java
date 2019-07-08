package io.pileworx.cqrs.projections;

import io.pileworx.cqrs.projections.application.ShoppingCartService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	@Autowired
	private ShoppingCartService shoppingCartService;

	@Test
	public void contextLoads() {
		assertThat(shoppingCartService, is(not(nullValue())));
	}

	@Test
	public void canCreateAndReadCart() {

	}
}
