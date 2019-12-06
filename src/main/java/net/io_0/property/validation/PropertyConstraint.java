package net.io_0.property.validation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import net.io_0.property.Property;
import java.util.Arrays;

@RequiredArgsConstructor
@Getter
@ToString
public class PropertyConstraint<T> {
  private final Property<T> property;
  private final PropertyValidator<T> validator;

  public static <T> NameBoundPropertyConstraint<T> on(String propertyName, PropertyValidator<T> validator) {
    return model -> new PropertyConstraint<>(model.getProperty(propertyName), validator);
  }

  @SafeVarargs
  public static <T> NameBoundPropertyConstraint<T> on(String propertyName, PropertyValidator<T>... validators) {
    return model -> new PropertyConstraint<>(model.getProperty(propertyName), Arrays.stream(validators).reduce(PropertyValidator::and).get());
  }

  public Validation check() {
    return validator.apply(property);
  }
}
