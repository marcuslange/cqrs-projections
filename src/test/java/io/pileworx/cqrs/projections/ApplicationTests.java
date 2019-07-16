package io.pileworx.cqrs.projections;

import io.pileworx.cqrs.projections.application.ShoppingCartService;
import io.pileworx.cqrs.projections.domain.command.AddLineItemCmd;
import io.pileworx.cqrs.projections.domain.command.CreateCartCmd;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;

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
		var cartDto = shoppingCartService.createCart(createCartCmd());

		assertThat(cartDto.getId(), is(not(nullValue())));
	}

	@Test
	public void canFindByProductName() {
		shoppingCartService.createCart(createCartCmd());

		var cartDtos = shoppingCartService.findByProductName("Gizmo");

		assertThat(cartDtos.size(), is(greaterThan(0)));
	}

	private CreateCartCmd createCartCmd() {
		return new CreateCartCmd(addLineItemCmd());
	}

	private AddLineItemCmd addLineItemCmd() {
		var ref = URI.create("http://www.product.com/myproduct");
		return new AddLineItemCmd(ref, 5, 5.25, "Gizmo");
	}
}
