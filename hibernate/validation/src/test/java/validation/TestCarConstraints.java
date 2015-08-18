package validation;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import junit.framework.Assert;

import org.bgi.hibernate.validation.bean.Car;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestCarConstraints {
	
	private static Validator validator;
	
	@BeforeClass
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
	
	@Test
	public void testNullColor() throws Exception {
		Car c = new Car();
		c.setMake("Make");
		Set<ConstraintViolation<Car>> violations = validator.validate(c);
		Assert.assertEquals(1, violations.size());
		ConstraintViolation<Car> v1 = violations.iterator().next();
		
	}

}
