/*
 * Copyright (c) 2008-2013 Sonatype, Inc.
 *
 * All rights reserved. Includes the third-party code listed at http://links.sonatype.com/products/nexus/pro/attributions
 * Sonatype and Sonatype Nexus are trademarks of Sonatype, Inc. Apache Maven is a trademark of the Apache Foundation.
 * M2Eclipse is a trademark of the Eclipse Foundation. All other trademarks are the property of their respective owners.
 */

package org.sonatype.nexus.plugins.capabilities.internal.validator;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import org.sonatype.nexus.plugins.capabilities.CapabilityDescriptorRegistry;
import org.sonatype.nexus.plugins.capabilities.CapabilityType;
import org.sonatype.nexus.plugins.capabilities.ValidationResult;
import org.sonatype.nexus.plugins.capabilities.Validator;
import org.sonatype.nexus.plugins.capabilities.support.validator.DefaultValidationResult;
import org.sonatype.sisu.goodies.i18n.I18N;
import org.sonatype.sisu.goodies.i18n.MessageBundle;

import com.google.inject.assistedinject.Assisted;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Validates capability configuration field is valid URI.
 *
 * @since 2.7
 */
public class UriValidator
    extends ValidatorSupport
    implements Validator
{
  private static interface Messages
      extends MessageBundle
  {
    @DefaultMessage("%s is a valid URI")
    String valid(String fieldLabel);

    @DefaultMessage("%s is not a valid URI")
    String invalid(String fieldLabel);

    @DefaultMessage("%s is not a valid URI (%s)")
    String invalidReason(String fieldLabel, String failure);
  }

  private static final Messages messages = I18N.create(Messages.class);

  private final String key;

  private final String label;

  @Inject
  public UriValidator(final Provider<CapabilityDescriptorRegistry> capabilityDescriptorRegistryProvider,
                      final @Assisted CapabilityType type,
                      final @Assisted String key)
  {
    super(capabilityDescriptorRegistryProvider, type);
    this.key = checkNotNull(key);
    this.label = propertyName(key);
  }

  @Override
  public ValidationResult validate(final Map<String, String> properties) {
    String value = properties.get(key);
    if (value != null) {
      try {
        new URI(value);
      }
      catch (URISyntaxException e) {
        return new DefaultValidationResult().add(key, messages.invalidReason(label, e.getMessage()));
      }
    }
    return ValidationResult.VALID;
  }

  @Override
  public String explainValid() {
    return messages.valid(label);
  }

  @Override
  public String explainInvalid() {
    return messages.invalid(label);
  }
}
