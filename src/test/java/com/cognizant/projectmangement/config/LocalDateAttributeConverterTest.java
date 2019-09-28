/**
 * 
 */
package com.cognizant.projectmangement.config;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.Date;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author CTS
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class LocalDateAttributeConverterTest {

	@InjectMocks
	LocalDateAttributeConverter localDateAttributeConverter;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testAttributeConverter() {
		assertNotNull(localDateAttributeConverter.convertToDatabaseColumn(LocalDate.now()));
		assertNull(localDateAttributeConverter.convertToDatabaseColumn(null));

		assertNotNull(localDateAttributeConverter.convertToEntityAttribute(Date.valueOf(LocalDate.now())));
		assertNull(localDateAttributeConverter.convertToEntityAttribute(null));
	}
}
