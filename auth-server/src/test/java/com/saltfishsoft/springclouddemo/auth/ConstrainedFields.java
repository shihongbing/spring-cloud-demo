package com.saltfishsoft.springclouddemo.auth;

import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.constraints.ResourceBundleConstraintDescriptionResolver;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.snippet.Attributes.key;
/**
 * Created by Shihongbing on 2020/6/22.
 */
public class ConstrainedFields {

    private final ConstraintDescriptions constraintDescriptions;

    public ConstrainedFields(Class<?> input) {

        ResourceBundleConstraintDescriptionResolver fallback = new ResourceBundleConstraintDescriptionResolver();
        this.constraintDescriptions = new ConstraintDescriptions(input, (constraint) -> {
            String message = (String) constraint.getConfiguration().get("message");
            if (message != null && !Pattern.compile("\\{(.*?)\\}").matcher(message).matches()) {
                return message;
            }
            return fallback.resolveDescription(constraint);
        });
    }

    public FieldDescriptor withPath(String path)
    {
        String value = StringUtils
                .collectionToDelimitedString(constraintDescriptions
                        .descriptionsForProperty(path), ". ");
        return fieldWithPath(path).attributes(key("constraints").value(value));
    }
}
